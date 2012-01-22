package examples.lift

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field._

class GeekToy private() extends MongoRecord[GeekToy] with ObjectIdPk[GeekToy] {
  def meta = GeekToy

  object brand extends StringField(this, 20)
  object model extends StringField(this, 40)
  object price extends IntField(this)
  object reviews extends BsonRecordListField(this, Review)
}

object GeekToy extends GeekToy with MongoMetaRecord[GeekToy]



class Review private() extends BsonRecord[Review] {
  def meta = Review

  object author extends StringField(this, 40)
  object comment extends StringField(this, 100)
}

object Review extends Review with BsonMetaRecord[Review]
