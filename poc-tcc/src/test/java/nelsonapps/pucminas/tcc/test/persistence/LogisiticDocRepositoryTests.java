package nelsonapps.pucminas.tcc.test.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import nelsonapps.pucminas.tcc.persistence.entities.DocNumSequence;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.Partner;
import nelsonapps.pucminas.tcc.persistence.entities.QLogisticDoc;
import nelsonapps.pucminas.tcc.persistence.enums.DocTypeEnum;
import nelsonapps.pucminas.tcc.persistence.repository.DocNumSequenceRepository;
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
	
	@Autowired
	private DocNumSequenceRepository docNumSequenceRepository;
	
	@Test
	public void jsonFileDataCreated(){
		Partner v_retrivedManufacturer =  manufacturerRepository.findAll().get(0);
		
		assertThat(v_retrivedManufacturer.getName()=="Nelson S/A");
	}
	
	@Test
	@Transactional
	public void filterLogisticDocByPartnerTest(){
		LogisticDoc v_doc = new LogisticDoc();
		v_doc.setCreatedDate(Calendar.getInstance().getTime());
		v_doc.setLastUpdateDate(Calendar.getInstance().getTime());
		v_doc.setDocType(DocTypeEnum.PO.getEnumValue());
		
		DocNumSequence v_DocNum = docNumSequenceRepository.save(new DocNumSequence());
		
		v_doc.setDocNum(v_DocNum);
		v_doc.setPartner(manufacturerRepository.findAll().get(0));
		logisticDocRepository.saveAndFlush(v_doc);
	    
		LogisticDoc v_retrivedDoc= logisticDocRepository.
				findOne(QLogisticDoc.logisticDoc.partner.name.eq("Nelson S/A"));
		
		assertThat(v_retrivedDoc.getPartner().getName().equals("Nelson S/A"));
		assertThat(v_retrivedDoc.getDocNum().getId()==v_DocNum.getId());
	
	}
}
