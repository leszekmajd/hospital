package pl.leszekmajda.hospital.dao

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Appointment (var appointmentDate : LocalDateTime? = null, var place: String="",
                   @ManyToOne var patient: Patient, @ManyToOne var doctor: Doctor, @Id @GeneratedValue var id: Long?=null){

}