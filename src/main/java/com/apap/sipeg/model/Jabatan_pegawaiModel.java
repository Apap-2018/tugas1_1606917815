package com.apap.sipeg.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jabatan_pegawai")
public class Jabatan_pegawaiModel implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

}


//package com.apap.sipeg.model;
//
//import java.io.Serializable;
//import java.math.BigInteger;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
///***Jabatan_pegawaiModel*/
//@Entity
//@Table(name="jabatan_pegawai")
//public class Jabatan_pegawaiModel implements Serializable{
//	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Size(max=20)
//	private BigInteger id;
//
//	@NotNull
//	@Size(max=20)
//	@Column(name="id_pegawai", nullable=false)
//	private BigInteger id_pegawai;
//	// foreign key ke PEGAWAI.id
//
//	@NotNull
//	@Size(max=20)
//	@Column(name="id_pejabat", nullable=false)
//	private BigInteger id_pejabat;
//	// foreign key ke JABATAN.id
//
//	public BigInteger getId() {
//		return id;
//	}
//
//	public void setId(BigInteger id) {
//		this.id = id;
//	}
//
//	public BigInteger getId_pegawai() {
//		return id_pegawai;
//	}
//
//	public void setId_pegawai(BigInteger id_pegawai) {
//		this.id_pegawai = id_pegawai;
//	}
//
//	public BigInteger getId_pejabat() {
//		return id_pejabat;
//	}
//
//	public void setId_pejabat(BigInteger id_pejabat) {
//		this.id_pejabat = id_pejabat;
//	}
//	
////	contoh hubungan di PilotModel
////	@OneToMany(mappedBy="pilot", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
////	private List<FlightModel> pilotFlight;
//
//}
//
//
//
//
//
//
//
