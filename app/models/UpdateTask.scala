package models

import play.api.libs.json.{Format, Json}
import reactivemongo.bson.BSONObjectID


case class UpdateTask(
                       _id:Option[BSONObjectID],
                       done: Boolean,
                     )

trait UpdateTaskJson {
  implicit val fmt : Format[UpdateTask] = Json.format[UpdateTask]
}

object UpdateTask extends UpdateTaskJson