import com.mongodb.Mongo
import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}
import com.foursquare.rogue.Rogue._

MongoDB.defineDb(DefaultMongoIdentifier, new Mongo, "test")

