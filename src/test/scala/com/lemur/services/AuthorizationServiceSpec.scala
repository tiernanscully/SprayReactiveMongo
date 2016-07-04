//package com.lemur.services
//
//import org.scalatest.mock.MockitoSugar
//import org.specs2.mutable.Specification
//import spray.http.HttpHeaders.RawHeader
//import spray.http.StatusCodes._
//import spray.http.{ContentTypes, HttpEntity}
//import spray.testkit.Specs2RouteTest
//
///**
// * Created by djhurley on 27/04/16.
// */
//
//trait ExtendedAuthorizationService extends AuthorizationService {
//  override def verifyToken(token: String): Boolean = {
//    token == "123456789"
//  }
//}
//
//class AuthorizationServiceSpec extends Specification with Specs2RouteTest with ExtendedAuthorizationService with MockitoSugar {
//  def actorRefFactory = system;
//
//  val validTokenHeader = RawHeader("token", "123456789");
//  val invalidTokenHeader = RawHeader("token", "987654321");
//
//  "AuthorizationService" should {
//
//    "return a message saying 'Token approved' for GET requests to the /v1/api/authorize/google path if token is verified as real" in {
//
//      Get("/v1/api/authorize/google") ~> addHeader(validTokenHeader) ~> authorizationRoutes ~> check {
//        status === OK;
//        body shouldEqual HttpEntity(ContentTypes.`application/json`, "{\"message\": \"Token approved\"}");
//      }
//    }
//
//    "return a message saying 'Unauthorized access' for GET requests to the /v1/api/authorize/google path if token is not verified as real" in {
//
//      Get("/v1/api/authorize/google") ~> addHeader(invalidTokenHeader) ~> authorizationRoutes ~> check {
//        status === Unauthorized;
//        body shouldEqual HttpEntity(ContentTypes.`application/json`, "{\"message\": \"Unauthorized access\"}");
//      }
//    }
//  }
//}
