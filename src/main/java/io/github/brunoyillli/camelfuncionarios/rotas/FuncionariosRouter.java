package io.github.brunoyillli.camelfuncionarios.rotas;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.http.HttpStatus;
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
			    .streamCaching()
			    .to("direct:getFuncionario")
				.log("Response: ${body}")
	        .endRest()
	        
	        .put("/{id}/applyRaise")
	        	.route()
	            .streamCaching()
	            .to("direct:getFuncionario")
	        	.log("Received funcionario data: ${body}")
	        	.choice()
	        		.when(header(Exchange.HTTP_RESPONSE_CODE).isNotEqualTo(HttpStatus.SC_OK))
	        			.stop()
	        	.otherwise()
	                .unmarshal().json(JsonLibrary.Jackson, Funcionario.class) 
		            .process("funcionarioSalaryRaiseProcessor")
		            .marshal().json(JsonLibrary.Jackson)
		            .to("direct:updateFuncionario")
	        .endRest();

	}

}
