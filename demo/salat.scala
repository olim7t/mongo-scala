import com.mongodb.casbah.Imports._

val connection = MongoConnection()
val db = connection("test")

import com.novus.salat._
import com.novus.salat.global._

import examples.salat._

/*
implicit val ctx = new Context {
  val name = "Custom Context"
  override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.WhenNecessary)
}

def save(geekToy: GeekToy) = {
  val dbo = grater[GeekToy].asDBObject(geekToy)
  db("geektoys").save(dbo, WriteConcern.Safe)
  geekToy._id match {
    case Some(_) => geekToy
    case None =>
      val newId = dbo.as[ObjectId]("_id")
      geekToy.copy(_id = Some(newId))
  }
}
*/
