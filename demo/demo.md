# Casbah

## Setup connection

In order to have a preconfigured REPL, I've created an SBT project (`demo`).
`demo/build.sbt`: the dependencies are listed here, as well as all project classes, are put in the REPL's classpath.

`casbah.scala`: I've prepared this to avoid typing everything in the console, I'll uncomment the lines progressively and reload it.
First 3 lines: import Casbah types and create a connection to a `test` database.

    sbt
    console
    :l casbah.scala

(NB: SLF4J raises a warning because no appender is configured; for our console session this does not matter)

NB: the connection has not been established yet, only when we first do something with the database.

Start MongoDB daemon:

    mongod

From the REPl, find the available collections (returns an empty set):

    db.collectionNames

## Create a first document from Scala

    val s2 = MongoDBObject("brand" -> "Samsung", "model" -> "Galaxy SII", "price" -> 530)
    db("products").save(s2)
    
    // NB: driver has set the id:
    s2
    
    // And we now have some collections in the database:
    db.collectionNames

## Check the result in Mongo

In a new console:

    mongo
    use test
    db.products.find()

## The other way around: insert from Mongo and load in Scala

In Mongo console:

    db.products.save( { brand: "Apple", model: "iPhone 4S", price: NumberInt(630) } )
    db.products.find()

(NB: in Javascript, numbers are floats by default, we need to explicitely ask for an integer.)

And check in Scala console (btw, this also demonstrates how to perform a query):

    val dbo = db("products").find("price" $gt 600).next

## Access object properties:

    dbo.get("model")
    dbo.get("price")
    
    // NB: result is not typed. To force conversion:
    dbo.as[String]("model")
    dbo.as[Int]("price")
    
    // of course we get a failure if we specify the wrong type:
    dbo.as[String]("price")
    
    // and also with a property name that does not exist:
    dbo.as[String]("color")
    
    // however, we can handle optional properties:
    dbo.getAs[String]("model")
    dbo.getAs[String]("color")

DB objects are actually maps:

    // Since I got this from a request, it's not wrapped in a MongoDBObject
    dbo.asInstanceOf[java.util.Map[String, Object]]

In Scala, we'll probably want stronger typing, by mapping the documents to some Scala types.

The first option is to map to case classes, using Salat.

# Salat

## The model

Gotchas:

* Salat won't work with classes defined in the Scala REPL (due to the fact that it uses ScalaSig pickled signatures - type information stored
  in the bytecode to avoid the cost of reflexion, somewhat similar to paranamer) => won't work with the Cake pattern either
* it doesn't work with the default package either.

As a workaround, I've defined the classes in a source file in my project.

[`SalatModel.scala`] show and explain class structure, in particular `Option` and default value for `_id` (ignore commented parts for now).

## From DB objects to case classes

[repl] try mapping the object that we previously loaded from the database:

    :l salat.scala
    
    val dbo = db("products").find("price" $gt 600).next
    val iphone = grater[Product].asObject(dbo)
    iphone.brand
    iphone.price

## From case classes to DB objects

Now try to go the other way - case class to DB object:

    val nexus = Product("Samsung", "Galaxy Nexus", 600)
    // NB: having _id with a default value is handy to create not-yet persisted instances
    
    grater[Product].asDBObject(nexus)

Before we save the object to the database, there is a small detail we need to fix.

*Q*: do you notice anything in particular in the output?
*A*: Salat has added a `typeHint` field to the object. This is the default behavior, but in fact the type hint is only necessary when:
* using subcollections typed to a trait or abstract superclass
* looking up a `grater` from a `DBObject` (not a type)

We will change the configuration to use hints only when necessary.

[`salat.scala`] uncomment custom context.

Now the previous example doesn't produce an extra property. Let's save the object.

## Saving

[`salat.scala`] uncomment `save` method and explain it. NB: case classes are immutable, which will probably lead to a more functional design

    val nexus2 = save(nexus)
    // returns a copy with the id set - nexus2 maps the persisted object
    
    // To change the price, make a copy and save it.
    val nexus3 = nexus2.copy(price = 550)
    // Since the id is set, MongoDB will update the existing record.
    save(nexus3)
    
    // Show that we have three different objects corresponding to the successive states:
    nexus
    nexus2
    nexus3

## Subdocuments

[`SalatModel.scala`] uncomment reviews property and corresponding case class.

[sbt]

    :q
    console
    :l salat.scala
    val ipad = Product("Apple", "iPad 3", 800, List(Review("Olivier", "Will it blend?")))
    save(ipad)

Check in Mongo => OK

## Mapping query results

To conclude, we'll load all the objects created so far and convert them to case classes:

    db("products").find().toSeq.map(grater[Product].asObject(_)).mkString("\n")

But this fails.

*Q*: do you see why?
*A*: we have some documents without a `reviews` property in the database.

[`SalatModel.scala`] add a default value of `List()` for `reviews` (I also tried changing the type to `Option[List[Review]]`, but then Salat would deserialize to plain DB objects).



# Lift

[`LiftModel.scala`] Lift-record version of our two model objects.
* class definition that defines the fields
* companion object for global methods

[`lift.scala`] imports and connection initialization

## Load a record

[sbt]

    :q
    console
    :l lift.scala
    val ipad = Product.findAll("model" -> "iPad 3").head

Access values:

    ipad.price
    // returns a custom type. to get to the actual value:
    val price: Int = ipad.price.is

Record handles missing properties (sets default values):

    db.products.save( { "brand": "Dell" } )

    val dell = (Product where (_.brand eqs "Dell") fetch).head
    dell.model.is
    dell.price.is

Records are well integrated in Lift's ecosystem (support for validation, generating forms, integration in wizards, etc.).

## Create and save a record

    val kindle = Product.createRecord.
      brand("Amazon").
      model("Kindle").
      price(100)
    kindle.save

## Modify a record (objects are mutable)

    val kindleReview = Review.createRecord.
      author("Olivier").
      comment("Great")
    kindle.reviews.set(List(kindleReview))
    kindle.save

# Rogue

Until now, all of our requests were not type safe:

    Product.findAll("model" -> "Kindle")
    // if there is a mistake on a field name, we simply get no results:
    Product.findAll("name" -> "Kindle")

Rogue allows us to do this:

    Product where (_.model eqs "Kindle") fetch

    // this time if we make a mistake we get a compile error
    Product where (_.name eqs "Kindle") fetch

More elaborate queries:

    Product where (_.reviews.subfield(_.author) eqs "Olivier") fetch
    // AFAIK, this only works for one level of subfield
    
    Product where (_.brand eqs "Apple") modify (_.price setTo 400) updateMulti

