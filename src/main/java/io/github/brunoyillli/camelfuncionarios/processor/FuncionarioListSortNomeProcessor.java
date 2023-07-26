package io.github.brunoyillli.camelfuncionarios.processor;

import java.util.Comparator;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import io.github.brunoyillli.camelfuncionarios.entity.Funcionario;

@Component
public class FuncionarioListSortNomeProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		List<Funcionario> funcionarios = exchange.getIn().getBody(List.class);
		funcionarios.sort(Comparator.comparing(Funcionario::getNome));
        exchange.setProperty("listaOrdenadaByNome", funcionarios);

	}

}
