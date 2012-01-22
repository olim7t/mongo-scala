package whattodo

import com.novus.salat.annotations.raw.Key
import org.bson.types.ObjectId
import org.joda.time.DateTime
import com.novus.salat._
import com.mongodb.casbah.query.Imports._
import com.mongodb.casbah.WriteConcern

// Domain objects
// Note: they need to be declared outside of the Domain trait because Salat doesn't serialize nested classes
case class Event(name: String, description: String, sessions: List[EventSession], venue: Venue, updated: DateTime, _id: Option[ObjectId] = None)

case class EventSession(showDateTimeStart: DateTime)

case class Venue(name: String, way: String, pCode: String, town: String, transports: Option[Transports] = None)

case class Transports(stations: List[Station])

case class Station(@Key("type") stationType: Option[String], ligne: String, name: String, distance: Int)


trait Domain {
  this: MongoClient =>


  // This prevents Salat from adding type hints in the serialized documents except when it' strictly necessary.
  // See https://github.com/novus/salat/wiki/TypeHints
  implicit val ctx = new Context {
    val name = "Custom Context"
    override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.WhenNecessary)
  }

  val eventRepository: EventRepository

  class EventRepository {

    // README the database connection is injected through the MongoClient trait.
    // In this class, you'll perform operations on the "events" collection.
    // See http://api.mongodb.org/scala/casbah/2.1.5.0/tutorial.html for instructions on how to retrieve a collection
    // from the connection.

    // TODO call the appropriate method on the events collection to retrieve a document by id, then map the result
    // using dbToEvent (defined below)
    // See MongoCollection's Scaladoc at http://api.mongodb.org/scala/casbah/current/scaladoc
    def findById(id: ObjectId): Option[Event] = None

    // TODO convert the case class to a DB object with eventToDB (defined below)
    // Call the appriopriate method on the "events" collection to save the object.
    // The method should return a copy of its input with the id set.
    def save(event: Event): Event = {
      throw new AssertionError("TODO")
    }

    // TODO call a method on the "events" collection to remove the object.
    // hint: there is no specific method to retrieve by id, you'll need to build a db object and pass it
    def removeById(id: ObjectId) = throw new AssertionError("TODO")

    // TODO return the number of documents in the "events" collection
    def count = throw new AssertionError("TODO")

    // TODO same as above, but restrict the count by passing a db object
    def countWithName(name: String) = throw new AssertionError("TODO")

    // TODO convert the session to a db object and add it to the event
    // NB - the operation should be atomic, no loading / saving back the event. Use an update query.
    def addSessionToEvent(eventId: ObjectId, session: EventSession) {
      throw new AssertionError("TODO")
    }

    def renameByDate(date: DateTime, newName: String) {
      // We consider any hour during the day, so you'll need to perform a range query:
      val startDate = date.withTime(0, 0, 0, 0)
      val endDate = startDate.plusDays(1)

      // TODO perform a query on sessions.showDateTimeStart and change the name in-place (again, the operation should be atomic)
      throw new AssertionError("TODO")
    }

    def findLast10By(town: String, descriptionContains: String) = {
      // TODO use a regexp to express the "contains" (to simplify things, we keep it case-sensitive)
      // NB: some documents in the database don't have an 'updated' field, add a query condition to ignore them
      throw new AssertionError("TODO")
    }

    // TODO use Salat to serialize case classes back / from DB objects
    // See https://github.com/novus/salat/wiki/Quick-start
    private def eventToDb(event: Event): DBObject = throw new AssertionError("TODO")

    private def dbToEvent(dbObject: DBObject): Event = throw new AssertionError("TODO")

    private def sessionToDb(session: EventSession): DBObject = throw new AssertionError("TODO")
  }

}