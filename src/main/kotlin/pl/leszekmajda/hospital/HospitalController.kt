package pl.leszekmajda.hospital

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import pl.leszekmajda.hospital.dao.*
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

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

    @PostMapping("/patient")
    fun addPatient(request: HttpServletRequest, model: Model): String {
        patientRepository.save(Patient(request.getParameter("name"), request.getParameter("surname"),
                request.getParameter("address")))
        model["title"] = "RSQ Hospital > List of patients"
        model["patients"] = patientRepository.findAll().map { it }
        return "patients"
    }

    @GetMapping("/patient/{id}")
    fun patient(@PathVariable id: Long, model: Model): String {
        val patient = patientRepository.findById(id).get()
        model["title"] = "RSQ Hospital > Patient id: ${patient.id}"
        model["patient"] = patient
        return "patient"
    }

    @GetMapping("/patient/delete/{id}")
    fun deletePatient(@PathVariable id: Long, model: Model): String {
        patientRepository.deleteById(id)
        model["title"] = "RSQ Hospital > List of doctors :: Patient id: $id deleted successfuly"
        model["patients"] = patientRepository.findAll().map { it }
        return "patients"
    }

    @GetMapping("/doctor/delete/{id}")
    fun deleteDoctor(@PathVariable id: Long, model: Model): String {
        doctorRepository.deleteById(id)
        model["title"] = "RSQ Hospital > List of doctors :: Doctor id: $id deleted successfuly"
        model["doctors"] = doctorRepository.findAll().map { it }
        return "doctors"
    }

    @GetMapping("/doctor")
    fun doctors(model: Model): String {
        model["title"] = "RSQ Hospital > List of doctors"
        model["doctors"] = doctorRepository.findAll().map { it }
        return "doctors"
    }

    @PostMapping("/doctor")
    fun addDoctor(request: HttpServletRequest, model: Model): String {
        doctorRepository.save(Doctor(request.getParameter("name"), request.getParameter("surname"),
                request.getParameter("specialisation")))
        model["title"] = "RSQ Hospital > List of doctors"
        model["doctors"] = doctorRepository.findAll().map { it }
        return "doctor"
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
        model["patients"]  = patientRepository.findAll().map { it }
        model["doctors"] = doctorRepository.findAll().map { it }
        return "appointments"
    }

    @PostMapping("/appointment")
    fun addAppointment(request: HttpServletRequest, model: Model): String {
        val patientId=request.getParameter("patient-id").toLong()
        val doctorId=request.getParameter("doctor-id").toLong()
        val date= LocalDateTime.parse(request.getParameter("date"))
        val place=request.getParameter("place")
        val patient=patientRepository.findById(patientId).get()
        val doctor=doctorRepository.findById(doctorId).get()
        appointmentRepository.save(Appointment(date,place,patient,doctor))
        model["title"] = "RSQ Hospital > List of Appointments"
        model["patients"]  = patientRepository.findAll().map { it }
        model["doctors"] = doctorRepository.findAll().map { it }
        model["appointments"] = appointmentRepository.findAll().map { it }
        return "appointments"
    }

    @GetMapping("/appointment/delete/{id}")
    fun deleteAppointment(@PathVariable id: Long, model: Model): String {
        appointmentRepository.deleteById(id)
        model["title"] = "RSQ Hospital > List of doctors :: Appointment id: $id deleted successfuly"
        model["patients"]  = patientRepository.findAll().map { it }
        model["doctors"] = doctorRepository.findAll().map { it }
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




