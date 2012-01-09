import com.mongodb.{DB, Mongo}
import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{FeatureSpec, GivenWhenThen}
import com.foursquare.rogue.Rogue._


class ModelSpec extends SpecBase {
  feature("test") {
    scenario("test") {
      val query = Event where (_.name eqs "Partons pour la Chine")
      val test = query.fetch().headOption.getOrElse(fail("there should be at least one event with this name"))
      println(test)
    }
  }
}