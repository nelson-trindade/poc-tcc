package nelsonapps.pucminas.tcc.persistence.repository;

import org.springframework.stereotype.Repository;

import nelsonapps.pucminas.tcc.persistence.entities.Client;

@Repository("clientRepository")
public interface ClientRepository extends BasePartnerRepository<Client> {

	Client findByCpf(String cpf);
}
