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
        val doctor1=patientRepository.save(Patient("John", "Harper", "Warsaw, Noakowskiego 5"))
        val doctor2=patientRepository.save(Patient("Michael", "Colins", "London, Queen Elisabeth str 4"))
        appointmentRepository.save(Appointment(LocalDateTime.parse("2018-12-12T16:50:00"), "Cabinet 108.",  doctor1, patient1))
        appointmentRepository.save(Appointment(LocalDateTime.parse("2018-11-12T12:30:00"), "Cabinet 134.",  doctor1, patient2))
        appointmentRepository.save(Appointment(LocalDateTime.parse("2018-12-11T13:50:00"), "Cabinet 765.",  doctor2, patient1))
        appointmentRepository.save(Appointment(LocalDateTime.parse("2018-11-11T14:10:00"), "Cabinet 112.",  doctor2, patient2))
    }
}