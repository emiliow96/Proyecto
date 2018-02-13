import java.util.regex.Pattern;

/** Proyecto: Juego de la vida.
 *  Implementa el concepto de Usuario según el modelo1.
 *  En esta versión no se han aplicado la mayoría de los estándares 
 *  de diseño OO dirigidos a conseguir un "código limpio". 
 *  La implementación es básica con el fin ilustrar 
 *  cómo se evoluciona desde un "código malo".
 *  Se pueden detectar varios defectos y antipatrones de diseño:
 *  	- Obsesión por los tipos primitivos.  
 *  @since: prototipo1.0
 *  @source: Usuario.java 
 *  @version: 1.1 - 2018/01/21 
 *  @author: ajp
 */

public class Usuario {

	public static final String[] ROLES = {"NORMAL", "INVITADO", "ADMINISTRADOR"};

	// Apartado 2:	
	private String nif;
	private String nombre;
	private String apellidos;
	private String idUsr;
	private String domicilio;
	private String correo;
	private String fechaNacimiento;
	private String fechaAlta;
	private String claveAcceso;
	private String rol;

	/**
	 * Constructor convencional. Utiliza métodos set...()
	 * @param nif
	 * @param nombre
	 * @param apellidos
	 * @param domicilio
	 * @param correo
	 * @param fechaNacimiento
	 * @param fechaAlta
	 * @param claveAcceso
	 * @param rol
	 */
	public Usuario(String nif, String nombre, String apellidos,
			String domicilio, String correo, String fechaNacimiento,
			String fechaAlta, String claveAcceso, String rol) {
		setNif(nif);
		setNombre(nombre);
		setApellidos(apellidos);
		generarIdUsr();
		setDomicilio(domicilio);
		setCorreo(correo);
		setFechaNacimiento(fechaNacimiento);
		setFechaAlta(fechaAlta);
		setClaveAcceso(claveAcceso);
		setRol(rol);
	}

	// Apartado 2:
	/**
	 * Constructor por defecto. Reenvía al constructor convencional.
	 */
	public Usuario() {
		this("00000000A", "Nombre", "Apellido Apellido", "Domicilio", "correo@correo.es", 
				"2000.01.27", "2018.01.27", "Miau#0", ROLES[0]);
	}

	/**
	 * Constructor copia.
	 * @param usr
	 */
	public Usuario(Usuario usr) {
		nif = new String(usr.nif);
		nombre = new String(usr.nombre);
		apellidos = new String(usr.apellidos);
		idUsr = new String(usr.idUsr);
		domicilio = new String(usr.domicilio);
		correo = new String(usr.correo);
		fechaNacimiento = new String(usr.fechaNacimiento);
		fechaAlta = new String(usr.fechaAlta);
		claveAcceso = new String(usr.claveAcceso);
		rol = new String(usr.rol);
	}

	// Apartado 4:
	/**
	 * Genera el idUsr con:
	 * La letra inicial del nombre, 
	 * Las dos iniciales del primer y segundo apellido,
	 * Los dos último caracteres del nif. 
	 */
	private void generarIdUsr() {	
		assert nif != null;
		assert nombre != null;
		assert apellidos != null;
		idUsr = "" 
				+ nombre.charAt(0) 
				+ apellidos.charAt(0) + apellidos.charAt(apellidos.indexOf(" ")+1)
				+ nif.substring(7);
	}

	/**
	 * Genera una variante cambiando la última letra del idUsr 
	 * por la siguiente en el alfabeto previsto para el nif.
	 */
	public void generarVarianteIdUsr() {
		assert idUsr != null;
		String alfabetoNif = "ABCDEFGHJKLMNPQRSTVWXYZ";
		String alfabetoNifDesplazado = "BCDEFGHJKLMNPQRSTVWXYZA";
		idUsr = idUsr.substring(0, 4) 
				+ alfabetoNifDesplazado.charAt(alfabetoNif.indexOf(idUsr.charAt(4)));
	}

	// Apartado 5:
	/**
	 * Encripta el texto de la contraseña con método de cesar.
	 * Asume desplazamiento fijo de 4.
	 * @param claveEnClaro
	 * @return texto de ClaveEncriptada
	 */
	private String encriptarCesar(String claveEnClaro) {
		String alfabetoNormal = "abcdefghijklmnñopqrstuvwxyz"
						+ "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ"
						+ "0123456789"
						+ "$%*+-_#";

		String alfabetoDesplazado = "efghijklmnñopqrstuvwxyz"
						+ "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ"
						+ "0123456789"
						+ "$%*+-_#"
						+ "abcd";

		StringBuilder ClaveEncriptada = new StringBuilder();

		for (int i = 0; i < claveEnClaro.length(); i++) {
			ClaveEncriptada.append(alfabetoDesplazado.charAt(
					alfabetoNormal.indexOf(claveEnClaro.charAt(i))));
		}
		return ClaveEncriptada.toString();
	}

