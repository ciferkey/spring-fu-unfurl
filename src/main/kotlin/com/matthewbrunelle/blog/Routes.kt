package com.matthewbrunelle.blog

import org.springframework.web.reactive.function.server.router

fun routes(urlHandler: UrlHandler) = router {
    GET("/unfurl/{url}", urlHandler::unfurl)
}