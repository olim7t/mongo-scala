import com.mongodb.casbah.{MongoConnection, MongoDB}

trait MongoClient {
  this: Configuration =>

  val mongoDb: MongoDB

  def connect() = {
    val mongoConnection = MongoConnection(mongoHost, mongoPort)
    mongoConnection(mongoDbName)
  }
}