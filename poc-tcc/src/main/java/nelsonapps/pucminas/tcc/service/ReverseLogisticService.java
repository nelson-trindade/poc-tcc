package nelsonapps.pucminas.tcc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nelsonapps.pucminas.tcc.integration.gateway.ReverseLogisticDocs;
import nelsonapps.pucminas.tcc.integration.message.LogisticDocItemMessage;
import nelsonapps.pucminas.tcc.integration.message.LogisticDocMessage;
import nelsonapps.pucminas.tcc.integration.service.LogisticDocMessageService;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDocItem;
import nelsonapps.pucminas.tcc.persistence.entities.Manufacturer;
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
		List<Pair<BiConsumer<LogisticDocItemMessage,Object>,Function<LogisticDocItem,Object>>> v_AdditionalInfoItens =
				new ArrayList<>();
		
		List<Pair<BiConsumer<LogisticDocMessage,Object>,Function<LogisticDoc,Object>>> v_AdditionalInfoHeader =
				new ArrayList<>();


		BiConsumer<LogisticDocItemMessage, Object> v_setAdditionalInfoFunc = 
				(docItemMessage,returnReason) -> docItemMessage.setReturnReason(((ReturnReasonEnum)returnReason).getDescription());
				
		Function<LogisticDocItem, Object> v_getAdditionalInfoFunc = (docItem) -> ReturnReasonEnum.valueOf(docItem.getReturnReason());

		
		v_AdditionalInfoItens.add(Pair.of(v_setAdditionalInfoFunc, v_getAdditionalInfoFunc));
		
		BiConsumer<LogisticDocMessage,Object> v_setManufacturerFunc = (docHeader,manufacturerCnpj) -> docHeader.setManufacturerCnpj((String)manufacturerCnpj);
		Function<LogisticDoc,Object> v_getManufacturerInfo = (plogisticDoc) -> ((Manufacturer)plogisticDoc.getPartner()).getCnpj();
				
        v_AdditionalInfoHeader.add(Pair.of(v_setManufacturerFunc, v_getManufacturerInfo));
		
        reverseLogisticDocs.notifyItensToCollect(logisticDocMessageService.create(logisticDoc, v_AdditionalInfoHeader, v_AdditionalInfoItens));
	}

}
