import com.mongodb.{DB, Mongo}
import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{FeatureSpec, GivenWhenThen}


class Test extends FeatureSpec with GivenWhenThen with ShouldMatchers {
  feature("test") {
    scenario("test") {
      // http://www.assembla.com/wiki/show/liftweb/Mongo_Configuration
      MongoDB.defineDb(DefaultMongoIdentifier, new Mongo, "WhatToDo")

      val db: DB = MongoDB.getDb(DefaultMongoIdentifier).getOrElse(fail("A database should be defined for the default identifier"))
      db.getCollectionNames() should not be ('empty)

      val test = Event.find("name", "Partons pour la Chine")
      println(test)
    }
  }
}