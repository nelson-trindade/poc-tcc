package nelsonapps.pucminas.tcc.test.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

import nelsonapps.pucminas.tcc.persistence.entities.DocNumSequence;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDocItem;
import nelsonapps.pucminas.tcc.persistence.entities.Manufacturer;
import nelsonapps.pucminas.tcc.persistence.enums.DocTypeEnum;
import nelsonapps.pucminas.tcc.persistence.enums.ReturnReasonEnum;
import nelsonapps.pucminas.tcc.service.interfaces.IReverseLogisticService;
import nelsonapps.pucminas.tcc.test.configs.IntegrationConfig;
import nelsonapps.pucminas.tcc.test.integration.endpoint.ReceiveManufacturerNotifyEndPoint;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={IntegrationConfig.class})
public class GatewaySendingTest {

	private LogisticDoc logisticDoc;
	
	@Autowired
	private IReverseLogisticService reverseLogisticService;
	
	@Autowired
	private ReceiveManufacturerNotifyEndPoint receiveNotifyEndPoint;
	
	@Before
	public void createLogisticDoc(){
		logisticDoc = new LogisticDoc();
		Calendar v_Date = Calendar.getInstance();
		v_Date.set(2017, 8, 23);
		logisticDoc.setCreatedDate(v_Date.getTime());
		logisticDoc.setDocType(DocTypeEnum.RET.getEnumValue());
		logisticDoc.setDocNum(new DocNumSequence(){{setId(Long.valueOf("30"));}});
		logisticDoc.setItems(Lists.newArrayList(createLogisticDocItem()));
		logisticDoc.setPartner(createManufacturer());
	}
	
	private LogisticDocItem createLogisticDocItem(){
		LogisticDocItem v_Item = new LogisticDocItem();
		v_Item.setDeliveryDate(Calendar.getInstance().getTime());
		v_Item.setQuantity(BigDecimal.valueOf(4));
		v_Item.setProductUUID("AAA");
		v_Item.setReturnReason(ReturnReasonEnum.EMPTY_PACKAGE.getEnumValue());
		return v_Item;
	}
	
	private Manufacturer createManufacturer(){
	    Manufacturer v_Manufacturer = new Manufacturer();
	    v_Manufacturer.setAddress("Rua A n/s");
	    v_Manufacturer.setName("Nelson S/A");
	    v_Manufacturer.setCnpj("11.111.111/1111-11");
	    return v_Manufacturer;
	}
	
	@Test
	public void sendMessage(){
		reverseLogisticService.sendReturnableLogisticDoc(logisticDoc);
		assertThat(receiveNotifyEndPoint.getReceivedMessage().getDocNum()==logisticDoc.getDocNum().getId());
	}
}
