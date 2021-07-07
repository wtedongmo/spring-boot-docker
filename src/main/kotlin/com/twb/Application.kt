package com.twb

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class Application {

	@RequestMapping("/")
	fun home(): String {
		return "Hello Docker World"
	}

	@RequestMapping("/message")
	fun message(): String {
		return "Hello Docker World. This is a good message"
	}

	companion object {

		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(Application::class.java, *args)
		}
	}

}