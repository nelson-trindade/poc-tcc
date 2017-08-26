package nelsonapps.pucminas.tcc.service.interfaces;

import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;

public interface IReverseLogisticService {

	void sendReturnableLogisticDoc(LogisticDoc logisticDoc);
}
