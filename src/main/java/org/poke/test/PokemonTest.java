package org.poke.test;

import org.junit.jupiter.api.Test;
import org.poke.http.PokemonRequest;

public class PokemonMain {

    @Test
    public void getNormalPoke(){
        PokemonRequest request = new PokemonRequest();
        request.getPokemons();
    }
}
