package org.bapp.mapper;

import org.bapp.dto.ChurchDTO;
import org.bapp.model.Church;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses=EmailMapper.class)
public interface ChurchMapper {

    ChurchMapper INSTANCE = Mappers.getMapper(ChurchMapper.class);

    ChurchDTO ChurchToChurchDto(Church church);

}
