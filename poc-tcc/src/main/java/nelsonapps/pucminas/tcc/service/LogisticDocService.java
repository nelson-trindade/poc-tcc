package nelsonapps.pucminas.tcc.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;

import nelsonapps.pucminas.tcc.constants.Constants;
import nelsonapps.pucminas.tcc.persistence.entities.DocNumSequence;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDocItem;
import nelsonapps.pucminas.tcc.persistence.entities.Partner;
import nelsonapps.pucminas.tcc.persistence.entities.QLogisticDoc;
import nelsonapps.pucminas.tcc.persistence.enums.DocTypeEnum;
import nelsonapps.pucminas.tcc.persistence.repository.DocNumSequenceRepository;
import nelsonapps.pucminas.tcc.persistence.repository.LogisticDocRepository;
import nelsonapps.pucminas.tcc.service.interfaces.ILogisticDocService;


@Service("logisticDocService")
public class LogisticDocService implements ILogisticDocService {

	@Autowired
	private LogisticDocRepository logisticDocRepository;
	
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
	
	
	private BooleanExpression generateFullPredicate(Partner partner, DocTypeEnum docType,
			Map<String,Date> dateReferences) throws Exception{
		
		BooleanExpression v_finalPredicate = null;
		BooleanExpression v_FirstPredicate = null;
		BooleanExpression v_SecondPredicate = null;
		
		queryExpressions = new ArrayList<>(); 
		
		getPartnerPredicate(partner);
		getDocTypePredicate(docType);
		getDateReferencesPredicate(dateReferences);
	
		if(queryExpressions.size()==1){
			v_finalPredicate = queryExpressions.get(0);
		} else if(queryExpressions.size()>1){
			Iterator<BooleanExpression> v_Iterator = queryExpressions.iterator();
			while(v_Iterator.hasNext()){
				v_FirstPredicate = v_Iterator.next();
				if(v_Iterator.hasNext()){
					v_SecondPredicate = v_Iterator.next();
					v_finalPredicate = v_finalPredicate == null ? 
							           v_FirstPredicate.and(v_SecondPredicate)
							           :v_finalPredicate.and(v_FirstPredicate.and(v_SecondPredicate));
				}
			}
		} 
	
		return v_finalPredicate;
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
				queryExpressions.add(QLogisticDoc.logisticDoc.createdDate.after(dateReferences.get("startDate")));

			if (dateReferences.containsKey(Constants.Labels.END_DATE_KEY))
				queryExpressions.add(QLogisticDoc.logisticDoc.createdDate.before(dateReferences.get("endDate")));

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
	public LogisticDoc addItems(LogisticDoc logisticDoc, LogisticDocItem... items) {
		logisticDoc.setItems(Lists.newArrayList(items));
		return logisticDocRepository.save(logisticDoc);
	}
}

