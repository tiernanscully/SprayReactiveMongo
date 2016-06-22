package com.lemur

import akka.actor.{ Actor, ActorRefFactory }
import com.lemur.services.{ LoginService, DemoService }

/**
 * Created by djhurley on 02/04/16.
 */
trait Routes extends LoginService with DemoService {
  val routes = demoRoute ~ loginRoute
}
class RoutesActor extends Actor with Routes {
  override val actorRefFactory = context
  override def receive = runRoute(routes)
}

