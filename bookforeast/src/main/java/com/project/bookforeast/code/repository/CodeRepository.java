package com.project.bookforeast.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.bookforeast.code.entity.Code;

public interface CodeRepository extends JpaRepository<Code, Long>{

	@Query("SELECT c FROM Code c " +
		       "LEFT JOIN FETCH c.parentCode " +
		       "LEFT JOIN FETCH c.childCodes " +
		       "WHERE c.codename = :codename")
	public Code findAllByCodename(String codename);


	public Code findByCodename(String codename);

}
