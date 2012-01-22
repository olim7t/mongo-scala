package examples.salat

import org.bson.types.ObjectId

case class GeekToy(
  brand: String,
  model: String,
  price: Int,
//  reviews: List[Review],
  _id: Option[ObjectId] = None
)

/*
case class Review(
  author: String,
  comment: String
)
*/
