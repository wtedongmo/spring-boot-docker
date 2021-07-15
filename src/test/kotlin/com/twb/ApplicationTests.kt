package com.twb

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes=[Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

	@Autowired
	lateinit var restTemplate : TestRestTemplate

	@LocalServerPort
	private val port: Int = 0

	private fun getRootUrl(): String {
		return "http://localhost:$port"
	}

	@Test
	fun contextLoads() {
	}


	@Test
	fun testGetMessage(){

		val headers = HttpHeaders()
		val entity = HttpEntity<String>(null, headers)

		val response = restTemplate.exchange(getRootUrl() + "/",
				HttpMethod.GET, entity, String::class.java)

		assert(response.body.toString().equals("Hello Docker World"))
	}
}
