package com.lemur.common

/**
  * Created by djhurley on 01/05/16.
  */
object Constants {
  val ServerIpAddress = "192.168.100.161"
  val ServerPort = 8080
  val ClientId = "822785640622-f0tj5dvauvn97hieujjpbtfbffvr5uvc.apps.googleusercontent.com"
  val GoogleIssuer = "https://accounts.google.com"

  val LoginPath = "login"

  val HeaderTokenKey = "token"
  val TokenApprovedMessage = "{\"message\": \"Token approved\"}"
  val UnauthorizedAccessMessage = "{\"message\": \"Unauthorized access\"}"
}
