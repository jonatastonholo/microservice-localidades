package com.microservices.localidades.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jônatas Tonholo
 * The entity called "UF" (in english: Federative unit or State)
 * was named in Portuguese to facilitate the relationship
 * between that entity and the brazilian API of citis from IBGE
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UF extends LocalidadeAbstract {
    @JsonProperty("sigla")
    private String sigla;
    @JsonProperty("regiao")
    Regiao regiao;
}
