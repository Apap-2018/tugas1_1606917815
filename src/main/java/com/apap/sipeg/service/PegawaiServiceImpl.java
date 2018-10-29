package com.apap.sipeg.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.sipeg.model.InstansiModel;
import com.apap.sipeg.model.JabatanModel;
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
	public PegawaiModel getDataPegawaiById(long id) {
		return pegawaiDb.findById(id);
	}

	@Override
	public PegawaiDb getPegawaiDb() {
		return pegawaiDb;
	}
	
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}
	
	@Override
	public List<PegawaiModel> getTuaMudaInstansi(InstansiModel instansi){
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan){
		List<PegawaiModel> hasil = new ArrayList<>();
		
		for(PegawaiModel pegawai : pegawaiDb.findByInstansi(instansi)) {
			if (pegawai.getListJabatan().contains(jabatan)) {
				hasil.add(pegawai);
			}
		}
		
		return hasil;
	}
	
	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		return pegawaiDb.findByInstansi(instansi);
	}
	
	@Override
	public List<PegawaiModel> getPegawaiByProvinsiAndJabatan(Long provinsiId, JabatanModel jabatan){
		List<PegawaiModel> hasil = new ArrayList<>();
		
		for(PegawaiModel pegawai : this.getPegawaiByProvinsi(provinsiId)) {
			if (pegawai.getListJabatan().contains(jabatan)) {
				hasil.add(pegawai);
			}
		}
		
		return hasil;
	}
	
	@Override
	public List<PegawaiModel> getPegawaiByProvinsi(Long provinsiId) {
		List<PegawaiModel> hasil = new ArrayList<>();
		
		for(PegawaiModel pegawai : pegawaiDb.findAll()) {
			if (pegawai.getInstansi().getProvinsi().getId() == provinsiId) {
				hasil.add(pegawai);
			}
		}
		
		return hasil;
	}
	
	@Override
	public List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan){
		return pegawaiDb.findByListJabatan(jabatan);
	}
	
	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk) {
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}
	
	@Override
	public void updatePegawai(String nip, PegawaiModel pegawai) {
		PegawaiModel updatePegawai = pegawaiDb.findByNip(nip);
		updatePegawai.setNama(pegawai.getNama());
		updatePegawai.setNip(pegawai.getNip());
		updatePegawai.setTanggalLahir(pegawai.getTanggalLahir());
		updatePegawai.setTempatLahir(pegawai.getTempatLahir());
		updatePegawai.setTahunMasuk(pegawai.getTahunMasuk());
		updatePegawai.setInstansi(pegawai.getInstansi());
		updatePegawai.setListJabatan(pegawai.getListJabatan());
		pegawaiDb.save(updatePegawai);
	}
	
//	public void setNipPegawai(PegawaiModel pegawai) {
//
//		String nipTglLahir = "";
//		
//		Date tglLahir = pegawai.getTanggalLahir();
//		String[] tanggalLahir = (String.valueOf(tglLahir).split("-"));
//		for (int i = 0; i < tanggalLahir.length; i++) {
//			nipTglLahir = tanggalLahir[i].substring(tanggalLahir[i].length()-2, tanggalLahir[i].length()) + nipTglLahir;
//		}
//		
//		List<PegawaiModel> listPegawai = pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasukOrderByNipAsc(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk());
//		int nomorPegawaiTemp = 0;
//		if (listPegawai.isEmpty()) {
//			nomorPegawaiTemp = 1;
//		} else {
//			PegawaiModel lastPegawai = listPegawai.get(listPegawai.size()-1);
//			nomorPegawaiTemp = Integer.valueOf(lastPegawai.getNip().substring(lastPegawai.getNip().length()-2)) + 1;
//		}
//		String nomorPegawai = (nomorPegawaiTemp < 10 ? "0" : "") + nomorPegawaiTemp;
//		
//		String nip = pegawai.getInstansi().getId() + nipTglLahir + pegawai.getTahunMasuk() + nomorPegawai;
//		
//		pegawai.setNip(nip);
//	}
	
	
}