package br.org.generation.lojagames.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.generation.lojagames.model.Categoria;
import br.org.generation.lojagames.repository.CategoriaRepository;
import br.org.generation.lojagames.repository.ProdutoRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	public List<Categoria> trendProducts(){
		
		/**
		 * Carrega na List categorias todas as categorias salvas na tabela tb_categorias
		 * do Banco de dados. 
		 */
		List<Categoria> categorias = categoriaRepository.findAll();
		
		/** 
		 * A estrutura for abaixo, irá percorrer todas as categorias que estão disponíveis
		 * no objeto List categorias e para cada objeto fará a contagem de quantos produtos 
		 * existem na tabela tb_produtos através da consulta personalizada contarProdutos, 
		 * criada na Interface ProdutoRepository.
		 * Após a contagem, o atributo numeroProdutos de cada categoria será atualizado com 
		 * o resultado da contagem.
		 */
		for (Categoria categoria : categorias) {
	
			categoria.setNumeroProdutos(produtoRepository.contarProdutos(categoria.getId()));
		}
		
		/**
		 *  A estrutura abaixo é responsável por ordenar a List categorias em ordem decrescente, 
		 *  mas para entender o código vamos analisar de dentro para a fora:
		 * 
		 *  Comparator.comparing(Categoria::getNumeroProdutos) -> Faz a ordenação da List categorias
		 *  pelo atributo numeroProdutos, da Classe Categoria, em ordem crescente através da comparação
		 *  dos elementos da List.
		 * 
		 *  Collections.reverseOrder(Comparator.comparing(Categoria::getNumeroProdutos)) -> inverte a 
		 *  ordenação da List, no caso deixando a List categorias em ordem descrescente.
		 * 
		 *  Collections.sort -> Indica qual a Collectios que será ordenada, em nosso caso a List categorias
		 *  e utiliza um objeto da Classe Comparator para definir a forma como os dados da Collection 
		 *  serão ordenados.
		 *  
		 */
		Collections.sort(categorias, Collections.reverseOrder(Comparator.comparing(Categoria::getNumeroProdutos)));
		
		return categorias;
	}
}

