package org.poke.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.poke.deserialiser.PokemanDeserialiser;
import org.poke.pokemodel.Pokemon;
import org.poke.pokemodel.PokemonGroup;
import org.poke.pokemodel.PokemonList;
import org.poke.pokemodel.PokemonResult;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class PokemonRequest {

    private HttpClient createHttpClient() {
        SSLContext sslContext;
        try {
            sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, (x509CertChain, authType) -> true)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new RuntimeException(e);
        }
        return HttpClientBuilder.create().setSSLContext(sslContext)
                .setConnectionManager(
                        new PoolingHttpClientConnectionManager(
                                RegistryBuilder.<ConnectionSocketFactory>create()
                                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                                        .register("https", new SSLConnectionSocketFactory(sslContext,
                                                NoopHostnameVerifier.INSTANCE))
                                        .build()))
                .build();
    }

    private String makeAPICall(String url) {
        HttpClient httpClient = createHttpClient();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Pokemon> getPokemons() {
        String url = "http://pokeapi.co/api/v2/pokemon?limit=30";
        String result = makeAPICall(url);
        PokemanDeserialiser deSerializer = new PokemanDeserialiser();
        PokemonList pokemonList = deSerializer.deSerialisePokemonList(result);
        List<Pokemon> normalTypes = new ArrayList<>();
        for (PokemonResult res : pokemonList.getResults()) {
            String singleResult = makeAPICall(res.getUrl());
            Pokemon pokemon = deSerializer.deSerialiseSinglePokemon(singleResult);
            List<PokemonGroup> types = pokemon.getTypes();
            types.stream()
                    .filter(type -> type.getType().getName().equalsIgnoreCase("normal"))
                    .forEach(type -> normalTypes.add(pokemon));
        }
        return normalTypes;
    }
}
