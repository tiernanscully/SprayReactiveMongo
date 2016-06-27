package com.lemur

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.lemur.common.Constants._
import spray.can.Http

import scala.concurrent.duration._

object Boot extends App {
  implicit val system = ActorSystem("on-spray-can")

  val lemurService = system.actorOf(Props[RoutesActor], "lemur-service")

  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(lemurService, interface = serverIpAddress, port = serverPort)
}
