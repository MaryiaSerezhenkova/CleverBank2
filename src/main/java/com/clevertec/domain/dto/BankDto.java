package com.clevertec.domain.dto;

import com.clevertec.utils.CustomLocalDateTimeDesSerializer;
import com.clevertec.utils.CustomLocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public @Data  class BankDto {

    private long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDesSerializer.class)
    private LocalDateTime dtCreate;
    private String name;
}
