package pl.leszekmajda.hospital

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

    @BeforeAll
    fun setup() {
        println("TODO some operation before tests")
    }

    @Test
    fun `check status code of main page`() {
        println("check status code of main page")
        val entity = restTemplate.getForEntity<String>("/")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `check page title and end tag`() {
        println("check page title and end tag ")
        val entity = restTemplate.getForEntity<String>("/")
        assertThat(entity.body).contains("<h1>RSQ Hospital</h1>")
        assertThat(entity.body).endsWith("</html>")
    }

    @AfterAll
    fun teardown() {
        println("TODO some operation after test")
    }

}