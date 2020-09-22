package com.microservices.localidades.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jônatas Tonholo
 * A Custom exception class for this microservice
 */
@AllArgsConstructor
@Getter
@Setter
public class APILocalidadesException extends Exception{
    private final String message;
}
