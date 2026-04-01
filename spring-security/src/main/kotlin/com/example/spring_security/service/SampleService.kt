package com.example.spring_security.service

import com.example.spring_security.data.dto.SampleDto
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service

@Service
class SampleService {
    fun createSample(sampleDto: SampleDto): Boolean {
        println("sample is created: $sampleDto")
        return true
    }

    fun getSample(id: Int): SampleDto {
        return SampleDto(id, "this is a sample")
    }

    fun public(): String {
        return "this is a public unsecured API"
    }
}