package com.apap.sipeg.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.sipeg.model.JabatanModel;
import com.apap.sipeg.model.ProvinsiModel;
import com.apap.sipeg.repository.ProvinsiDb;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService {
	
	@Autowired
	private ProvinsiDb provinsiDb;

	@Override
	public ProvinsiDb getProvinsiDb() {
		return provinsiDb;
	}
	
	@Override
	public List<ProvinsiModel> getAllProvinsi() {
		return provinsiDb.findAll();
	}
	
	@Override
	public ProvinsiModel getProvinsiById(long id) {
		return provinsiDb.findById(id);
	}
	
}