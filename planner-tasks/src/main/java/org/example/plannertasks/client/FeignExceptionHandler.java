package org.example.plannertasks.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class FeignExceptionHandler implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 406 -> {
                {
                    return new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, readResponseBody(response));
                }
            }
        }
        return null;
    }

    @SneakyThrows
    private String readResponseBody(Response response) {
        InputStream is = response.body().asInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        return br.lines().collect(Collectors.joining("\n"));
    }
}
