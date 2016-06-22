package com.lemur.services

import com.lemur.common.Constants._
import spray.http.MediaTypes
import spray.routing.{ HttpService }
import com.lemur.utils.GoogleVerifier
import com.lemur.utils.Verifier

/**
 * Created by djhurley on 02/04/16.
 */
trait LoginService extends HttpService {

  val loginRoute =
    path(loginPath) {
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          headerValueByName(headerTokenKey) { token =>
            if (verifyToken(token)) {
              respondWithStatus(200) {
                complete(tokenApprovedMessage)
              }
            } else {
              respondWithStatus(401) {
                complete(unauthorizedAccessMessage)
              }
            }
          }
        }
      }
    }
  def verifyToken(token: String): Boolean = {
    GoogleVerifier.verify(token)
  }

}
