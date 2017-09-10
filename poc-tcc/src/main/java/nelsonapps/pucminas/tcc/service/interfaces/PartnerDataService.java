package nelsonapps.pucminas.tcc.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import nelsonapps.pucminas.tcc.persistence.entities.Partner;
import nelsonapps.pucminas.tcc.persistence.repository.BasePartnerRepository;


public abstract class PartnerDataService extends BaseDataService<Partner, BasePartnerRepository<Partner>> {
	public abstract <E extends Partner> Page<E> findByNameLike(String nameSearchTerm,Pageable pageRequest);
	public abstract <E extends Partner> Page<E> findByNameContaining(String nameSearchTerm,Pageable pageRequest);
}
