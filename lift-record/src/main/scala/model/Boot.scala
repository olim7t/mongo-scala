package model

import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}
import com.mongodb.Mongo
import java.lang.AssertionError


/** Bootstrap class to initialize the MongoDB connection */
object Boot {
  def defineDB {
    // TODO configure the connection to MongoDB and associate it to the default identifier
    // see http://www.assembla.com/wiki/show/liftweb/Mongo_Configuration
    throw new AssertionError("TODO")
  }
}