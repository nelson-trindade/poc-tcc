package nelsonapps.pucminas.tcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nelsonapps.pucminas.tcc.persistence.entities.Manufacturer;
import nelsonapps.pucminas.tcc.persistence.repository.ManufacturerRepository;
import nelsonapps.pucminas.tcc.service.interfaces.IManufacturerDataService;


@Service("manufacturerDataService")
public class ManufacturerDataService extends IManufacturerDataService{

	@Autowired
	private ManufacturerRepository manufacturerRepository;
	
	@Override
	public Manufacturer findByCnpj(String cnpj) {
		return manufacturerRepository.findByCnpj(cnpj);
	}

}
