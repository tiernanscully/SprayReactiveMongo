package com.lemur.database


import scala.concurrent._
import scala.concurrent.duration._
import org.scalatest._
import de.flapdoodle.embed.mongo.distribution._
import com.github.simplyscala.{MongoEmbedDatabase, MongodProps}
import com.lemur.model.domain.DomainEntity
import org.scalatest.{BeforeAndAfter, FlatSpec}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Milliseconds, Minutes, Span}

/**
  * Created by lemur on 30/06/16.
  */
class DatabaseManagerTestSpec[A <: DomainEntity] extends FlatSpec with MongoEmbedDatabase with BeforeAndAfter with ScalaFutures with Matchers {

  var mongoInstance: MongodProps = null

  val timeout = Duration(10, SECONDS)

  before {
    try {
      mongoInstance = mongoStart(port = 27017, version = Version.V2_7_1)
    }
    catch {
      case ex: Exception => println("Mongo is already running locally on port 27017")
    }
  }

  after {
    mongoStop(mongoInstance)
  }

  "The database" should "be able to be accessed " in {
    Await.result(DatabaseConnector.database, timeout)
  }

}
