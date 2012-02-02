package models

import org.bson.types.ObjectId
import com.mongodb.casbah.query.Imports._
import com.mongodb.casbah.Imports.WriteConcern
import com.mongodb.casbah.{MongoConnection, MongoDB}
import com.novus.salat._
import com.novus.salat.global._

case class Product(
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

object ProductRepository {
  private val products = MongoConnection()("test")("products")

  def all: Seq[Product] = products.find().map(fromDb).toSeq

  def byId(id: ObjectId): Option[Product] = products.findOneByID(id).map(fromDb)

  def save(product: Product) = {
    val dbo = toDb(product)
    products.save(dbo, WriteConcern.Safe)
    product._id match {
      case Some(_) => product
      case None =>
        val newId = dbo.as[ObjectId]("_id")
        product.copy(_id = Some(newId))
    }
  }

  def removeById(id: ObjectId) = products.remove(MongoDBObject("_id" -> id))

  // Customize Salat context
  implicit val ctx = new Context {
    val name = "Custom Context"
    override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.WhenNecessary)
  }

  private def fromDb(dbObject: DBObject): Product = grater[Product].asObject(dbObject)
  private def toDb(product: Product): DBObject = grater[Product].asDBObject(product)
}
