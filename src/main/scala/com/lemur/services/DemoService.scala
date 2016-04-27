package com.lemur.services

import spray.http._
import spray.routing._

trait DemoService extends HttpService {

  val demoRoute =
    path("demo") {
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          complete {
            "{\"message\" : \"demo\"}"
          }
        }
      }
    }
}