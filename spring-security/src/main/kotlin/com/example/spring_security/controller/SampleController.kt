package com.example.spring_security.controller

import com.example.spring_security.data.dto.SampleDto
import com.example.spring_security.service.SampleService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sample")
class SampleController(val sampleService: SampleService) {
    @PostMapping("/create")
    @PreAuthorize("@authorization.isAuthorized('creation')")
    fun createSample(@RequestBody sample: SampleDto): Boolean {
        return sampleService.createSample(sample)
    }

    @GetMapping("/get/{id}")
    fun getSample(@PathVariable id: Int): SampleDto {
     return sampleService.getSample(id)
    }

    @GetMapping("/public")
    fun public(): String {
        return sampleService.public()
    }
}