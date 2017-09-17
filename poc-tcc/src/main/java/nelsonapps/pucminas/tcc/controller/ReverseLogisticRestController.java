package nelsonapps.pucminas.tcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nelsonapps.pucminas.tcc.constants.Constants;
import nelsonapps.pucminas.tcc.dto.ResponseMessage;
import nelsonapps.pucminas.tcc.integration.message.LogisticDocMessage;
import nelsonapps.pucminas.tcc.service.interfaces.IReverseLogisticService;

@RestController
@RequestMapping("/returnLogisticDoc")
public class ReverseLogisticRestController {

	@Autowired
	private IReverseLogisticService reverseLogisticService;

	@PostMapping(value = "")
	public ResponseMessage sendReturnableLogisticDoc(@RequestBody LogisticDocMessage logisticDocMessage) {
		try {
			reverseLogisticService.sendReturnableLogisticDoc(logisticDocMessage);
			return new ResponseMessage() {
				{
					message = Constants.SuccessMessages.RETURN_LOGISTIC_DOC_DESPATCHED;
				}
			};

		} catch (Exception ex) {
			return new ResponseMessage() {
				{
					message = Constants.ErrorMessages.RETURN_LOGISTIC_DOC_DESPATCH_ERROR + " " + ex.getLocalizedMessage();
				}
			};
		}
	}
}
