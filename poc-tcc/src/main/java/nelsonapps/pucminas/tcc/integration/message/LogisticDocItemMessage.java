package nelsonapps.pucminas.tcc.integration.message;

public class LogisticDocItemMessage {

	private double quantity;	
	private String productUUID;
    private String returnReason;
	
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getProductUUID() {
		return productUUID;
	}
	public void setProductUUID(String productUUID) {
		this.productUUID = productUUID;
	}
	public String getReturnReason(){
		return returnReason;
	}
	public void setReturnReason(String returnReason){
		this.returnReason = returnReason;
	}
}
