package nelsonapps.pucminas.tcc.test.dataservices;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import nelsonapps.pucminas.tcc.persistence.entities.Client;
import nelsonapps.pucminas.tcc.persistence.entities.Manufacturer;
import nelsonapps.pucminas.tcc.service.ManufacturerDataService;
import nelsonapps.pucminas.tcc.service.interfaces.IClientDataService;
import nelsonapps.pucminas.tcc.test.configs.TestDatabaseConfig;
import nelsonapps.pucminas.tcc.test.configs.TestServicesConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={TestDatabaseConfig.class,TestServicesConfig.class})
public class DataServicesTests {

	@Autowired
	private ManufacturerDataService manufacturerDataService;
	
	@Autowired
	private IClientDataService clientDataService;
	
	@Test
	@Transactional
	public void findManufacturerByNameLike(){
		
		Manufacturer v_retrivedManufacturer = (Manufacturer)manufacturerDataService
				.findByNameLike("Ac%", new PageRequest(0, 10)).getContent().get(0);
		
		assertThat(v_retrivedManufacturer.getCnpj()=="22.222.222/2222-22");
	}
	
	@Test
	@Transactional
	public void findClientByCpf(){
		
		Client v_retrivedClient = clientDataService.findByCpf("088.888.888-88");
		
		assertThat(v_retrivedClient.getName()=="Pernalonga");
	}
	
}
