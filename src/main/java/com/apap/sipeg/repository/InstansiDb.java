package com.apap.sipeg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.sipeg.model.InstansiModel;

@Repository
public interface InstansiDb extends JpaRepository<InstansiModel, Long>{
	
	InstansiModel findById(long id);

}