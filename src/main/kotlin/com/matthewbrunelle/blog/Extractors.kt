package com.matthewbrunelle.blog

import org.jsoup.nodes.Document

fun openGradeExtract(document: Document): UnfurlResponse? {
    if (!document.select("html[prefix*=og: http://ogp.me/ns#]").any()) {
        return null
    }

    // TODO: validate these, don't assume they will be there because the
    val title = document.select("meta[property=og:title]").first()?.attr("content") ?: ""

    // We want exactly og:image or exactly og:image:url
    val thumbnailUrl = document.select("meta[property=og:image]").first()?.attr("content")
        ?: document.select("meta[property=og:image:url]").first()?.attr("content") ?: ""

    val body = document.select("meta[property=og:description]").first()?.attr("content") ?: ""

    return UnfurlResponse(title, thumbnailUrl, body)
}

fun defaultExtract(document: Document): UnfurlResponse {

    // Try article header first?
    val title = document.select("h1").firstOrNull()?.text() ?: ""

    // TODO: should thumbnail be excluded if its an article?
    val thumbnailUrl = document.select("img").firstOrNull()?.let {
        it.baseUri() + it.attr("src")
    } ?: ""

    val body = document.select("p:not(header p)").firstOrNull()?.text() ?: ""

    return UnfurlResponse(title, thumbnailUrl, body)
}