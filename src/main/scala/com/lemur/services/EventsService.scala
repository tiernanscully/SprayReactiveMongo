package com.lemur.services

import scala.util.{Failure, Success}
import com.lemur.database.EventManager
import com.lemur.models.{CommonMessageModel}
import com.lemur.models.domain.Event
import com.wordnik.swagger.annotations._
import spray.http.MediaTypes
import spray.routing.HttpService

@Api(value = "v1/api/events", description = "Events API")
trait EventsService extends HttpService {

  import com.lemur.utils.Json4sSupport._
  import scala.concurrent.ExecutionContext.Implicits.global

  def eventsRoutes = getAllEventsRoute ~ getEventRoute ~ postEventRoute ~ putEventRoute

  @ApiOperation(httpMethod = "GET", value = "Returns a list of events.")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "List of events returned.")
  ))
  def getAllEventsRoute =
    path("events") {
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          onComplete(EventManager.findAll()) {
            case Success(entities) =>
              if(entities.isEmpty){
                respondWithStatus(404)(complete(new CommonMessageModel("No events found.")))
              }else {
                respondWithStatus(200)(complete(entities))
              }
            case Failure(ex) =>
              respondWithStatus(500)(complete(new CommonMessageModel("Internal Error occurred.")))
          }
        }
      }
    }

  @ApiOperation(httpMethod = "GET", value = "Returns an event.", response = classOf[Event])
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "eventId", value = "ID of event", required = true, dataType = "integer", paramType = "path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Event returned.")
  ))
  def getEventRoute =
    path("events" / IntNumber) { id =>
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          onComplete(EventManager.findById(id.toString())) {
            case Success(entity) =>
              if(entity.isEmpty){
                respondWithStatus(404)(complete(new CommonMessageModel("No event found with id: " + id)))
              }else {
                respondWithStatus(200)(complete(entity))
              }
            case Failure(ex) =>
              respondWithStatus(500)(complete(new CommonMessageModel("Internal Error occurred.")))
          }
        }
      }
    }

  @ApiOperation(httpMethod = "POST", value = "Add a new event", consumes = "application/json", response = classOf[CommonMessageModel])
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "body", value = "Event object", dataType = "Event", required = true, paramType = "body")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Successfully added event.")
  ))
  def postEventRoute =
    path("events") {
      post {
        entity(as[Event]) { event ⇒
          respondWithMediaType(MediaTypes.`application/json`) {
            onComplete(EventManager.insert(event)) {
              case Success(true) =>
                respondWithStatus(200)(complete(new CommonMessageModel("Successfully added event: " + event.id)))
              case Success(false) =>
                respondWithStatus(400)(complete(new CommonMessageModel("Unable to add event: " + event.id)))
              case Failure(ex) =>
                respondWithStatus(500)(complete(new CommonMessageModel("Internal Error occurred. Event: " + event.id + " could not be added.")))
            }
          }
        }
      }
    }

  @ApiOperation(httpMethod = "Put", value = "Update an event", consumes = "application/json", response = classOf[CommonMessageModel])
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "body", value = "Event object", dataType = "Event", required = true, paramType = "body")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Successfully updated event.")
  ))
  def putEventRoute =
    path("events") {
      put {
        entity(as[Event]) { event ⇒
          respondWithMediaType(MediaTypes.`application/json`) {
            onComplete(EventManager.update(event)) {
              case Success(true) =>
                respondWithStatus(200)(complete(new CommonMessageModel("Successfully updated event: " + event.id)))
              case Success(false) =>
                respondWithStatus(400)(complete(new CommonMessageModel("Unable to update event: " + event.id)))
              case Failure(ex) =>
                respondWithStatus(500)(complete(new CommonMessageModel("Internal Error occurred. Event: " + event.id + " could not be updated.")))
            }
          }
        }
      }
    }
}