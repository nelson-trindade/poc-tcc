package nelsonapps.pucminas.tcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nelsonapps.pucminas.tcc.persistence.entities.Manufacturer;
import nelsonapps.pucminas.tcc.service.ManufacturerDataService;

@RestController
@RequestMapping(value="/manufacturer",produces=MediaType.APPLICATION_JSON_VALUE)
public class ManufacturerDataServiceRestController {
     
	@Autowired
	private ManufacturerDataService manufacturerDataService;
	
	@GetMapping(path="")
	public Page<Manufacturer>searchByName(@RequestParam("searchTerm")String searchTerm,
			@RequestParam("page")int page,@RequestParam("size")int size){
		return manufacturerDataService.findByNameContaining(searchTerm, new PageRequest(page, size));
	}
}
