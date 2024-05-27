package it.unisa.control;

import java.io.IOException; 
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.ProdottoBean;
import it.unisa.model.ProdottoDao;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProdottoDao prodDao = new ProdottoDao();
		ProdottoBean bean = new ProdottoBean();
		String sort = request.getParameter("sort");
		String action = request.getParameter("action");
		String redirectedPage = request.getParameter("page");
		// input validation
		// creo una lista una whitelist di pagine a cui è possibile fare l'accesso 
		List<String> validPages = Arrays.asList("Account.jsp", "Carrello.jsp", "Catalogo.jsp", "Checkout.jsp", "ComposizioneOrdine.jsp", "Dettagli.jsp", "Home.jsp", "Login.jsp", "MieiOrdini.jsp", "Ps4.jsp", "Ps5.jsp", "Registrazione.jsp", "Switch.jsp", "XboxOne.jsp", "XboxSeries.jsp", "admin/AddProdotto.jsp", "admin/GestioneCatalogo.jsp", "admin/ModificaProdotto.jsp", "admin/ViewOrdini.jsp");
		if (!validPages.contains(redirectedPage)) { // se la pagina non è contenuta nella whitelist
			redirectedPage = "Home.jsp"; // reindirizzo sempre alla home
		}
	
		try {
			if(action!=null) {
				if (action.equalsIgnoreCase("add")) {
				    bean.setNome(escapeHtml(request.getParameter("nome")));
				    bean.setDescrizione(escapeHtml(request.getParameter("descrizione")));
				    bean.setIva(escapeHtml(request.getParameter("iva")));
				    bean.setPrezzo(Double.parseDouble(escapeHtml(request.getParameter("prezzo"))));
				    bean.setQuantità(Integer.parseInt(escapeHtml(request.getParameter("quantità"))));
				    bean.setPiattaforma(escapeHtml(request.getParameter("piattaforma")));
				    bean.setGenere(escapeHtml(request.getParameter("genere")));
				    bean.setImmagine(escapeHtml(request.getParameter("img")));
				    bean.setDataUscita(escapeHtml(request.getParameter("dataUscita")));
				    bean.setDescrizioneDettagliata(escapeHtml(request.getParameter("descDett")));
				    bean.setInVendita(true);
				    prodDao.doSave(bean);
				    
				} else if (action.equalsIgnoreCase("modifica")) {
				    bean.setIdProdotto(Integer.parseInt(escapeHtml(request.getParameter("id"))));
				    bean.setNome(escapeHtml(request.getParameter("nome")));
				    bean.setDescrizione(escapeHtml(request.getParameter("descrizione")));
				    bean.setIva(escapeHtml(request.getParameter("iva")));
				    bean.setPrezzo(Double.parseDouble(escapeHtml(request.getParameter("prezzo"))));
				    bean.setQuantità(Integer.parseInt(escapeHtml(request.getParameter("quantità"))));
				    bean.setPiattaforma(escapeHtml(request.getParameter("piattaforma")));
				    bean.setGenere(escapeHtml(request.getParameter("genere")));
				    bean.setImmagine(escapeHtml(request.getParameter("img")));
				    bean.setDataUscita(escapeHtml(request.getParameter("dataUscita")));
				    bean.setDescrizioneDettagliata(escapeHtml(request.getParameter("descDett")));
				    bean.setInVendita(true);
				    prodDao.doUpdate(bean);
				}


				request.getSession().removeAttribute("categorie");

			}
			
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}


		try {
			request.getSession().removeAttribute("products");
			request.getSession().setAttribute("products", prodDao.doRetrieveAll(sort));
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
		
			
			response.sendRedirect(request.getContextPath() + "/" +redirectedPage);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	
	private String escapeHtml(String input) {
	    if (input == null) {
	        return null;
	    }
	    
	    StringBuilder escaped = new StringBuilder();
	    for (char c : input.toCharArray()) {
	        switch (c) {
	            case '<':
	                escaped.append("&lt;");
	                break;
	            case '>':
	                escaped.append("&gt;");
	                break;
	            case '&':
	                escaped.append("&amp;");
	                break;
	            case '"':
	                escaped.append("&quot;");
	                break;
	            case '\'':
	                escaped.append("&#x27;");
	                break;
	            case '/':
	                escaped.append("&#x2F;");
	                break;
	            default:
	                escaped.append(c);
	        }
	    }
	    return escaped.toString();
	}

}
