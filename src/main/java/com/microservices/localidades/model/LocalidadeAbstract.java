package com.microservices.localidades.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * @author Jônatas Tonholo
 * The entity abstraction called "LocalidadeAbstract" (in english: locations)
 * was named in Portuguese to facilitate the relationship
 * between that entity and the brazilian API of citis from IBGE
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class LocalidadeAbstract implements LocalidadeInterface {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("nome")
    private String nome;
}
