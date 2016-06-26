package com.lemur.common

object Constants {
  val serverIpAddress = "0.0.0.0"
  val serverPort = 8080
  val clientId = "822785640622-f0tj5dvauvn97hieujjpbtfbffvr5uvc.apps.googleusercontent.com"
  val googleIssuer = "https://accounts.google.com"

  val loginPath = "login"

  val headerTokenKey = "token"
  val tokenApprovedMessage = "{\"message\": \"Token approved.\"}"
  val unauthorizedAccessMessage = "{\"message\": \"Unauthorized access.\"}"
  val invalidTokenMessage = "{\"message\": \"Token provided is invalid.\"}"
}
