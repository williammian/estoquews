package br.com.caelum.estoque.webservice;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class RelatorioService {

	@Oneway
    @WebMethod(operationName="GerarRelatorio")
    public void gerarRelatorio() { 
        // c�digo omitido
    }

}