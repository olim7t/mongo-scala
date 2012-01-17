package examples

import org.bson.types.ObjectId

case class GeekToy(brand: String, model: String, price: Int, _id: Option[ObjectId] = None)
