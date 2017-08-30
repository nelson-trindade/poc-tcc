package nelsonapps.pucminas.tcc.integration.message;

import java.util.List;

public class LogisticDocMessage{
	
	private long docNum;
	
	private List<LogisticDocItemMessage>items;
	private String manufacturerCnpj;
	
	public long getDocNum() {
		return docNum;
	}
	public void setDocNum(long docNum) {
		this.docNum = docNum;
	}
	public List<LogisticDocItemMessage> getItems() {
		return items;
	}
	public void setItems(List<LogisticDocItemMessage> items) {
		this.items = items;
	}
	public String getManufacturerCnpj(){
		return manufacturerCnpj;
	}
	public void setManufacturerCnpj(String manufacturerCnpj){
		this.manufacturerCnpj = manufacturerCnpj;
	}
}
