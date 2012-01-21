import com.mongodb.Mongo
import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}
import com.foursquare.rogue.Rogue._
import org.bson.types._
import net.liftweb.json.JsonDSL._

MongoDB.defineDb(DefaultMongoIdentifier, new Mongo, "test")

import examples.lift._
