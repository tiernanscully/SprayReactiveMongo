package com.lemur

import akka.actor.{Actor, ActorRefFactory}
import com.lemur.services.{LoginService, DemoService}

/**
  * Created by djhurley on 02/04/16.
  */
class RoutesActor extends Actor with Routes {
  override val actorRefFactory: ActorRefFactory = context
  def receive = runRoute(routes)
}

trait Routes extends LoginService with DemoService {
  val routes = {
    demoRoute ~
      loginRoute
  }
}
