/** 
 * Proyecto: Juego de la vida.
 * Clase JUnit de prueba automatizada de las características de la clase Usuario según el modelo 2.
 * @since: prototipo2
 * @source: TestUsuario.java 
 * @version: 2.0 - 2017.03.21
 * @author: ajp
 */

package test.modelo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modelo.ClaveAcceso;
import modelo.Correo;
import modelo.DireccionPostal;
import modelo.Nif;
import modelo.Usuario;
import modelo.Usuario.RolUsuario;
import util.Fecha;

public class UsuarioTest {
	private Usuario usuario1; 
	private Usuario usuario2; 
	
	/**
	 * Método que se ejecuta antes de cada @Test para preparar datos de prueba.
	 * @throws ModeloException 
	 */
	@Before
	public void iniciarlizarDatosPrueba() {
		// Objetos para la prueba.
		try {
			usuario1 = new Usuario(); 
			usuario2 = new Usuario(new Nif("00000000T"), "Luis", "Pérez Ruiz",
					new DireccionPostal("Roncal", "10", "30130", "Murcia"), 
					new Correo("luis@gmail.com"), new Fecha(2000, 03, 21),
					new Fecha(2017,05,12), new ClaveAcceso(), RolUsuario.NORMAL);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que se ejecuta después de cada @Test para limpiar datos de prueba.
	 */
	@After	
	public void borrarDatosPrueba() {
		usuario1 = null;
		usuario2 = null;
	}
	
	// Test CON DATOS VALIDOS
	@Test
	public void testSetFechaAlta() {
		try {
			usuario1.setFechaAlta(new Fecha(2012, 2, 9, 10, 9, 0));
		} 
		catch (Exception e) { }
		assertEquals( usuario1.getFechaAlta(), new Fecha(2012, 2, 9, 10, 9, 0));
	}

	@Test
	public void testSetClaveAcceso() {
		try {
			usuario1.setClaveAcceso(new ClaveAcceso("Hola#12"));
			assertEquals(usuario1.getClaveAcceso(), new ClaveAcceso("Hola#12"));
		} 
		catch (Exception e) { } 
	}

	@Test
	public void testSetRol() {
		usuario1.setRol(RolUsuario.INVITADO);
		assertEquals(usuario1.getRol(), RolUsuario.INVITADO);
	}
	
	@Test
	public void testToString() {
		assertNotNull(usuario2.toString());	
	}

	@Test
	public void testEqualsObject() {
		try {
			usuario1 = new Usuario(new Nif("00000000T"), "Luis", "Pérez Ruiz",
					new DireccionPostal("Roncal", "10", "30130", "Murcia"), 
					new Correo("luis@gmail.com"), new Fecha(2000, 03, 21),
					new Fecha(2017,05,12), new ClaveAcceso(), RolUsuario.NORMAL);
		} 
		catch (Exception e) { }
		assertEquals(usuario1, usuario2);
	}

	public void testHashCode() {
		System.out.println(usuario2.hashCode());
		assertEquals(usuario2.hashCode(), -2032408461);
	}

	// Test CON DATOS NO VALIDOS
	@Test
	public void testSetClaveAccesoNull() {
		try {
			usuario1.setNif(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError e) { 
			assertTrue(true);
		}
	}
	
	@Test
	public void testSetFechaAltaNull() {
		try {
			usuario2.setFechaAlta(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError | Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testSetRolNull() {
		try {
			usuario1.setRol(null);
			fail("No debe llegar aquí...");
		} 
		catch (AssertionError e) { 
			assertTrue(true);
		}
	}
	
} // class
