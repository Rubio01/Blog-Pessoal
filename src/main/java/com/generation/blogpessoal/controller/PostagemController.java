package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagens;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController // Deﬁne que a Classe é do tipo RestController que receberá requisições composta por :URL,Verbo Método HTTP, Body - corpo com dados ou não
@RequestMapping("/postagens")// Essa anotação indica que as requisições HTTP direcionadas para /postagens serão tratadas pelo método ou classe que ela anota.
@CrossOrigin(origins="*",allowedHeaders="*")
public class PostagemController {
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagens>> getAll(){
		return ResponseEntity.ok(postagemRepository.findAll());
		
	}
	
	
}