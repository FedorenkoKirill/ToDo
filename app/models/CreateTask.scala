package models

import org.joda.time.DateTime
import play.api.libs.json.{Format, Json}
import reactivemongo.play.json._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._

case class CreateTask(
                  _id:Option[BSONObjectID],
                  _creationDate: Option[DateTime],
                  _updateDate: Option[DateTime],
                  label: String
                )

object CreateTask{
  implicit val fmt : Format[CreateTask] = Json.format[CreateTask]
  implicit object TaskBSONReader extends BSONDocumentReader[CreateTask] {
    def read(doc: BSONDocument): CreateTask = {
      CreateTask(
        doc.getAs[BSONObjectID]("_id"),
        doc.getAs[BSONDateTime]("_creationDate").map(dt => new DateTime(dt.value)),
        doc.getAs[BSONDateTime]("_updateDate").map(dt => new DateTime(dt.value)),
        doc.getAs[String]("label").get
      )
    }
  }

  implicit object TaskBSONWriter extends BSONDocumentWriter[CreateTask] {
    def write(task: CreateTask): BSONDocument = {
      BSONDocument(
        "_id" -> task._id,
        "_creationDate" -> task._creationDate.map(date => BSONDateTime(date.getMillis)),
        "_updateDate" -> task._updateDate.map(date => BSONDateTime(date.getMillis)),
        "label" -> task.label,
        "done" -> false,
        "deleted" -> false
      )
    }
  }
}