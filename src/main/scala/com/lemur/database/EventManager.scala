package com.lemur.database

import com.lemur.model.domain.Event
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import DatabaseConnector.database
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by lemur on 29/06/16.
  */
object EventManager extends DatabaseManager[Event] {

  def collectionFuture: Future[BSONCollection] = database.map(_ ("events"))

  override def insert(event: Event): Future[Boolean] = {
    require(event != null)
    val writeResult = for {
      collection <- collectionFuture
      result <- collection.insert(event)
    } yield result.ok

    writeResult.recover {
      case ex: Exception => throw new DatabaseInsertionException(ex)
    }
  }

  override def update(event: Event): Future[Boolean] = {
    require(event != null)
    val writeResult = for {
      collection <- collectionFuture
      result <- collection.update(queryById(event.id), event)
    } yield result.ok

    writeResult.recover {
      case ex: Exception => throw new DatabaseUpdateException(ex)
    }
  }


  override def deleteById(id: String): Future[Boolean] = {
    require(id != null)
    val deleteResult = for {
      collection <- collectionFuture
      result <- collection.remove(queryById(id))
    } yield result.ok

    deleteResult.recover {
      case ex: Exception => throw new DatabaseDeletionException(ex)
    }
  }


  override def findById(id: String): Future[Option[Event]] = {
    require(id != null)
    val event = for {
      collection <- collectionFuture
      eventProducer = collection.find(queryById(id)).cursor[Event]()
      result <- eventProducer.headOption
    } yield result

    event.recover {
      case ex: Exception => throw new DatabaseFindException(ex)
    }
  }

  override def findByParameters(values: BSONDocument): Future[List[Event]] = {
    require(values != null)
    val events = for {
      collection <- collectionFuture
      eventProducer = collection.find(values).cursor[Event]()
      result <- eventProducer.collect[List]()
    } yield result

    events.recover {
      case ex: Exception => throw new DatabaseFindException(ex)
    }
  }

  override def findAll(): Future[List[Event]] = {
    val events = for {
      collection <- collectionFuture
      result <- collection.find(emptyQuery).cursor[Event]().collect[List]()
    } yield result

    events.recover {
      case ex: Exception => throw new DatabaseFindException(ex)
    }
  }
}
