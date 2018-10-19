package com.apap.sipeg.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.sipeg.model.InstansiModel;
import com.apap.sipeg.model.JabatanModel;
import com.apap.sipeg.model.PegawaiModel;
import com.apap.sipeg.model.ProvinsiModel;
import com.apap.sipeg.service.InstansiService;
import com.apap.sipeg.service.JabatanService;
import com.apap.sipeg.service.PegawaiService;
import com.apap.sipeg.service.ProvinsiService;

@Controller
public class PegawaiController{
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	//+fitur 6 done
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	//fitur 4 done
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String cariPegawai (@RequestParam(value="idProvinsi") Optional<Long> idProvinsi, 
								@RequestParam(value="idInstansi") Optional<Long> idInstansi, 
								@RequestParam(value="idJabatan") Optional<Long> idJabatan, Model model)
	{
		
		ProvinsiModel provinsi = null;
		InstansiModel instansi = null;
		JabatanModel jabatan = null;
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", new HashSet(listJabatan));
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		List<PegawaiModel> hasilPencarian = null;
		if (idProvinsi.isPresent()) {
			provinsi = provinsiService.getProvinsiById(idProvinsi.get());
			if (idInstansi.isPresent()) {
				instansi = instansiService.getInstansiById(idInstansi.get()).get();	
				//pegawai (ada) instansi (ada) jabatan (ada)
				if (idJabatan.isPresent()) {
					jabatan = jabatanService.getJabatanDetailById(idJabatan.get());	
					hasilPencarian = pegawaiService.getPegawaiByInstansiAndJabatan(instansi, jabatan);
				}
				
				//pegawai (ada) instansi (ada) jabatan (tidak ada)
				else { 
					hasilPencarian = pegawaiService.getPegawaiByInstansi(instansi);
				}
			}
			//pegawai (ada) instansi (tidak ada) jabatan (ada)
			else if (idJabatan.isPresent()) {
				jabatan = jabatanService.getJabatanDetailById(idJabatan.get());	
				hasilPencarian = pegawaiService.getPegawaiByProvinsiAndJabatan(idProvinsi.get(), jabatan);
			}
			//pegawai (ada) instansi (tidak ada) jabatan (tidak ada)
			else { 
				hasilPencarian = pegawaiService.getPegawaiByProvinsi(idProvinsi.get());
			}
		}
		else { 
			
			//pegawai (tidak ada) instansi pasti (tidak ada)
			//tidak ada kemungkinan pegawai (tidak ada) instansi (ada) jabatan (ada)
			//tidak ada kemungkinan pegawai (tidak ada) instansi (ada) jabatan (tidak ada)
		
			//pegawai (tidak ada) instansi (tidak ada) jabatan (ada)
			if (idJabatan.isPresent()) { //pegawai (tidak ada) instansi (tidak ada) jabatan (ada)
				jabatan = jabatanService.getJabatanDetailById(idJabatan.get());	
				hasilPencarian = pegawaiService.getPegawaiByJabatan(jabatan);
			}
			
			//tidak ada kemungkinan pegawai (tidak ada) instansi (tidak ada) jabatan (tidak ada)
		}
		
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("instansi", instansi);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("hasilPencarian", hasilPencarian);
		return "find_pegawai";
	}
	
	//fitur 1 done
	@RequestMapping(value = "/pegawai")
	private String viewPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getDataPegawaiByNIP(nip);
			
		double gajiPegawai = pegawai.getListJabatan().get(0).getGajiPokok();
		if(pegawai.getListJabatan().size() > 1) {
			for (int i=1; i<pegawai.getListJabatan().size(); i++) {
				 if (pegawai.getListJabatan().get(i).getGajiPokok() > gajiPegawai) {
					 gajiPegawai = pegawai.getListJabatan().get(i).getGajiPokok();
				 }
			}
		}
			
		double tunjangan = pegawai.getInstansi().getProvinsi().getPersentaseTunjangan()/100;
		gajiPegawai = gajiPegawai + (tunjangan * gajiPegawai);
		String gaji = String.format ("%.0f", gajiPegawai);
			
		model.addAttribute("gaji", gaji);
		model.addAttribute("pegawai", pegawai);
		return "view-pegawai";
		
	}
	
	//fitur 3 belum
	@RequestMapping(value = "/pegawai/ubah",  method = RequestMethod.GET)
	private String ubahPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getDataPegawaiByNIP(nip);
		model.addAttribute("pegawai", pegawai);
		return "update_pegawai";
	}
	
	//fitur 10 done
	@RequestMapping(value = "/pegawai/tertua-termuda")
	private String viewPegawai(@RequestParam("id") long id, Model model) {
		List<PegawaiModel> listPegawai = pegawaiService.getTuaMudaInstansi(instansiService.getInstansiById(id));
		model.addAttribute("pegawaiTertua", listPegawai.get(0));
		model.addAttribute("pegawaiTermuda", listPegawai.get(listPegawai.size()-1));
		return "view_pegawai_tertua_termuda";
	}
}