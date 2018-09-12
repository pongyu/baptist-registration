package org.bapp.mapper;

import org.bapp.dto.ChurchDTO;
import org.bapp.model.Church;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses={EmailMapper.class, RegistrantMapper.class})
public interface ChurchMapper {

    ChurchMapper INSTANCE = Mappers.getMapper(ChurchMapper.class);

    ChurchDTO toChurchDto(Church church);

    Church toChurch(ChurchDTO churchDTO);

}
