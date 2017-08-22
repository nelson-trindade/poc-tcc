package nelsonapps.pucminas.tcc.test.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import nelsonapps.pucminas.tcc.persistence.entities.Partner;
import nelsonapps.pucminas.tcc.persistence.repository.LogisticDocRepository;
import nelsonapps.pucminas.tcc.persistence.repository.ManufacturerRepository;
import nelsonapps.pucminas.tcc.test.configs.TestDatabaseConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestDatabaseConfig.class)
public class LogisiticDocRepositoryTests {

	@Autowired
	private LogisticDocRepository logisticDocRepository; 
	
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	
	@Test
	public void jsonFileDataCreated(){
		Partner v_retrivedManufacturer =  manufacturerRepository.findAll().get(0);
		
		assertThat(v_retrivedManufacturer.getName()=="Nelson S/A");
	}
}
