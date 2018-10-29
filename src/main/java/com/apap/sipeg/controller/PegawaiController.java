package com.apap.sipeg.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	//fitur 2 udah tapi no depan masih 12 terus
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.GET)
	private String add(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setListJabatan(new ArrayList<JabatanModel>());
		pegawai.getListJabatan().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		return "add_pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST, params= {"addRow"})
	private String addRow(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawai.getListJabatan().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		return "add_pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.POST, params={"deleteRow"})
	public String deleteRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		System.out.println(rowId);
		pegawai.getListJabatan().remove(rowId.intValue());
	    model.addAttribute("pegawai", pegawai);
	    return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String kode = String.valueOf(pegawai.getInstansi().getId());
		
		SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
		String tanggalLahir = newFormat.format(pegawai.getTanggalLahir()).replaceAll("-", "");
		
		String tahunKerja = pegawai.getTahunMasuk();

		int urutan = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size()+1;
		
		String strUrutan;
		if(urutan<10) strUrutan="0"+urutan;
		else strUrutan=""+urutan;
		
		String nip = kode + tanggalLahir + tahunKerja + strUrutan;
		
		pegawai.setNip(nip);
		
		pegawaiService.addPegawai(pegawai);
		
		String msg = "Pegawai dengan NIP "+ nip +" berhasil ditambahkan";
		model.addAttribute("message", msg);
		return "success";
	}
	
	//fitur 3 udah tapi no depan masih 12 terus
	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.GET)
	public String updatePegawai(@RequestParam("nip") String nip, Model model) {
		
		PegawaiModel pegawai = pegawaiService.getDataPegawaiByNIP(nip);		
	    model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi()));
		
	    return "update_pegawai";
	}
	@RequestMapping(value="/pegawai/ubah", params={"addRow"}, method = RequestMethod.POST)
	public String addRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi()));
		
		ProvinsiModel provinsi = pegawai.getInstansi().getProvinsi();
		model.addAttribute("selectedItem", provinsi);
		
		pegawai.getListJabatan().add(new JabatanModel());
	    model.addAttribute("pegawai", pegawai);
	    
	    return "update_pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"deleteRow"}, method = RequestMethod.POST)
	public String deleteRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		model.addAttribute("listInstansi", instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi()));
		
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		pegawai.getListJabatan().remove(rowId.intValue());
	    model.addAttribute("pegawai", pegawai);
	    return "update_pegawai";
	}
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String updatePegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String oldNip = pegawai.getNip();
		PegawaiModel oldPegawai = pegawaiService.getDataPegawaiByNIP(oldNip);
		
		String newNip;
		if((!oldPegawai.getTahunMasuk().equals(pegawai.getTahunMasuk())) || 
				(!oldPegawai.getTanggalLahir().equals(pegawai.getTanggalLahir())) || 
				(!oldPegawai.getInstansi().equals(pegawai.getInstansi()))) {
			
			String kode = String.valueOf(pegawai.getInstansi().getId());
			
			SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
			String tanggalLahir = newFormat.format(pegawai.getTanggalLahir()).replaceAll("-", "");
			
			String tahunKerja = pegawai.getTahunMasuk();
			
			int urutan = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size()+1;
			
			String strUrutan;
			if(urutan<10) strUrutan="0"+urutan;
			else strUrutan=""+urutan;
			
			newNip = kode + tanggalLahir + tahunKerja + strUrutan;
			pegawai.setNip(newNip);
		}
		else {
			 newNip = oldNip;
			 pegawai.setNip(oldNip);
		}
		
		
		pegawaiService.updatePegawai(oldNip, pegawai);
		
		String msg = "Pegawai dengan NIP "+ newNip +" berhasil diubah";
		model.addAttribute("message", msg);
		return "success";
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
	
	//fitur 10 done
	@RequestMapping(value = "/pegawai/tertua-termuda")
	private String viewPegawai(@RequestParam("id") long id, Model model) {
		List<PegawaiModel> listPegawai = pegawaiService.getTuaMudaInstansi(instansiService.getInstansiById(id));
		model.addAttribute("pegawaiTertua", listPegawai.get(0));
		model.addAttribute("pegawaiTermuda", listPegawai.get(listPegawai.size()-1));
		return "view_pegawai_tertua_termuda";
	}
}