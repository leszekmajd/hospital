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

    @PostMapping("/patient/{id}")
    fun editPatient(@PathVariable id: Long,request: HttpServletRequest, model: Model): String {
        var patient=patientRepository.findById(id).get()
        patient.address= request.getParameter("address")
        patient.name=request.getParameter("name")
        patient.surname=request.getParameter("surname")
        patientRepository.save(patient)
        model["title"] = "RSQ Hospital > Patient id: ${patient.id}"
        model["patient"] = patient
        model["appointments"]=appointmentRepository.findAllByPatientId(id).map { it }
        return "patient"
    }

    @PostMapping("/doctor/{id}")
    fun editDoctor(@PathVariable id: Long,request: HttpServletRequest, model: Model): String {
        var doctor=doctorRepository.findById(id).get()
        doctor.specialisation= request.getParameter("specialisation")
        doctor.name=request.getParameter("name")
        doctor.surname=request.getParameter("surname")
        doctorRepository.save(doctor)
        model["title"] = "RSQ Hospital > Doctor id: ${doctor.id}"
        model["doctor"] = doctor
        model["appointments"]=appointmentRepository.findAllByPatientId(id).map { it }
        return "doctor"
    }

    @PostMapping("/appointment/{id}")
    fun editAppointment(@PathVariable id: Long,request: HttpServletRequest, model: Model): String {
        val patientId=request.getParameter("patient-id").toLong()
        val doctorId=request.getParameter("doctor-id").toLong()
        val date= LocalDateTime.parse(request.getParameter("date"))
        val place=request.getParameter("place")
        val patient=patientRepository.findById(patientId).get()
        val doctor=doctorRepository.findById(doctorId).get()
        var appointment=appointmentRepository.findById(id).get()
        appointment.doctor=doctor
        appointment.patient=patient
        appointment.place=place
        appointment.appointmentDate=date
        appointmentRepository.save(appointment)
        model["title"] = "RSQ Hospital > Appointment id: ${appointment.id}"
        model["appointment"] = appointment
        model["patients"]  = patientRepository.findAll().map { it }
        model["doctors"] = doctorRepository.findAll().map { it }
        return "appointment"
    }

    @GetMapping("/patient/{id}")
    fun patient(@PathVariable id: Long, model: Model): String {
        val patient = patientRepository.findById(id).get()
        model["title"] = "RSQ Hospital > Patient id: ${patient.id}"
        model["patient"] = patient
        model["appointments"]=appointmentRepository.findAllByPatientId(id).map { it }
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
        model["appointments"]=appointmentRepository.findAllByDoctorId(id).map { it }
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
        model["title"] = "RSQ Hospital > List of appointments"
        model["patients"]  = patientRepository.findAll().map { it }
        model["doctors"] = doctorRepository.findAll().map { it }
        model["appointments"] = appointmentRepository.findAll().map { it }
        return "appointments"
    }

    @GetMapping("/appointment/delete/{id}")
    fun deleteAppointment(@PathVariable id: Long, model: Model): String {
        appointmentRepository.deleteById(id)
        model["title"] = "RSQ Hospital > List of appointments :: Appointment id: $id deleted successfuly"
        model["patients"]  = patientRepository.findAll().map { it }
        model["doctors"] = doctorRepository.findAll().map { it }
        model["appointments"] = appointmentRepository.findAll().map { it }
        return "appointments"
    }

    @GetMapping("/patient/{id}/delete-appointment/{idAppointment}")
    fun deletePatientAppointment(@PathVariable id: Long, @PathVariable idAppointment: Long, model: Model): String {
        appointmentRepository.deleteById(idAppointment)
        val patient=patientRepository.findById(id).get()
        model["title"] = "RSQ Hospital > Patient id: ${patient.id}"
        model["patient"] = patient
        model["appointments"]=appointmentRepository.findAllByPatientId(id).map { it }
        return "patient"
    }

    @GetMapping("/doctor/{id}/delete-appointment/{idAppointment}")
    fun deleteDoctorAppointment(@PathVariable id: Long, @PathVariable idAppointment: Long, model: Model): String {
        appointmentRepository.deleteById(idAppointment)
        val doctor=doctorRepository.findById(id).get()
        model["title"] = "RSQ Hospital > Doctor id: ${doctor.id}"
        model["doctor"] = doctor
        model["appointments"]=appointmentRepository.findAllByDoctorId(id).map { it }
        return "doctor"
    }

    @GetMapping("/appointment/{id}")
    fun appointment(@PathVariable id: Long, model: Model): String {
        val appointment = appointmentRepository.findById(id).get()
        model["title"] = "RSQ Hospital > Appointment id: ${appointment.id}"
        model["appointment"] = appointment
        model["patients"]  = patientRepository.findAll().map { it }
        model["doctors"] = doctorRepository.findAll().map { it }
        return "appointment"
    }
}




