package nelsonapps.pucminas.tcc.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import nelsonapps.pucminas.tcc.persistence.entities.Partner;
import nelsonapps.pucminas.tcc.persistence.repository.BasePartnerRepository;

public abstract class PartnerDataService extends BaseDataService<Partner, BasePartnerRepository<Partner>> {

	public Page<Partner> findByNameLike(String nameSearchTerm,Pageable pageRequest){
		return this.repository.findByNameLike(nameSearchTerm, pageRequest);
	}
}
