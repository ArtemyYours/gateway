package ru.balacetracker.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import ru.balacetracker.properties.ApplicationProperties;
import ru.balacetracker.utils.Utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final ApplicationProperties applicationProperties;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        String requestBody = new String(body, StandardCharsets.UTF_8);
        String responseBody = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
        HttpMethod method = request.getMethod();
        HttpStatus status = response.getStatusCode();
        HttpHeaders headers = request.getHeaders();
        log.info(
                "Exchange: [method={}][url={}][request-body={}][response-body={}][response-status={}][auth={}][realm={}]",
                method == null ? null : method.name(),
                request.getURI().toString(),
                Utils.cutStringForLog(Utils.toStringOfNullable(requestBody), applicationProperties.getLogBodyLimit()),
                Utils.cutStringForLog(Utils.toStringOfNullable(responseBody), applicationProperties.getLogBodyLimit()),
                status.name() + ":" + status.value(),
                headers.containsKey(HttpHeaders.AUTHORIZATION) ? "yes" : "no",
                headers.getFirst("Realm")
        );
        return response;
    }

}