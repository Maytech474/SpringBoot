package br.org.generation.lojagames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.generation.lojagames.model.Produto;

@Repository
public interface ProdutoRepository  extends JpaRepository<Produto, Long>{
	
	public List<Produto> findAllByNomeContainingIgnoreCase(String nome);

	/**
	 * Método contarProdutos: Retorna a contagem de produtos conforme o id
	 * informado no parâmetro
	 * 
	 * Anottation @Query -> Permite executar uma consulta no Spring utilizando
	 * código SQL
	 * 
	 * :id -> Parâmetro da consulta SQL
	 * 
	 * nativeQuery = true -> Indica que o código da consulta é o SQL nativo que
	 * é compatível com qualquer SGBD Relacional
	 * 
	 * @Param("id") -> Mapeia o parâmetro :id da consulta na variável long id 
	 * que é o parâmetro do método contarProdutos
	 * 
	 */
	@Query(value = "select count(categoria_id) from tb_produtos where categoria_id = :id", nativeQuery = true)
	public int contarProdutos(@Param("id") long id);

}