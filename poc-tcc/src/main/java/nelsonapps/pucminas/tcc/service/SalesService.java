package nelsonapps.pucminas.tcc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nelsonapps.pucminas.tcc.integration.message.LogisticDocItemMessage;
import nelsonapps.pucminas.tcc.integration.message.LogisticDocMessage;
import nelsonapps.pucminas.tcc.persistence.entities.Client;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDocItem;
import nelsonapps.pucminas.tcc.persistence.enums.DocTypeEnum;
import nelsonapps.pucminas.tcc.service.interfaces.IClientDataService;
import nelsonapps.pucminas.tcc.service.interfaces.ILogisticDocService;
import nelsonapps.pucminas.tcc.service.interfaces.ISalesService;

@Service("salesService")
public class SalesService implements ISalesService {

	@Autowired
	private ILogisticDocService logisticDocService;
	
	@Autowired
	private IClientDataService clientDataService;
	
	@Override
	@Transactional
	public void createSalesOrder(LogisticDocMessage logisticDocMessage) {
		Client v_Client = clientDataService.findByCpf(logisticDocMessage.getClientCpf());
		LogisticDoc v_salesOrder = logisticDocService.create(DocTypeEnum.SO, v_Client);
		ArrayList<LogisticDocItem>v_Itens = parseDocItens(logisticDocMessage.getItems());
		logisticDocService.addItems(v_salesOrder, v_Itens);
	}

	private ArrayList<LogisticDocItem> parseDocItens(LogisticDocItemMessage[] items) {
		ArrayList<LogisticDocItem> v_Array = new ArrayList<>();
		LogisticDocItem[] v_Itens = Arrays.asList(items).stream().map(it -> {
			LogisticDocItem v_MapItem = new LogisticDocItem();
			v_MapItem.setPrice(BigDecimal.valueOf(it.getPrice()));
			v_MapItem.setQuantity(BigDecimal.valueOf(it.getQuantity()));
			v_MapItem.setProductUUID(it.getProductUUID());
			return v_MapItem;
		}).toArray(size -> new LogisticDocItem[items.length]);
		
		Collections.addAll(v_Array, v_Itens);
		return v_Array;
	}

}
