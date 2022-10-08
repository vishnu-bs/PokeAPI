package org.poke.model;

import lombok.Data;

import java.util.List;

@Data
// 3
public class Pokemon {

    private String name;
    private int weight;
    private List<PokemonGroup> types;
}
