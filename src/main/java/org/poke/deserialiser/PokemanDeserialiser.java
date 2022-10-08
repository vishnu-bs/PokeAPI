package org.poke;

import com.google.gson.Gson;

public class PokemanDeserialiser {

    public void deserialise(String json) {
        Gson gson = new Gson();
        gson.fromJson(json, Poken)
    }
}
