package nelsonapps.pucminas.tcc.integration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import nelsonapps.pucminas.tcc.integration.message.LogisticDocItemMessage;
import nelsonapps.pucminas.tcc.integration.message.LogisticDocMessage;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDocItem;

@Service("logisticDocMessageService")
public class LogisticDocMessageService {

	public <E> LogisticDocMessage create(LogisticDoc logisticDoc,
			BiConsumer<LogisticDocItemMessage,E> setAdditionalInfoFunction,
			Function<LogisticDocItem,E> getAdditionalInfoFunction) {
		LogisticDocMessage v_logisticDocMessage =  new LogisticDocMessage();
		v_logisticDocMessage.setDocNum(logisticDoc.getDocNum().getId());
		
		List<LogisticDocItemMessage> v_itemsPayload = new ArrayList<>();
		logisticDoc.getItems().stream().map(i -> {
			LogisticDocItemMessage v_ItemMessage = new LogisticDocItemMessage();
		    v_ItemMessage.setProductUUID(i.getProductUUID());
		    v_ItemMessage.setQuantity(i.getQuantity());
			setAdditionalInfoFunction.accept(v_ItemMessage, getAdditionalInfoFunction.apply(i));
			return v_ItemMessage;
		}).forEach(i -> v_itemsPayload.add(i));
		
		v_logisticDocMessage.setItems(v_itemsPayload);
		return v_logisticDocMessage;	
	
	}
}
