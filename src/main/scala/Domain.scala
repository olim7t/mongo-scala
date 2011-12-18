import com.mongodb.casbah.Imports._
import com.novus.salat.annotations.raw.Key
import org.bson.types.ObjectId
import org.joda.time.DateTime
import com.novus.salat._
import com.novus.salat.global._

// Our domain objects
// Note: these classes need to be declared outside of the Domain trait, otherwise Salat can't read the pickled Scala
// signature from the class file
case class Event(name: String, description: String, sessions: List[EventSession], venue: Venue, updated: DateTime, _id: Option[ObjectId] = None)

case class EventSession(showDateTimeStart: DateTime)

case class Venue(name: String, way: String, pCode: String, town: String, transports: Option[Transports] = None)

case class Transports(stations: List[Station])

case class Station(@Key("type") stationType: String, ligne: String, name: String, distance: Int)


trait Domain {
  this: MongoClient =>

  val eventRepository: EventRepository

  // The repository implementation:
  class EventRepository {

    def save(event: Event): Event = {
      val dbObject = grater[Event].asDBObject(event)
      mongoDb("events").save(dbObject)
      grater[Event].asObject(dbObject)
    }

    def count = mongoDb("events").size
  }

}