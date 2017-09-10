package nelsonapps.pucminas.tcc.test.restServices;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import nelsonapps.pucminas.tcc.controller.ManufacturerDataServiceRestController;
import nelsonapps.pucminas.tcc.persistence.entities.Manufacturer;
import nelsonapps.pucminas.tcc.test.configs.TestDatabaseConfig;
import nelsonapps.pucminas.tcc.test.configs.TestServicesConfigWithOutIntegration;

@RunWith(SpringRunner.class)
@Import(value={TestServicesConfigWithOutIntegration.class,TestDatabaseConfig.class})
@WebMvcTest(value=ManufacturerDataServiceRestController.class,secure=false)
public class ManufacturerDataServiceRestTesting {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Test
	public void sucessResultWhenCallingSearchByName() throws Exception{
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				                        .get("/manufacturer")
				                        .param("searchTerm", "cme")
				                        .param("page", "0")
				                        .param("size", "3");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String v_Json = response.getContentAsString();
		
		Manufacturer[] v_RetrivedManufacturerArray = objectMapper.readValue(
				                                     objectMapper.readTree(v_Json).get("content").toString(),Manufacturer[].class);
		
		assertThat(v_RetrivedManufacturerArray[0].getName().equals("Acme Corp"));
	}
}
