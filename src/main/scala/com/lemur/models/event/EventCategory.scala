package com.lemur.models.event

/**
  * Created by lemur on 27/06/16.
  */
sealed trait EventCategory

final case class Sport() extends EventCategory

final case class Party() extends EventCategory

final case class General() extends EventCategory




