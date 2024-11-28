package me.kruase.mipt.api.util;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


public final class SimpleHttpClient {
    private static final HttpResponse.BodyHandler<String> charsetHandler =
            HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8);

    private final HttpClient client = HttpClient.newHttpClient();

    public HttpResponse<String> get(String url) throws IOException, InterruptedException {
        return client.send(
                getBaseBuilder(url)
                        .GET()
                        .build(),
                charsetHandler
        );
    }

    public HttpResponse<String> post(String url, String body)
            throws IOException, InterruptedException {
        return client.send(
                getBaseBuilder(url)
                        .POST(BodyPublishers.ofString(body))
                        .build(),
                charsetHandler
        );
    }

    public HttpResponse<String> put(String url, String body)
            throws IOException, InterruptedException {
        return client.send(
                getBaseBuilder(url)
                        .PUT(BodyPublishers.ofString(body))
                        .build(),
                charsetHandler
        );
    }

    public HttpResponse<String> delete(String url) throws IOException, InterruptedException {
        return client.send(
                getBaseBuilder(url)
                        .DELETE()
                        .build(),
                charsetHandler
        );
    }

    private HttpRequest.Builder getBaseBuilder(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("Content-Type", "application/json");
    }
}
