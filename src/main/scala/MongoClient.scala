import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers
import com.mongodb.casbah.{MongoConnection, MongoDB}

trait MongoClient {
  this: Configuration =>

  val mongoDb: MongoDB

  def connect() = {
    RegisterJodaTimeConversionHelpers()

    val mongoConnection = MongoConnection(mongoHost, mongoPort)
    mongoConnection(mongoDbName)
  }
}