package org.poke.model;

import lombok.Data;

import java.util.List;

@Data
// 1
public class PokemonList {

    private int count;
    private List<PokemonResult> results;
}
