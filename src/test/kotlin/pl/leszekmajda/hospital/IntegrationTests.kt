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
    fun `check status code of patients page`() {
        println("check status code of patient page")
        val entity = restTemplate.getForEntity<String>("/patient")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `check status code of doctor page`() {
        println("check status code of doctor page")
        val entity = restTemplate.getForEntity<String>("/doctor")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `check status code of appointments page`() {
        println("check status code of appointments page")
        val entity = restTemplate.getForEntity<String>("/appointment")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `check page title and content of main`() {
        println("check page title and content of main")
        val entity = restTemplate.getForEntity<String>("/")
        assertThat(entity.body).contains("<h1>RSQ Hospital</h1>")
        assertThat(entity.body).endsWith("</html>")
    }

    @Test
    fun `check pcontent of appointment list`() {
        println("check content of appointment page")
        val entity = restTemplate.getForEntity<String>("/appointment/")
        assertThat(entity.body).contains("RSQ Hospital")
        assertThat(entity.body).contains("Cabinet 108")
        assertThat(entity.body).endsWith("</html>")
    }

    @Test
    fun `check content of doctor list`() {
        println("check  content of doctor list page")
        val entity = restTemplate.getForEntity<String>("/doctor/")
        assertThat(entity.body).contains("RSQ Hospital")
        assertThat(entity.body).contains("Laryngologist")
        assertThat(entity.body).endsWith("</html>")
    }

    @Test
    fun `check content of patient list`() {
        println("check  content of patient list page")
        val entity = restTemplate.getForEntity<String>("/patient/")
        assertThat(entity.body).contains("RSQ Hospital")
        assertThat(entity.body).contains("Smith")
        assertThat(entity.body).endsWith("</html>")
    }

    @AfterAll
    fun teardown() {
        println("TODO some operation after test")
    }

}