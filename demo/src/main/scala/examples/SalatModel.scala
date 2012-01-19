package examples

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
  name: String,
  comment: String
)
*/
