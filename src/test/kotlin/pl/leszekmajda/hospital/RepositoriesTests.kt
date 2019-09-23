package pl.leszekmajda.hospital

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.*
import pl.leszekmajda.hospital.dao.*
import java.time.LocalDateTime


@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class RepositoriesTests @Autowired constructor(
        val entityManager: TestEntityManager,
        val patientRepository: PatientRepository,
        val appointmentRepository: AppointmentRepository,
        val doctorRepository: DoctorRepository) {

    @Test
    fun `When findByIdOrNull then return Appointment`() {
        val patient = Patient("Leszek", "Majda", "Poznań, Grobla")
        entityManager.persist(patient)
        val doctor = Doctor("Maciej", "Łopucki", "dermatolog")
        entityManager.persist(doctor)
        d
        val appointment = Appointment(appointmentDate=LocalDateTime.parse("2018-12-12T16:50:00"), place="Gabinet 108.", doctor = doctor, patient=patient)
        entityManager.persist(appointment)
        entityManager.flush()
        val found = appointmentRepository.findByIdOrNull(appointment.id!!)
        assertThat(found).isEqualTo(appointment)
    }

    @Test
    fun `When findById then return Patient`() {
        val patient = Patient("Leszek", "Majda", "Poznań, Grobla")
        entityManager.persist(patient)
        entityManager.flush()
        val patient2 = patientRepository.findByIdOrNull(patient.id!!)

        assertThat(patient).isEqualTo(patient2)
    }
}