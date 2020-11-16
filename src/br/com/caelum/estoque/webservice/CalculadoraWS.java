package br.com.caelum.estoque.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
//@SOAPBinding(style=Style.RPC)
public class CalculadoraWS {

    @WebMethod(operationName="CalculaMedia")    
    public double media(@WebParam(name="primeiroNumero") int x,
                        @WebParam(name="segundoNumero") int y) {

        return ( x + y ) / 2.0;

    }
}