	// Apartado 6:
	/**
	 * Comprueba validez del nif.
	 * @param nif.
	 * @return true si cumple.
	 */
	private boolean nifValido(String nif) {
		assert nif != null;
		return	nif.matches("^[\\d]{8}[BCDEFGHJKLMNPQRSTUVWXYZA]$");
	}

	/**
	 * Comprueba validez del nombre.
	 * @param nombre.
	 * @return true si cumple.
	 */
	private boolean nombreValido(String nombre) {
		assert nombre != null;
		return	nombre.matches("^[A-ZÑÁÉÍÓÚ][a-zñáéíóú]+");
	}

	/**
	 * Comprueba validez de los apellidos.
	 * @param apellidos.
	 * @return true si cumple.
	 */
	private boolean apellidosValidos(String apellidos) {
		assert apellidos != null;
		return apellidos.matches("[A-ZÑÁÉÍÓÚ][a-zñáéíóú]+ [A-ZÑÁÉÍÓÚ][a-zñáéíóú]+");
	}

	/**
	 * Comprueba validez de una dirección.
	 * @param direccion.
	 * @return true si cumple.
	 */
	private boolean direccionValida(String direccion) {
		assert direccion != null;
		return	direccion.matches("[A-ZÑÁÉÍÓÚa-zñáéíóú\\.,/\\d ]+");
	}

	/**
	 * Comprueba validez de un dirección de correo.
	 * @param correo.
	 * @return true si cumple.
	 */
	private boolean correoValido(String correo) {
		assert correo != null;
		return	correo.matches("^[\\w-\\+]+(\\.[\\w-\\+]+)*@"
				+ "[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}

	/**
	 * Comprueba validez de una fecha de nacimiento.
	 * @param fechaNacimiento.
	 * @return true si cumple.
	 */
	private boolean fechaNacimientoValida(String fechaNacimiento) {
		assert fechaNacimiento != null;
		return fechaNacimiento.matches("[\\d]{4}[\\.-/][\\d]{2}[\\.-/][\\d]{1,2}");
	}

	/**
	 * Comprueba validez de una fecha de alta.
	 * @param fechaAlta.
	 * @return true si cumple.
	 */
	private boolean fechaAltaValida(String fechaAlta) {
		assert fechaAlta != null;
		return fechaAlta.matches("[\\d]{4}[\\.-/][\\d]{2}[\\.-/][\\d]{1,2}");
	}

	/**
	 * Comprueba validez de una clave de acceso.
	 * @param claveAcceso.
	 * @return true si cumple.
	 */
	private boolean claveAccesoValida(String claveAcceso) {
		assert claveAcceso != null;
		return	claveAcceso.matches("([\\wñÑ$*-+&!?#]){5,}");
	}

	/**
	 * Comprueba validez de un rol.
	 * @param rol.
	 * @return true si cumple.
	 */
	private boolean rolValido(String rol) {
		assert	rol != null;
		return ROLES[0].equals(rol) 
				|| ROLES[1].equals(rol)
				|| ROLES[2].equals(rol);
	}

	// Apartado 2:
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		if (nifValido(nif)) {
			this.nif = nif;
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {	
		if (nombreValido(nombre)) {
			this.nombre = nombre;
		}
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		if (apellidosValidos(apellidos)) {
			this.apellidos = apellidos;
		}
	}

	public String getIdUsr() {
		return idUsr;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		if (direccionValida(domicilio)) {
			this.domicilio = domicilio;
		}
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		if (correoValido(correo)) {
			this.correo = correo;
		}
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		if (fechaNacimientoValida(fechaNacimiento)) {
			this.fechaNacimiento = fechaNacimiento;
		}
	}
	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		if (fechaAltaValida(fechaAlta)) {
			this.fechaAlta = fechaAlta;
		}
	}

	public String getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(String claveAcceso) {
		if (claveAccesoValida(claveAcceso)) {
			this.claveAcceso = encriptarCesar(claveAcceso);
		}
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		if (rolValido(rol)) {
			this.rol = rol;
		}
	}

	/**
	 * Redefine el método heredado de la clase Objecto.
	 * @return el texto formateado del estado -valores de atributos- de objeto de la clase Usuario.  
	 */
	@Override
	public String toString() {
		return String.format(
							"%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n"
						+ "%-16s %s\n", 
						"nif:", nif, "nombre:", nombre, "apellidos:", apellidos, "idUsr:", idUsr, 
						"domicilio:", domicilio, "correo:", correo, "fechaNacimiento:", fechaNacimiento, 
						"fechaAlta:", fechaAlta, "claveAcceso:", claveAcceso, "rol:", rol);		
	}

} // class

