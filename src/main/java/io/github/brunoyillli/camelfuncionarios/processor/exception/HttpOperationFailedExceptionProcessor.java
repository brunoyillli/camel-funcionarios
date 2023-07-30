package io.github.brunoyillli.camelfuncionarios.processor.exception;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.stereotype.Component;

@Component
public class HttpOperationFailedExceptionProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		HttpOperationFailedException cause = 
				exchange.getProperty(
						Exchange.EXCEPTION_CAUGHT, 
						HttpOperationFailedException.class);
        if (cause != null) {
            exchange.getMessage().setBody(cause.getResponseBody());
            exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, cause.getStatusCode());
        }
	}

}
