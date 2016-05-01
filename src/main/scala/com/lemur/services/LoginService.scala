package com.lemur.services

import com.lemur.common.Constants
import com.lemur.utils.GoogleVerifier
import spray.http.MediaTypes
import spray.routing.{HttpService}

/**
  * Created by djhurley on 02/04/16.
  */
trait LoginService extends HttpService {

  var googleVerifier = new GoogleVerifier()

  val loginRoute =
    path(Constants.LoginPath) {
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          headerValueByName(Constants.HeaderTokenKey) { token =>
            val isValidToken = googleVerifier.verify(token)
            if(isValidToken) {
              respondWithStatus(200) {
                complete(Constants.TokenApprovedMessage)
              }
            }else{
              respondWithStatus(401) {
                complete(Constants.UnauthorizedAccessMessage)
              }
            }
          }
        }
      }
    }
}