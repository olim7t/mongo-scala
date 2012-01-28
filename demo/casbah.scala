import com.mongodb.casbah.Imports._

val mongoHost = "localhost"
val mongoPort = 27017
val mongoDbName = "test"

val connection = MongoConnection(mongoHost, mongoPort)
val db = connection(mongoDbName)
