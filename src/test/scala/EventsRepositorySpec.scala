import com.mongodb.casbah.Imports._

class EventsRepositorySpec extends SpecBase {

  feature("The driver can connect to a running MongoDB instance") {

    scenario("the presence of collections in the WhatToDo database is checked") {

      given("the connection data")
      val host = "localhost"
      val port = 27017

      when("connecting to the WhatToDo database")
      val mongoDB = MongoConnection(host, port)("WhatToDo")

      then("it should contain some collections")
      assert(!mongoDB.collectionNames.isEmpty)
    }
  }
}
