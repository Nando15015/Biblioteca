package com.api.bibliotech.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.bibliotech.emprestimos.DadosAlteracaoEmprestimo;
import com.api.bibliotech.emprestimos.DadosCadastroEmprestimo;
import com.api.bibliotech.emprestimos.DadosListagemEmprestimo;
import com.api.bibliotech.emprestimos.Emprestimo;
import com.api.bibliotech.emprestimos.EmprestimoRepository;
import com.api.bibliotech.livros.LivroRepository;
import com.api.bibliotech.pessoas.PessoaRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
	@Autowired
	private EmprestimoRepository repository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired 
	private PessoaRepository pessoaRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody DadosCadastroEmprestimo dados) {
		if (!livroRepository.existsById(dados.id_livro())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
		}
		if (!pessoaRepository.existsById(dados.id_pessoa())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
		}
		var emprestimo = new Emprestimo(dados);
		repository.save(emprestimo);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(emprestimo.getId()).toUri();
		return ResponseEntity.created(location).body(emprestimo);
	}

	@GetMapping
	public ResponseEntity<List<DadosListagemEmprestimo>> listar() {
		var lista = repository.findAll().stream().map(DadosListagemEmprestimo::new).toList();
		return ResponseEntity.ok(lista);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<?> alterar(@RequestBody DadosAlteracaoEmprestimo dados) {
		if (!livroRepository.existsById(dados.id_livro())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
		}
		if (!pessoaRepository.existsById(dados.id_pessoa())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
		}
		if (!repository.existsById(dados.id())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Emprestimo não encontrado");
		}
		var emprestimo = repository.getReferenceById(dados.id());
		emprestimo.atualizainformacoes(dados);
		return ResponseEntity.ok(dados);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		if (!repository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Emprestimo não encontrado");
		}
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar (@PathVariable Long id) {
		if(!repository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Emprestimo não encontado");
		}
		var emprestimo = repository.getReferenceById(id);
		DadosListagemEmprestimo dados = new DadosListagemEmprestimo(emprestimo);
		return ResponseEntity.ok(dados);
	}
}
