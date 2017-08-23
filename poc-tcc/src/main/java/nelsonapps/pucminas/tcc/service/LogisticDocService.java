package nelsonapps.pucminas.tcc.service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;

import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.Partner;
import nelsonapps.pucminas.tcc.persistence.entities.QLogisticDoc;
import nelsonapps.pucminas.tcc.persistence.enums.DocTypeEnum;
import nelsonapps.pucminas.tcc.persistence.repository.LogisticDocRepository;
import nelsonapps.pucminas.tcc.service.interfaces.ILogisticDocService;


@Service("logisticDocRepository")
public class LogisticDocService implements ILogisticDocService {

	@Autowired
	private LogisticDocRepository logisticDocRepository;

	
	@Override
	public Page<LogisticDoc> findByPartnerDocTypeAndDateInterval(Partner partner, DocTypeEnum docType,
			Map<String,Date> dateReferences,Pageable pageRequest) throws Exception {
		
		if(partner==null && docType ==null && dateReferences==null){
			throw new Exception("Ao menos um parâmetro deve ser diferente de nulo");
		}
		
		BooleanExpression v_finalPredicate = null;
		
		Optional<BooleanExpression> partnerPredicate = Optional.ofNullable(getPartnerPredicate(partner));
		Optional<BooleanExpression> docTypePredicate = Optional.ofNullable(getDocTypePredicate(docType));
		Optional<BooleanExpression> dateReferencesPredicate = Optional.ofNullable(
				getDateReferencesPredicate(dateReferences));
		
		if(partnerPredicate.isPresent() && docTypePredicate.isPresent()){
			if(dateReferencesPredicate.isPresent()){
				v_finalPredicate = partnerPredicate.get()
						.and(docTypePredicate.get().and(dateReferencesPredicate.get()));
			} else {
				v_finalPredicate = partnerPredicate.get().and(docTypePredicate.get());
			}
		} else if(partnerPredicate.isPresent() && !(docTypePredicate.isPresent())){
			if(dateReferencesPredicate.isPresent()){
				v_finalPredicate = partnerPredicate.get().and(dateReferencesPredicate.get());
			} else {
				v_finalPredicate = partnerPredicate.get();
			}
		} else if(!(partnerPredicate.isPresent()) && docTypePredicate.isPresent()){
			if(dateReferencesPredicate.isPresent()){
				v_finalPredicate = docTypePredicate.get().and(dateReferencesPredicate.get());
			} else {
				v_finalPredicate = docTypePredicate.get();
			}
		}
		
		return logisticDocRepository.findAll(v_finalPredicate, pageRequest);
	
	}
	
	private BooleanExpression getPartnerPredicate(Partner partner){
		if(partner!=null){
			return QLogisticDoc.logisticDoc.partner.eq(partner);
		} else {
			return null;
		}
	}
	
	private BooleanExpression getDocTypePredicate(DocTypeEnum docType){
		if(docType!=null){
			return QLogisticDoc.logisticDoc.docType.eq(docType.getEnumValue());
		} else {
			return null;
		}
	}
	
	private BooleanExpression getDateReferencesPredicate(Map<String,Date> dateReferences) throws Exception{
		if(dateReferences!=null){
			if(dateReferences.containsKey("startDate")){
				return QLogisticDoc.logisticDoc.createdDate.after(dateReferences.get("startDate"));
			} else if(dateReferences.containsKey("endDate")){
				return QLogisticDoc.logisticDoc.createdDate.before(dateReferences.get("endDate"));
			} else if(dateReferences.containsKey("startDate") && dateReferences.containsKey("endDate")){
				return QLogisticDoc.logisticDoc.createdDate.between(dateReferences.get("startDate"),
						dateReferences.get("endDate"));
			} else {
				throw new Exception("Intervalo de dada em formato incorreto");
			}
		} else {
			return null;
		}
	}

}
