import net.liftweb.mongodb.record.field.{BsonRecordListField, BsonRecordField, ObjectIdPk}
import net.liftweb.mongodb.record.{BsonMetaRecord, BsonRecord, MongoMetaRecord, MongoRecord}
import net.liftweb.record.field.{DateTimeField, IntField, StringField}
import org.bson.types.ObjectId
import com.foursquare.rogue.Rogue._
import org.joda.time.DateTime
import java.util.regex.Pattern


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
    Event where (_.id eqs eventId) modify (_.sessions push newSession) updateOne()
  }

  def renameByDate(date: DateTime, newName: String) {
    val startDate = date.withTime(0, 0, 0, 0)
    val endDate = startDate.plusDays(1)
    Event where (_.sessions.subfield(_.showDateTimeStart) between(startDate, endDate)) modify (_.name setTo newName) updateMulti()
  }

  def findLast10By(town: String, descriptionContains: String) = {
    Event where (_.venue.subfield(_.town) eqs town) and (_.description matches Pattern.compile(".*" + descriptionContains + ".*")) limit (10) orderDesc (_.updated) fetch()
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