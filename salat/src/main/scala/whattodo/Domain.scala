package whattodo

import com.novus.salat.annotations.raw.Key
import org.bson.types.ObjectId
import org.joda.time.DateTime
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.query.Imports._

// Domain objects
// Note: they need to be declared outside of the Domain trait because Salat doesn't serialize nested classes
case class Event(name: String, description: String, sessions: List[EventSession], venue: Venue, updated: DateTime, _id: Option[ObjectId] = None)

case class EventSession(showDateTimeStart: DateTime)

case class Venue(name: String, way: String, pCode: String, town: String, transports: Option[Transports] = None)

case class Transports(stations: List[Station])

case class Station(@Key("type") stationType: Option[String], ligne: String, name: String, distance: Int)


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

    def countWithName(name: String) = mongoDb("events").count(MongoDBObject("name" -> name))

    def addSessionToEvent(eventId: ObjectId, session: EventSession) {
      val dbObject = sessionToDb(session)
      mongoDb("events").update(MongoDBObject("_id" -> eventId), $push("sessions" -> dbObject))
    }

    def renameByDate(date: DateTime, newName: String) {
      val startDate = date.withTime(0, 0, 0, 0)
      val endDate = startDate.plusDays(1)
      mongoDb("events").updateMulti(("sessions.showDateTimeStart" $gt startDate $lt endDate), $set("name" -> newName))
    }

    def findLast10By(town: String, descriptionContains: String) = {
      val descriptionRegexp = (".*" + descriptionContains + ".*").r
      val criteria =
        MongoDBObject("venue.town" -> town, "description" -> descriptionRegexp) ++
          ("sessions" $exists true) ++
          ("updated" $exists true)

      mongoDb("events").find(criteria).
        sort(MongoDBObject("updated" -> -1)).
        limit(10).
        map(dbToEvent);
    }

    private def eventToDb(event: Event): DBObject = grater[Event].asDBObject(event)

    private def dbToEvent(dbObject: DBObject): Event = grater[Event].asObject(dbObject)

    private def sessionToDb(session: EventSession): DBObject = grater[EventSession].asDBObject(session)
  }

}