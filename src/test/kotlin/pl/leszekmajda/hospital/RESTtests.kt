package pl.leszekmajda.hospital

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import pl.leszekmajda.hospital.dao.*
import pl.leszekmajda.hospital.*
import java.time.LocalDateTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTests(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun `check status code of api patient `() {
        val entity = restTemplate.getForEntity<String>("/api/patient/")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `check status code of  api doctor `() {
        val entity = restTemplate.getForEntity<String>("/api/doctor/")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `check status code of api appointment`() {
        val entity = restTemplate.getForEntity<String>("/api/appointment/")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `check write and read (POST, GET) ability of doctors`() {
        val doctor  = restTemplate.postForEntity("/api/doctor/add",
                Doctor(name = "John", surname="Kenedy", specialisation="specXYZ"),
                Doctor::class.java)

        val entity = restTemplate.getForEntity<String>("/api/doctor/${doctor.body!!.id}")

        assertThat(entity.body).contains("John")
        assertThat(entity.body).contains("Kenedy")
        assertThat(entity.body).contains("specXYZ")
    }
    @Test
    fun `check write and read (POST, GET) ability of patients`() {
        val patient  = restTemplate.postForEntity("/api/patient/add",
                Patient(name = "John", surname="Kenedy", address="specAdresXYZ"),
                Patient::class.java)

        val entity = restTemplate.getForEntity<String>("/api/patient/${patient.body!!.id}")

        assertThat(entity.body).contains("John")
        assertThat(entity.body).contains("Kenedy")
        assertThat(entity.body).contains("specAdresXYZ")
    }

    @Test
    fun `check write and read (POST, GET) ability of Appointments`() {
        val patient  = restTemplate.postForEntity("/api/patient/add",
                Patient(name = "Ivo", surname="Andersone", address="addressXYZ"),
                Patient::class.java)


        val doctor  = restTemplate.postForEntity("/api/doctor/add",
                Doctor(name = "Markus", surname="Honeker", specialisation="specXYZ"),
                Doctor::class.java)

        val appointment  = restTemplate.postForEntity("/api/appointment/add",
                AppointmentController.AppointmentDTO(appointmentDate = LocalDateTime.parse("2018-12-12T16:50:00"), place = "place_of_appointmentXYZ",
                        patientId = patient.body!!.id, doctorId = doctor.body!!.id),Appointment::class.java).body

        val entity = restTemplate.getForEntity<String>("/api/appointment/${appointment!!.id}")

        assertThat(entity.body).contains("Andersone")
        assertThat(entity.body).contains("Honeker")
        assertThat(entity.body).contains("place_of_appointmentXYZ")
    }

}