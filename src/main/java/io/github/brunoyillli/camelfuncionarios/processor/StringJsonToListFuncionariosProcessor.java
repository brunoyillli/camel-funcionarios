package io.github.brunoyillli.camelfuncionarios.processor;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.brunoyillli.camelfuncionarios.entity.Funcionario;

@Component
public class StringJsonToListFuncionariosProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		String body = exchange.getIn().getBody(String.class);
        if (body != null && !body.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            List<Funcionario> funcionarios = mapper.readValue(body, new TypeReference<List<Funcionario>>() {});
            exchange.getIn().setBody(funcionarios);
        } else {
            exchange.getIn().setBody("Empty Response");
        }
	}

}
