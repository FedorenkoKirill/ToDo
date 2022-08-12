package models

import org.joda.time.DateTime
import play.api.libs.json.{Format, Json}
import reactivemongo.play.json._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._


case class UpdateTask(
                       _id:Option[BSONObjectID],
                       done: Boolean,
                     )

object UpdateTask {
  implicit val fmt: Format[UpdateTask] = Json.format[UpdateTask]
}
