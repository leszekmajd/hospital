package pl.leszekmajda.hospital.dao

import javax.persistence.*

@Entity
class Patient (var name: String="",var surname: String="", var address: String="",  @Id @GeneratedValue  var id: Long?=null){

}