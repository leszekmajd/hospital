package pl.leszekmajda.hospital.dao

import javax.persistence.*

@Entity
class Doctor ( var name: String, var specialisation: String, @Id @GeneratedValue var id: Long?=null){

}
