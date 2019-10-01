package pl.leszekmajda.hospital

import org.springframework.web.bind.annotation.*
import pl.leszekmajda.hospital.dao.*
import java.time.LocalDateTime


@RestController
@RequestMapping("/api/patient" )
class PatientController(private val repository: PatientRepository) {

    /*
        GET method
        Returns array of all patients
    */
    @GetMapping("/")
    fun findAll(): Iterable<Patient> = repository.findAll()

    /*
    GET method
    Returns specific patient with id given in URL
    */
    @GetMapping("/{patient}")
    fun findOne(@PathVariable patient: Patient) = patient

    /*
    For proper execution add header in request : Content-Type:"application/json"
    It is also mandatory to use json names accurate for entity you're going to create. In this case body should
    look like this:
    {
        "name": "nameString",
        "surname": "surnameString",
        "address": "addressString"
    }
    You will get created patient with Id in response
     */
    @PostMapping("/add")
    fun create(@RequestBody patient: Patient): Patient {
        return repository.save(patient)
    }

    /*
    DELETE Method
    Deletes specific patient with id given in URL
    Returns String OK if appointment has been deleted with success
    */
    @DeleteMapping("/delete/{patient}")
    @ResponseStatus
    fun delete(@PathVariable patient: Patient): String{
        repository.delete(patient)
        return "OK"
    }

    /*
    PUT method
    Updates patient given in URL. Elements for proper modification should be placed in body with json like that:
    {
        "name": "new name",
        "surname": "new surname",
        "address": "new address"
    }
    every element is optional.
    Returns modified patient
    */
    @PutMapping("/update/{patient}")
    fun update(@PathVariable patient: Patient, @RequestBody request: PatientDTO): Patient{
        patient.name = request.name ?: patient.name
        patient.surname = request.surname ?: patient.surname
        patient.address = request.address ?: patient.address
        return repository.save(patient)
    }
    //class needed for update operation.
    data class PatientDTO(val name: String? = null, val surname: String? = null , val address: String? = null)

}

@RestController
@RequestMapping("/api/doctor")
class DoctorController(private val repository: DoctorRepository) {

    /*
    GET method
    Returns array of all doctors
    */
    @GetMapping("/")
    fun findAll(): Iterable<Doctor> = repository.findAll()

    /*
    GET method
    Returns specific doctor with id given in URL
    */
    @GetMapping("/{doctor}")
    fun findOne(@PathVariable doctor: Doctor) = doctor

    /*
    For proper execution add header in request : Content-Type:"application/json"
    It is also mandatory to use json names accurate for entity you're going to create. In this case body should
    look like this:
    {
        "name": "nameString",
        "surname": "surnameString",
        "specialisation": "specialisationX"
    }
    You will get created doctor with Id in response
    */
    @PostMapping("/add")
    fun create(@RequestBody doctor: Doctor): Doctor{
        return repository.save(doctor)
    }

    /*
    DELETE Method
    Deletes specific doctor with id given in URL
    Returns String OK if appointment has been deleted with success
    */
    @DeleteMapping("/delete/{doctor}")
    @ResponseStatus
    fun delete(@PathVariable doctor: Doctor): String{
        repository.delete(doctor)
        return "OK"
    }

    /*
    PUT method
    Updates doctor given in URL. Elements for proper modification should be placed in body with json like that:
    {
        "name": "new name",
        "surname": "new surname",
        "specialisation": "new specialisation"
    }
    every element is optional.
    Returns modified doctor
    */
    @PutMapping("/update/{doctor}")
    fun update(@PathVariable doctor: Doctor, @RequestBody request: DoctorDTO): Doctor{
        doctor.name = request.name ?: doctor.name
        doctor.surname = request.surname ?: doctor.surname
        doctor.specialisation = request.specialisation ?: doctor.specialisation
        return repository.save(doctor)
    }
    //class needed for update operation.
    data class DoctorDTO(val name: String? = null, val surname: String? = null , val specialisation: String? = null)
}

@RestController
@RequestMapping("/api/appointment")
class AppointmentController(private val repository: AppointmentRepository,
                            private val patientRepository: PatientRepository,
                            private val doctorRepository: DoctorRepository) {
    /*
    GET method
    Returns array of all appointments and related ptaients and doctors
    */
    @GetMapping("/")
    fun findAll(): Iterable<Appointment> = repository.findAll()

    /*
    GET method
    Returns specific appointment with id given in URL
    */
    @GetMapping("/{appointment}")
    fun findOne(@PathVariable appointment: Appointment) = appointment

    /*
    GET method
    Returns specific Patient appointments
    */
    @GetMapping("/patient/{patientId}")
    fun findByPatient(@PathVariable patientId: Long) :Iterable<Appointment>
            = repository.findAllByPatientId(patientId)

    /*
    GET method
    Returns specific Doctor appointments
    */
    @GetMapping("/doctor/{doctorId}")
    fun findByDoctor( @PathVariable doctorId: Long) :Iterable<Appointment>
            = repository.findAllByDoctorId(doctorId)

    /*
    POST method
    For proper execution add header in request : Content-Type:"application/json"
    It is also mandatory to use json names accurate for appointment except patient and doctor references.
    patientId and doctorId should contain valid identifier.
    In this case body should look like this:
    {
        "place": "place of appointment",
        "appointmentDate": "2012-10-26T22:00",
        "patientId": 234
        "doctorId": 123
    }
    You will get created appointment with Id in response
    */
    @PostMapping("/add")
    fun create(@RequestBody request: AppointmentDTO): Appointment{
        val patient= patientRepository.findById(request.patientId!!).get()
        val doctor= doctorRepository.findById(request.doctorId!!).get()
        return repository.save(Appointment(request.appointmentDate, request.place!!, patient, doctor))
    }

    @PostMapping("/create")
    fun create(@RequestBody appointment: Appointment): Appointment{

        return repository.save(appointment)
    }

    /*
    DELETE Method
    Deletes specific appointment with id given in URL
    Returns String OK if appointment has been deleted with success
    */
    @DeleteMapping("/delete/{appointment}")
    @ResponseStatus
    fun delete(@PathVariable appointment: Appointment): String{
        repository.delete(appointment)
        return "OK"
    }

    /*
    PUT method
    Updates appointment given in URL. Elements for proper modification should be placed in body with json like that:
    {
        "place": "place of appointment",
        "appointmentDate": "2012-10-26T22:00",
        "patientId": 234
        "doctorId": 123
    }
    every element is optional.
    Returns modified appointment
    */
    @PutMapping("/update/{appointment}")
    fun update(@PathVariable appointment: Appointment, @RequestBody request: AppointmentDTO): Appointment{
        appointment.place = request.place ?: appointment.place
        appointment.appointmentDate = request.appointmentDate ?: appointment.appointmentDate
        if(request.patientId!=null){
            appointment.patient=patientRepository.findById(request.patientId).get()
        }
        if(request.doctorId!=null){
            appointment.doctor=doctorRepository.findById(request.doctorId).get()
        }
         return repository.save(appointment)
    }

    //class needed for create and update operation.
    data class AppointmentDTO(val place: String? = null, val appointmentDate: LocalDateTime? = null, val patientId: Long?  =null,
                                 val doctorId: Long? = null )

}
