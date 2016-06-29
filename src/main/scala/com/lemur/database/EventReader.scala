package com.lemur.database

import akka.actor.Actor
import com.lemur.database.DatabaseFactory.futureDatabase
import reactivemongo.api.collections.bson.BSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import reactivemongo.bson.BSONDocument
import spray.json._

/**
  * Created by lemur on 27/06/16.
  */
class EventReader extends Actor {

  private val events: String = "Events"

  override def receive = {
    case "List" => sender ! getEvents()
    case eventId: Int => sender ! getEvent(eventId: Int)
    case _ => throw new IllegalArgumentException("Bad rest point!")
  }

  def getEvents(): JsValue = {
    val query = BSONDocument()
    runQuery(collection(events), query)
  }

  def getEvent(eventId: Int): JsValue = {
    val query = BSONDocument("eventId" -> eventId)
    runQuery(collection(events), query)
  }

  def collection(collectionName: String): Future[BSONCollection] = {
    futureDatabase.map(_.collection(collectionName))
  }

  private def runQuery(futureCollection: Future[BSONCollection], query: BSONDocument): JsValue = {
    futureCollection
      .map(_.find(query).cursor[BSONDocument]())
      .collect[List]
      .toJson

  }


}
