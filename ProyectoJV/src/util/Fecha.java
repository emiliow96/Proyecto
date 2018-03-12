package util;

/** Proyecto: Juego de la vida.
 *  Implementa clase de utilidad para menejo 
 *  del tiempo en el programa.  
 *  @since: prototipo1.2
 *  @source: Fecha.java 
 *  @version: 1.2 - 2018/03/06
 *  @author: ajp
 */
import java.util.Calendar;
import java.util.GregorianCalendar;

import modelo.Nif;
import modelo.Usuario;

// Clase adaptadora para simplificar el interfaz 
// de la clase Calendar.
public class Fecha {

	// Objeto de la clase adaptada.
	private Calendar calendario;

	/**
	 * Constructor defecto.
	 */
	public Fecha() {
		calendario = new GregorianCalendar();
	}

	/**
	 * Constructor convencional adaptado.
	 */
	public Fecha(int año, int mes, int dia) {
		calendario = new GregorianCalendar(año, mes-1, dia);
	}

	/**
	 * Constructor convencional adaptado.
	 */
	public Fecha(int año, int mes, int dia, int hora, int minuto, int segundo) {
		calendario = new GregorianCalendar(año, mes-1, dia, hora, minuto, segundo);
	}
	
	public Fecha(Fecha fecha) {
		calendario = (Calendar) fecha.calendario.clone();
	}

	//Métodos de acceso adaptados
	public int getAño() {
		return calendario.get(Calendar.YEAR);
	}

	public int getMes() {
		return calendario.get(Calendar.MONTH) + 1;
	}

	public int getDia() {
		return calendario.get(Calendar.DAY_OF_MONTH);
	}

	public int getHora() {
		return calendario.get(Calendar.HOUR_OF_DAY);
	}

	public int getMinuto() {
		return calendario.get(Calendar.MINUTE);
	}

	public int getSegundo() {
		return calendario.get(Calendar.SECOND);
	}
	
	public void setAño(int año) {
		calendario.set(Calendar.YEAR, año);
	}

	public void setMes(int mes) {
		calendario.set(Calendar.YEAR, mes);
	}

	public void setDia(int dia) {
		calendario.set(Calendar.DAY_OF_MONTH, dia);
	}

	public void setHora(int hora) {
		calendario.set(Calendar.HOUR_OF_DAY, hora);
	}


	public void setMinuto(int minuto) {
		calendario.set(Calendar.MINUTE, minuto);
	}
	
	public void setSegundo(int segundo) {
		calendario.set(Calendar.SECOND, segundo);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calendario == null) ? 0 : calendario.hashCode());
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
			if (getAño() == ((Fecha)obj).getAño()  
					&& getMes() == ((Fecha)obj).getMes() 
					&& getDia() == ((Fecha)obj).getDia()  
					&& getHora() == ((Fecha)obj).getHora() 
					&& getMinuto() == ((Fecha)obj).getMinuto() 
					&& getSegundo() == ((Fecha)obj).getSegundo() 
				) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%4d.%02d.%02d - %02d:%02d:%02d", 
				getAño(), getMes(), getDia(), getHora(), getMinuto(), getSegundo());
	}

} // class