package nelsonapps.pucminas.tcc.integration.service;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import nelsonapps.pucminas.tcc.integration.message.LogisticDocItemMessage;
import nelsonapps.pucminas.tcc.integration.message.LogisticDocMessage;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDocItem;

@Service("logisticDocMessageService")
public class LogisticDocMessageService {
	
	public LogisticDocMessage create(LogisticDoc logisticDoc,
			List<Pair<BiConsumer<LogisticDocMessage,Object>,
			Function<LogisticDoc,Object>>>getAdditionalInfoFunctionsHeader,
			List<Pair<BiConsumer<LogisticDocItemMessage,Object>,Function<LogisticDocItem,Object>>> getAdditionalInfoFunctionsItens) {
		
		LogisticDocMessage v_logisticDocMessage =  new LogisticDocMessage();
		v_logisticDocMessage.setDocNum(logisticDoc.getDocNum().getId());
		getAdditionalInfoFunctionsHeader.forEach(p-> p.getLeft().accept(v_logisticDocMessage, p.getRight().apply(logisticDoc)));
		
		LogisticDocItemMessage[] v_itemsPayload = 
		logisticDoc.getItems().stream().map(i -> {
			LogisticDocItemMessage v_ItemMessage = new LogisticDocItemMessage();
		    v_ItemMessage.setProductUUID(i.getProductUUID());
		    v_ItemMessage.setQuantity(i.getQuantity().doubleValue());
			getAdditionalInfoFunctionsItens.forEach(pair ->
			                                  pair.getLeft().accept(v_ItemMessage, pair.getRight().apply(i)));
			return v_ItemMessage;
		}).toArray(size -> new LogisticDocItemMessage[size]);
		
		v_logisticDocMessage.setItems(v_itemsPayload);
		return v_logisticDocMessage;	
	
	}
	
}
