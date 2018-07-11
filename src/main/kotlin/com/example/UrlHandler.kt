package com.example

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class UrlHandler {
    fun unfurl(request: ServerRequest): Mono<ServerResponse> {
        val url = request.pathVariable("url")
        return ServerResponse.ok().syncBody(url)
    }
}