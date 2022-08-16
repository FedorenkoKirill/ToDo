package dto

import play.api.libs.json.{Format, Json}

case class TaskDto(
  id: String,
  label: String
)

trait TaskDtoJson {
  implicit val writesJson: Format[TaskDto] = Json.format[TaskDto]
}

object TaskDto extends TaskDtoJson