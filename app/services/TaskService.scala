package services
import models.Task
import dao.TaskDAO
import dto.{CreateTask, UpdateTask}
import play.api.mvc.{Action, AnyContent}
import play.modules.reactivemongo.ReactiveMongoApi
import play.mvc.BodyParser.Json

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext


@Singleton
class TaskService @Inject()(val taskDAO: TaskDAO)(implicit executionContext: ExecutionContext) {
  def findAll: Future[Seq[Task]] = taskDAO.findAll()

  def findOne(objectId: BSONObjectID): Future[Option[Task]] = taskDAO.findOne(objectId)

  def create(createTask: CreateTask): Future[WriteResult] = {
    taskDAO.create(label = createTask.label, done = false, deleted = false)
  }

  def updateAll(task: UpdateTask): Future[WriteResult] = taskDAO.updateAll(task.done)

  def update(objectId: BSONObjectID, task: UpdateTask): Future[WriteResult] = taskDAO.update(objectId, task.done)

  def delete(objectId: BSONObjectID): Future[WriteResult] = taskDAO.delete(objectId)

  def deleteAll(): Future[WriteResult] = taskDAO.deleteAll()
}
