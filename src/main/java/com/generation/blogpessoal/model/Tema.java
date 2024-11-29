package com.generation.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="tb_tema")
public class Tema {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "O atributo descrição é obrigatório")
	private String descricao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tema", cascade = CascadeType.REMOVE)//Um Tema pode ter Muitas Postagens
	@JsonIgnoreProperties("tema")	
	private List<Postagens> postagens;
	
	public List<Postagens> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<Postagens> postagem) {
		this.postagens = postagem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
//	Por que fetch e cascade estão na classe Tema?
//			fetch = FetchType.LAZY no Tema:
//
//			Para evitar carregar todas as Postagens de um Tema automaticamente, o que seria custoso em consultas.
//			Na Postagem, carregar o Tema (@ManyToOne) é geralmente mais barato, então EAGER é o padrão.
//			cascade = CascadeType.REMOVE no Tema:
//
//			Faz sentido que a exclusão de um Tema remova todas as Postagens associadas.
//			O contrário (remover uma Postagem apagando o Tema) não é desejável, então o cascade não é configurado no lado Postagem.

}
