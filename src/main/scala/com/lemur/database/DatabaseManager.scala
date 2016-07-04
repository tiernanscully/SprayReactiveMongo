package com.lemur.database

import com.lemur.models.domain.DomainEntity
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

/**
  * Created by lemur on 29/06/16.
  */
trait DatabaseManager[T <: DomainEntity] {

  def insert(dbEntity: T): Future[Boolean] = {
    require(dbEntity != null)
    val writeResult = for {
      collection <- futureCollection()
      result <- collection.insert(dbEntity)
    } yield result.ok

    writeResult.recover {
      case ex: Exception => throw new DatabaseInsertionException(ex)
    }
  }

  def update(newDbEntity: T): Future[Boolean] = {
    require(newDbEntity != null)
    val writeResult = for {
      collection <- futureCollection()
      result <- collection.update(queryById(newDbEntity.id), newDbEntity)
    } yield result.ok

    writeResult.recover {
      case ex: Exception => throw new DatabaseUpdateException(ex)
    }
  }

  def deleteById(id: String): Future[Boolean] = {
    require(id != null)
    val deleteResult = for {
      collection <- futureCollection()
      result <- collection.remove(queryById(id))
    } yield result.ok

    deleteResult.recover {
      case ex: Exception => throw new DatabaseDeletionException(ex)
    }
  }

  def findById(id: String): Future[Option[T]] = {
    require(id != null)
    val entity = for {
      collection <- futureCollection()
      eventProducer = collection.find(queryById(id)).cursor[T]()
      result <- eventProducer.headOption
    } yield result

    entity.recover {
      case ex: Exception => throw new DatabaseFindException(ex)
    }
  }

  def findByParameters(values: BSONDocument): Future[List[T]] = {
    require(values != null)
    val resultList = for {
      collection <- futureCollection()
      eventProducer = collection.find(values).cursor[T]()
      results <- eventProducer.collect[List]()
    } yield results

    resultList.recover {
      case ex: Exception => throw new DatabaseFindException(ex)
    }
  }

  def findAll(): Future[List[T]] = {
    val resultList = for {
      collection <- futureCollection()
      results <- collection.find(emptyQuery).cursor[T]().collect[List]()
    } yield results

    resultList.recover {
      case ex: Exception => throw new DatabaseFindException(ex)
    }
  }

  def queryById(id: String): BSONDocument = BSONDocument("_id" -> id)

  val emptyQuery: BSONDocument = BSONDocument()

  def futureCollection(): Future[BSONCollection]

  implicit val writer: BSONDocumentWriter[T]

  implicit val reader: BSONDocumentReader[T]
}
