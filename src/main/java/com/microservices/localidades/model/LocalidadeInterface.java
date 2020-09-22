package com.microservices.localidades.model;

import java.io.Serializable;

/**
 * @author Jônatas Tonholo
 * The entity interface called "Localidade" (in english: locations)
 * was named in Portuguese to facilitate the relationship
 * between that entity and the brazilian API of citis from IBGE
 * TODO: Verify if this interface is necessary
 */
public interface LocalidadeInterface extends Serializable {
    Long getId();
}
