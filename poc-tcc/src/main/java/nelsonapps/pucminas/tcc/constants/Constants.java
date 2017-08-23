package nelsonapps.pucminas.tcc.constants;

public class Constants {

	public static final String cnpjRegex = "[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}/?[0-9]{4}-?[0-9]{2}";
	public static final String cpfRegex = "([0-9]{3}\\.?){2}[0-9]{3}\\-?[0-9]{2}";
	
	public class Labels {
		public static final String COT = "Cotação";
		public static final String PO = "Pedido de compra";
		public static final String SO = "Ordem de venda";
		public static final String RET = "Devolução";
		public static final String EXPIRED_DATE = "Data de validade";
		public static final String EMPTY_PACKAGE = "Embalagem vazia";
		public static final String START_DATE_KEY = "dataInicio";
		public static final String END_DATE_KEY="dataFim";
	}
	
	public class ErrorMessages{
		public static final String AT_LEAST_ONE_NOT_NULL_ARGUMENT_ERROR="Ao menos um parâmetro deve ser diferente de nulo";
		public static final String INCORRECT_DATES_KEY_LOGISTICDOCS_SEARCH_ERROR="Chaves de datas incorretas na busca de documentos de compra";
	}
	
}
