package com.lemur.services

import org.mockito.Mockito
import com.lemur.utils.GoogleVerifier
import org.scalatest.mock.MockitoSugar
import org.specs2.mutable.Specification
import spray.http.HttpHeaders.RawHeader
import spray.http.{StatusCodes, ContentTypes, HttpEntity}
import spray.testkit.Specs2RouteTest
import StatusCodes._

/**
  * Created by djhurley on 27/04/16.
  */
class LoginServiceSpec extends Specification with Specs2RouteTest with LoginService with MockitoSugar{
  def actorRefFactory = system;

  val validTokenHeader = RawHeader("token", "123456789");
  val invalidTokenHeader = RawHeader("token", "987654321");
  this.googleVerifier = mock[GoogleVerifier];
  Mockito.when(googleVerifier.verify("123456789")).thenReturn(true);

  "LoginService" should {

    "return a message saying 'Token approved' for GET requests to the /login path if token is verified as real" in {

      Get("/login") ~> addHeader(validTokenHeader) ~> loginRoute ~> check {
        status === OK;
        body shouldEqual HttpEntity(ContentTypes.`application/json`, "{\"message\": \"Token approved\"}");
      }
    }

    "return a message saying 'Unauthorized access' for GET requests to the /login path if token is not verified as real" in {

      Get("/login") ~> addHeader(invalidTokenHeader) ~> loginRoute ~> check {
        status === Unauthorized;
        body shouldEqual HttpEntity(ContentTypes.`application/json`, "{\"message\": \"Unauthorized access\"}");
      }
    }
  }
}