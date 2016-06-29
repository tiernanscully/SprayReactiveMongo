package com.lemur.common

object Constants {
  val googleIssuer = "https://accounts.google.com"

  val loginPath = "login"

  val headerTokenKey = "token"
  val tokenApprovedMessage = "{\"message\": \"Token approved.\"}"
  val unauthorizedAccessMessage = "{\"message\": \"Unauthorized access.\"}"
  val invalidTokenMessage = "{\"message\": \"Token provided is invalid.\"}"
}
