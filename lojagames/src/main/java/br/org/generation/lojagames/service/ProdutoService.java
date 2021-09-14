package br.org.generation.lojagames.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.org.generation.lojagames.model.Produto;
import br.org.generation.lojagames.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	/**
	 * Método Curtir -> Soma 1 ao atributo curtidos
	 */
	public Produto curtir(Long id) {

		Produto produto = buscarProdutoPeloId(id);

		produto.setCurtidos(produto.getCurtidos() + 1);

		return produtoRepository.save(produto);

	}

	/**
	 * Método Descurtir -> Subtrai 1 do atributo curtidos enquanto for
	 * maior do que zero. Se for igual a zero, mantém o zero 
	 */
	public Produto descurtir(Long id) {

		Produto produto = buscarProdutoPeloId(id);

		if (produto.getCurtidos() > 0) {

			produto.setCurtidos(produto.getCurtidos() - 1);

		} else {

			produto.setCurtidos(0);

		}

		return produtoRepository.save(produto);

	}

	/**
	 * Método buscarProdutoPeloId -> Implmenta o método findById checando
	 * se o produto existe.
	 */
	private Produto buscarProdutoPeloId(Long id) {

		Produto produtoSalvo = produtoRepository.findById(id).orElse(null);

		if (produtoSalvo == null) {

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrada!", null);
		}

		return produtoSalvo;
	}

}
