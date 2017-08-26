package nelsonapps.pucminas.tcc.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import nelsonapps.pucminas.tcc.persistence.external.entities.Product;

public interface IStockService {

	Page<Product> findReturnableProductsByShortNameLike(String shortNameSearchTerm,Pageable pageRequest);
}
