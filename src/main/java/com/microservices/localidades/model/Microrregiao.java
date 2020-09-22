package com.microservices.localidades.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * @author Jônatas Tonholo
 * The entity called "Microrregiao" (in english: Microregion)
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
public class Microrregiao extends LocalidadeAbstract {
    @JsonProperty("mesorregiao")
    Mesorregiao mesorregiao;
}
