import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{GivenWhenThen, FeatureSpec}

/**
 * Parent type for unit tests.
 * Note : had to make this a class, apparently child classes don't inherit the annotations of a trait.
 */
@RunWith(classOf[JUnitRunner])
class SpecBase extends FeatureSpec with GivenWhenThen