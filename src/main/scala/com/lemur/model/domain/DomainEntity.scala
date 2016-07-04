package com.lemur.model.domain

import com.lemur.common.Copying
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}

sealed trait DomainEntity{
  def id: String
}

case class Event private(val id: String,
                         title: String,
                         description: String,
                         category: String,
                         startTime: String,
                         endTime: String,
                         latitude: String,
                         longitude: String) extends DomainEntity

object Event extends Copying[Event]{

  val columnNames = Array("_id","title","description","category","startTime","endTime","latitude","longitude")

  def apply(title: String,
            description: String,
            category: String,
            startTime: String,
            endTime: String,
            latitude: String,
            longitude: String): Event = new Event(BSONObjectID.generate.stringify, title, description, category, startTime, endTime, latitude, longitude)

  implicit object EventReader extends BSONDocumentReader[Event] {
    def read(doc: BSONDocument): Event = apply(
      doc.getAs[String](columnNames(0)).get,
      doc.getAs[String](columnNames(1)).get,
      doc.getAs[String](columnNames(2)).get,
      doc.getAs[String](columnNames(3)).get,
      doc.getAs[String](columnNames(4)).get,
      doc.getAs[String](columnNames(5)).get,
      doc.getAs[String](columnNames(6)).get,
      doc.getAs[String](columnNames(7)).get
    )
  }

  implicit object EventWriter extends BSONDocumentWriter[Event] {
    def write(event: Event): BSONDocument =
      BSONDocument(
        columnNames(0) -> event.id,
        columnNames(1) -> event.title,
        columnNames(2) -> event.description,
        columnNames(3) -> event.category,
        columnNames(4) -> event.startTime,
        columnNames(5) -> event.endTime,
        columnNames(6) -> event.latitude,
        columnNames(7) -> event.longitude
      )
  }
}


