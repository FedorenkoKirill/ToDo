package dao
import javax.inject._
import reactivemongo.api.bson.collection.BSONCollection
import play.modules.reactivemongo.ReactiveMongoApi
import scala.concurrent.{ExecutionContext, Future}
import models.{Task}
import dto.TaskDto
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.api.bson.{BSONDocument, BSONObjectID}
import reactivemongo.api.commands.WriteResult


@Singleton
class TaskDAO @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit executionContext: ExecutionContext) {

  def collection: Future[BSONCollection] = reactiveMongoApi.database.map(db => db.collection("tasks"))

  def findAll(limit: Int = 100): Future[Seq[Task]] = {
    collection.flatMap(
      _.find(BSONDocument(), Option.empty[Task])
        .cursor[Task](ReadPreference.Primary)
        .collect[Seq](limit, Cursor.FailOnError[Seq[Task]]())
    )
  }

  def findOne(id: BSONObjectID): Future[Option[Task]] = {
    collection.flatMap(_.find(BSONDocument("_id" -> id), Option.empty[Task]).one[Task])
  }

  def create(task: Task): Future[WriteResult] = {
    collection.flatMap(_.insert(BSONDocument(
      "_id" -> task.id,
      "label" -> task.label,
      "done" -> task.done,
      "deleted" -> task.deleted
    )))
  }

  def updateAll(done: Boolean):Future[WriteResult] = {
    collection.flatMap(
      _.update(BSONDocument("done" -> true), BSONDocument("$set" -> BSONDocument("done" -> done)), multi = true)
      )
  }

  def update(id: BSONObjectID, done: Boolean):Future[WriteResult] = {
      collection.flatMap(
      _.update(ordered = false).one(BSONDocument("_id" -> id),
        BSONDocument("$set" -> BSONDocument("done" -> done))
      )
    )
  }

  def delete(id: BSONObjectID):Future[WriteResult] = {
    collection.flatMap(
      _.update(ordered = false).one(BSONDocument("_id" -> id),
        BSONDocument("$set" -> BSONDocument("deleted" -> true))
      )
    )
  }

  def deleteAll():Future[WriteResult] = {
    collection.flatMap(
      _.update(BSONDocument("done" -> true), BSONDocument("$set" -> BSONDocument("deleted" -> true)), multi = true)
    )
  }
}

