package nelsonapps.pucminas.tcc.test.integration.endpoint;

import nelsonapps.pucminas.tcc.integration.message.LogisticDocMessage;

public class ReceiveManufacturerNotifyEndPoint {

	private LogisticDocMessage receivedMessage;
	
	public void receive(LogisticDocMessage logisticDocMessage){
		receivedMessage = logisticDocMessage;
	}
	public LogisticDocMessage getReceivedMessage(){
		return receivedMessage;
	}
}
