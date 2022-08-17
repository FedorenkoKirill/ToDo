package services

import models.Task
import dto.TaskDto
import dao.TaskDAO
import dto.{CreateTask, UpdateTask}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.bson.BSONObjectID

@Singleton
class TaskService @Inject()(
  val taskDAO: TaskDAO
)(implicit executionContext: ExecutionContext) {

  def taskModelToDto(model: Task): TaskDto = TaskDto(id = model.id.stringify, label = model.label)

  def findAll: Future[Seq[TaskDto]] = taskDAO.findAll().map(_.map(taskModelToDto))

  def findOne(objectId: BSONObjectID): Future[Option[TaskDto]] =
    taskDAO.findOne(objectId).map(_.map(taskModelToDto))

  def create(createTask: CreateTask): Future[WriteResult] = {
    taskDAO.create(Task(id = BSONObjectID.generate(), label = createTask.label, done = false, deleted = false))
  }

  def updateAll(task: UpdateTask): Future[WriteResult] = taskDAO.updateAll(task.done)

  def update(objectId: BSONObjectID, task: UpdateTask): Future[WriteResult] = taskDAO.update(objectId, task.done)

  def delete(objectId: BSONObjectID): Future[WriteResult] = taskDAO.delete(objectId)

  def deleteAll(): Future[WriteResult] = taskDAO.deleteAll()
}
