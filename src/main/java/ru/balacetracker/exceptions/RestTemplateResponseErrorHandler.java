package ru.balacetracker.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.io.CharStreams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import ru.balacetracker.utils.Utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    @SneakyThrows
    public boolean hasError(ClientHttpResponse httpResponse) {
        return httpResponse.getStatusCode().series() == CLIENT_ERROR || httpResponse.getStatusCode().series() == SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(httpResponse.getBody(), StandardCharsets.UTF_8.name())) {
            String body = CharStreams.toString(isr);
            log.error(body);
            try {
                throw new HttpException(httpResponse.getStatusCode(), Utils.jsonToObjectLoosely(body, ExceptionDto.class));
            } catch (JsonProcessingException e) {
                throw new HttpException(httpResponse.getStatusCode(), MessageCode.REST_EXCHANGE_FAILURE, null, body);
            }
        }
    }

}