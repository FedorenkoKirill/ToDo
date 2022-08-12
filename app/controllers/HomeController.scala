package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.{Task, CreateTask, UpdateTask}
import services.TaskService
import play.api.mvc._
import scala.concurrent.ExecutionContext
import reactivemongo.bson.BSONObjectID
import play.api.libs.json.{Json, __}
import dto.GetTasksResponse
import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.JsValue


@Singleton
class TaskController @Inject()(
                                val taskService: TaskService,
                                val controllerComponents: ControllerComponents
                              )(implicit executionContext: ExecutionContext)
  extends BaseController {

  def index() = Action {
    Redirect(routes.TaskController.findAll())
  }

  def findAll(): Action[AnyContent] = Action.async {
    taskService.findAll.map {
      tasks => Ok(Json.toJson(GetTasksResponse(tasks)))
    }
  }

  def findOne(id: String): Action[AnyContent] = Action.async {
    taskService.findOne(BSONObjectID.parse(id).get).map {
      tasks => Ok(Json.toJson(GetTasksResponse(tasks.toSeq)))
    }
  }

  def create(): Action[CreateTask] = Action(parse.json[CreateTask]).async { request =>
    import request.{body => createTask}
    taskService.create(createTask).map {
      _ => Created
    }
  }

  def updateAll(): Action[UpdateTask] = Action(parse.json[UpdateTask]).async { request =>
    import request.{body => updateTask}
    taskService.updateAll(updateTask).map {
      _ => Ok
    }
  }

  def update(id: String): Action[UpdateTask] = Action(parse.json[UpdateTask]).async { request =>
    import request.{body => updateTask}
    taskService.update(BSONObjectID.parse(id).get, updateTask).map {
      _ => Ok
    }
  }

  def delete(id: String): Action[AnyContent] = Action.async {
    taskService.delete(BSONObjectID.parse(id).get).map {
      _ => Ok
    }
  }

  def deleteAll(): Action[AnyContent] = Action.async {
    taskService.deleteAll().map(
      _ => Ok
    )
  }
}
