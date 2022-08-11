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
import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.JsValue


@Singleton
class TaskController @Inject()(
                                implicit executionContext: ExecutionContext,
                                val taskService: TaskService,
                                val controllerComponents: ControllerComponents)
  extends BaseController {

  def index() = Action {
    Redirect(routes.TaskController.findAll())
  }

  def findAll(): Action[AnyContent] = Action.async {
    taskService.findAll.map {
      tasks => Ok(Json.toJson(tasks))
    }
  }

  def findOne(id: BSONObjectID): Action[AnyContent] = Action.async {
    taskService.findOne(id).map {
      tasks => Ok(Json.toJson(tasks))
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
    taskService.update(updateTask).map {
      _ => Ok
    }
  }

  def update(id: BSONObjectID): Action[UpdateTask] = Action(parse.json[UpdateTask]).async { request =>
    import request.{body => updateTask}
    taskService.update(id, updateTask).map {
      _ => Ok
    }
  }

  def delete(id: BSONObjectID): Action[AnyContent] = Action.async {
    taskService.delete(id).map {
      _ => Ok
    }
  }

  def deleteAll(): Action[AnyContent] = Action.async {
    taskService.delete().map(
      _ => Ok
    )
  }
}
