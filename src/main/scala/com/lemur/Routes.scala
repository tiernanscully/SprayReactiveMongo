package com.lemur

import akka.actor.{Actor}
import com.lemur.services.{DemoService, LoginService}
import com.gettyimages.spray.swagger._

import scala.reflect.runtime.universe._
import com.wordnik.swagger.model.ApiInfo

class RoutesActor extends Actor with LoginService with DemoService {
  override val actorRefFactory = context
  override def receive = runRoute(demoRoute ~ loginRoute ~ swaggerService.routes ~
    get {
      pathPrefix("") { pathEndOrSingleSlash {
        getFromResource("swagger-ui/index.html")
      }
    } ~ getFromResourceDirectory("swagger-ui")
  })

  val swaggerService = new SwaggerHttpService {
    override def apiTypes = Seq(typeOf[DemoService], typeOf[LoginService])
    override def apiVersion = "2.0"
    override def baseUrl = "/"
    override def docsPath = "api-docs"
    override def actorRefFactory = context
    override def apiInfo = Some(new ApiInfo("Lemur Swagger",
      "Lemur API using spray and spray-swagger.",
      "TOC Url", "support@lemur.com",
      "Apache V2", "http://www.apache.org/licenses/LICENSE-2.0"))
  }
}

