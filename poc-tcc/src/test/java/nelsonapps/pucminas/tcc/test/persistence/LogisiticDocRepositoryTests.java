package nelsonapps.pucminas.tcc.test.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import nelsonapps.pucminas.tcc.constants.Constants;
import nelsonapps.pucminas.tcc.persistence.entities.DocNumSequence;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDocItem;
import nelsonapps.pucminas.tcc.persistence.entities.Partner;
import nelsonapps.pucminas.tcc.persistence.enums.DocTypeEnum;
import nelsonapps.pucminas.tcc.persistence.enums.ReturnReasonEnum;
import nelsonapps.pucminas.tcc.persistence.repository.DocNumSequenceRepository;
import nelsonapps.pucminas.tcc.persistence.repository.LogisticDocItemRepository;
import nelsonapps.pucminas.tcc.persistence.repository.LogisticDocRepository;
import nelsonapps.pucminas.tcc.persistence.repository.ManufacturerRepository;
import nelsonapps.pucminas.tcc.service.LogisticDocService;
import nelsonapps.pucminas.tcc.test.configs.TestDatabaseConfig;
import nelsonapps.pucminas.tcc.test.configs.TestServicesConfigWithOutIntegration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={TestDatabaseConfig.class,TestServicesConfigWithOutIntegration.class})
public class LogisiticDocRepositoryTests {

	@Autowired
	private LogisticDocRepository logisticDocRepository; 
	
	@Autowired
	private LogisticDocItemRepository logisticItemRepository;
	
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	
	@Autowired
	private DocNumSequenceRepository docNumSequenceRepository;
	
	@Autowired
	private LogisticDocService logisticDocService;
	
	@Test
	@Transactional
	public void filterLogisticDocByPartnerTest() throws Exception{
	    LogisticDoc v_savedDoc = createLogisticDoc();
		Partner v_populatedPartner = manufacturerRepository.findAll().get(0);
	    
		LogisticDoc v_retrivedDoc = logisticDocService.findByPartnerDocTypeAndDateInterval(
				v_populatedPartner, null, null, new PageRequest(0,10)).iterator().next();
		
		assertThat(v_retrivedDoc.getPartner().getName().equals(v_savedDoc.getPartner().getName()));
		assertThat(v_retrivedDoc.getDocNum().getId()==v_savedDoc.getDocNum().getId());
	
	}
	
	@Test
	@Transactional
	public void filterLogisticDocUsingDocTypeTest() throws Exception{
		LogisticDoc v_savedDoc = createLogisticDoc();

		LogisticDoc v_retrivedDoc = logisticDocService.findByPartnerDocTypeAndDateInterval(
				null,DocTypeEnum.PO,null, new PageRequest(0,10)).iterator().next();

		assertThat(v_retrivedDoc.getPartner().getName().equals(v_savedDoc.getPartner().getName()));
		assertThat(v_retrivedDoc.getDocNum().getId() == v_savedDoc.getDocNum().getId());
	}
	
	@Test
	@Transactional
	public void findLogisticDocWithAllFiltersTest() throws Exception{
		LogisticDoc v_savedDoc = createLogisticDoc();
        HashMap<String,Date>v_DateInterval = new HashMap<>();
        
        Calendar v_startDate = Calendar.getInstance();
        v_startDate.set(2017, 8, 19);
        
        Calendar v_endDate = Calendar.getInstance();
        v_endDate.set(2017, 8, 24);
        
        v_DateInterval.put(Constants.Labels.START_DATE_KEY, v_startDate.getTime());
        v_DateInterval.put(Constants.Labels.END_DATE_KEY, v_endDate.getTime());
		
		LogisticDoc v_retrivedDoc = logisticDocService.findByPartnerDocTypeAndDateInterval(
				v_savedDoc.getPartner(),DocTypeEnum.PO,v_DateInterval, new PageRequest(0,10)).iterator().next();

		assertThat(v_retrivedDoc.getPartner().getName().equals(v_savedDoc.getPartner().getName()));
		assertThat(v_retrivedDoc.getDocNum().getId() == v_savedDoc.getDocNum().getId());
        assertThat(v_retrivedDoc.getDocType().equals(v_savedDoc.getDocType()));  
	}
	
	@Test
	@Transactional
	public void findLogisticDocAfterCreatedDateInFuture() throws Exception{
		createLogisticDoc();
		
		Calendar v_ReferenceDate = Calendar.getInstance();
		v_ReferenceDate.set(2017, 8, 31);
		
		HashMap<String,Date> v_DateInterval = new HashMap<>();
		v_DateInterval.put(Constants.Labels.START_DATE_KEY, v_ReferenceDate.getTime());
		
		Page<LogisticDoc> v_retrivedDate = logisticDocService.findByPartnerDocTypeAndDateInterval(
				null, null, v_DateInterval, new PageRequest(0,10));
		
		assertThat(v_retrivedDate.getNumberOfElements()==0);
	}
	
	@Test
	@Transactional
	public void dataFormatExceptionTest() {

		createLogisticDoc();

		Calendar v_ReferenceDate = Calendar.getInstance();
		v_ReferenceDate.set(2017, 8, 31);

		HashMap<String, Date> v_DateInterval = new HashMap<>();
		v_DateInterval.put("A", v_ReferenceDate.getTime());
		v_DateInterval.put("B",v_ReferenceDate.getTime());

		try{
			assertThat(logisticDocService.findByPartnerDocTypeAndDateInterval(
					null, null,v_DateInterval,new PageRequest(0,10)).getContent().size()>0);
		} catch(Exception ex){
			assertThat(ex.getMessage().equals(Constants.ErrorMessages.INCORRECT_DATES_KEY_LOGISTICDOCS_SEARCH_ERROR));
		}
	}
	
	
	@Test
	@Transactional
	public void createLogisticDocWithItem(){
		LogisticDoc v_doc = logisticDocService.create(DocTypeEnum.RET, manufacturerRepository.findAll().get(0));
		LogisticDocItem v_Item = new LogisticDocItem();
		v_Item.setDocHeader(v_doc);
		v_Item.setProductUUID("ABC");
		v_Item.setQuantity(BigDecimal.valueOf(10));
		v_Item.setPrice(BigDecimal.valueOf(0));
		v_Item.setExpiringDate(Calendar.getInstance().getTime());
		v_Item.setReturnReason(ReturnReasonEnum.EXPIRED_DATE.getEnumValue());
		List<LogisticDocItem> v_List = new ArrayList<>();
		v_List.add(v_Item);
		
		logisticDocService.addItems(v_doc, v_List);

		List<LogisticDocItem>v_retrivedItens = logisticItemRepository.findAll();
		LogisticDoc v_retrivedDoc = logisticDocRepository.findOne(v_doc.getId());
		assertThat(v_retrivedItens.get(0).getDocHeader().getId()==v_doc.getId());
		assertThat(v_retrivedDoc.getItems().size()==1);
		
	}

	private LogisticDoc createLogisticDoc(){
		LogisticDoc v_doc = new LogisticDoc();
		Calendar v_Date = Calendar.getInstance();
		v_Date.set(2017, 8, 23);
		v_doc.setCreatedDate(v_Date.getTime());
		v_doc.setDocType(DocTypeEnum.PO.getEnumValue());
		
		DocNumSequence v_DocNum = docNumSequenceRepository.save(new DocNumSequence());
		
		v_doc.setDocNum(v_DocNum);
		v_doc.setPartner(manufacturerRepository.findAll().get(0));
		return logisticDocRepository.save(v_doc);
	}
}
