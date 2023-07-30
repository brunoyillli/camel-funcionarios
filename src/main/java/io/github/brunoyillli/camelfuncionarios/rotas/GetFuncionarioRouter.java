package io.github.brunoyillli.camelfuncionarios.rotas;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class GetFuncionarioRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("direct:getFuncionario")
    	.doTry()
	        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
	    	.toD("http://localhost:8080/api/funcionarios/${header.id}?bridgeEndpoint=true")
	    .doCatch(Exception.class)
			.process("httpOperationFailedExceptionProcessor")
		.end();		
	}

}
