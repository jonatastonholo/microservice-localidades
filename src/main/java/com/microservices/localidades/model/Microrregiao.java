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
public class Microrregiao extends LocalidadeAbstrata {
    @JsonProperty("mesorregiao")
    Mesorregiao mesorregiao;
}
