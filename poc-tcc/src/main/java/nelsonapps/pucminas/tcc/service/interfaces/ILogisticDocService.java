package nelsonapps.pucminas.tcc.service.interfaces;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.Partner;
import nelsonapps.pucminas.tcc.persistence.enums.DocTypeEnum;

public interface ILogisticDocService {

	Page<LogisticDoc> findByPartnerDocTypeAndDateInterval(Partner partner
			,DocTypeEnum docType,Map<String,Date> dateReferences,
			Pageable pageRequest)throws Exception;
}
