package com.apap.sipeg.service;

import java.util.List;

import com.apap.sipeg.model.InstansiModel;
import com.apap.sipeg.model.JabatanModel;
import com.apap.sipeg.model.PegawaiModel;
import com.apap.sipeg.model.ProvinsiModel;
import com.apap.sipeg.repository.PegawaiDb;

public interface PegawaiService {
	
	PegawaiDb getPegawaiDb();
	PegawaiModel getDataPegawaiByNIP(String nip);
	PegawaiModel getDataPegawaiById(long id);
	List<PegawaiModel> getTuaMudaInstansi(InstansiModel instansiById);
	List<PegawaiModel> getPegawaiByProvinsi(Long provinsiId);
	List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan);
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
	List<PegawaiModel> getPegawaiByProvinsiAndJabatan(Long long1, JabatanModel jabatan);
	
}