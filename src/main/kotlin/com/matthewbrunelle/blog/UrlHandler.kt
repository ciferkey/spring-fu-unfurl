package com.matthewbrunelle.blog

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URLDecoder
import org.jsoup.Jsoup
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters.fromPublisher
import org.springframework.web.reactive.function.client.WebClient
import java.nio.charset.StandardCharsets

@Suppress("UNUSED_PARAMETER")
class UrlHandler(private val urlDecoder: URLDecoder) {
    val charSet = StandardCharsets.UTF_8.toString()

    val client = WebClient.create("")

    fun unfurl(request: ServerRequest): Mono<ServerResponse> {
        // TODO: handling null url

        val url = URLDecoder.decode(request.pathVariable("url"), charSet)

        val response = client.get().uri(url).retrieve().bodyToMono(String::class.java)
                .map {
                    val document = Jsoup.parse(it)
                    openGradeExtract(document)
                            ?: defaultExtract(document)
                }

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(fromPublisher(response, UnfurlResponse::class.java))
    }
}

