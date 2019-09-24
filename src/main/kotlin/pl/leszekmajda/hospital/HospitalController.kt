package pl.leszekmajda.hospital

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import pl.leszekmajda.hospital.dao.*

@Controller
class HospitalController(private val appointmentRepository: AppointmentRepository,
                         private val doctorRepository: DoctorRepository,
                         private val patientRepository: PatientRepository) {

    @GetMapping("/")
    fun hospital(model: Model): String {
        model["title"] = "RSQ Hospital"
        return "hospital"
    }

    @GetMapping("/patient")
    fun patients(model: Model): String {
        model["title"] = "RSQ Hospital > List of patients"
        model["patients"] = patientRepository.findAll().map { it }
        return "patients"
    }

    @GetMapping("/patient/{id}")
    fun patient(@PathVariable id: Long, model: Model): String {
        val patient = patientRepository.findById(id).get()
        println(patient)
        model["title"] = "RSQ Hospital > Patient id: ${patient.id}"
        model["patient"] = patient
        return "patient"
    }

    @GetMapping("/doctor")
    fun doctors(model: Model): String {
        model["title"] = "RSQ Hospital > List of doctors"
        model["doctors"] = doctorRepository.findAll().map { it }
        return "doctors"
    }

    @GetMapping("/doctor/{id}")
    fun doctor(@PathVariable id: Long, model: Model): String {
        val doctor = doctorRepository.findById(id).get()
        model["title"] = "RSQ Hospital > Doctor id: ${doctor.id}"
        model["doctor"] = doctor
        return "doctor"
    }

    @GetMapping("/appointment")
    fun appointments(model: Model): String {
        model["title"] = "RSQ Hospital > List of appointments"
        model["appointments"] = appointmentRepository.findAll().map { it }
        return "appointments"
    }

    @GetMapping("/appointment/{id}")
    fun appointment(@PathVariable id: Long, model: Model): String {
        val appointment = appointmentRepository.findById(id).get()
        model["title"] = "RSQ Hospital > Appointment id: ${appointment.id}"
        model["appointment"] = appointment
        return "appointment"
    }
}




