package dto
import models.Task
import dto.TaskDto
import play.api.libs.json.{Json, Writes}
import play.api.libs.json.{Format, Json}

case class GetTasksResponse (tasks: Seq[TaskDto])

trait GetTasksResponseJson {
  implicit val writes: Writes[GetTasksResponse] = Json.writes[GetTasksResponse]
}

object GetTasksResponse extends GetTasksResponseJson