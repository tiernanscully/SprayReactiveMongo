package com.lemur.database

import reactivemongo.api.{DefaultDB, MongoDriver}
import reactivemongo.core.nodeset.Authenticate
import scala.concurrent._
import ExecutionContext.Implicits.global

import scala.concurrent.Future

/**
  * Created by lemur on 27/06/16.
  */
object DatabaseFactory {

  private val driver = new MongoDriver
  private val server = List("0.0.0.0")

  private val databaseName = "lemur-db"
  private val userName = "lemur"
  private val password = "lemur"
  private val credentials = List(Authenticate(databaseName, userName, password))
  private val connection = driver.connection(server, authentications = credentials)

  def futureDatabase: Future[DefaultDB] = connection.database(databaseName)

}
