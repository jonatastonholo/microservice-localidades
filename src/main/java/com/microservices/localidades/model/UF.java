package com.microservices.localidades.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UF extends LocalidadeAbstrata {
    @JsonProperty("sigla")
    private String sigla;
    @JsonProperty("regiao")
    Regiao regiao;
}
