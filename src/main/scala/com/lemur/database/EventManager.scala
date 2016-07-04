package com.lemur.database

import com.lemur.models.domain.Event
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter}
import DatabaseConnector.database

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by lemur on 29/06/16.
  */
object EventManager extends DatabaseManager[Event] {

  override def futureCollection() = database.map(_ ("events"))
  override implicit val writer: BSONDocumentWriter[Event] = Event.EventWriter
  override implicit val reader: BSONDocumentReader[Event] = Event.EventReader
}
