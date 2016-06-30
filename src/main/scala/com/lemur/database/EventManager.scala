package com.lemur.database

import com.lemur.model.domain.Event
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import DatabaseConnector.database
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by lemur on 29/06/16.
  */
object EventManager extends DatabaseManager[Event] {

  val collectionFuture: Future[BSONCollection] = database.map(_ ("events"))

  override def insert(event: Event): Future[Boolean] = {
    require(event != null)
    for {
      collection <- collectionFuture
      writeResult <- collection.insert(event)
    } yield writeResult.ok
  }

  override def updateById(event: Event): Future[Boolean] = {
    require(event != null)
    for {
      collection <- collectionFuture
      writeResult <- collection.update(queryById(event.id), event)
    } yield writeResult.ok
  }

  override def findById(id: BSONObjectID): Future[Option[Event]] = {
    require(id != null)
    for {
      collection <- collectionFuture
      eventProducer = collection.find(queryById(id)).cursor[Event]()
      event <- eventProducer.headOption
    } yield event
  }

  override def findByParameters(values: BSONDocument): Future[List[Event]] = {
    require(values != null)
    for {
      collection <- collectionFuture
      eventProducer = collection.find(values).cursor[Event]()
      eventList <- eventProducer.collect[List]()
    } yield eventList
  }

  override def findAll(values: BSONDocument): Future[List[Event]] = {
    require(values != null)
    for {
      collection <- collectionFuture
      eventProducer = collection.find(emptyQuery).cursor[Event]()
      eventList <- eventProducer.collect[List]()
    } yield eventList
  }

  override def deleteById(id: BSONObjectID): Future[Boolean] = {
    require(id != null)
    for {
      collection <- collectionFuture
      writeResult <- collection.remove(queryById(id))
    } yield writeResult.ok
  }

}
