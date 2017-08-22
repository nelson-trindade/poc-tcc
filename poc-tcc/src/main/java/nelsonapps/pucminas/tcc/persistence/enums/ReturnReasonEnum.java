package nelsonapps.pucminas.tcc.persistence.enums;

import nelsonapps.pucminas.tcc.constants.Constants;

public enum ReturnReasonEnum {

	EXPIRED_DATE("EXPIRED_DATE",Constants.Labels.EXPIRED_DATE),
	EMPTY_PACKAGE("EMPTY_PACKAGE",Constants.Labels.EMPTY_PACKAGE);

	private final String enumValue;
	private final String description;
	
	ReturnReasonEnum(String enumValue,String description){
		this.enumValue = enumValue;
		this.description = description;
	}
	
	public String getEnumValue(){
		return enumValue;
	}
	
	public String getDescription(){
		return description;
	}
}
