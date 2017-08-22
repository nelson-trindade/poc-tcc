package nelsonapps.pucminas.tcc.persistence.enums;

import nelsonapps.pucminas.tcc.constants.Constants;

public enum DocTypeEnum {
	COT("COT",Constants.Labels.COT),
	PO("PO",Constants.Labels.PO),
	SO("SO",Constants.Labels.SO),
	RET("RET",Constants.Labels.RET);
	
	private final String enumValue;
	private final String description;
	
	DocTypeEnum(String enumValue,String description){
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
