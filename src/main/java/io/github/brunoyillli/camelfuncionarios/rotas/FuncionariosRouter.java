package io.github.brunoyillli.camelfuncionarios.rotas;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.brunoyillli.camelfuncionarios.entity.Funcionario;
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
				.to("direct:funcionarioListToJson")
			.endRest()
			
			.get("/{id}")
				.route()
				.toD("http://localhost:8080/api/funcionarios/${header.id}?bridgeEndpoint=true")
		        .log("Response: ${body}")
		        .streamCaching()
	        .endRest()
	        
	        .put("/{id}/applyRaise")
	        	.route()
	            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
	        	.toD("http://localhost:8080/api/funcionarios/${header.id}?bridgeEndpoint=true")
	            .log("Received funcionario data: ${body}")
	            .streamCaching()
                .unmarshal().json(JsonLibrary.Jackson, Funcionario.class) 
	            .process("funcionarioSalaryRaiseProcessor")
	            .marshal().json(JsonLibrary.Jackson)
	            .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
	            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
	            .toD("http://localhost:8080/api/funcionarios/${header.id}?bridgeEndpoint=true")
	            .log("Updated funcionario data: ${body}")
	        .end();

	}

}
