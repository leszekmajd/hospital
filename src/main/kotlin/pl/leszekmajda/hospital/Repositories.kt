package pl.leszekmajda.hospital

import org.springframework.data.repository.CrudRepository
import pl.leszekmajda.hospital.dao.Appointment
import pl.leszekmajda.hospital.dao.Doctor
import pl.leszekmajda.hospital.dao.Patient

interface AppointmentRepository : CrudRepository<Appointment, Long> {

    fun findAllByPatientId(patientId: Long): Iterable<Appointment>
    fun findAllByDoctorId(doctorId: Long): Iterable<Appointment>
}

interface PatientRepository: CrudRepository<Patient, Long> {

}

interface DoctorRepository: CrudRepository<Doctor, Long> {

}