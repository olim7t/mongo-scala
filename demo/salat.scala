import com.mongodb.casbah.Imports._

val connection = MongoConnection()
val db = connection("test")

import com.novus.salat._
import com.novus.salat.global._

import examples.salat._

/*
implicit val ctx = new Context {
  val name = "Custom Context"
  override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.WhenNecessary)
}
*/

def save(product: Product) = {
  val dbo = grater[Product].asDBObject(product)
  db("products").save(dbo, WriteConcern.Safe)
  product._id match {
    case Some(_) => product
    case None =>
      val newId = dbo.as[ObjectId]("_id")
      product.copy(_id = Some(newId))
  }
}
