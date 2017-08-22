package nelsonapps.pucminas.tcc.persistence.repository;

import org.springframework.stereotype.Repository;

import nelsonapps.pucminas.tcc.persistence.entities.Manufacturer;

@Repository("manufacturerRepository")
public interface ManufacturerRepository extends BasePartnerRepository<Manufacturer> {

	Manufacturer findByCnpj(String cnpj);
}
