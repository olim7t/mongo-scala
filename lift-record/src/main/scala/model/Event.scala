import net.liftweb.mongodb.record.field.{BsonRecordListField, BsonRecordField, ObjectIdPk}
import net.liftweb.mongodb.record.{BsonMetaRecord, BsonRecord, MongoMetaRecord, MongoRecord}
import net.liftweb.record.field.{IntField, StringField}

class Event private() extends MongoRecord[Event] with ObjectIdPk[Event] {
  def meta = Event

  object name extends StringField(this, 100)

  object description extends StringField(this, 2048)

  object venue extends BsonRecordField(this, Venue)

}

object Event extends Event with MongoMetaRecord[Event]

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