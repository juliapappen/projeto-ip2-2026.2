package com.alimentabem.service;

import com.alimentabem.exception.ValidacaoException;
import com.alimentabem.model.Endereco;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ViaCepService {

    public Endereco buscarEnderecoPorCep(String cepLimpo) {
        if (cepLimpo == null || cepLimpo.length() != 8) {
            throw new ValidacaoException("CEP inválido! O CEP deve conter exatamente 8 dígitos.");
        }

        try {
            String url = "https://brasilapi.com.br/api/cep/v1/" + cepLimpo;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new ValidacaoException("CEP " + cepLimpo + " não encontrado na base nacional (BrasilAPI).");
            }

            String json = response.body();
            String rua = extrairJson(json, "street");
            String bairro = extrairJson(json, "neighborhood");
            String cidade = extrairJson(json, "city");
            String estado = extrairJson(json, "state");

            return new Endereco(cepLimpo, rua, bairro, cidade, estado);

        } catch (ValidacaoException e) {
            throw e;
        } catch (Exception e) {
            return new Endereco(cepLimpo, "Rua Desconhecida", "Bairro Desconhecido", "Cidade Desconhecida", "UF");
        }
    }

    private String extrairJson(String json, String chave) {
        String busca = "\"" + chave + "\":\"";
        int inicio = json.indexOf(busca);
        if (inicio == -1) return "";
        inicio += busca.length();
        int fim = json.indexOf("\"", inicio);
        return (fim != -1) ? json.substring(inicio, fim) : "";
    }
}