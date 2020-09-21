package com.microservices.localidades.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class LocalidadeAbstrata implements LocalidadeInterface {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("nome")
    private String nome;
}
