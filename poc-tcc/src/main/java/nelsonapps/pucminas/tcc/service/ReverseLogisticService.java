package nelsonapps.pucminas.tcc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nelsonapps.pucminas.tcc.integration.gateway.ReverseLogisticDocs;
import nelsonapps.pucminas.tcc.integration.message.LogisticDocItemMessage;
import nelsonapps.pucminas.tcc.integration.message.LogisticDocMessage;
import nelsonapps.pucminas.tcc.integration.service.LogisticDocMessageService;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDocItem;
import nelsonapps.pucminas.tcc.persistence.entities.Manufacturer;
import nelsonapps.pucminas.tcc.persistence.entities.Partner;
import nelsonapps.pucminas.tcc.persistence.enums.DocTypeEnum;
import nelsonapps.pucminas.tcc.persistence.enums.ReturnReasonEnum;
import nelsonapps.pucminas.tcc.service.interfaces.ILogisticDocService;
import nelsonapps.pucminas.tcc.service.interfaces.IManufacturerDataService;
import nelsonapps.pucminas.tcc.service.interfaces.IReverseLogisticService;

@Service("reverseLogisticService")
public class ReverseLogisticService implements IReverseLogisticService {

	@Autowired
	private LogisticDocMessageService logisticDocMessageService;
	
	@Autowired
	private ReverseLogisticDocs reverseLogisticDocs;
	
	@Autowired
	private IManufacturerDataService manufacturerDataService;
	
	@Autowired
	private ILogisticDocService logisticDocService;
	
	private ArrayList<LogisticDocItem> parseDocItens(LogisticDocItemMessage[] payload){
		ArrayList<LogisticDocItem> v_LogisticDocItems = new ArrayList<>();
		LogisticDocItem[] v_Array=Arrays.asList(payload).stream().map(item_payload -> {
			LogisticDocItem v_Item = new LogisticDocItem();
			v_Item.setQuantity(BigDecimal.valueOf(item_payload.getQuantity()));
			v_Item.setProductUUID(item_payload.getProductUUID());
			v_Item.setReturnReason(item_payload.getReturnReason());
			v_Item.setPrice(BigDecimal.ZERO);
			v_Item.setExpiringDate(item_payload.getExpiringDate());
			return v_Item;
		}).toArray(size -> new LogisticDocItem[size]);
		
		Collections.addAll(v_LogisticDocItems,v_Array);
		
		return v_LogisticDocItems;
	}
	
	
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
	
	
	@Override
	public void sendReturnableLogisticDoc(LogisticDocMessage logisticDocMessage) {
		LogisticDoc v_doc = createReturnLogisticDoc(logisticDocMessage);
		sendReturnableLogisticDoc(v_doc);
	}

	@Transactional
	private LogisticDoc createReturnLogisticDoc(LogisticDocMessage logisticDocMessage) {
		Partner v_Partner = manufacturerDataService.findByCnpj(logisticDocMessage.getManufacturerCnpj());
		DocTypeEnum v_DocType = DocTypeEnum.RET;
		LogisticDoc v_LogisticDoc = logisticDocService.create(v_DocType, v_Partner);
		ArrayList<LogisticDocItem> v_Itens = parseDocItens(logisticDocMessage.getItems());
		return logisticDocService.addItems(v_LogisticDoc,v_Itens);
	}

}
