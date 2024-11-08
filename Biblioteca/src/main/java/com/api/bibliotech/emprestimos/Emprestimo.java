package com.api.bibliotech.emprestimos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "emprestimo")
@Entity(name = "emprestimos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Emprestimo {
	public Emprestimo (DadosCadastroEmprestimo dados) {
		this.data_emprestimo = dados.data_emprestimo();
		this.data_devolucao = dados.data_devolucao();
		this.id_livro = dados.id_livro();
		this.id_pessoa = dados.id_pessoa();
	}
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private String data_emprestimo;
	private String data_devolucao;
	private Long id_livro;
	private Long id_pessoa;
	
	public void atualizainformacoes (DadosAlteracaoEmprestimo dados) {
		if(dados.data_emprestimo() != null) {
			this.data_emprestimo = dados.data_emprestimo();
		}
		if(dados.data_devolucao() != null) {
			this.data_devolucao = dados.data_devolucao();
		}
		if(dados.id_livro() != null) {
			this.id_livro = dados.id_livro();
		}
		if(dados.id_pessoa() != null) {
			this.id_livro = dados.id_pessoa();
		}
	}
}
