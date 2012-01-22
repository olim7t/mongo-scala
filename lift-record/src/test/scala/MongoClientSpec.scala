import com.mongodb.{DB, Mongo}
import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{FeatureSpec, GivenWhenThen}
import com.foursquare.rogue.Rogue._


class MongoClientSpec extends SpecBase {
  feature("The application can act as a client of a MongoDB instance") {

    scenario("The client connects to the instance and checks that it contains some collections") {
      given("the default database configured in Lift")
      val db: DB = defaultDb

      then("it contains some collections")
      db.getCollectionNames() should not be ('empty)
    }
  }

  def defaultDb = MongoDB.getDb(DefaultMongoIdentifier).getOrElse(fail("A database should be defined for the default identifier"))
  // NB the database is set up in class `Boot`
}