package pl.leszekmajda.hospital.dao

import java.time.LocalDateTime

class Appointment {
    var appointmentDate : LocalDateTime? = null
    var patient : Patient? = null
    var Doctor : Doctor? = null
    var place : String = ""

}