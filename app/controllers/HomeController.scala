package controllers

import javax.inject._
import play.api.mvc._
import services.TaskService

import scala.concurrent.ExecutionContext
import reactivemongo.api.bson.BSONObjectID
import play.api.libs.json.{Json, __}
import dto.{CreateTask, GetTasksResponse, TaskDto, UpdateTask}

@Singleton
class TaskController @Inject()(
  val taskService: TaskService,
  val controllerComponents: ControllerComponents
)(implicit executionContext: ExecutionContext) extends BaseController {

  def index() = Action {
    Redirect(routes.TaskController.findAll())
  }

  def findAll(): Action[AnyContent] = Action.async {
    taskService.findAll.map {
      tasks => Ok(Json.toJson(GetTasksResponse(tasks)))
    }
  }

  def findOne(id: BSONObjectID): Action[AnyContent] = Action.async {
    taskService.findOne(id).map {
      case Some(task: TaskDto) => Ok(Json.toJson(GetTasksResponse(Seq(task))))
      case None => NotFound
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
      _ => NoContent
    }
  }

  def update(id: BSONObjectID): Action[UpdateTask] = Action(parse.json[UpdateTask]).async { request =>
    import request.{body => updateTask}
    taskService.update(id, updateTask).map {
      _ => NoContent
    }
  }

  def delete(id: BSONObjectID): Action[AnyContent] = Action.async {
    taskService.delete(id).map {
      _ => NoContent
    }
  }

  def deleteAll(): Action[AnyContent] = Action.async {
    taskService.deleteAll().map(
      _ => NoContent
    )
  }
}
