package br.org.generation.blogpessoal.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.blogpessoal.model.Postagem;
import br.org.generation.blogpessoal.repository.PostagemRepository;

/**
 * 
 * Annotation @RestController: Annotation (Anotação), que indica que a Classe é uma RestController,
 * ou seja, é responsável por responder às requisições http enviadas para um endpoint
 * (endereço) definido na anotação @RequestMapping
 * 
 * @RequestMapping("/postagens"): Annotation (Anotação), que indica o endpoint (endereço) 
 * que a controladora responderá as requisições 
 * 
 * @CrossOrigin("*"): Annotation (Anotação), que indica que a classe controladora permitirá o 
 * recebimento de requisições realizadas de fora do domínio (localhost, em nosso caso) ao qual 
 * ela pertence. Essa anotação é essencial para que o front-end (Angular em nosso caso), tenha
 * acesso à nossa API (O termo técnico é consumir a API)
 * 
 * Para as versões mais recentes do Angular, recomenda-se alterar esta anotação para: 
 * Annotation @CrossOrigin(origins = "*", allowedHeaders = "*") 
 * Esta anotação, além de liberar as origens, libera também os cabeçalhos das requisições
 * 
 */

@RestController
@RequestMapping("/postagens") 
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class PostagemController {
	
	/*
	 * 
	 * Injeção de Dependência (@Autowired): Consiste  na  maneira,  ou  seja,  na  implementação 
	 * utilizada pelo  Spring  Framework  de  aplicar  a  Inversão  de  Controle  quando  for 
	 * necessário.
	 * 
	 * A Injeção de Dependência define quais classes serão instanciadas e em quais lugares serão 
	 * injetadas quando houver necessidade. 
	 * 
	 * Em nosso exemplo a classe controladora cria um ponto de injeção da interface PostagemRepository, 
	 * e quando houver a necessidade o Spring Framework irá criar uma instância (objeto) desta interface
	 * permitindo o uso de todos os métodos (padrão ou personalizados em PostagemRepository)
	 *  
	 * */
	
	@Autowired 
	private PostagemRepository postagemRepository;
	
	/**
	 * Listar todas as Postagens
	 *  
	 * Annotation @GetMapping: Annotation (Anotação), que indica que o método abaixo responderá todaas as 
	 * requisições do tipo GET que forem enviadas no endpoint /postagens
	 * 
	 * O Método getAll() será do tipo ResponseEntity porque ele responderá a requisição (Request),
	 * com uma HTTP Response (Resposta http), neste caso Response Status 200 => OK
	 * 
	 * <List<Postagem>>: Como o Método listará todos os registros da nossa tabela, o método retornará 
	 * dentro da resposta um objeto do tipo List (Collection) preenchido com objetos do tipo Postagem,
	 * que são os dados da tabela.
	 * 
	 * return ResponseEntity.ok(postagemRepository.findAll());: Executa o método findAll(), que é um
	 * método padrão da interface JpaRepository e retorna o status OK = 200
	 * 
	 * Como o Método sempre irá criar a List independente ter ou não valores na tabela, ele sempre
	 * retornará 200.
	 */

	@GetMapping
	public ResponseEntity<List<Postagem>> getAll (){
		return ResponseEntity.ok(postagemRepository.findAll()); // OK = 200
	}
	
	/**
	 * Listar postagem por id - Forma 01: Usando if/else
	 *  
	 * @GetMapping("idifelse/{id}"): Annotation (Anotação), que indica que o método abaixo responderá todas
	 * as requisições do tipo GET que forem enviadas no endpoint /postagens/ifelse/id
	 * 
	 * O Método getByIdIfElse(@PathVariable long id) será do tipo ResponseEntity porque ele responderá a 
	 * requisição (Request), com uma HTTP Response (Resposta http), neste caso Response Status 200 => OK, 
	 * caso a Postagem seja encontrada. Caso não seja encontrada, a resposta será Not Found => 404
	 * 
	 * Annotation @PathVariable long id: Anntotation (anotação) que insere a variável de path (caminho ou url do endpoint), 
	 * passada no endereço da requisição, e insere no parâmetro id do método getByIdIfElse
	 * 
	 * Exemplo
	 * 
	 * http://localhost:8080/postagens/idifelse/1
	 * 
	 * o parâmetro id do método receberá 1 (Id que será procurado na tabela postagens via findById())
	 * 
	 * <Postagem>: Como o Método listará apenas 1 registro da nossa tabela, o método retornará 
	 * dentro da resposta um objeto do tipo Postagem, que são os dados da tabela.
	 * 
	 * Optional<Postagem> postagem = postagemRepository.findById(id);: Cria um objeto do tipo Postagem
	 * e armazena o resultado do método findById(id), que é um método padrão da interface JpaRepository
	 * O Optional serve para evitar o erro NullPointerException (Objeto nulo), caso a Postagem não seja 
	 * encontrada na tabela.
	 * 
	 * if (postagem.isPresent()): verifica se a Postagem existe
	 * 
	 * return ResponseEntity.ok(postagem.get());: Se a postagem existir, retorna o status OK = 200
	 * 
	 * return ResponseEntity.notFound().build();: Se a postagem não for encontrada, retorna o status 
	 * Not Found = 404
	 *
	 */
		
	@GetMapping("idifelse/{id}")
	public ResponseEntity<Postagem> getByIdIfElse(@PathVariable long id) {

		Optional<Postagem> postagem = postagemRepository.findById(id);
		if (postagem.isPresent()) {
			return ResponseEntity.ok(postagem.get());
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * Listar postagem por id - Forma 02: Usando try/catch
	 *  
	 * @GetMapping("idtrycatch/{id}"): Annotation (Anotação), que indica que o método abaixo responderá todas
	 * as requisições do tipo GET que forem enviadas no endpoint /postagens/ifelse/id
	 * 
	 * O Método getByIdTryCatch(@PathVariable long id) será do tipo ResponseEntity porque ele responderá a 
	 * requisição (Request), com uma HTTP Response (Resposta http), neste caso Response Status 200 => OK, 
	 * caso a Postagem seja encontrada. Caso não seja encontrada, a resposta será Not Found => 404
	 * 
	 * Annotation @PathVariable long id: Anntotation (anotação) que insere a variável de path (caminho ou url do endpoint), 
	 * passada no endereço da requisição, e insere no parâmetro id do método getByIdTryCatch
	 * 
	 * Exemplo
	 * 
	 * http://localhost:8080/postagens/idtrycatch/1
	 * 
	 * o parâmetro id do método receberá 1 (Id que será procurado na tabela postagens via findById(id))
	 * 
	 * <Postagem>: Como o Método listará apenas 1 registro da nossa tabela, o método retornará 
	 * dentro da resposta um objeto do tipo Postagem, que são os dados da tabela.
	 * 
	 * Optional<Postagem> postagem = postagemRepository.findById(id);: Cria um objeto do tipo Postagem
	 * e armazena o resultado do método findById(id), que é um método padrão da interface JpaRepository
	 * O Optional serve para evitar o erro NullPointerException (Objeto nulo), caso a Postagem não seja 
	 * encontrada na tabela.
	 * 
	 * Try/Catch: verifica se houve algum erro na execução do método findById(id) (A Postagem existe?)
	 * 
	 * return ResponseEntity.ok(postagem.get());: Se a postagem existir, retorna o status OK = 200
	 * 
	 * return ResponseEntity.notFound().build();: Se a postagem não for encontrada (erro!), retorna o status 
	 * Not Found = 404
	 *
	 */

	@GetMapping("/idtrycatch/{id}")
	public ResponseEntity<Postagem> getByIdTryCatch(@PathVariable long id) {

		Optional<Postagem> postagem = postagemRepository.findById(id);
		try {
			return ResponseEntity.ok(postagem.get());
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}

	/*
	 * Listar postagem por id - Forma 03: usando Lambda
	 *  
	 * As expressões Lambda representam uma função anônima, ou seja, uma função lambda é uma função sem declaração, 
	 * isto é, não é necessário colocar um nome, um tipo de retorno e o modificador de acesso. A ideia é que o 
	 * método seja declarado no mesmo lugar em que será usado. As expressões lambda em Java tem a sintaxe definida 
	 * como (argumento) -> (corpo)
	 * 
	 * @GetMapping("/{id}"): Annotation (Anotação), que indica que o método abaixo responderá todas
	 * as requisições do tipo GET que forem enviadas no endpoint /postagens/id
	 * 
	 * O Método getById(@PathVariable long id) será do tipo ResponseEntity porque ele responderá a 
	 * requisição (Request), com uma HTTP Response (Resposta http), neste caso Response Status 200 => OK, 
	 * caso a Postagem seja encontrada. Caso não seja encontrada, a resposta será Not Found => 404
	 * 
	 * Annotation @PathVariable long id: Anntotation (anotação) que insere a variável de path (caminho ou url do endpoint), 
	 * passada no endereço da requisição, e insere no parâmetro id do método getById
	 * 
	 * Exemplo
	 * 
	 * http://localhost:8080/postagens/1
	 * 
	 * o parâmetro id do método receberá 1 (Id que será procurado na tabela postagens via findById(id))
	 * 
	 * <Postagem>: Como o Método listará apenas 1 registro da nossa tabela, o método retornará 
	 * dentro da resposta um objeto do tipo Postagem, que são os dados da tabela.
	 * 
	 * return postagemRepository.findById(id): Retorna a execução do método findById(id)
	 * 
	 * .map(resp -> ResponseEntity.ok(resp)): Se a postagem existir, a função map (tipo Optional), aplica
	 * o valor de resp (objeto do tipo Postagem com o retorno do método findById(id) no método: 
	 * ResponseEntity.ok(resp); e retorna o status OK = 200
	 * 
	 * .orElse(ResponseEntity.notFound().build()); : Se a postagem não for encontrada, retorna 
	 * o status Not Found = 404
	 *
	 */

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable long id) {
		return postagemRepository.findById(id)
			.map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.notFound().build());
	}
	
	/*
	 * Consultar postagens por titulo 
	 * 
	 * @GetMapping("/titulo/{titulo}"): Annotation (Anotação), que indica que o método abaixo responderá todas
	 * as requisições do tipo GET que forem enviadas no endpoint /postagens/titulo/titulo
	 * 
	 * O Método getByTitulo(@PathVariable long titulo) será do tipo ResponseEntity porque ele responderá a 
	 * requisição (Request), com uma HTTP Response (Resposta http), neste caso Response Status 200 => OK, 
	 * caso a Postagem seja encontrada. Caso não seja encontrada, a resposta será Not Found => 404
	 * 
	 * @PathVariable String titulo: Anntotation (anotação) que insere a variável de path (caminho ou url do endpoint), 
	 * passada no endereço da requisição, e insere no parâmetro titulo do método getByTitulo
	 * 
	 * Exemplo
	 * 
	 * http://localhost:8080/postagens/titulo/primeira
	 * 
	 * o parâmetro titulo do método receberá "primeira" (palavra que será procurada na tabela postagens 
	 * no campo titulo via findAllByTituloContainingIgnoreCase(titulo))
	 * 
	 * <List<Postagem>>: Como o Método listará todos os registros da nossa tabela, que possuam a string enviada
	 * pelo path, o método retornará dentro da resposta um objeto do tipo List (Collection) preenchido com 
	 * objetos do tipo Postagem, que são os dados da tabela.
	 * 
	 * return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));: Executa o 
	 * método findAllByTituloContainingIgnoreCase(titulo) e retorna o status OK = 200
	 * 
	 * Como o Método sempre irá criar a List independente ter ou não valores na tabela, ele sempre
	 * retornará 200.
	 * 
	 */
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	/*
	 * Criar nova postagem 
	 * 
	 * @PostMapping: Annotation (Anotação), que indica que o método abaixo responderá todas
	 * as requisições do tipo POST que forem enviadas no endpoint /postagens
	 * 
	 * O Método ResponseEntity<Postagem> postPostagem (@RequestBody Postagem postagem) será do tipo 
	 * ResponseEntity porque ele responderá a requisição (Request), com uma HTTP Response (Resposta http), 
	 * neste caso Response Status 201 => CREATED, caso a Postagem seja inserida na tabela. Caso não seja 
	 * inserida na tabela, a resposta será Internal Server Error => 500
	 * 
	 * @RequestBody Postagem postagem: Anntotation (anotação) que insere o objeto do tipo Postagem enviado
	 * no corpo da requisição (Request Body) e insere no parâmetro postagem do método postPostagem
	 * 
	 * <Postagem>: O Método retornará dentro da resposta um objeto do tipo Postagem, que são os dados da tabela.
	 *  
	 * return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));: Executa o 
	 * método save(postagem) e retorna o status CREATED = 201 se o objeto Postagem foi inserido na tabela 
	 * postagens no Banco de dados.
	 * 
	 * Ao fazer o envio dos dados via Postman ou front-end, não é necessário passar o Id e a Data
	 * 
	 */
	
	@PostMapping
	public ResponseEntity<Postagem> postPostagem (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}
	
	/*
	 * Editar uma postagem 
	 * 
	 * @PutMapping: Annotation (Anotação), que indica que o método abaixo responderá todas
	 * as requisições do tipo PUT que forem enviadas no endpoint /postagens
	 * 
	 * O Método ResponseEntity<Postagem> putPostagem (@RequestBody Postagem postagem) será do tipo 
	 * ResponseEntity porque ele responderá a requisição (Request), com uma HTTP Response (Resposta http), 
	 * neste caso Response Status 200 => OK, caso a Postagem seja atualizada na tabela. Caso não seja 
	 * atualizada na tabela, a resposta será Internal Server Error => 500
	 * 
	 * @RequestBody Postagem postagem: Anntotation (anotação) que insere o objeto do tipo Postagem enviado
	 * no corpo da requisição (Request Body) e insere no parâmetro postagem do método postPostagem
	 * 
	 * <Postagem>: O Método retornará dentro da resposta um objeto do tipo Postagem, que são os dados da tabela.
	 *  
	 * return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));: Executa o 
	 * método save(postagem) e retorna o status OK = 200 se o objeto Postagem foi atualizado na tabela 
	 * postagens no Banco de dados.
	 * 
	 * Ao fazer o envio dos dados via Postman ou front-end é necessário passar o Id para identificar qual
	 * Postagem será atualizada. Caso o Id não seja passadao, o Spring criará um novo registro na tabela
	 *  
	 */
	
	@PutMapping
	public ResponseEntity<Postagem> putPostagem (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
	}
			
	/*
	 * Deletar uma postagem 
	 *   
	 * @DeleteMapping("/{id}"): Annotation (Anotação), que indica que o método abaixo responderá todas
	 * as requisições do tipo DELETE que forem enviadas no endpoint /postagens/id
	 * 
	 * O Método deletePostagem(@PathVariable long id)  será do tipo ResponseEntity porque ele responderá a 
	 * requisição (Request), com uma HTTP Response (Resposta http), neste caso Response Status 200 => OK, 
	 * caso a Postagem seja encontrada e excluída da tabela. Caso não seja encontrada, a resposta será 
	 * Internal Server Error => 500
	 * 
	 * @PathVariable long id: Anntotation (anotação) que insere a variável de path (caminho ou url do endpoint), 
	 * passada no endereço da requisição, e insere no parâmetro id do método deletePostagem
	 * 
	 * Exemplo
	 * 
	 * http://localhost:8080/postagens/1
	 * 
	 * o parâmetro id do método receberá 1 (Id que será procurado na tabela postagens e deletado via deleteById())
	 * 
	 * void: Como o Método não retornará nehum valor, ele foi definido como void.
	 * 
	 * postagemRepository.deleteById(id);: Apaga o registro da tabela postagens através do método deleteById(id), 
	 * que é um método padrão da interface JpaRepository o status OK = 200 se o objeto Postagem foi apagado na 
	 * tabela postagens no Banco de dados. Caso a Postagem não seja encontrada, a resposta será Internal Server 
	 * Error => 500
	 * 
	 */
	
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable long id) {
		postagemRepository.deleteById(id);
	}	

}