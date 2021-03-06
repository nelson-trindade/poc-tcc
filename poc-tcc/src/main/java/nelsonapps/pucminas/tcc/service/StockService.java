package nelsonapps.pucminas.tcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import nelsonapps.pucminas.tcc.persistence.external.entities.Product;
import nelsonapps.pucminas.tcc.persistence.external.repository.ProductRepository;
import nelsonapps.pucminas.tcc.service.interfaces.IStockService;

@Service("stockService")
public class StockService implements IStockService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Page<Product> findReturnableProductsByShortNameLike(String shortNameSearchTerm, Pageable pageRequest) {
		return productRepository.findByShortNameLikeAndReturnablePackage(shortNameSearchTerm, true, pageRequest);
	}

	

}
