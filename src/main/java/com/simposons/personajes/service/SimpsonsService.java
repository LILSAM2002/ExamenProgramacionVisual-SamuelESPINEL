package com.simposons.personajes.service;

import com.simposons.personajes.model.Personaje;
import com.simposons.personajes.model.SimpsonsResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class SimpsonsService {
    private static final String API_URL = "https://simpsons-api.herokuapp.com/api/characters/";
    private final HttpClient httpClient;
    private final Gson gson;

    public SimpsonsService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public List<Personaje> buscarPersonajes(String nombre) throws IOException, InterruptedException {
        String encodedNombre = URLEncoder.encode(nombre, StandardCharsets.UTF_8);
        String url = API_URL + encodedNombre;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            List<Personaje> personajes = gson.fromJson(response.body(), 
                    new TypeToken<List<Personaje>>(){}.getType());
            return personajes != null ? personajes : Collections.emptyList();
        }

        return Collections.emptyList();
    }
}
