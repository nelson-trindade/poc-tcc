package nelsonapps.pucminas.tcc.integration.message;

import java.math.BigDecimal;

public class LogisticDocItemMessage {

	private BigDecimal quantity;
	private String productUUID;
    private String returnReason;
	
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
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
