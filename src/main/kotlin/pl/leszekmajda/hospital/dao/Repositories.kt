package pl.leszekmajda.hospital.dao

import org.springframework.data.repository.CrudRepository

interface AppointmentRepository : CrudRepository<Appointment, Long> {

    fun findAllByPatientId(PatientId: Long): Iterable<Appointment>
    fun findAllByDoctorId(DoctorId: Long): Iterable<Appointment>
}

interface PatientRepository: CrudRepository<Patient, Long> {

}

interface DoctorRepository: CrudRepository<Doctor, Long> {

}