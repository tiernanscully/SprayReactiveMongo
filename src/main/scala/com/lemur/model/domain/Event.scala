package com.lemur.model.domain

import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}

/**
  * Created by lemur on 27/06/16.
  */
case class Event(id: BSONObjectID = BSONObjectID.generate,
                 title: String,
                 description: String,
                 category: String,
                 startTime: String,
                 endTime: String,
                 latitude: String,
                 longitude: String)

object Event {

  implicit object EventReader extends BSONDocumentReader[Event] {
    def read(doc: BSONDocument): Event = new Event(
      doc.getAs[BSONObjectID]("_id").get,
      doc.getAs[String]("description").get,
      doc.getAs[String]("description").get,
      doc.getAs[String]("category").get,
      doc.getAs[String]("startTime").get,
      doc.getAs[String]("endTime").get,
      doc.getAs[String]("latitude").get,
      doc.getAs[String]("longitude").get
    )
  }

  implicit object EventWriter extends BSONDocumentWriter[Event] {
    def write(event: Event): BSONDocument =
      BSONDocument(
        "_id" -> event.id,
        "title" -> event.title,
        "description" -> event.description,
        "category" -> event.category,
        "startTime" -> event.startTime,
        "endTime" -> event.endTime,
        "latitude" -> event.latitude,
        "latitude" -> event.latitude
      )
  }

}


