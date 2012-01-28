import com.mongodb.{Mongo, ServerAddress}
import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}
import com.foursquare.rogue.Rogue._
import org.bson.types._
import net.liftweb.json.JsonDSL._

val mongoHost = "localhost"
val mongoPort = 27017
val mongoDbName = "test"

val mongoAddress = new ServerAddress(mongoHost, mongoPort)
MongoDB.defineDb(DefaultMongoIdentifier, new Mongo(mongoAddress), mongoDbName)

import examples.lift._
