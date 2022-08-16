package models

import reactivemongo.api.bson.{BSONDocumentHandler, BSONObjectID, Macros}
import reactivemongo.api.bson.Macros.Annotations.Key

case class Task(
  @Key("_id") id: BSONObjectID,
  label: String,
  done: Boolean,
  deleted: Boolean
)

trait TaskBson {
  implicit val writesBson: BSONDocumentHandler[Task] = Macros.handler[Task]
}

object Task extends TaskBson