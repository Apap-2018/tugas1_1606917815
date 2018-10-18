package com.apap.sipeg.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.sipeg.service.PegawaiService;
import com.apap.sipeg.model.PegawaiModel;
import com.apap.sipeg.repository.InstansiDb;
import com.apap.sipeg.repository.JabatanDb;
import com.apap.sipeg.repository.PegawaiDb;
import com.apap.sipeg.repository.ProvinsiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDb pegawaiDb;
	@Autowired
	private InstansiDb instansiDb;
	@Autowired
	private ProvinsiDb provinsiDb;
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public PegawaiModel getDataPegawaiByNIP(String nip) {
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public PegawaiDb getPegawaiDb() {
		return pegawaiDb;
	}
}