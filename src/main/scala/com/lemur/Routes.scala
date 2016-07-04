package com.lemur

import akka.actor.Actor
import com.gettyimages.spray.swagger._
import com.lemur.services.{AuthorizationService, EventsService}
import com.wordnik.swagger.model.ApiInfo

import scala.reflect.runtime.universe._

class RoutesActor extends Actor with AuthorizationService with EventsService {
  override val actorRefFactory = context

  val swaggerService = new SwaggerHttpService {
    override def apiTypes = Seq(typeOf[AuthorizationService], typeOf[EventsService])
    override def apiVersion = "2.0"
    override def baseUrl = "/"
    override def docsPath = "api-docs"
    override def actorRefFactory = context
    override def apiInfo = Some(new ApiInfo("Lemur Swagger",
      "Lemur API using spray and spray-swagger.",
      "TOC Url", "support@lemur.com",
      "Apache V2", "http://www.apache.org/licenses/LICENSE-2.0"))
  }

  override def receive = runRoute(
    pathPrefix("v1" / "api") {
      eventsRoutes ~ authorizationRoutes
    }
      ~ swaggerService.routes
      ~ get {
      pathPrefix("") {
        pathEndOrSingleSlash {
          getFromResource("swagger-ui/index.html")
        }
      } ~ getFromResourceDirectory("swagger-ui")
    })
}

