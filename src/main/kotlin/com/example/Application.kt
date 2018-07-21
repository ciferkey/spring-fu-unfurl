package com.example

import org.springframework.fu.application
import org.springframework.fu.module.logging.*
import org.springframework.fu.module.logging.logback.logback
import org.springframework.fu.module.logging.logback.debug
import org.springframework.fu.module.logging.logback.consoleAppender
import org.springframework.fu.module.logging.logback.rollingFileAppender
import org.springframework.fu.module.webflux.jackson.jackson
import org.springframework.fu.module.webflux.netty.netty
import org.springframework.fu.module.webflux.webflux
import org.springframework.fu.ref
import java.net.URLDecoder

val app = application {
    bean<UrlHandler>()
	bean<URLDecoder>() // can spring web handle decoding params?
	logging {
		level(LogLevel.INFO)
		logback {
			consoleAppender()
		}
	}
	webflux {
		val port = if (profiles.contains("test")) 8181 else 8080
		server(netty(port)) {
			codecs {
				jackson()
			}
			include { routes(ref()) }
		}
	}
}

fun main(args: Array<String>) = app.run(await = true)