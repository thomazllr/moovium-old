package com.thomazllr.moovium.mapper;

import com.thomazllr.moovium.model.Client;
import com.thomazllr.moovium.request.client.ClientPostRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientPostRequest request);
}
