package modelo;
/** Proyecto: Juego de la vida.
 *  Implementa el concepto de direccion postal según el modelo1.2.
 *  Utiliza un varios string para representar los distintos campos.  
 *  @since: prototipo1.2
 *  @source: DireccionPostal.java 
 *  @version: 1.2 - 2018/02/14 
 *  @author: ajp
 */
public class DireccionPostal {
	private String calle;
	private String numero;
	private String cp;
	private String poblacion;

	public DireccionPostal(String calle, String numero, String cp, String poblacion) {
		setCalle(calle);
		setNumero(numero);
		setCp(cp);
		setPoblacion(poblacion);
	}

	public DireccionPostal() {
		this("Calle", "00", "00000", "Población");
	}

	public DireccionPostal(DireccionPostal dp) {
		calle = new String(dp.calle);
		numero = new String(dp.numero);
		cp = new String(dp.cp);
		poblacion = new String(dp.poblacion);
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		if  (calleValida(calle)) {
			this.calle = calle;
		}
	}

	/**
	 * Comprueba validez de una calle.
	 * @param calle.
	 * @return true si cumple.
	 */
	private boolean calleValida(String calle) {
		assert calle != null;
		return	calle.matches("[A-ZÑÁÉÍÓÚa-zñáéíóú/\\d ]+");
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		if (numeroValido(numero)) {
			this.numero = numero;
		}
	}

	/**
	 * Comprueba validez de un numero postal.
	 * @param numero.
	 * @return true si cumple.
	 */
	private boolean numeroValido(String numero) {
		assert numero != null;
		return	numero.matches("[\\w]+");
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		if (cpValido(cp)) {
			this.cp = cp;
		}
	}

	/**
	 * Comprueba validez de un código postal.
	 * @param cp.
	 * @return true si cumple.
	 */
	private boolean cpValido(String cp) {
		assert cp != null;
		return	cp.matches("[\\d]{5}");
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		if (poblacionValida(poblacion)) {
			this.poblacion = poblacion;
		}
	}

	/**
	 * Comprueba validez de una población.
	 * @param poblacion.
	 * @return true si cumple.
	 */
	private boolean poblacionValida(String poblacion) {
		assert poblacion != null;
		return	poblacion.matches("[A-ZÑÁÉÍÓÚa-zñáéíóú ]+");
	}

	/**
	 * hashCode() complementa al método equals y sirve para comparar objetos de forma 
	 * rápida en estructuras Hash. 
	 * Cuando Java compara dos objetos en estructuras de tipo hash (HashMap, HashSet etc)
	 * primero invoca al método hashcode y luego el equals.
	 * @return un número entero de 32 bit.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calle == null) ? 0 : calle.hashCode());
		result = prime * result + ((cp == null) ? 0 : cp.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((poblacion == null) ? 0 : poblacion.hashCode());
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
			if (calle.equals(((DireccionPostal)obj).calle) 
					&& cp.equals(((DireccionPostal)obj).cp) 
					&& numero.equals(((DireccionPostal)obj).numero) 
					&& poblacion.equals(((DireccionPostal)obj).poblacion)
				) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Genera un clon del propio objeto realizando una copia profunda.
	 * @return el objeto clonado.
	*/
	@Override
	public Object clone() {
		// Utiliza el constructor copia.
		return new DireccionPostal(this);
	}
	
	@Override
	public String toString() {
		return calle + ", " + numero + ", " + cp + ", " + poblacion;
	}

}
