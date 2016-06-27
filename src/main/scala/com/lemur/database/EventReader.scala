package com.lemur.database

import akka.actor.Actor
import com.lemur.database.DatabaseFactory.database
import scala.concurrent.Future
import reactivemongo.api.collections.bson.BSONCollection
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by lemur on 27/06/16.
  */
class EventReader extends Actor {
  override def receive: Receive = {

    val eventCollection: Future[BSONCollection] = database.map(_.collection("Events"))

  }
}
