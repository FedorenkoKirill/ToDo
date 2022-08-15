package dto

import play.api.libs.json.{Format, Json}
import reactivemongo.api.bson.Macros.Annotations.Key

case class TaskDto (
                @Key("_id") id: String,
                label: String
              )

trait TaskDtoJson {
  implicit val writesJson : Format[TaskDto] = Json.format[TaskDto]
}

object TaskDto extends TaskDtoJson