package nelsonapps.pucminas.tcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import nelsonapps.pucminas.tcc.persistence.entities.Client;
import nelsonapps.pucminas.tcc.persistence.repository.ClientRepository;
import nelsonapps.pucminas.tcc.service.interfaces.IClientDataService;

@Service("clientDataService")
public class ClientDataService extends IClientDataService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Override
	public Client findByCpf(String cpf) {
		return clientRepository.findByCpf(cpf);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Client> findByNameLike(String nameSearchTerm, Pageable pageRequest) {
		return clientRepository.findByNameLike(nameSearchTerm, pageRequest);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Client> findByNameContaining(String nameSearchTerm, Pageable pageRequest) {
		return clientRepository.findByNameContaining(nameSearchTerm, pageRequest);
	}

}
