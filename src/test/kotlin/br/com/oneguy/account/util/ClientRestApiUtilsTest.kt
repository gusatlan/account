package br.com.oneguy.account.util

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.net.URI

class ClientRestApiUtilsTest {

    @Test
    fun shouldBuildUri() {
        val baseUrl = "http://myawesomeserver.com/"
        val endpoint = "/v1/myawesomeendpoint"
        val uri = "http://myawesomeserver.com/v1/myawesomeendpoint"
        val headers = mapOf("Authentication" to "Bearer ")
        val request1 = PostRequest<String>(
            baseUrl = baseUrl,
            endpoint = endpoint,
            payload = """{"id": 777, "name": "Luke Skywalker"}""",
            headers = headers
        )
        val request2 = PostRequest<String>(
            baseUrl = "http://myawesomeserver.com",
            endpoint = endpoint,
            payload = """{"id": 777, "name": "Luke Skywalker"}""",
            headers = headers
        )
        val request3 = PostRequest<String>(
            baseUrl = baseUrl,
            endpoint = "v1/myawesomeendpoint",
            payload = """{"id": 777, "name": "Luke Skywalker"}""",
            headers = headers
        )

        assertEquals(uri, request1.uri.toString())
        assertEquals(uri, request2.uri.toString())
        assertEquals(uri, request3.uri.toString())
    }

    @Test
    fun shouldBuildHeaders() {
        val headers = mapOf(
            "Authentication" to "Bearer ",
            "ID" to "123"
        )

        mapToHeader(WebClient.create().post(), headers).headers {
            assertEquals(2, it.size)
        }

        mapToHeader(WebClient.create().post(), emptyMap()).headers {
            assertTrue(it.isEmpty())
        }
    }

    @Test
    fun shouldCallRestApiMono() {
        val request = PostRequest(
            baseUrl = "http://myawesomeserver.com",
            endpoint = "v1/myawesomeendpoint",
            payload = "payload"
        )
        val webClient = mockk<WebClient>()

        every {
            webClient
                .post()
                .uri(any() as URI)
                .contentType(any())
                .accept(any())
                .bodyValue(any())
                .retrieve()
                .bodyToMono(String::class.java)
        } returns Mono.just("OK")

        StepVerifier.create(
            postMono(
                client = webClient,
                request = request,
                String::class.java
            )
        )
            .expectNext("OK")
            .verifyComplete()
    }

    @Test
    fun shouldCallRestApiFlux() {
        val request = PostRequest(
            baseUrl = "http://myawesomeserver.com",
            endpoint = "v1/myawesomeendpoint",
            payload = "payload"
        )
        val webClient = mockk<WebClient>()

        every {
            webClient
                .post()
                .uri(any() as URI)
                .contentType(any())
                .accept(any())
                .bodyValue(any())
                .retrieve()
                .bodyToFlux(String::class.java)
        } returns Flux.just("OK")

        StepVerifier.create(
            postFlux(
                client = webClient,
                request = request,
                String::class.java
            )
        )
            .expectNext("OK")
            .verifyComplete()
    }
}
