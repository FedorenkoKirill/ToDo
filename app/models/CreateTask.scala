package models

import org.joda.time.DateTime
import play.api.libs.json.{Format, Json}
import reactivemongo.play.json._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._

case class CreateTask(
                  label: String
                )

object CreateTask{
  implicit val fmt : Format[CreateTask] = Json.format[CreateTask]
  implicit object TaskBSONReader extends BSONDocumentReader[CreateTask] {
    def read(doc: BSONDocument): CreateTask = {
      CreateTask(
        doc.getAs[BSONObjectID]("_id"),
        doc.getAs[String]("label").get
      )
    }
  }

  implicit object TaskBSONWriter extends BSONDocumentWriter[CreateTask] {
    def write(task: CreateTask): BSONDocument = {
      BSONDocument(
        "_id" -> task._id,
        "label" -> task.label,
      )
    }
  }
}