package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Postagens;

public interface PostagemRepository extends JpaRepository<Postagens, Long>{
	
	public List<Postagens> findAllByTituloContainingIgnoreCase(@Param("título")String titulo);

}
