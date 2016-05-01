package com.lemur

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import com.lemur.common.Constants
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

object Boot extends App {
  implicit val system = ActorSystem("on-spray-can")

  val lemurService = system.actorOf(Props[RoutesActor], "lemur-service")

  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(lemurService, interface = Constants.ServerIpAddress, port = Constants.ServerPort)
}
