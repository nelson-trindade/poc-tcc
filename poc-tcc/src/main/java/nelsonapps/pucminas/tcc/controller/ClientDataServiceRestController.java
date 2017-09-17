package nelsonapps.pucminas.tcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nelsonapps.pucminas.tcc.persistence.entities.Client;
import nelsonapps.pucminas.tcc.service.interfaces.IClientDataService;

@RestController
@RequestMapping(value="/client",produces=MediaType.APPLICATION_JSON_VALUE)
public class ClientDataServiceRestController {

	@Autowired
	private IClientDataService clientDataSevice;
	
	@GetMapping
	public Page<Client> searchByName(@RequestParam("searchTerm")String searchTerm,
			@RequestParam("page")int page,@RequestParam("size")int size){
		
		return clientDataSevice.findByNameContaining(searchTerm, new PageRequest(page,size));
	
	}
	
}
