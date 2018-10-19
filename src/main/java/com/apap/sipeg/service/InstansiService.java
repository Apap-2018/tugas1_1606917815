package com.apap.sipeg.service;

import java.util.List;
import java.util.Optional;

import com.apap.sipeg.model.InstansiModel;
import com.apap.sipeg.repository.InstansiDb;

public interface InstansiService {
	List<InstansiModel> getAllInstansi();
	InstansiDb getInstansiDb();
	InstansiModel getInstansiById(long id);
	Optional<InstansiModel> getInstansiById(Long id);
	
	
}