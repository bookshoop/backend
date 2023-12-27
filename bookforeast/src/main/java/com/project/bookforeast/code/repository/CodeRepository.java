package com.project.bookforeast.code.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookforeast.code.entity.Code;

public interface CodeRepository extends JpaRepository<Code, Long>{

	@Query("SELECT c FROM Code c LEFT JOIN FETCH c.parentCode WHERE c.codename = :codename")
	public Code findAllByCodename(String codename);

}
