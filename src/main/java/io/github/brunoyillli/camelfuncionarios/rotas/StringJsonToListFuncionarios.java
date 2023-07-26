package io.github.brunoyillli.camelfuncionarios.rotas;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class StringJsonToListFuncionarios extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("direct:stringJsonToListFuncionarios")
		.process("stringJsonToListFuncionariosProcessor");
		
	}

}
