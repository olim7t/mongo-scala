import net.liftweb.mongodb.record.field.{BsonRecordListField, BsonRecordField, ObjectIdPk}
import net.liftweb.mongodb.record.{BsonMetaRecord, BsonRecord, MongoMetaRecord, MongoRecord}
import net.liftweb.record.field.{DateTimeField, IntField, StringField}
import org.bson.types.ObjectId
import org.joda.time.DateTime


class Event private() extends MongoRecord[Event] with ObjectIdPk[Event] {
  def meta = Event

  object name extends StringField(this, 100)

  object description extends StringField(this, 2048)

  object sessions extends BsonRecordListField(this, EventSession)

  object venue extends BsonRecordField(this, Venue)

  object updated extends DateTimeField(this)

}

object Event extends Event with MongoMetaRecord[Event] {
  def addSession(eventId: ObjectId, newSession: EventSession) {
    // TODO perform an update query with Rogue (the operation must be atomic, don't do a load followed by a save)
    // See https://github.com/foursquare/rogue
    // and a lot of query examples here:
    // https://github.com/foursquare/rogue/blob/master/src/test/scala/com/foursquare/rogue/QueryTest.scala
    throw new AssertionError("TODO")
  }

  def renameByDate(date: DateTime, newName: String) {
    // select any hour of this day
    val startDate = date.withTime(0, 0, 0, 0)
    val endDate = startDate.plusDays(1)

    // TODO perform a range query on sessions.showDateTimeStart (use the subfield() method)
    throw new AssertionError("TODO")
  }

  def findLast10By(town: String, descriptionContains: String) = {
    // TODO use a regexp for the description (Rogue uses java.util.regex.Pattern)
    // dont' forget to sort by date descending
    throw new AssertionError("TODO")
  }
}

class EventSession private() extends BsonRecord[EventSession] {
  def meta = EventSession

  object showDateTimeStart extends DateTimeField(this)

}

object EventSession extends EventSession with BsonMetaRecord[EventSession]

class Venue private() extends BsonRecord[Venue] {
  def meta = Venue

  object name extends StringField(this, 100)

  object way extends StringField(this, 256)

  object pCode extends StringField(this, 256)

  object town extends StringField(this, 256)

  object transports extends BsonRecordField(this, Transports)

}

object Venue extends Venue with BsonMetaRecord[Venue]

class Transports private() extends BsonRecord[Transports] {
  def meta = Transports

  object stations extends BsonRecordListField(this, Station)

}

object Transports extends Transports with BsonMetaRecord[Transports]

class Station private() extends BsonRecord[Station] {
  def meta = Station

  object stationType extends StringField(this, 100) {
    override def name = "type"
  }

  object ligne extends StringField(this, 100)

  object name extends StringField(this, 100)

  object distance extends IntField(this)

}

object Station extends Station with BsonMetaRecord[Station]