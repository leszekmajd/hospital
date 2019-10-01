package pl.leszekmajda.hospital.dao

import jdk.nashorn.internal.runtime.JSType.toLong
import javax.persistence.*

@Entity
class Patient (var name: String="",var surname: String="", var address: String="",  @Id @GeneratedValue  var id: Long?=null){

}