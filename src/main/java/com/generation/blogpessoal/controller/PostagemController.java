package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;//usado para mapear URLs para classes ou métodos.
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController; // anotação restcontroller vem desse Módulo spring web usado para criar APIsRESTfull 
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagens;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController
// Deﬁne que a Classe é do tipo RestController que receberá requisições composta  por :URL,Verbo Método HTTP, Body - corpo com dados ou não
//O Spring identifica a classe como um controlador que responderá a requisições HTTP.
//Ele registra os métodos anotados (como @GetMapping) para responder às URLs configuradas.
// RestController também é junção de duas anotações : @Controller, que indica que é uma classe controladora e @ResponseBody Garante que o retorno dos 
//métodos será diretamente enviado no corpo da resposta (em vez de renderizar uma página HTML, por exemplo). RestController é usada para criar APIs REST 
//que retornam dados (geralmente em JSON ou XML).

@RequestMapping("/postagens") // Define a URL base para todas as requisições tratadas por essa classe.
//Os métodos também podem ter endpoints proprios a partir desse endpoint base

@CrossOrigin(origins = "*", allowedHeaders = "*")
//Essa anotação configura o CORS (Cross-Origin Resource Sharing), que define quais origens externas podem acessar essa API.
//
//origins = "*": Permite que qualquer domínio (origem) consuma a API. Por exemplo um front-end em outro servidor e dominio 
//allowedHeaders = "*": Permite todos os cabeçalhos HTTP.Cabeçalhos são partes importantes de uma requisição ou resposta HTTP
//Isso é útil quando sua API será acessada por aplicações hospedadas em outros domínios (como um front-end hospedado em outro servidor).
public class PostagemController {

	@Autowired // injeção de dependecnia
//	O Spring cria o objeto PostagemRepository automaticamente.
//	O Spring injeta esse objeto no atributo postagemRepository.
//	A PostagemController simplesmente usa o postagemRepository sem se preocupar com sua criação.
	private PostagemRepository postagemRepository;

	@Autowired
	private TemaRepository temaRepository;

	@GetMapping
	public ResponseEntity<List<Postagens>> getAll() {

		return ResponseEntity.ok(postagemRepository.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagens> getById(@PathVariable Long id) {
		return postagemRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("titulo/{titulo}")
	public ResponseEntity<List<Postagens>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping
	public ResponseEntity<Postagens> post(@Valid @RequestBody Postagens postagem) {
		if (temaRepository.existsById(postagem.getTema().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!", null);
	}

	@PutMapping
	public ResponseEntity<Postagens> put(@Valid @RequestBody Postagens postagem) {
		if (postagemRepository.existsById(postagem.getId())) {

			if (temaRepository.existsById(postagem.getTema().getId()))
				return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!", null);

		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Postagens> postagem = postagemRepository.findById(id);

		if (postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); // erro nada encontrado
		postagemRepository.deleteById(id);
	}

}
