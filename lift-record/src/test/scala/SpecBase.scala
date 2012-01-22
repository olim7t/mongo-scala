import model.Boot
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{FeatureSpec, GivenWhenThen}


class SpecBase extends FeatureSpec with GivenWhenThen with ShouldMatchers {
  Boot.defineDB
}