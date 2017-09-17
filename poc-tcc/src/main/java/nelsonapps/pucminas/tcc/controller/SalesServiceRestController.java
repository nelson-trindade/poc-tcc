package nelsonapps.pucminas.tcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nelsonapps.pucminas.tcc.constants.Constants;
import nelsonapps.pucminas.tcc.dto.ResponseMessage;
import nelsonapps.pucminas.tcc.integration.message.LogisticDocMessage;
import nelsonapps.pucminas.tcc.service.interfaces.ISalesService;

@RestController
@RequestMapping(value="/sales",produces=MediaType.APPLICATION_JSON_VALUE)
public class SalesServiceRestController {
	
	@Autowired
	private ISalesService salesService;
	
	@PostMapping("/salesOrder")
	public ResponseMessage createSalesOrder(@RequestBody LogisticDocMessage logisticDocMessage){
		try{
			salesService.createSalesOrder(logisticDocMessage);
			return new ResponseMessage(){
				{
					message = Constants.SuccessMessages.SO_CREATED_SUCCESS;
				}
			};
		}catch(Exception ex){
			return new ResponseMessage(){
				{
					message = Constants.ErrorMessages.SO_CREAT_ERROR +" "+ex.getLocalizedMessage();
				}
			};
		}
	}
}
