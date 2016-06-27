package com.lemur.models.event

/**
  * Created by lemur on 27/06/16.
  */
sealed trait EventSubCategory

final case class Free() extends EventSubCategory

final case class Paid() extends EventSubCategory