package accesoDatos;

/** 
 * Proyecto: Juego de la vida.
 * Clase para la gestión del almacenamiento.
 * de datos del programa.
 * @since: prototipo1.2
 * @source: JVPrincipal.java 
 * @version: 1.2 - 2018/03/08
 * @author: ajp
 */
import java.util.ArrayList;
import java.util.HashMap;

import modelo.*;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class Datos {
	
	private static ArrayList <Usuario> datosUsuarios;
	private static ArrayList <SesionUsuario> datosSesiones;
	private static HashMap<String, String> equivalenciasId = new HashMap<String, String>();
	
	/**
	 * Constructor por defecto.
	 */
	public Datos() {
		datosUsuarios = new ArrayList <Usuario>();
		datosSesiones = new ArrayList <SesionUsuario>();
		cargarDatosUsuariosPrueba();
	}
	
	public ArrayList <Usuario> getDatosUsuarios() {
		return datosUsuarios;
	}

	public ArrayList <SesionUsuario> getDatosSesiones() {
		return datosSesiones;
	}

	public int getUsuariosRegistrados() {
		return datosUsuarios.size();
	}

	public int getSesionesRegistradas() {
		return datosSesiones.size();
	}
	
	/**
	 * Genera datos de prueba válidos dentro 
	 * del almacén de datos.
	 */
	public void cargarDatosUsuariosPrueba() {
		String[] NifValidos = { "00000000T", "00000001R", "00000002W", "00000003A", "00000004G",
				"00000005M", "00000006Y", "00000007F", "00000008P", "00000009D" };
		for (int i = 0; i < NifValidos.length ; i++) {
			Usuario usr =  new Usuario(new Nif(NifValidos[i]), "Pepe",
					"López Pérez", new DireccionPostal("Alta", "10", "30012", "Murcia"), 
					new Correo("pepe" + i + "@gmail.com"), new Fecha(2000, 11, 30), 
					new Fecha(), new ClaveAcceso(), RolUsuario.NORMAL);
			try {
				registrarUsuario(usr);
			} catch (Exception e) {		
				e.printStackTrace();
			}
		}
	}
	/**
	 * Obtiene el idUsr que equivale a la credencial recibida.
	 * @param credencialUsr, puede ser nif o correo.
	 */
	public String equivalenciaId(String credencialUsr) {	
		return equivalenciasId.get(credencialUsr);
	}

	/**
	 * Búsqueda binaria de usuario dado su id.
	 * @param id - el id de Usuario a buscar.
	 * @return - el Usuario encontrado o null si no existe.
	 */
	public Usuario buscarUsuario(String id) {
		String idUsr = equivalenciasId.get(id);
		if (idUsr != null) {
			int posicion = obtenerPosicion(idUsr);
			if (posicion >= 0) {
				return datosUsuarios.get(posicion - 1);
			}
		}
		return null;				
	}
	
	/**
	 *  Alta de un nuevo usuario en orden y sin repeticiones según el campo idUsr. 
	 *  Localiza previamente la posición que le corresponde por búsqueda binaria.
	 *	@param usr - Objeto a almacenar.
	 * @throws Exception 
	 */
	public void registrarUsuario(Usuario usr) throws Exception  {
		assert usr != null : "Usuario null...";
		int posicionInsercion = obtenerPosicion(usr.getIdUsr()); 
		if (posicionInsercion < 0) {
			datosUsuarios.add(-posicionInsercion-1, usr); 	// Inserta el usuario en orden.
			registrarEquivalenciaId(usr);
		}
		else {
			// Si usr es diferente del almacenado...
			if (!usr.equals(datosUsuarios.get(posicionInsercion-1))) {
				int intentos = "ABCDEFGHJKLMNPQRSTUVWXYZ".length();
				do {
					usr.generarVarianteIdUsr();
					posicionInsercion = obtenerPosicion(usr.getIdUsr());
					if (posicionInsercion < 0) {
						datosUsuarios.add(-posicionInsercion - 1, usr); // Inserta el usuario en orden.
						registrarEquivalenciaId(usr);
						return;
					}
					intentos--;
				} while (intentos >= 0);
				throw new Exception("ALTA: imposible generar variante del " + usr.getIdUsr());
			}
			throw new Exception("ALTA: El Usuario " + usr.getIdUsr() + " ya existe...");
		}
	}

	/**
	 *  Obtiene por búsqueda binaria, la posición que ocupa, o ocuparía,  un usuario en 
	 *  la estructura.
	 *	@param IdUsr - id de Usuario a buscar.
	 *	@return - la posición, en base 1, que ocupa un objeto o la que ocuparía (negativo).
	 */
	private int obtenerPosicion(String idUsr) {
		int comparacion;
		int inicio = 0;
		int fin = datosUsuarios.size()-1;
		int medio = 0;
		while (inicio <= fin) {
			medio = (inicio + fin) / 2;			    // Calcula posición central.
			// Obtiene > 0 si idUsr va después que medio.
			comparacion = idUsr.compareTo(datosUsuarios.get(medio).getIdUsr());
			if (comparacion == 0) {			
				return medio + 1;   				// Posción ocupada, base 1	  
			}		
			if (comparacion > 0) {
				inicio = medio + 1;
			}			
			else {
				fin = medio - 1;
			}
		}	
		return -(inicio + 1);						// Posición que ocuparía, negativo, base 1
	}

	/**
	 *  Añade nif y correo como equivalencias de idUsr.
	 *	@param usr - Objeto a almacenar.
	 * @throws Exception 
	 */
	private void registrarEquivalenciaId(Usuario usr) {
		//Añade equivalencias para idUsr 
		equivalenciasId.put(usr.getIdUsr(), usr.getIdUsr());
		equivalenciasId.put(usr.getNif().getTexto(), usr.getIdUsr());
		equivalenciasId.put(usr.getCorreo().getTexto(), usr.getIdUsr());
	}
	
	/**
	 * Añade una nueva sesión en el almacén de datos.
	 * @param sesionUsuario a guardar.
	 */
	public void registrarSesion(SesionUsuario sesionUsuario) {
		datosSesiones.add(sesionUsuario);	
	}

	/**
	 * Obtiene todos los usuarios almacenados en texto formateado.
	 */
	public String textoDatosUsuarios() {
		StringBuilder aux = new StringBuilder();
		for (Usuario u: datosUsuarios) {
			aux.append("\n" + u);
		}
		return aux.toString();
	}

	/**
	 * Volcado de todos los usuarios almacenados.
	 */
	public String volcarDatosUsuarios() {
		StringBuilder aux = new StringBuilder();
		for (Usuario u: datosUsuarios) {
			aux.append("<usr>"  
					+"<attrib>"+u.getNif()+"</attrib>"
					+"<attrib>"+u.getNombre()+"</attrib>"
					+"<attrib>"+u.getApellidos()+"</attrib>"
					+"<attrib>"+u.getIdUsr()+"</attrib>"
					+"<attrib>"+u.getDomicilio()+"</attrib>"
					+"<attrib>"+u.getCorreo()+"</attrib>"
					+"<attrib>"+u.getFechaNacimiento()+"</attrib>"
					+"<attrib>"+u.getFechaAlta()+"</attrib>"
					+"<attrib>"+u.getClaveAcceso()+"</attrib>"
					+"<attrib>"+u.getRol()+"</attrib>"
					+"</usr>");
		}
		return aux.toString();
	}

	/**
	 * Volcado de todos las sesiones almacenadas.
	 */
	public String volcarDatosSesiones() {
		StringBuilder aux = new StringBuilder();
		for (SesionUsuario s: datosSesiones) {
			aux.append("<sesion>"
					+"<usr>"  
						+"<attrib>"+s.getUsr().getNif()+"</attrib>"
						+"<attrib>"+s.getUsr().getNombre()+"</attrib>"
						+"<attrib>"+s.getUsr().getApellidos()+"</attrib>"
						+"<attrib>"+s.getUsr().getIdUsr()+"</attrib>"
						+"<attrib>"+s.getUsr().getDomicilio()+"</attrib>"
						+"<attrib>"+s.getUsr().getCorreo()+"</attrib>"
						+"<attrib>"+s.getUsr().getFechaNacimiento()+"</attrib>"
						+"<attrib>"+s.getUsr().getFechaAlta()+"</attrib>"
						+"<attrib>"+s.getUsr().getClaveAcceso()+"</attrib>"
						+"<attrib>"+s.getUsr().getRol()+"</attrib>"
					+"</usr>"
					+"<attrib>"+s.getFecha()+"</attrib>"
					+"<sesion>");
		}
		return aux.toString();
	}

} //class
