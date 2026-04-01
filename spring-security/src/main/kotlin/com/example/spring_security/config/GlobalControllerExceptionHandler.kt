package com.example.spring_security.config

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.jwt.JwtDecoderInitializationException
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.RestClientException

@ControllerAdvice
class GlobalControllerExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleKeycloakConnectionError(exception: Exception): ResponseEntity<String> {
        println(exception)
        return ResponseEntity("Service temporarily unavailable, can not connect to the authentication service.", HttpStatus.SERVICE_UNAVAILABLE)
    }
}