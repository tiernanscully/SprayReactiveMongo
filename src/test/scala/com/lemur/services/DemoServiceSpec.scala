package com.example

import com.lemur.services.DemoService
import org.specs2.mutable.Specification
import spray.http.StatusCodes._
import spray.http._
import spray.testkit.Specs2RouteTest

class DemoServiceSpec extends Specification with Specs2RouteTest with DemoService {
  def actorRefFactory = system

  "DemoService" should {

    "return a message saying 'demo' for GET requests to the /demo path" in {
      Get("/demo") ~> demoRoute ~> check {
        status === OK
        body shouldEqual HttpEntity(ContentTypes.`application/json`, "{\"message\": \"demo\"}")
      }
    }
  }
}
