package br.com.nevesHoteis.domain.Dto;

import br.com.nevesHoteis.domain.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.catalina.filters.AddDefaultCharsetFilter;

public record AddressCompleteDto (
        @NotNull
        Long id,
     @Pattern(regexp = "\\d{5}-\\d{3}")
     @NotBlank
     String cep,
     @NotBlank
     @Pattern(regexp = "[A-Z]{2}")
     String state,
     @NotBlank
     @Size( min = 2, max = 30)
     String city,
     @NotBlank
     @Size( min = 2, max = 50)
     String neighborhood,
     @NotBlank
     @Size( min = 2, max = 50)
     String propertyLocation){

    public AddressCompleteDto(Address address){
        this(address.getId(), address.getCep(), address.getState(),
                 address.getCity(), address.getNeighborhood(), address.getPropertyLocation());
    }
}


