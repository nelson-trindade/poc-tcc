package nelsonapps.pucminas.tcc.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import nelsonapps.pucminas.tcc.persistence.external.entities.Product;
import nelsonapps.pucminas.tcc.persistence.external.repository.ProductRepository;

public abstract class IProductDataService extends BaseDataService<Product, ProductRepository> {

	public abstract Page<Product> findByShortNameLike(String shortNameSearchTerm,Pageable pageRequest);
}
