package nelsonapps.pucminas.tcc.integration.message;

import java.util.Date;

public class LogisticDocItemMessage {

	private double quantity;	
	private String productUUID;
	private double price;
	private Date expiringDate;
	private Date deliveryDate;
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
	public Date getExpiringDate() {
		return expiringDate;
	}
	public void setExpiringDate(Date expiringDate) {
		this.expiringDate = expiringDate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
}
