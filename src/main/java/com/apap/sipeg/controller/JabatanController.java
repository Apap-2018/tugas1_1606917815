package com.apap.sipeg.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.sipeg.model.JabatanModel;
import com.apap.sipeg.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	private long id;
	
	//fitur 5.1 done
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "add_Jabatan";
	}
	
	//fitur 5.2 done
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		return "add_Jabatan_Berhasil";
	}
	
	//fitur 6 done
		@RequestMapping(value = "/jabatan/view")
		private String viewPegawai(@RequestParam("id") Long id, Model model) {
			JabatanModel jabatan = jabatanService.getJabatanDetailById(id);	
			model.addAttribute("jabatan", jabatan);
			return "view_jabatan";
		}
			
	//fitur 7.1 done
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam("id") Long id, Model model) {
		JabatanModel oldJabatan = jabatanService.getJabatanDetailById(id);
		model.addAttribute("jabatan", oldJabatan);
//		model.addAttribute("newJabatan", new JabatanModel());
		return "update_jabatan";
	}
	
	//fitur 7.2 done
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	 private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.updateJabatan(jabatan.getId(), jabatan);
	     return "update-success";	 
	 }
	
	//fitur 8 done
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	public String hapusJabatan (Model model, @RequestParam(value = "id", required = true) Long id) {
		try {
			jabatanService.deleteById(id);
			return "delete_Jabatan_Sukses";
		}
		catch (Exception ex) {
			return "delete_Jabatan_Gagal";
		}
		
	}
	
	//fitur 9 done
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	public String ubahJabatan (Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		return "viewall_Jabatan";
	}
	 
	
	 
}