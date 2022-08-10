package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.{Task, CreateTask, UpdateTask}
import play.api.mvc._
import dao.TaskDAO
import scala.concurrent.ExecutionContext
import reactivemongo.bson.BSONObjectID
import play.api.libs.json.{Json, __}
import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.JsValue


@Singleton
class TaskController @Inject()(
                                implicit executionContext: ExecutionContext,
                                val taskDAO: TaskDAO,
                                val controllerComponents: ControllerComponents)
  extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.TaskController.findAll())
  }

  def findAll(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    taskDAO.findAll().map {
      tasks => Ok(Json.toJson(tasks))
    }
  }

  def findOne(id: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val objectIdTryResult = BSONObjectID.parse(id)
    objectIdTryResult match {
      case Success(objectId) => taskDAO.findOne(objectId).map {
        task => Ok(Json.toJson(task))
      }
      case Failure(_) => Future.successful(BadRequest("Cannot parse the task id"))
    }
  }

  def create(): Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {
    request.body.validate[CreateTask].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")),
      task =>
        taskDAO.create(Task(_id = task._id, label = task.label, done = false, deleted = false) ).map {
          _ => Created(Json.toJson(task))
        }
    )
  }
  }

  def updateAll(): Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {
    request.body.validate[UpdateTask].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")),
      task =>
        taskDAO.update(task.done).map {
          _ => Ok
        }
    )
  }
  }

  def update(id: String): Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {
    val objectIdTryResult = BSONObjectID.parse(id)
    objectIdTryResult match {
      case Success(objectId) => request.body.validate[UpdateTask].fold(
        _ => Future.successful(BadRequest("Cannot parse request body")),
        task =>
          taskDAO.update(objectId, task.done).map {
            _ => Ok
          }
      )
      case Failure(_) => Future.successful(BadRequest("Cannot parse the task id"))
    }
  }
  }

  def delete(id: String): Action[AnyContent] = Action.async { implicit request => {
    val objectIdTryResult = BSONObjectID.parse(id)
    objectIdTryResult match {
      case Success(objectId) => taskDAO.delete(objectId).map {
        _ => Ok
      }
      case Failure(_) => Future.successful(BadRequest("Cannot parse the task id"))
    }
  }
  }

  def deleteAll(): Action[AnyContent] = Action.async { implicit request => {
    taskDAO.delete().map(
      _ => Ok
    )
  }}
}
