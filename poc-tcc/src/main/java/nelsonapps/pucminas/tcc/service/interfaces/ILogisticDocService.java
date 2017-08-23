package nelsonapps.pucminas.tcc.service.interfaces;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDocItem;
import nelsonapps.pucminas.tcc.persistence.entities.Partner;
import nelsonapps.pucminas.tcc.persistence.enums.DocTypeEnum;

public interface ILogisticDocService {

	LogisticDoc create(DocTypeEnum docType,Partner partner);
	LogisticDoc update(LogisticDoc logisticDoc);
	LogisticDoc addItems(LogisticDoc logisticDoc,LogisticDocItem ... items);
	
	
	Page<LogisticDoc> findByPartnerDocTypeAndDateInterval(Partner partner
			,DocTypeEnum docType,Map<String,Date> dateReferences,
			Pageable pageRequest)throws Exception;
}
