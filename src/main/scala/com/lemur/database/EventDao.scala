package com.lemur.database

import com.lemur.model.domain.Event
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONObjectID}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by lemur on 29/06/16.
  */
trait EventDao extends MongoDao {

  val collectionFuture: Future[BSONCollection] = database.map(_ ("events"))

  def insert(event: Event): Future[Boolean] = {
    require(event != null)
    for {
      collection <- collectionFuture
      writeResult <- collection.insert(event)
    } yield writeResult.ok
  }

  def updateById(event: Event): Future[Boolean] = {
    require(event != null)
    for {
      collection <- collectionFuture
      writeResult <- collection.update(queryById(event.id), event)
    } yield writeResult.ok
  }

  def findById(id: BSONObjectID): Future[Option[Event]] = {
    require(id != null)
    for {
      collection <- collectionFuture
      eventProducer = collection.find(queryById(id)).cursor[Event]()
      event <- eventProducer.headOption
    } yield event
  }

  def findByParameters(values: BSONDocument): Future[List[Event]] = {
    require(values != null)
    for {
      collection <- collectionFuture
      eventProducer = collection.find(values).cursor[Event]()
      eventList <- eventProducer.collect[List]()
    } yield eventList
  }

  def findAll(values: BSONDocument): Future[List[Event]] = {
    require(values != null)
    for {
      collection <- collectionFuture
      eventProducer = collection.find(emptyQuery).cursor[Event]()
      eventList <- eventProducer.collect[List]()
    } yield eventList
  }

  def deleteById(id: BSONObjectID): Future[Boolean] = {
    require(id != null)
    for {
      collection <- collectionFuture
      writeResult <- collection.remove(queryById(id))
    } yield writeResult.ok
  }

  private def queryById(id: BSONObjectID) = BSONDocument("_id" -> id)

  private def emptyQuery = BSONDocument()

}
