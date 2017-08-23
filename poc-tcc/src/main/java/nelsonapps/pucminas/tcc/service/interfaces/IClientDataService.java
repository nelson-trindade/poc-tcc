package nelsonapps.pucminas.tcc.service.interfaces;

import nelsonapps.pucminas.tcc.persistence.entities.Client;

public abstract class IClientDataService extends PartnerDataService{

	public abstract Client findByCpf(String cpf);
}
