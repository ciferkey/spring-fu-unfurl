package com.example

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URLDecoder
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.nio.charset.StandardCharsets

/**
 * Things I love so far:
 * - nullable types
 * - elvis operator
 * - let
 */


@Suppress("UNUSED_PARAMETER")
class UrlHandler(private val urlDecoder: URLDecoder) {
    val charSet = StandardCharsets.UTF_8.toString()

    fun unfurl(request: ServerRequest): Mono<ServerResponse> {
        // TODO: handling null url
        val url = URLDecoder.decode(request.pathVariable("url"), charSet)

        val document = Jsoup.connect(url).get()

        val result = defaultExtract(document)

        return ServerResponse.ok().syncBody(result.toString())
    }

    fun defaultExtract(document: Document): Result {

        val imageUrl = document.select("img").firstOrNull()?.let {
            it.baseUri() + it.attr("src")
        } ?: ""

        return Result(imageUrl)
    }
}

// TODO: mapping results to output JSON
data class Result(val imageUrl: String)