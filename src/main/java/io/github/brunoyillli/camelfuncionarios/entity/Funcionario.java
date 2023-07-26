package io.github.brunoyillli.camelfuncionarios.entity;

import java.io.Serializable;
import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Funcionario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1109870286672980906L;

	private Integer id;

	private String nome;

	private String designacao;

	private BigDecimal salario;

	private String telefone;

	private String endereco;

}
