package com.microservices.localidades.model;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Jônatas Tonholo
 * A Enumerator to define the used IBGE's Rest API URI
 */
@Getter
@ToString
public enum EIBGEApiURI {
    MUNICIPIOS("/estados/{UF}/municipios"),
    UF("/estados");

    private final String uri;

    EIBGEApiURI(String uri) {
        this.uri = uri;
    }
}
