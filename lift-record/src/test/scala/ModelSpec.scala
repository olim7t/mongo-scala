import collection.immutable.List
import java.util.{Calendar, GregorianCalendar}
import com.foursquare.rogue.Rogue._


class ModelSpec extends SpecBase {
  feature("Objects can be created, searched and removed") {

    scenario("An event is created") {
      given("a new event")
      val event: Event = buildTestEvent
      and("the current number of events")
      val countBeforeSave = Event.count

      when("the event is saved")
      event.save

      then("the number of events is incremented")
      Event.count should equal (countBeforeSave + 1)
    }
  }

  feature("test") {
    scenario("test") {
      val query = Event where (_.name eqs "Partons pour la Chine")
      val test = query.fetch().headOption.getOrElse(fail("there should be at least one event with this name"))
      println(test)
    }
  }


  private def buildTestEvent: Event = {
    val sessions: List[EventSession] = List(
      EventSession.createRecord.showDateTimeStart(new GregorianCalendar(2011, Calendar.DECEMBER, 30, 12, 0)),
      EventSession.createRecord.showDateTimeStart(new GregorianCalendar(2012, Calendar.JANUARY, 6, 12, 0)))

    Event.createRecord.
      name("Danse OukaOuka").
      description("Everybody dancing UkaUka").
      sessions(sessions).
      venue(Venue.createRecord.name("Palais Royal").way("22 rue de David Feta").pCode("75001").town("Paris")).
      updated(new GregorianCalendar())
  }

}