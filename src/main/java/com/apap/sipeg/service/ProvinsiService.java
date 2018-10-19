package com.apap.sipeg.service;

import java.util.List;
import java.util.Optional;

import com.apap.sipeg.model.JabatanModel;
import com.apap.sipeg.model.ProvinsiModel;
import com.apap.sipeg.repository.ProvinsiDb;

public interface ProvinsiService {

	List<ProvinsiModel> getAllProvinsi();
	ProvinsiDb getProvinsiDb();
//	Optional<Long> getProvinsiById(Long long1);
	ProvinsiModel getProvinsiById(long id);
	
}