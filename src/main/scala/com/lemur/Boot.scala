package com.lemur

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import spray.can.Http

import scala.concurrent.duration._

object Boot extends App {

  val config = ConfigFactory.load()
  val hostname = config.getString("http.host")
  val portNumber = config.getInt("http.port")

  implicit val system = ActorSystem("on-spray-can")
  implicit val timeout = Timeout(5.seconds)
  val lemurService = system.actorOf(Props[RoutesActor], "web-service")

  IO(Http) ? Http.Bind(lemurService, interface = hostname, port = portNumber)
}
