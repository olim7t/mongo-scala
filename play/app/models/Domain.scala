package models

import com.novus.salat.annotations.raw.Key
import org.bson.types.ObjectId
import org.joda.time.DateTime
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.query.Imports._
import com.mongodb.casbah.Imports.WriteConcern

import org.bson.types.ObjectId

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

trait Domain {
  this: MongoClient =>

  val geekToyRepository: GeekToyRepository

  class GeekToyRepository {
    private def geekToys = mongoDb("geektoys")

    def all() = geekToys.find().map(fromDb)

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

    private def fromDb(dbObject: DBObject): GeekToy = grater[GeekToy].asObject(dbObject)
    private def toDb(geekToy: GeekToy): DBObject = grater[GeekToy].asDBObject(geekToy)
  }
}
