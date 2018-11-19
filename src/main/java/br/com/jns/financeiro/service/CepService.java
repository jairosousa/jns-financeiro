package br.com.jns.financeiro.service;

import br.com.jns.financeiro.domain.Endereco;
import br.com.jns.financeiro.service.dto.EnderecoDTO;
import br.com.jns.financeiro.service.exceptions.ViaCepException;
import br.com.jns.financeiro.service.mapper.EnderecoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Service
public class CepService {
    private final Logger log = LoggerFactory.getLogger(CepService.class);

    private final EnderecoMapper enderecoMapper;

    public CepService(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public EnderecoDTO buscarCep(String cep) throws ViaCepException {
        log.debug("Request to find cep : {}", cep);

        String urlLocal = "http://viacep.com.br/ws/" + cep + "/json/";

        JSONObject obj = null;

        Endereco endereco = new Endereco();

        try {
            obj = new JSONObject(getHttpGET(urlLocal));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!obj.has("erro")) {
            try {
                endereco.setCep(obj.getString("cep"));
                endereco.setLogradouro(obj.getString("logradouro"));
                endereco.setComplemento(obj.getString("complemento"));
                endereco.setBairro(obj.getString("bairro"));
                endereco.setCidade(obj.getString("localidade"));
                endereco.setUf(obj.getString("uf"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            throw new ViaCepException("Não foi possível encontrar o CEP", cep, ViaCepException.class.getName());
    }

        return enderecoMapper.toDto(endereco);
    }

    private String getHttpGET(String url2) {
        StringBuilder result = new StringBuilder();

        try {
            URL url3 = new URL(url2);
            HttpURLConnection conn = (HttpURLConnection) url3.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

        } catch (MalformedURLException | ProtocolException ex) {
            // verifica os Eventos

        } catch (IOException ex) {

            //throw new ViaCepException(ex.getMessage(), ex.getClass().getName());
        }

        return result.toString();
    }
}
