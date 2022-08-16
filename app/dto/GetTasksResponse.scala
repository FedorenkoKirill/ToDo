package dto

import play.api.libs.json.{Json, Writes}

case class GetTasksResponse(tasks: Seq[TaskDto])

trait GetTasksResponseJson {
  implicit val writes: Writes[GetTasksResponse] = Json.writes[GetTasksResponse]
}

object GetTasksResponse extends GetTasksResponseJson