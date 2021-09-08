package br.org.generation.farmacia.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.farmacia.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	public List <Produto> findAllByNomeContainingIgnoreCase(String nome);
	
	/**
	 *  Método Personalizado - Buscar por Nome do Produto e pelo Nome do Laboratório
	 *  
	 *  MySQL: select * from tb_produtos where nome = "produto" and laboratorio = "laboratorio";
	 */
	 
	public List <Produto> findByNomeAndLaboratorio(String nome, String laboratorio);
	
	/**
	 *  Método Personalizado - Buscar por Nome do Produto ou pelo Nome do Laboratório
	 *  
	 *  MySQL: select * from tb_produtos where nome = "produto" or laboratorio = "laboratorio";
	 */
	public List <Produto> findByNomeOrLaboratorio(String nome, String laboratorio);
	
	/**
	 *  Método Personalizado - Buscar todos os Produtos cujo o preço seja maior do que um valor digitado 
	 *  ordenado pelo preço em ordem crescente
	 *  
	 *  MySQL: select * from tb_produtos where preco > preco order by preco;
	 */
	 
	public List <Produto> findByPrecoGreaterThanOrderByPreco(BigDecimal preco);
	
	/**
	 *  Método Personalizado - Buscar todos os Produtos cujo o preço seja menor do que um valor digitado 
	 *  ordenado pelo preço em ordem decrescente
	 *  
	 *  MySQL: select * from tb_produtos where preco < preco order by preco;
	 */
	 
	public List <Produto> findByPrecoLessThanOrderByPrecoDesc(BigDecimal preco);

}
