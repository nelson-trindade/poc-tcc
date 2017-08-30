package nelsonapps.pucminas.tcc.integration.gateway;

import nelsonapps.pucminas.tcc.integration.message.LogisticDocMessage;

public interface ReverseLogisticDocs {
	public void notifyItensToCollect(LogisticDocMessage logisticDocMessage);
}
