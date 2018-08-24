package org.bapp;

import org.bapp.dto.RegistrantDTO;
import org.bapp.mapper.RegistrantMapper;
import org.bapp.model.Registrant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.Mapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RegistrationApplicationTests {

	@Test
	public void contextLoads() {
//		System.out.print("testing...");
	}

	@Test
    public void registrantDtoToRegistrant(){
        RegistrantDTO r = new RegistrantDTO();
        r.setChurchId("12345");
        Registrant r2 = RegistrantMapper.INSTANCE.registrantDtoToRegistrant(r);
        System.out.print(r2.getChurch());
    }

}
