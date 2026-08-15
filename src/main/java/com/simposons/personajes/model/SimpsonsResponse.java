package com.simposons.personajes.model;

import java.util.List;

public class SimpsonsResponse {
    private List<Personaje> relatedCharacters;

    public List<Personaje> getRelatedCharacters() {
        return relatedCharacters;
    }

    public void setRelatedCharacters(List<Personaje> relatedCharacters) {
        this.relatedCharacters = relatedCharacters;
    }
}
