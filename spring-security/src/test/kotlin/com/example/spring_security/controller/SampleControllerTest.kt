package com.example.spring_security.controller

import com.example.spring_security.data.dto.SampleDto
import dasniko.testcontainers.keycloak.KeycloakContainer
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClient
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.random.Random

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@Testcontainers
class SampleControllerTest(
    @Autowired val restTestClient: RestTestClient,
) {

    @LocalServerPort private var port: Int = 0

    companion object {
        @Container
        val keycloak = KeycloakContainer("quay.io/keycloak/keycloak:latest")
            .withRealmImportFile("/spring-keycloak-test-realm.json")
            .withAdminUsername("admin").withAdminPassword("admin")

        var token = ""

        @BeforeAll
        @JvmStatic
        fun setUp() {
            val keycloakAdminClient = KeycloakBuilder.builder().
            serverUrl(keycloak.authServerUrl)
                .realm(KeycloakContainer.MASTER_REALM)
                .clientId(KeycloakContainer.ADMIN_CLI_CLIENT)
                .username(keycloak.adminUsername)
                .password(keycloak.adminPassword)
                .build()

            val userResource = keycloakAdminClient.realm("spring-keycloak-test").users()
            val user = UserRepresentation()
            user.username = "test-user"
            user.firstName = "Test"
            user.lastName = "User"
            user.email = "user@test.com"
            user.isEnabled = true
            val response = userResource.create(user)

            val userId = CreatedResponseUtil.getCreatedId(response)
            val credential = CredentialRepresentation()
            credential.type = CredentialRepresentation.PASSWORD
            credential.id = userId
            credential.value = "test123"
            credential.isTemporary = false
            userResource.get(userId).resetPassword(credential)
            val body : MultiValueMap<String, String> = LinkedMultiValueMap()
            val rc = RestClient.create()
            body.setAll(mapOf("grant_type" to "password", "client_id" to "spring-boot-app", "client_secret" to "secret123", "username" to "test-user", "password" to "test123"))
            val result = rc.post().uri("${keycloak.authServerUrl}/realms/spring-keycloak-test/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body).retrieve().toEntity(Map::class.java)
            token = result.body?.get("access_token") as String
        }

        @DynamicPropertySource
        @JvmStatic
        fun dynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri") { "${keycloak.authServerUrl}/realms/spring-keycloak-test" }
        }
    }

    @Test
    fun createSample() {
        restTestClient.post().uri("http://localhost:$port/sample/create")
            .header("Authorization", "Bearer $token")
            .body(SampleDto(Random.nextInt(), "sample"))
            .exchange().expectStatus().isOk
    }
}