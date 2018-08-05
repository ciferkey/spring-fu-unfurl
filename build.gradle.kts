import org.gradle.kotlin.dsl.version
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.jetbrains.kotlin.jvm") version "1.2.51"
	id("io.spring.dependency-management") version "1.0.5.RELEASE"
	id("org.springframework.boot") version "2.0.3.RELEASE"
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.fu:spring-fu-dependencies:0.0.1.BUILD-SNAPSHOT")
	}
}

dependencies {
	implementation("org.springframework.fu.module:spring-fu-webflux-jackson")
	implementation("org.springframework.fu.module:spring-fu-logging-logback")
	implementation("org.springframework.fu.module:spring-fu-webflux-netty")
	compile("org.jsoup:jsoup:1.8.3")
	testImplementation("org.springframework.fu.module:spring-fu-test")
}

repositories {
	mavenCentral()
	maven("https://repo.spring.io/libs-milestone")
	maven("https://repo.spring.io/snapshot")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		jvmTarget = "1.8"
		freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
	}
}