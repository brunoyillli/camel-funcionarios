package io.github.brunoyillli.camelfuncionarios.rotas;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.brunoyillli.camelfuncionarios.processor.FuncionarioListSortNomeProcessor;

@Component
public class FuncionariosRouter extends RouteBuilder {

	@Autowired
	FuncionarioListSortNomeProcessor funcionarioListProcessor;
	
	@Override
	public void configure() throws Exception {
		restConfiguration().component("jetty").port(8081);
		rest("/funcionarios")
		.get()
		.route()
		.to("http://localhost:8080/api/funcionarios?bridgeEndpoint=true")
		.log("Response: ${body}")
		.streamCaching()
		.convertBodyTo(String.class)
		.to("direct:stringJsonToListFuncionarios")
		.process("funcionarioListSortNomeProcessor")
        .setBody(exchangeProperty("listaOrdenadaByNome"))
		.to("direct:funcionarioListToJson");

	}

}
