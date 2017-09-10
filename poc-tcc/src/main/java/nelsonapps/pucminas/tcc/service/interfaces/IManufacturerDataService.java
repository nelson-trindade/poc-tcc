package nelsonapps.pucminas.tcc.service.interfaces;

import nelsonapps.pucminas.tcc.persistence.entities.Manufacturer;

public abstract class IManufacturerDataService extends PartnerDataService {

	public abstract Manufacturer findByCnpj(String cnpj);
	
}
