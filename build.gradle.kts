import org.gradle.kotlin.dsl.version
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	// Had to downgrade kotlin compiler version:
	// https://discuss.kotlinlang.org/t/kotlin-1-2-50-causing-java-lang-illegalstateexception-scriptingcompilerconfigurationcomponentregistrar-is-not-compatible-with-this-version-of-compiler/8157/16
	id("org.jetbrains.kotlin.jvm") version "1.2.41"
	id("io.spring.dependency-management") version "1.0.5.RELEASE"
	id("org.springframework.boot") version "2.0.3.RELEASE"
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.fu:spring-fu-dependencies:0.0.1.BUILD-SNAPSHOT")
	}
}

dependencies {
	implementation("org.springframework.fu.module:spring-fu-logging-logback")
	implementation("org.springframework.fu.module:spring-fu-webflux-netty")
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

tasks.withType<Test> {
	useJUnitPlatform()
}
