package io.github.brunoyillli.camelfuncionarios.rotas;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioListToJsonRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:funcionarioListToJson")
        .marshal().json(JsonLibrary.Jackson);
    }

}
