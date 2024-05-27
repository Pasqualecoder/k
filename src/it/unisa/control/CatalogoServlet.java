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
		// creo una lista una whitelist di pagine a cui � possibile fare l'accesso 
		List<String> validPages = Arrays.asList("Account.jsp", "Carrello.jsp", "Catalogo.jsp", "Checkout.jsp", "ComposizioneOrdine.jsp", "Dettagli.jsp", "Home.jsp", "Login.jsp", "MieiOrdini.jsp", "Ps4.jsp", "Ps5.jsp", "Registrazione.jsp", "Switch.jsp", "XboxOne.jsp", "XboxSeries.jsp", "admin/AddProdotto.jsp", "admin/GestioneCatalogo.jsp", "admin/ModificaProdotto.jsp", "admin/ViewOrdini.jsp");
		if (!validPages.contains(redirectedPage)) { // se la pagina non � contenuta nella whitelist
			redirectedPage = "Home.jsp"; // reindirizzo sempre alla home
		}
	
		try {
			if(action!=null) {
				if(action.equalsIgnoreCase("add")) {
					bean.setNome(request.getParameter("nome"));
					bean.setDescrizione(request.getParameter("descrizione"));
					bean.setIva(request.getParameter("iva"));
					bean.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
					bean.setQuantit�(Integer.parseInt(request.getParameter("quantit�")));
					bean.setPiattaforma(request.getParameter("piattaforma"));
					bean.setGenere(request.getParameter("genere"));
					bean.setImmagine(request.getParameter("img"));
					bean.setDataUscita(request.getParameter("dataUscita"));
					bean.setDescrizioneDettagliata(request.getParameter("descDett"));
					bean.setInVendita(true);
					prodDao.doSave(bean);
				}
				
				else if(action.equalsIgnoreCase("modifica")) {
					
					bean.setIdProdotto(Integer.parseInt(request.getParameter("id")));
					bean.setNome(request.getParameter("nome"));
					bean.setDescrizione(request.getParameter("descrizione"));
					bean.setIva(request.getParameter("iva"));
					bean.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
					bean.setQuantit�(Integer.parseInt(request.getParameter("quantit�")));
					bean.setPiattaforma(request.getParameter("piattaforma"));
					bean.setGenere(request.getParameter("genere"));
					bean.setImmagine(request.getParameter("img"));
					bean.setDataUscita(request.getParameter("dataUscita"));
					bean.setDescrizioneDettagliata(request.getParameter("descDett"));
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

}
