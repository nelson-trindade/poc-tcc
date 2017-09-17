package nelsonapps.pucminas.tcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nelsonapps.pucminas.tcc.persistence.external.entities.Product;
import nelsonapps.pucminas.tcc.service.interfaces.IProductDataService;

@RestController
@RequestMapping(value="/product",produces=MediaType.APPLICATION_JSON_VALUE)
public class ProductDataServiceRestController {

	@Autowired
	private IProductDataService productDataService;
	
	@GetMapping
	public Page<Product>searchByName(@RequestParam("searchTerm")String searchTerm,
			@RequestParam("page")int page,@RequestParam("size")int size){
		return productDataService.findByShortNameLike(searchTerm, new PageRequest(page,size));
	}
	
}
