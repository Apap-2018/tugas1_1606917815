package com.apap.sipeg.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.sipeg.model.InstansiModel;
import com.apap.sipeg.model.JabatanModel;
import com.apap.sipeg.model.PegawaiModel;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	
	PegawaiModel findById(long id);
	PegawaiModel findByNip(String nip);
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	List<PegawaiModel> findByListJabatan(JabatanModel listJabatan);
//	List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasukOrderByNipAsc(InstansiModel instansi,
//			Date tanggalLahir, String tahunMasuk);
	List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir,
			String tahunMasuk);

}