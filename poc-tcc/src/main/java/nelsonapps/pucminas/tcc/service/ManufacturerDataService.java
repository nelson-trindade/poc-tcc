package nelsonapps.pucminas.tcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	

	@SuppressWarnings("unchecked")
	public Page<Manufacturer> findByNameLike(String nameSearchTerm,Pageable pageRequest){
		return manufacturerRepository.findByNameContaining(nameSearchTerm, pageRequest);
		
	}
	
	@SuppressWarnings("unchecked")
	public Page<Manufacturer> findByNameContaining(String nameSearchTerm,Pageable pageRequest){
		return manufacturerRepository.findByNameContaining(nameSearchTerm, pageRequest);
	}
	
}
