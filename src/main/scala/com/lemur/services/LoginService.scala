package com.lemur.services

import com.lemur.utils.GoogleVerifier
import spray.http.MediaTypes
import spray.routing.{Route, RequestContext, HttpService}

/**
  * Created by djhurley on 02/04/16.
  */
trait LoginService extends HttpService {

  val loginRoute =
    path("login") {
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          headerValueByName("token") { token =>
            val isValidToken = GoogleVerifier.verify(token)
            if(isValidToken) {
              respondWithStatus(200) {
                complete("{\"message\": \"Token approved\"}")
              }
            }else{
              respondWithStatus(401) {
                complete("{\"message\": \"Unauthorized access\"}")
              }
            }
          }
        }
      }
    }
}