package com.lemur.database

import com.lemur.model.domain.DomainEntity
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import scala.concurrent.Future

/**
  * Created by lemur on 29/06/16.
  */
trait DatabaseManager[T <: DomainEntity] {

  def insert(dbEntity: T): Future[Boolean]

  def updateById(dbEntity: T): Future[Boolean]

  def findById(id: BSONObjectID): Future[Option[T]]

  def findByParameters(values: BSONDocument): Future[List[T]]

  def findAll(values: BSONDocument): Future[List[T]]

  def deleteById(id: BSONObjectID): Future[Boolean]

  def queryById(id: BSONObjectID): BSONDocument = BSONDocument("_id" -> id)

  def emptyQuery: BSONDocument = BSONDocument()

}
