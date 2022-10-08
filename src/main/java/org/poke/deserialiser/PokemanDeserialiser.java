package org.poke;

import com.google.gson.Gson;

public class PokemanDeserialiser {

    public PokemonList deSerialisePokemonList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, PokemonList.class);
    }
    
     public Pokemon deSerialiseSinglePokemon(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Pokemon.class);
    }
}
