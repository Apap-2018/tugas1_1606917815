package com.apap.sipeg.service;

import com.apap.sipeg.model.PegawaiModel;
import com.apap.sipeg.repository.PegawaiDb;

public interface PegawaiService {
	
	PegawaiDb getPegawaiDb();
	PegawaiModel getDataPegawaiByNIP(String nip);
	
}