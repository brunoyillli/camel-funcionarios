package io.github.brunoyillli.camelfuncionarios.rotas;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UpdateFuncionario extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("direct:updateFuncionario")
        .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
        .toD("http://localhost:8080/api/funcionarios/${header.id}?bridgeEndpoint=true")
        .log("Updated funcionario data: ${body}")
        .streamCaching();
	}

}
