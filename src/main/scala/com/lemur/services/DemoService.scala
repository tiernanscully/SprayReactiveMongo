package com.lemur.services

import com.lemur.models.CommonMessageModel
import com.wordnik.swagger.annotations.{Api, _}
import spray.http._
import spray.routing.HttpService

@Api(value = "/demo", description = "Demo API")
trait DemoService extends HttpService {

  @ApiOperation(httpMethod = "GET", value = "Returns a demo message.", response = classOf[CommonMessageModel])
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Demo message returned.")
  ))
  def demoRoute =
    path("list") {
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          complete {
            "{\"message\": \"demo\"}"
          }
        }
      }
    }
}