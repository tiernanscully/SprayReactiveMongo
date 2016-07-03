package com.lemur.model.domain

import com.lemur.common.Copying
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}

sealed trait DomainEntity

case class Event private(id: String,
                         title: String,
                         description: String,
                         category: String,
                         startTime: String,
                         endTime: String,
                         latitude: String,
                         longitude: String) extends DomainEntity

object Event extends Copying[Event]{

  def apply(title: String,
            description: String,
            category: String,
            startTime: String,
            endTime: String,
            latitude: String,
            longitude: String): Event = new Event(BSONObjectID.generate.stringify, title, description, category, startTime, endTime, latitude, longitude)

  implicit object EventReader extends BSONDocumentReader[Event] {
    def read(doc: BSONDocument): Event = apply(
      doc.getAs[String]("_id").get,
      doc.getAs[String]("title").get,
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
        "longitude" -> event.longitude
      )
  }
}


