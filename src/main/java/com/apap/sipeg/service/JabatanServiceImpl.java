package com.apap.sipeg.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.sipeg.model.JabatanModel;
import com.apap.sipeg.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	
	@Autowired
	private JabatanDb jabatanDb;

	@Override
	public JabatanDb getJabatanDb() {
		return jabatanDb;
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}
	
	@Override
	public JabatanModel getJabatanDetailById(long id) {
		return jabatanDb.findById(id);
	}
	
	@Override
	public Optional<JabatanModel> getJabatanById(Long id) {
		return jabatanDb.findById(id);
	}
	
	@Override
	public List<JabatanModel> getAllJabatan() {
		return jabatanDb.findAll();
	}

//	@Override
//	public void updateJabatan(JabatanModel jabatan) {
//		JabatanModel jabatanToUpdate = jabatanDb.getOne(jabatan.getId());
//		jabatanToUpdate.setNama(jabatan.getNama());
//		jabatanToUpdate.setDeskripsi(jabatan.getDeskripsi());
//		jabatanToUpdate.setGajiPokok(jabatan.getGajiPokok());
//		
//        jabatanDb.save(jabatan);
//	}
	
	@Override
	public void updateJabatan (long id, JabatanModel newJabatan) {
		JabatanModel oldJabatan = this.getJabatanDetailById(id);
		oldJabatan.setNama(newJabatan.getNama());
		oldJabatan.setDeskripsi(newJabatan.getDeskripsi());
		oldJabatan.setGajiPokok(newJabatan.getGajiPokok());
	}
	
	@Override
	public void deleteById(long id) {
		jabatanDb.deleteById(id);
	}
	
}