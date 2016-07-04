package com.lemur.services

import com.lemur.common.Constants._
import com.lemur.model.CommonMessageModel
import com.lemur.utils.GoogleVerifier
import com.wordnik.swagger.annotations._
import spray.http.MediaTypes
import spray.routing.HttpService

@Api(value = "/login", description = "Google Login API")
trait LoginService extends HttpService {

  @ApiOperation(httpMethod = "GET", value = "Validate token", notes = "Returns success response if token is validate", response = classOf[CommonMessageModel])
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "token", value = "Token for google account.", required = true, dataType = "String", paramType = "header")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Valid token."),
    new ApiResponse(code = 400, message = "Invalid token."),
    new ApiResponse(code = 401, message = "Unauthorized access.")
  ))
  def loginRoute =
    path(loginPath) {
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          headerValueByName(headerTokenKey) { token =>
            try {
              if (verifyToken(token)) {
                respondWithStatus(200) {
                  complete(tokenApprovedMessage)
                }
              } else {
                respondWithStatus(401) {
                  complete(unauthorizedAccessMessage)
                }
              }
            }catch {
                case e:IllegalArgumentException =>
                  respondWithStatus(400) {
                    complete(invalidTokenMessage)
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
