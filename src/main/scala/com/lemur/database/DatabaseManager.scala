package com.lemur.database

import com.lemur.model.domain.DomainEntity
import reactivemongo.bson.BSONDocument
import scala.concurrent.Future

/**
  * Created by lemur on 29/06/16.
  */
trait DatabaseManager[T <: DomainEntity] {

  def insert(dbEntity: T): Future[Boolean]

  def update(newDbEntity: T): Future[Boolean]

  def findById(id: String): Future[Option[T]]

  def findByParameters(values: BSONDocument): Future[List[T]]

  def findAll(): Future[List[T]]

  def deleteById(id: String): Future[Boolean]

  def queryById(id: String): BSONDocument = BSONDocument("_id" -> id)

  def emptyQuery: BSONDocument = BSONDocument()

}
