package com.alimentabem.service;

import com.alimentabem.exception.ValidacaoException;
import com.alimentabem.model.Endereco;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnderecoService {
    private static final String BASE_URL = "https://brasilapi.com.br/api/cep/v1/";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    private void validarFormatoCep(String cep) {
        if (cep == null || !cep.replace("-", "").matches("\\d{8}")) {
            throw new ValidacaoException("Formato de CEP inválido. O CEP deve conter 8 dígitos.");
        }
    }

        public Endereco consultaCep(String cep) throws IOException, InterruptedException {
        validarFormatoCep(cep);
        String cepLimpo = cep.replace("-", "");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + cepLimpo))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 404) {
            throw new ValidacaoException("CEP " + cep + " não foi encontrado na base de dados.");
        }
        if (response.statusCode() != 200) {
            throw new IOException("Falha na consulta do CEP " + cep + ". Código de status: " + response.statusCode());
        }

        String json = response.body();
        return new Endereco(
            extrairCampo(json, "cep"),
            extrairCampo(json, "street"),
            extrairCampo(json, "neighborhood"),
            extrairCampo(json, "city"),
            extrairCampo(json, "state")
        );
    }

        private String extrairCampo(String json, String campo) {
        Pattern padrao = Pattern.compile("\"" + campo + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher m = padrao.matcher(json);
        return m.find() ? m.group(1) : "";
    }
}