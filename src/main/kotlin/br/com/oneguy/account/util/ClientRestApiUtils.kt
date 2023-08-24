package br.com.oneguy.account.util

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI

data class PostRequest<T : Any>(
    val baseUrl: String = "",
    val endpoint: String = "",
    val payload: T,
    val headers: Map<String, String> = emptyMap()
) {
    val uri = URI(
        buildUrl(
            baseUrl = baseUrl,
            endpoint = endpoint
        )
    )

    override fun toString() =
        """
            {
            "baseUrl": "$baseUrl",
            "endpoint": "$endpoint",
            "payload": $payload,
            "headers": ${headers.toList().joinToString(",")},
            }
        """.trimIndent()
}

fun buildUrl(baseUrl: String, endpoint: String): String {
    val base = if (baseUrl.endsWith("/")) {
        baseUrl.substring(0, baseUrl.length - 1)
    } else {
        baseUrl
    }

    val point = if (endpoint.startsWith("/")) {
        endpoint.substring(1)
    } else {
        endpoint
    }
    return "$base/$point"
}

fun mapToHeader(clientBuilder: WebClient.RequestBodySpec, map: Map<String, String>): WebClient.RequestBodySpec {
    if (map.isNotEmpty()) {
        map
            .entries
            .stream()
            .forEach { entry ->
                clientBuilder.header(entry.key, entry.value)
            }
    }
    return clientBuilder
}

fun <T : Any> post(
    client: WebClient = WebClient.create(),
    request: PostRequest<T>
): WebClient.ResponseSpec {
    return client
        .post()
        .uri(request.uri)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .apply {
            mapToHeader(
                clientBuilder = this,
                map = request.headers
            )
        }
        .bodyValue(request::payload)
        .retrieve()
}

fun <T : Any, R : Any> postMono(
    client: WebClient = WebClient.create(),
    request: PostRequest<T>,
    responseType: Class<R>
): Mono<R> = post(client = client, request = request).bodyToMono(responseType)

fun <T : Any, R : Any> postFlux(
    client: WebClient = WebClient.create(),
    request: PostRequest<T>,
    responseType: Class<R>
): Flux<R> = post(client = client, request = request).bodyToFlux(responseType)
