package org.bapp.dto;

import java.util.List;

public class FullRegistrantDTO {

    private ChurchDTO churchDTO;

    private List<RegistrantDTO> registrantDTOList;

    public ChurchDTO getChurchDTO() {
        return churchDTO;
    }

    public void setChurchDTO(ChurchDTO churchDTO) {
        this.churchDTO = churchDTO;
    }

    public List<RegistrantDTO> getRegistrantDTOList() {
        return registrantDTOList;
    }

    public void setRegistrantDTOList(List<RegistrantDTO> registrantDTOList) {
        this.registrantDTOList = registrantDTOList;
    }
}
