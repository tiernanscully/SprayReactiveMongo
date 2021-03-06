package com.lemur.utils

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.lemur.common.Constants.googleIssuer
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

trait Verifier {

  def verify(idTokenString: String): Boolean
}

object GoogleVerifier extends Verifier {

  override def verify(idTokenString: String): Boolean = {

    val clientId = ConfigFactory.load().getString("authentication.client.id")
    val transport = GoogleNetHttpTransport.newTrustedTransport
    val jsonFactory = JacksonFactory.getDefaultInstance
    val verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
      .setIssuer(googleIssuer).setAudience(ArrayBuffer(clientId)).build()
    val idToken = Option(verifier.verify(idTokenString))

    idToken match {
      case Some(token) =>
        println(token.getPayload)
        true

      case None =>
        println("Invalid ID token: " + idTokenString)
        false
    }
  }
}
