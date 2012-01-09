import com.mongodb.{DB, Mongo}
import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{FeatureSpec, GivenWhenThen}
import com.foursquare.rogue.Rogue._


class SpecBase extends FeatureSpec with GivenWhenThen with ShouldMatchers {
  // http://www.assembla.com/wiki/show/liftweb/Mongo_Configuration
  MongoDB.defineDb(DefaultMongoIdentifier, new Mongo, "WhatToDo")
}