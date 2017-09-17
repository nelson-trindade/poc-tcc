package nelsonapps.pucminas.tcc.persistence.external.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nelsonapps.pucminas.tcc.persistence.external.entities.Product;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findByManufacturerCnpj(String manufacturerCnpj,Pageable pageRequest);
	Page<Product> findByShortNameLike(String shortNameSearchTerm,Pageable pageRequest);
	Page<Product> findByShortNameLikeAndReturnablePackage(String shortNameSearchTerm,boolean returnablePackage,Pageable pageRequest);
}


