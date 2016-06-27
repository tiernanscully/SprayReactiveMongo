package com.lemur.models.event

/**
  * Created by lemur on 27/06/16.
  */
trait Event {

  val title: String
  val description: String
  val category: EventCategory
  val subCategory: EventSubCategory

}
