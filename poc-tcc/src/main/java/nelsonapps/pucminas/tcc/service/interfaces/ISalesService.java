package nelsonapps.pucminas.tcc.service.interfaces;

import nelsonapps.pucminas.tcc.integration.message.LogisticDocMessage;

public interface ISalesService {

	void createSalesOrder(LogisticDocMessage logisticDocMessage);
}
