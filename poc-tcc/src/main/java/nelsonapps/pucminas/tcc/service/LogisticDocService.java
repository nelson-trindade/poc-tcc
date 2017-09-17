package nelsonapps.pucminas.tcc.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import nelsonapps.pucminas.tcc.constants.Constants;
import nelsonapps.pucminas.tcc.persistence.entities.DocNumSequence;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDocItem;
import nelsonapps.pucminas.tcc.persistence.entities.Partner;
import nelsonapps.pucminas.tcc.persistence.entities.QLogisticDoc;
import nelsonapps.pucminas.tcc.persistence.enums.DocTypeEnum;
import nelsonapps.pucminas.tcc.persistence.repository.DocNumSequenceRepository;
import nelsonapps.pucminas.tcc.persistence.repository.LogisticDocItemRepository;
import nelsonapps.pucminas.tcc.persistence.repository.LogisticDocRepository;
import nelsonapps.pucminas.tcc.service.interfaces.ILogisticDocService;


@Service("logisticDocService")
public class LogisticDocService implements ILogisticDocService {

	@Autowired
	private LogisticDocRepository logisticDocRepository;
	
	@Autowired
    private LogisticDocItemRepository logisticDocItemRepository; 
	
	
	@Autowired
	private DocNumSequenceRepository docNumSequenceRepository;
	
	private List<BooleanExpression> queryExpressions;
	
	
	@Override
	public Page<LogisticDoc> findByPartnerDocTypeAndDateInterval(Partner partner, DocTypeEnum docType,
			Map<String,Date> dateReferences,Pageable pageRequest) throws Exception {
		
		if(partner==null && docType ==null && dateReferences==null){
			throw new Exception(Constants.ErrorMessages.AT_LEAST_ONE_NOT_NULL_ARGUMENT_ERROR);
		}
		 		
		return logisticDocRepository.findAll(generateFullPredicate(partner, docType, dateReferences), pageRequest);
	}
	
	
	private Predicate generateFullPredicate(Partner partner, DocTypeEnum docType,
			Map<String,Date> dateReferences) throws Exception{
		
		queryExpressions = new ArrayList<>(); 
		
		getPartnerPredicate(partner);
		getDocTypePredicate(docType);
		getDateReferencesPredicate(dateReferences);
	
		BooleanBuilder v_builder = new BooleanBuilder();
		queryExpressions.forEach(exp -> v_builder.and(exp));
		
		return v_builder.getValue();
	}
	
	
	private void getPartnerPredicate(Partner partner){
		Optional.ofNullable(partner).ifPresent(
				partnerValue -> queryExpressions.add(QLogisticDoc.logisticDoc.partner.eq(partnerValue)));

	}
	
	private void getDocTypePredicate(DocTypeEnum docType){
		Optional.ofNullable(docType).ifPresent(
				docTypeValue -> queryExpressions.add(QLogisticDoc.logisticDoc.docType.eq(docTypeValue.getEnumValue())));
		
	}
	
	private void getDateReferencesPredicate(Map<String, Date> dateReferences) throws Exception {
		if (dateReferences != null) {
			if (!(dateReferences.containsKey(Constants.Labels.START_DATE_KEY) ||
					dateReferences.containsKey(Constants.Labels.END_DATE_KEY)))
				throw new Exception(Constants.ErrorMessages.INCORRECT_DATES_KEY_LOGISTICDOCS_SEARCH_ERROR);

			if (dateReferences.containsKey(Constants.Labels.START_DATE_KEY))
				queryExpressions.add(QLogisticDoc.logisticDoc.createdDate.after(dateReferences.get(Constants.Labels.START_DATE_KEY)));

			if (dateReferences.containsKey(Constants.Labels.END_DATE_KEY))
				queryExpressions.add(QLogisticDoc.logisticDoc.createdDate.before(dateReferences.get(Constants.Labels.END_DATE_KEY)));
		}
	}

	@Override
	public LogisticDoc create(DocTypeEnum docType, Partner partner) {
		LogisticDoc v_NewLogisticDoc = new LogisticDoc();
		v_NewLogisticDoc.setCreatedDate(Calendar.getInstance().getTime());
		v_NewLogisticDoc.setDocType(docType.getEnumValue());
		v_NewLogisticDoc.setDocNum(docNumSequenceRepository.save(new DocNumSequence()));
		v_NewLogisticDoc.setPartner(partner);
		
		return logisticDocRepository.save(v_NewLogisticDoc);
	}

	@Override
	public LogisticDoc update(LogisticDoc logisticDoc) {
		logisticDoc.setLastUpdateDate(Calendar.getInstance().getTime());
		return logisticDocRepository.save(logisticDoc);
	}


	@Override
	@Transactional
	public LogisticDoc addItems(LogisticDoc logisticDoc,Collection<LogisticDocItem> items) { 
		items.forEach(it->{
			it.setDocHeader(logisticDoc);
			logisticDocItemRepository.save(it);
		});
	    
	    logisticDoc.setItems(items);
	    return logisticDoc;
	}
}

