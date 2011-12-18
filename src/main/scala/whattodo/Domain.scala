package whattodo

import com.novus.salat.annotations.raw.Key
import org.bson.types.ObjectId
import org.joda.time.DateTime
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._

// Domain objects
// Note: they need to be declared outside of the Domain trait because Salat doesn't serialize nested classes
case class Event(name: String, description: String, sessions: List[EventSession], venue: Venue, updated: DateTime, _id: Option[ObjectId] = None)

case class EventSession(showDateTimeStart: DateTime)

case class Venue(name: String, way: String, pCode: String, town: String, transports: Option[Transports] = None)

case class Transports(stations: List[Station])

case class Station(@Key("type") stationType: String, ligne: String, name: String, distance: Int)


trait Domain {
  this: MongoClient =>

  val eventRepository: EventRepository

  class EventRepository {

    def findById(id: ObjectId): Option[Event] = mongoDb("events").findOneByID(id).map(dbToEvent)

    def save(event: Event): Event = {
      val dbObject = eventToDb(event)
      mongoDb("events").save(dbObject)
      dbToEvent(dbObject)
    }

    def removeById(id: ObjectId) = mongoDb("events").remove(MongoDBObject("_id" -> id))

    def count = mongoDb("events").size

    private def eventToDb(event: Event): DBObject = grater[Event].asDBObject(event)

    private def dbToEvent(dbObject: DBObject): Event = grater[Event].asObject(dbObject)
  }

}