package services
import models.{CreateTask, Task, UpdateTask}
import dao.TaskDAO
import play.api.mvc.{Action, AnyContent}
import play.modules.reactivemongo.ReactiveMongoApi
import play.mvc.BodyParser.Json

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext


@Singleton
class TaskService @Inject()(
                             implicit executionContext: ExecutionContext,
                             val taskDAO: TaskDAO,
                           ) {
  def findAll: Future[Seq[Task]] = taskDAO.findAll()

  def findOne(objectId: BSONObjectID): Future[Option[Task]] = taskDAO.findOne(objectId)

  def create(createTask: CreateTask): Future[WriteResult] = {
    taskDAO.create(label = createTask.label, done = false, deleted = false)
  }

  def update(task: UpdateTask): Future[WriteResult] = taskDAO.update(task.done)

  def update(objectId: BSONObjectID, task: UpdateTask): Future[WriteResult] = taskDAO.update(objectId, task.done)

  def delete(objectId: BSONObjectID): Future[WriteResult] = taskDAO.delete(objectId)

  def delete(): Future[WriteResult] = taskDAO.delete()
}
