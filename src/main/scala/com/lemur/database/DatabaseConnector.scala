package com.lemur.database

import com.typesafe.config.ConfigFactory
import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by lemur on 29/06/16.
  */
object DatabaseConnector {

  private val config = ConfigFactory.load()

  private val driver = new MongoDriver

  def database: Future[DefaultDB] = for {
    uri <- Future.fromTry(MongoConnection.parseURI(config.getString("mongodb.uri")))
    con = driver.connection(uri)
    dn <- Future(uri.db.get)
    db <- con.database(dn)
  } yield db
}
