package nelsonapps.pucminas.tcc.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import nelsonapps.pucminas.tcc.persistence.entities.Partner;

public interface BasePartnerRepository<E extends Partner> extends JpaRepository<E, Long> {

	Page<E> findByNameLike(String nameSearchTerm,Pageable pageRequest);
}
