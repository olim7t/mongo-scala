package whattodo

import org.bson.types.ObjectId
import org.joda.time.DateTime

class DomainSpec extends SpecBase with Configuration with MongoClient with Domain {
  val mongoHost = "localhost"
  val mongoPort = 27017
  val mongoDbName = "WhatToDo"

  val mongoDb = connect()

  val eventRepository = new EventRepository()

  feature("Objects can be created, searched and removed") {

    scenario("An event is created") {
      given("a transient event")
      val transientEvent = newTestEvent
      and("the current number of events")
      val countBeforeSave = eventRepository.count

      when("the event is saved")
      val savedEvent = eventRepository.save(transientEvent)

      then("we get a new event instance with an id")
      savedEvent._id should be('defined)
      and("the number of events is incremented")
      eventRepository.count should equal(countBeforeSave +1)
    }

    scenario("An event is searched by id") {
      given("the id of a persisted event")
      val savedEvent = eventRepository.save(newTestEvent)
      val id = savedEvent._id.getOrElse(fail("saved event should have an id"))

      when("looking for the event with this id")
      val searchResult = eventRepository.findById(id)

      then("we get back the same event")
      searchResult match {
        case None => fail("saved event not found")
        case Some(foundEvent) => foundEvent should equal(savedEvent)
      }
    }

    scenario("An event is removed") {
      given("the id of a persisted event")
      val savedEvent = eventRepository.save(newTestEvent)
      val id = savedEvent._id.getOrElse(fail("saved event should have an id"))
      and("the current number of events")
      val countBeforeRemove = eventRepository.count

      when("the event with this id is removed")
      eventRepository.removeById(id)

      then("the number of events is decremented")
      eventRepository.count should equal(countBeforeRemove - 1)
    }
  }


  feature("Updates can be performed directly in the database") {

    scenario("A session is added to an existing event") {
      given("the id of an existing event")
      val eventId: ObjectId = new ObjectId("4e9d4420f7dbcc6b4f9711b5")
      and("the number of sessions of that event")
      val sessionCountBefore = eventRepository.findById(eventId).
        getOrElse(fail("event should exist in the database")).
        sessions.size

      when("a new session is added to this event")
      eventRepository.addSessionToEvent(eventId, EventSession(new DateTime(2012, 3, 10, 12, 0)))

      then("its session count is incremented")
      val sessionCountAfter = eventRepository.findById(eventId).
        getOrElse(fail("event should exist in the database")).
        sessions.size
      sessionCountAfter should equal(sessionCountBefore + 1)
    }

    scenario("The name of all the events at a given date is updated") {
      given("A date")
      val date = new DateTime(2011, 11, 03, 0, 0)

      when("the events with a session at this date are renamed")
      eventRepository.renameByDate(date, "NewName")

      then("the count of elements with the new name is 99")
      eventRepository.countWithName("NewName") should equal(99)
    }
  }


  feature("Queries can use multiple criteria") {

    scenario("Jazz events are searched in Paris") {
      when("searching")
      val events = eventRepository.findLast10By(town = "Paris", descriptionContains = "jazz")

      then("the first element matches the expected result")
      events.next.name should equal("Socalled")
    }
  }

  private def newTestEvent = Event(
    name = "Danse OukaOuka",
    description = "Everybody dancing UkaUka",
    sessions = List(
      EventSession(new DateTime(2011, 12, 30, 12, 0)),
      EventSession(new DateTime(2012, 1, 6, 12, 0))),
    venue = Venue("Palais Royal", "22 rue de David Feta", "75001", "Paris"),
    updated = new DateTime())
}