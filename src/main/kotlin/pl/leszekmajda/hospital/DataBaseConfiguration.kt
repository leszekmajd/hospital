package pl.leszekmajda.hospital

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.*
import pl.leszekmajda.hospital.dao.*
import java.time.LocalDateTime

@Configuration
class DataBaseConfiguration {

    @Bean
    fun databaseInitializer(patientRepository: PatientRepository,
                            doctorRepository: DoctorRepository,appointmentRepository: AppointmentRepository) = ApplicationRunner {

        val patient1=patientRepository.save(Patient("Mark", "Twain", "NY, 6th ave"))
        val patient2=patientRepository.save(Patient("Adam", "Smith", "Berlin, Goethe str. 5"))
        val doctor1=doctorRepository.save(Doctor("John", "Harper", "Cardiologist"))
        val doctor2=doctorRepository.save(Doctor("Michael", "Colins", "Laryngologist"))
        appointmentRepository.save(Appointment(LocalDateTime.parse("2018-12-12T16:50:00"), "Cabinet 108", patient1,  doctor1))
        appointmentRepository.save(Appointment(LocalDateTime.parse("2018-11-12T12:30:00"), "Cabinet 134", patient2,  doctor1))
        appointmentRepository.save(Appointment(LocalDateTime.parse("2018-12-11T13:50:00"), "Cabinet 765", patient1,  doctor2))
        appointmentRepository.save(Appointment(LocalDateTime.parse("2018-11-11T14:10:00"), "Cabinet 112", patient2,  doctor2))
    }
}