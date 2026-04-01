package com.example.spring_security.config

import org.springframework.stereotype.Component

@Component
class Authorization {
    fun isAuthorized(authority: String) : Boolean {
        println("is authorized called with authority: $authority")
        return authority.equals("creation", true)
    }
}