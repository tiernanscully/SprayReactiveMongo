package com.lemur.database

import com.lemur.model.domain.Event
import reactivemongo.bson.BSONDocument

import scala.concurrent.Await

/**
  * Created by lemur on 30/06/16.
  */
class EventManagerTest extends DatabaseManagerTestSpec[Event] {

  val sampleEvent1 = Event("5-aside",
    "Football with the lads",
    "Sport",
    "2016-06-30T18:15:00+01:00",
    "2016-06-30T20:15:00+01:00",
    "53.4239",
    "7.9407")

  val updatedSampleEvent1 = sampleEvent1.copy(title = "Rugby")

  val sampleEvent2 = Event("Poker",
    "Poker with the lads",
    "Gaming",
    "2016-07-01T18:15:00+01:00",
    "2016-07-01T20:15:00+01:00",
    "53.4239",
    "7.9407")

  private val locationQuery = BSONDocument("latitude" -> "53.4239", "longitude" -> "7.9407")

  private def loadEventsIntoDatabase(): Unit = {
    loadEventIntoDB(sampleEvent1)
    loadEventIntoDB(sampleEvent2)
  }

  private def loadEventIntoDB(event: Event): Unit = {
    val insertOperationResult = Await.result(EventManager.insert(event), timeout)
    assert(insertOperationResult)
  }

  "An event" should "be able to be inserted into the database " in {
    loadEventsIntoDatabase()
  }

  "An event" should "not be able to be inserted into the database twice" in {
    loadEventsIntoDatabase()
    intercept[DatabaseInsertionException] {
      loadEventIntoDB(sampleEvent1)
    }
  }

  "An event" should "be able to be deleted after it has been inserted into the database" in {
    loadEventsIntoDatabase()
    assert(findEvent(sampleEvent1.id).isDefined)
    val deletionOperationResult = Await.result(EventManager.deleteById(sampleEvent1.id), timeout)
    assert(deletionOperationResult)
    assert(findEvent(sampleEvent1.id).isEmpty)
  }

  "An event" should "be able to be updated after it has been inserted into the database" in {
    loadEventsIntoDatabase()
    val oldEvent = findEvent(sampleEvent1.id).get
    assert(oldEvent.id === sampleEvent1.id)
    assert(oldEvent.title === "5-aside")
    val updateOperationResult = Await.result(EventManager.update(updatedSampleEvent1), timeout)
    assert(updateOperationResult)
    val updatedEvent = findEvent(sampleEvent1.id).get
    assert(updatedEvent.id === sampleEvent1.id)
    assert(updatedEvent.title === "Rugby")
  }

  "An event" should "be retrievable by id field since id field is unique" in {
    loadEventsIntoDatabase()
    val event = findEvent(sampleEvent1.id)
    assert(event.isDefined)
    assert(event.get === sampleEvent1)
  }

  "No event" should "be retrieved if it doesn't exists in the database" in {
    loadEventsIntoDatabase()
    val event = findEvent("random id")
    assert(event.isEmpty)
  }

  def findEvent(id: String): Option[Event] = {
    Await.result(EventManager.findById(id), timeout)
  }

  "A list of events" should "be retrievable by field values" in {
    loadEventsIntoDatabase()
    val eventList = Await.result(EventManager.findByParameters(locationQuery), timeout)
    assert(eventList.size == 2)
    assert(eventList.head === sampleEvent1)
    assert(eventList.tail.head === sampleEvent2)
  }

  "All events in the database" should "be retrievable" in {
    loadEventsIntoDatabase()
    val eventList = Await.result(EventManager.findAll(), timeout)
    assert(eventList.size == 2)
    assert(eventList.head === sampleEvent1)
    assert(eventList.tail.head === sampleEvent2)
  }

}
