package whattodo

import com.mongodb.casbah.{MongoConnection, MongoDB}

trait MongoClient {
  this: Configuration =>

  val mongoDb: MongoDB

  def connect(): MongoDB = {

    throw new AssertionError("TODO")
    // Create a database connection using the information provided in the Configuration trait.
    // Also, register the converters for Joda Time.
    // See http://api.mongodb.org/scala/casbah/2.1.5.0/tutorial.html
  }
}