package dto

import play.api.libs.json.{Format, Json}

case class UpdateTask(
  done: Boolean,
)

trait UpdateTaskJson {
  implicit val writes: Format[UpdateTask] = Json.format[UpdateTask]
}

object UpdateTask extends UpdateTaskJson