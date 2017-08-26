package nelsonapps.pucminas.tcc.integration.gateway;

import nelsonapps.pucminas.tcc.integration.message.LogisticDocMessage;

public interface ReverseLogisticDocs {
	/*
	public ReverseLogisticDocs(){
		System.out.println("Construção do bean");
	}
	
	public void testStringPayload(String text){
		System.out.println(text);
	}*/
	void notifyItensToCollect(LogisticDocMessage logisticDocMessage);
}
