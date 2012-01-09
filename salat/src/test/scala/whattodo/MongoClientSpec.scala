package whattodo

class MongoClientSpec extends SpecBase with Configuration with MongoClient {

  val mongoHost = "localhost"
  val mongoPort = 27017
  val mongoDbName = "WhatToDo"

  val mongoDb = connect()

  feature("The application can act as a client of a MongoDB instance") {

    scenario("The client connects to the instance and checks that it contains some collections") {
      when("Connected to the database (performed above)")

      then("The database contains some collections")
      assert(!mongoDb.collectionNames.isEmpty)
    }
  }
}
