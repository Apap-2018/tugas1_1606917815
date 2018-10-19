package com.apap.sipeg.service;

import java.util.List;
import java.util.Optional;

import com.apap.sipeg.model.JabatanModel;
import com.apap.sipeg.repository.JabatanDb;

public interface JabatanService {
	
	JabatanDb getJabatanDb();
	void addJabatan(JabatanModel jabatan);
	JabatanModel getJabatanDetailById(long id);
	Optional<JabatanModel> getJabatanById(Long id);
	void updateJabatan(long id, JabatanModel newJabatan);
	List<JabatanModel> getAllJabatan();
	void deleteById(long id);
	
}