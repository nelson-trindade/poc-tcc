package nelsonapps.pucminas.tcc.integration.message;

public class LogisticDocMessage{
	
	private long docNum;
	
	private LogisticDocItemMessage[] items;
	private String manufacturerCnpj;
	private String clientCpf;
	
	public long getDocNum() {
		return docNum;
	}
	public void setDocNum(long docNum) {
		this.docNum = docNum;
	}
	public LogisticDocItemMessage[] getItems() {
		return items;
	}
	public void setItems(LogisticDocItemMessage[] items) {
		this.items = items;
	}
	public String getManufacturerCnpj(){
		return manufacturerCnpj;
	}
	public void setManufacturerCnpj(String manufacturerCnpj){
		this.manufacturerCnpj = manufacturerCnpj;
	}
	public String getClientCpf() {
		return clientCpf;
	}
	public void setClientCpf(String clientCpf) {
		this.clientCpf = clientCpf;
	}
}
