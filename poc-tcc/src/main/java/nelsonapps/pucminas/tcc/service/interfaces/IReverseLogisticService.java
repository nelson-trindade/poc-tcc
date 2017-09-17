package nelsonapps.pucminas.tcc.service.interfaces;

import nelsonapps.pucminas.tcc.integration.message.LogisticDocMessage;
import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;

public interface IReverseLogisticService {

	void sendReturnableLogisticDoc(LogisticDoc logisticDoc);
	void sendReturnableLogisticDoc(LogisticDocMessage logisticDocMessage);
}
