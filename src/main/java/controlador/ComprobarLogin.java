package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloDAO.ModeloUsuario;
import modeloDTO.Usuario;

/**
 * Comprueba el login del usuario
 */
@WebServlet("/ComprobarLogin")
public class ComprobarLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComprobarLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("login/loginUsuario.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dni = request.getParameter("dni");
		String contrasena = request.getParameter("contrasena");
		
		ModeloUsuario modeloUsuario = new ModeloUsuario();
		modeloUsuario.conectar();
		
		if (modeloUsuario.getLogin(dni, contrasena)) {
			HttpSession sesion = request.getSession();
			
			Usuario usuario = modeloUsuario.getUsuariosViaDNI(dni);
			
			sesion.setAttribute("usuario", usuario);
			
			response.sendRedirect("VistaUsuario");
			//request.getRequestDispatcher("VistaUsuario").forward(request, response);
		}else {
			doGet(request, response);
		}
		
		modeloUsuario.cerrar();
	}
}
