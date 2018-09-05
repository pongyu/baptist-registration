package org.bapp.mapper;

import org.bapp.dto.RegistrantDTO;
import org.bapp.model.Registrant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {EmailMapper.class, ChurchIdMapper.class})
public interface RegistrantMapper {

    RegistrantMapper INSTANCE = Mappers.getMapper(RegistrantMapper.class);

    @Mapping(source = "church", target = "churchId")
    RegistrantDTO registrantToRegistrantDto(Registrant registrant);

    @Mapping(source = "churchId", target = "church")
    Registrant registrantDtoToRegistrant(RegistrantDTO registrantDTO);

    List<RegistrantDTO> registrantToRegistrantDtoList(List<Registrant> registrants);
}
