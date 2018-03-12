package modelo;

/** Proyecto: Juego de la vida.
 *  Implementa el concepto de Usuario según el modelo1.
 *  En esta versión no se han aplicado la mayoría de los estándares 
 *  de diseño OO dirigidos a conseguir un "código limpio". 
 *  La implementación es básica con el fin ilustrar 
 *  cómo se evoluciona desde un "código malo".
 *  Se pueden detectar varios defectos y antipatrones de diseño:
 *  @since: prototipo1.0
 *  @source: Usuario.java 
 *  @version: 1.2 - 2018/02/14 
 *  @author: ajp
 */

import util.Fecha;

public class Usuario {

	public enum RolUsuario {
		INVITADO, 
		NORMAL, 
		ADMINISTRADOR
	}

	private Nif nif;
	private String nombre;
	private String apellidos;
	private String idUsr;
	private DireccionPostal domicilio;
	private Correo correo;
	private Fecha fechaNacimiento;
	private Fecha fechaAlta;
	private ClaveAcceso claveAcceso;
	private RolUsuario rol;

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
	public Usuario(Nif nif, String nombre, String apellidos,
			DireccionPostal domicilio, Correo correo, Fecha fechaNacimiento,
			Fecha fechaAlta, ClaveAcceso claveAcceso, RolUsuario rol) {
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

	/**
	 * Constructor por defecto. Reenvía al constructor convencional.
	 */
	public Usuario() {
		this(new Nif(), "Nombre", "Apellido Apellido", 
				new DireccionPostal(), 
				new Correo(), 
				new Fecha(2000, 01, 12), 
				new Fecha(), 
				new ClaveAcceso(), RolUsuario.NORMAL);
	}

	/**
	 * Constructor copia.
	 * @param usr
	 */
	public Usuario(Usuario usr) {
		nif = new Nif(usr.nif);
		nombre = new String(usr.nombre);
		apellidos = new String(usr.apellidos);
		idUsr = new String(usr.idUsr);
		domicilio = new DireccionPostal(usr.domicilio);
		correo = new Correo(usr.correo);
		fechaNacimiento = new Fecha(usr.fechaNacimiento);
		fechaAlta = new Fecha(usr.fechaAlta);
		claveAcceso = new ClaveAcceso(usr.claveAcceso);
		rol = usr.rol;
	}

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
				+ nif.getTexto().substring(7);
	}

	/**
	 * Genera una variante cambiando la última letra del idUsr 
	 * por la siguiente en el alfabeto previsto para el nif.
	 */
	public void generarVarianteIdUsr() {
		assert idUsr != null;
		String alfabetoNif = "ABCDEFGHJKLMNPQRSTUVWXYZ";
		String alfabetoNifDesplazado = "BCDEFGHJKLMNPQRSTUVWXYZA";
		idUsr = idUsr.substring(0, 4) + alfabetoNifDesplazado.charAt(alfabetoNif.indexOf(idUsr.charAt(4)));
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
	 * @return true si cumplenull.
	 */
	private boolean direccionValida(String direccion) {
		assert direccion != null;
		return	direccion.matches("[A-ZÑÁÉÍÓÚa-zñáéíóú\\.,/ \\d]+");
	}

	/**
	 * Comprueba validez de una fecha de nacimiento.
	 * @param fechaNacimiento.
	 * @return true si cumple.
	 */
	private boolean fechaNacimientoValida(Fecha fechaNacimiento) {
		// Que sea fecha del pasado.
		return true;
	}

	public Nif getNif() {
		return nif;
	}

	public void setNif(Nif nif) {
		assert nif != null;
		this.nif = nif;
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

	public DireccionPostal getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(DireccionPostal domicilio) {
		assert domicilio != null;
		this.domicilio = domicilio;
	}

	public Correo getCorreo() {
		return correo;
	}

	public void setCorreo(Correo correo) {
		assert correo != null;
		this.correo = correo;
	}

	public Fecha getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Fecha fechaNacimiento) {
		assert fechaNacimiento != null;
		if (fechaNacimientoValida(fechaNacimiento)) {
			this.fechaNacimiento = fechaNacimiento;
		}
	}
	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) {
		assert fechaAlta != null;
		this.fechaAlta = fechaAlta;
	}

	public ClaveAcceso getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(ClaveAcceso claveAcceso) {
		assert claveAcceso != null;
		this.claveAcceso = claveAcceso;
	}

	public RolUsuario getRol() {
		return rol;
	}

	public void setRol(RolUsuario rol) {
		assert rol != null;
		this.rol = rol;
	}
	
	@Override
	public Usuario clone() {
		return new Usuario(this);	
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((claveAcceso == null) ? 0 : claveAcceso.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result + ((domicilio == null) ? 0 : domicilio.hashCode());
		result = prime * result + ((fechaAlta == null) ? 0 : fechaAlta.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((idUsr == null) ? 0 : idUsr.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		return result;
	}

	/**
	 * Dos objetos son iguales si: 
	 * Son de la misma clase.
	 * Tienen los mismos valores en los atributos; o son el mismo objeto.
	 * @return falso si no cumple las condiciones.
	*/
	@Override
	public boolean equals(Object obj) {
		if (obj != null && getClass() == obj.getClass()) {
			if (this == obj) {
				return true;
			}
			if (nif.equals(((Usuario)obj).nif) 
					&& nombre.equals(((Usuario)obj).nombre) 
					&& apellidos.equals(((Usuario)obj).apellidos) 
					&& idUsr.equals(((Usuario)obj).idUsr) 
					&& domicilio.equals(((Usuario)obj).domicilio) 
					&& correo.equals(((Usuario)obj).correo) 
					&& fechaNacimiento.equals(((Usuario)obj).fechaNacimiento) 
					&& fechaAlta.equals(((Usuario)obj).fechaAlta) 
					&& claveAcceso.equals(((Usuario)obj).claveAcceso) 
					&& rol.equals(((Usuario)obj).rol) 
				) {
				return true;
			}
		}
		return false;
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

