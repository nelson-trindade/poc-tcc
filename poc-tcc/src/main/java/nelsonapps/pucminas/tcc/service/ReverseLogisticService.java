package nelsonapps.pucminas.tcc.service;

import java.util.function.BiConsumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nelsonapps.pucminas.tcc.integration.gateway.ReverseLogisticDocs;
import nelsonapps.pucminas.tcc.integration.message.LogisticDocItemMessage;
import nelsonapps.pucminas.tcc.integration.service.LogisticDocMessageService;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDocItem;
import nelsonapps.pucminas.tcc.persistence.enums.ReturnReasonEnum;
import nelsonapps.pucminas.tcc.service.interfaces.IReverseLogisticService;

@Service("reverseLogisticService")
public class ReverseLogisticService implements IReverseLogisticService {

	@Autowired
	private LogisticDocMessageService logisticDocMessageService;
	
	@Autowired
	private ReverseLogisticDocs reverseLogisticDocs;
	
	@Override
	public void sendReturnableLogisticDoc(LogisticDoc logisticDoc) {
		BiConsumer<LogisticDocItemMessage, ReturnReasonEnum> v_setAdditionalInfoFunc = 
				(docItemMessage,returnReason) -> docItemMessage.setReturnReason(returnReason.getDescription());
				
		Function<LogisticDocItem, ReturnReasonEnum> v_getAdditionalInfoFunc = (docItem) -> ReturnReasonEnum.valueOf(docItem.getReturnReason());
		reverseLogisticDocs.notifyItensToCollect(logisticDocMessageService.create(logisticDoc, v_setAdditionalInfoFunc, v_getAdditionalInfoFunc));
	}

}
