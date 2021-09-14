package br.org.generation.lojagames.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "O Atributo Nome do Produto não pode ser Nulo!")
	private String nome;

	@NotNull(message = "O AtributoDescrição não pode ser Nulo!")
	@Size(min = 5, max = 500, message = "O Atributo descrição deve ter no mínino 5 ne no máximo 500 caracteres")
	private String descricao;

	@NotNull(message = "O Atributo Console não pode ser Nulo!")
	private String console;

	private int quantidade;

	@NotNull(message = "O Atributo Preço não pode ser Nulo!")
	@PositiveOrZero(message = "O Atributo preço não pode ser Negativo!")
	private BigDecimal preco;

	/**
	 * Atributo curtidas (Não esquecer de Gerar Get e Set)
	 */
	@Column(columnDefinition = "integer default 0")
	private int curtidos;

	@ManyToOne
	@JsonIgnoreProperties("produto")
	private Categoria categoria;

	@ManyToOne
	@JsonIgnoreProperties("produto")
	private Usuario usuario;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public int getCurtidos() {
		return this.curtidos;
	}

	public void setCurtidos(int curtidos) {
		this.curtidos = curtidos;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}