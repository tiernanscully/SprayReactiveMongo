package com.lemur.database

import de.flapdoodle.embed.mongo.distribution._
import com.github.simplyscala.{MongoEmbedDatabase, MongodProps}
import com.lemur.model.domain.Event
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Milliseconds, Minutes, Span}

/**
  * Created by lemur on 30/06/16.
  */
class EventManagerTest extends FunSuite with MongoEmbedDatabase with BeforeAndAfter with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Minutes), interval = Span(500, Milliseconds))

  var mongoInstance: MongodProps = null
  val sampleEvent1 = Event("5-aside",
    "Football with the lads",
    "Sport",
    "2016-06-30T18:15:00+01:00",
    "2016-06-30T20:15:00+01:00",
    "53.4239",
    "7.9407")

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

  private def loadEventIntoDB(event: Event): Unit = {
    val insertOperationResult = EventManager.insert(sampleEvent1)
    whenReady(insertOperationResult) {
      result => assert(result)
    }
    println("Event has been loaded into database")
  }

  test("Should be able to access database") {
    whenReady(DatabaseConnector.database) {
      result => println("Database is up and running!")
    }
  }

  test("Should be able to insert an event into database") {
    loadEventIntoDB(sampleEvent1)
  }

  test("Should not be able to insert the same event into database twice") {
    loadEventIntoDB(sampleEvent1)
    intercept[Exception] {
      loadEventIntoDB(sampleEvent1)
    }
  }

  test("Should be able to find an event in database after adding it") {
    loadEventIntoDB(sampleEvent1)
    val findOperationResult = EventManager.findById(sampleEvent1.id)
    whenReady(findOperationResult) {
      result => {
        assert(result.isDefined)
        assert(result.get == sampleEvent1)
      }
    }
  }

}
