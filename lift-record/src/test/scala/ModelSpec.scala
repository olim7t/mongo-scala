import collection.immutable.List
import java.util.{Calendar, GregorianCalendar}
import com.foursquare.rogue.Rogue._
import net.liftweb.common.{Box, Failure, Empty, Full}
import org.bson.types.ObjectId


class ModelSpec extends SpecBase {
  feature("Objects can be created, searched and removed") {

    scenario("An event is created") {
      given("a new event")
      val event: Event = newTestEvent
      and("the current number of events")
      val countBeforeSave = Event.count

      when("the event is saved")
      event.save

      then("the number of events is incremented")
      Event.count should equal(countBeforeSave + 1)
    }

    scenario("An event is searched by id") {
      given("the id of a persisted event")
      val originalEvent: Event = newTestEvent
      originalEvent.save
      val id = originalEvent.id.value

      when("looking for the event with this id")
      val searchResult = Event.find(id)

      then("we get back the same event")
      searchResult match {
        case Empty => fail("saved event not found")
        case Failure(msg, _, _) => fail("unexpected error: " + msg)
        case Full(e) => e.name.value should equal(originalEvent.name.value)
      }
    }

    scenario("An event is removed") {
      given("the id of a persisted event")
      val event: Event = newTestEvent
      event.save
      val id = event.id.value
      and("the current number of events")
      val countBeforeDelete = Event.count

      when("the event with this id is removed")
      event.delete_!

      then("the number of events is decremented")
      Event.count should equal(countBeforeDelete - 1)
    }
  }

  feature("Updates can be performed directly in the database") {
    scenario("A session is added to an existing event") {
      given("the id of an existing event")
      val id = new ObjectId("4e9d4420f7dbcc6b4f9711b5")
      and("the number of sessions of that event")
      val sessionCountBefore = countSessions(eventId = id)

      when("a new session is added to this event")
      Event where (_.id eqs id) modify (_.sessions push newTestSession) updateOne()

      then("its session count is incremented")
      val sessionCountAfter = countSessions(eventId = id)
      sessionCountAfter should equal(sessionCountBefore + 1)
    }

    scenario("The name of all the events at a given date is updated") {
    }

  }

  feature("test") {
    scenario("test") {
      val query = Event where (_.name eqs "Partons pour la Chine")
      val test = query.fetch().headOption.getOrElse(fail("there should be at least one event with this name"))
      println(test)
    }
  }


  private def newTestEvent: Event = {
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

  private def newTestSession = EventSession.createRecord.showDateTimeStart(new GregorianCalendar(2012, Calendar.MARCH, 10, 12, 0))

  private def countSessions(eventId: ObjectId): Int = Event.find(eventId).
    flatMap(_.sessions.valueBox).
    map(_.size).
    getOrElse(0)
}