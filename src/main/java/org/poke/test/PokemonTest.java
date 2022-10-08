package org.poke.test;

import org.junit.jupiter.api.Test;
import org.poke.http.PokemonRequest;
import org.poke.pokemodel.Pokemon;

import java.util.List;

public class PokemonTest {

    @Test
    public void getNormalPoke(){
        PokemonRequest request = new PokemonRequest();
        List<Pokemon> normalPoke = request.getPokemons();
        normalPoke.stream().forEach(pokemon -> System.out.println("Normal Poke Name - "+pokemon.getName()));
    }
}
