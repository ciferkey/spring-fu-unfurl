package com.example

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URLDecoder
import org.jsoup.Jsoup
import org.springframework.http.MediaType
import java.nio.charset.StandardCharsets

@Suppress("UNUSED_PARAMETER")
class UrlHandler(private val urlDecoder: URLDecoder) {
    val charSet = StandardCharsets.UTF_8.toString()

    fun unfurl(request: ServerRequest): Mono<ServerResponse> {
        // TODO: handling null url
        val url = URLDecoder.decode(request.pathVariable("url"), charSet)

        val document = Jsoup.connect(url).get()

        // TODO: url matching -> correct extractor
        // twitter, instagram, youtube

        val result = openGradeExtract(document)
                ?: defaultExtract(document)

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .syncBody(result)
    }
}

