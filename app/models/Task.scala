package models

import play.api.libs.json.{Format, Json}
import reactivemongo.play.json._
import reactivemongo.api.bson.{BSONDocumentHandler, Macros}

case class Task(
               label: String,
               done: Boolean,
               deleted: Boolean
               )


trait TaskWrites {
  implicit val writesJson : Format[Task] = Json.format[Task]
  implicit val writesBson : BSONDocumentHandler[Task] = Macros.handler[Task]
}

object Task extends TaskWrites