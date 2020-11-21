package br.com.caelum.estoque.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;

import br.com.caelum.estoque.exception.AutorizacaoException;
import br.com.caelum.estoque.modelo.item.Filtro;
import br.com.caelum.estoque.modelo.item.Filtros;
import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;
import br.com.caelum.estoque.modelo.item.ItemValidador;
import br.com.caelum.estoque.modelo.item.ListaItens;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;

@WebService
//@SOAPBinding(style=Style.RPC)
//@SOAPBinding(style=Style.DOCUMENT,parameterStyle=ParameterStyle.BARE) // -> quando queremos entregar apenas o item sem ter conhecimento de qual método/procedimento é chamado no lado do servidor
@SOAPBinding(style=Style.DOCUMENT,parameterStyle=ParameterStyle.WRAPPED) // -> este é o padrão e não precisa ser explícito
public class EstoqueWS {
	
	private ItemDao dao = new ItemDao();

	@WebMethod(operationName = "todosOsItens")
	//@ResponseWrapper(localName = "itens")
	@WebResult(name = "itens")
	//@RequestWrapper(localName = "listaItens")
    public ListaItens getItens(@WebParam(name="filtros") Filtros filtros) {
		System.out.println("Chamando getItens()");
        List<Filtro> lista = filtros.getLista();
        List<Item> itensResultado = dao.todosItens(lista);
        return new ListaItens(itensResultado);
    }
	
	@WebMethod(action="CadastrarItem", operationName="CadastrarItem") 
	@WebResult(name="item")
	public Item cadastrarItem(@WebParam(name="tokenUsuario", header=true) TokenUsuario token, 
			@WebParam(name="item") Item item) throws AutorizacaoException {
		System.out.println("Cadastrando " + item + ", " + token); //imprimindo o token tbm
		
		boolean valido = new TokenDao().ehValido(token);

		if(!valido) {
			throw new AutorizacaoException("Autorizacao falhou");
		}
		
		new ItemValidador(item).validate();
		
		this.dao.cadastrar(item);
		return item;
	}

}
