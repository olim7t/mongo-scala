package models

import org.bson.types.ObjectId
import com.mongodb.casbah.query.Imports._
import com.mongodb.casbah.Imports.WriteConcern
import com.mongodb.casbah.{MongoConnection, MongoDB}
import com.novus.salat._
import com.novus.salat.global._

case class GeekToy(
  brand: String,
  model: String,
  price: Int,
  reviews: List[Review] = List(),
  _id: Option[ObjectId] = None
)

case class Review(
  author: String,
  comment: String
)

object GeekToyRepository {
  private val geekToys = MongoConnection()("test")("geektoys")

  def all: Seq[GeekToy] = geekToys.find().map(fromDb).toSeq

  def byId(id: ObjectId): Option[GeekToy] = geekToys.findOneByID(id).map(fromDb)

  def save(geekToy: GeekToy) = {
    val dbo = toDb(geekToy)
    geekToys.save(dbo, WriteConcern.Safe)
    geekToy._id match {
      case Some(_) => geekToy
      case None =>
        val newId = dbo.as[ObjectId]("_id")
        geekToy.copy(_id = Some(newId))
    }
  }

  def removeById(id: ObjectId) = geekToys.remove(MongoDBObject("_id" -> id))

  // Customize Salat context
  implicit val ctx = new Context {
    val name = "Custom Context"
    override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.WhenNecessary)
  }

  private def fromDb(dbObject: DBObject): GeekToy = grater[GeekToy].asObject(dbObject)
  private def toDb(geekToy: GeekToy): DBObject = grater[GeekToy].asDBObject(geekToy)
}
