package dto

import play.api.libs.json.{Format, Json}

case class CreateTask(
  label: String
)

trait CreateTaskJson {
  implicit val writes: Format[CreateTask] = Json.format[CreateTask]
}

object CreateTask extends CreateTaskJson