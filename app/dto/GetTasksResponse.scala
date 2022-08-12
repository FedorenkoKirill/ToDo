package dto
import models.Task
import play.api.libs.json.{Json, Writes}

case class GetTasksResponse (tasks: Seq[Task])

trait GetTasksResponseJson {
  implicit val writes: Writes[GetTasksResponse] = Json.writes[GetTasksResponse]
}

object GetTasksResponse extends GetTasksResponseJson