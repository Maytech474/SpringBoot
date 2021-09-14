package br.org.generation.lojagames.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "O Atributo tipo não pode ser Nulo!")
	private String tipo;

	/**
	 * Atributo numeroProdutos (Não esquecer de Gerar Get e Set)
	 * 
	 * A Annotation @Transient indica que o atributo não será
	 * inserido na tabela tb_categorias, ou seja, não se torna
	 * um campo da tabela no Banco de dados.
	 * 
	 */
	@Transient
	private int numeroProdutos;

	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("categoria")
	private List<Produto> produto;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getNumeroProdutos() {
		return this.numeroProdutos;
	}

	public void setNumeroProdutos(int numeroProdutos) {
		this.numeroProdutos = numeroProdutos;
	}

	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}

}
