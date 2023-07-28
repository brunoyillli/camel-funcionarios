package io.github.brunoyillli.camelfuncionarios.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import io.github.brunoyillli.camelfuncionarios.entity.Funcionario;

@Component
public class FuncionarioSalaryRaiseProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		Funcionario funcionario = exchange.getIn().getBody(Funcionario.class);
		if(funcionario.getSalario() != null) {
			BigDecimal increasedSalary = funcionario.getSalario().multiply(BigDecimal.valueOf(1.25)).setScale(2, RoundingMode.HALF_UP);;
			funcionario.setSalario(increasedSalary);
			exchange.getMessage().setBody(funcionario);
		}
	}

}
