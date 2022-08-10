package models

import org.joda.time.DateTime
import play.api.libs.json.{Format, Json}
import reactivemongo.play.json._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._

case class Task(
               _id:Option[BSONObjectID],
               _creationDate: Option[DateTime],
               _updateDate: Option[DateTime],
               label: String,
               done: Boolean,
               deleted: Boolean
               )


object Task {
  implicit val fmt: Format[Task] = Json.format[Task]

  implicit object TaskBSONReader extends BSONDocumentReader[Task] {
    def read(doc: BSONDocument): Task = {
      Task(
        doc.getAs[BSONObjectID]("_id"),
        doc.getAs[BSONDateTime]("_creationDate").map(dt => new DateTime(dt.value)),
        doc.getAs[BSONDateTime]("_updateDate").map(dt => new DateTime(dt.value)),
        doc.getAs[String]("label").get,
        doc.getAs[Boolean]("done").get,
        doc.getAs[Boolean]("deleted").get
      )
    }
  }

  implicit object TaskBSONWriter extends BSONDocumentWriter[Task] {
    def write(task: Task): BSONDocument = {
      BSONDocument(
        "_id" -> task._id,
        "_creationDate" -> task._creationDate.map(date => BSONDateTime(date.getMillis)),
        "_updateDate" -> task._updateDate.map(date => BSONDateTime(date.getMillis)),
        "label" -> task.label,
        "done" -> task.done,
        "deleted" -> task.deleted
      )
    }
  }
}
