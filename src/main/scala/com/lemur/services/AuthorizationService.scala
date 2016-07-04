package com.lemur.services

import com.lemur.common.Constants._
import com.lemur.models.CommonMessageModel
import com.lemur.utils.GoogleVerifier
import com.wordnik.swagger.annotations._
import spray.http.MediaTypes
import spray.routing.HttpService
import javax.ws.rs.Path

@Api(value = "v1/api/authorize", description = "Authorization API")
trait AuthorizationService extends HttpService {

  import com.lemur.utils.Json4sSupport._

  def authorizationRoutes = googleAuthorizationRoute

  @Path("/google")
  @ApiOperation(httpMethod = "GET", value = "Validate google token", notes = "Returns success response if token is validate", response = classOf[CommonMessageModel])
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "token", value = "Token for google account.", required = true, dataType = "String", paramType = "header")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Valid token."),
    new ApiResponse(code = 400, message = "Invalid token."),
    new ApiResponse(code = 401, message = "Unauthorized access.")
  ))
  def googleAuthorizationRoute =
    path("authorize" / "google") {
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          headerValueByName(headerTokenKey) { token =>
            try {
              if (verifyToken(token)) {
                respondWithStatus(200) {
                  complete(new CommonMessageModel(tokenApprovedMessage))
                }
              } else {
                respondWithStatus(401) {
                  complete(new CommonMessageModel(unauthorizedAccessMessage))
                }
              }
            }catch {
                case e:IllegalArgumentException =>
                  respondWithStatus(400) {
                    complete(new CommonMessageModel(invalidTokenMessage))
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
