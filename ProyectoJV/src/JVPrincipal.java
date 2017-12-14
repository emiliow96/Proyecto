/** 
 * Proyecto: Juego de la vida.
 * Pruebas iniciales de las clases Usuario y SesionUsuario del modelo1.
 * Implementación del control de inicio de sesión y ejecución de la simulación por defecto. 
 * En esta versión no se han aplicado la mayoría de los estándares 
 * de diseño OO dirigidos a conseguir un "código limpio".
 * Se pueden detectar varios defectos y antipatrones de diseño:
 *  	- Ausencia de encapsulación.
 *  	- Clase demasiado grande. 
 * @since: prototipo1.0
 * @source: JVPrincipal.java 
 * @version: 1.0 - 2017/11/29
 * @author: ajp
 */

import java.util.Scanner;

public class JVPrincipal {
	// Apartado 6:
	static final int MAX_USUARIOS = 10;
	static final int MAX_SESIONES = 10;
	static final int MAX_INTENTOS_FALLIDOS = 3;
	
	static Usuario[] datosUsuarios = new Usuario[MAX_USUARIOS];
	static SesionUsuario[] datosSesiones = new SesionUsuario[MAX_SESIONES];
	static int sesionesRegistradas = 0;				// Control de sesiones registradas.
	
	public static void main(String[] args) {	
		// Apartados 4 y 5: Pruebas previas
		//probarUsuario();
		//probarSesionUsuario();

		// Apartado 7: 
		cargarUsuariosPrueba();		
		mostrarTodosUsuarios();

		// Apartado 8:
		if (inicioSesionCorrecto()) {
			arrancarSimulacion();
		}
		else {
			System.out.println("\nDemasiados intentos fallidos...");
		}
			
		System.out.println("Fin del programa.");
	}

	/**
	 * Apartado 8:
	 * Controla el acceso de usuario 
	 * y registro de la sesión correspondiente. 
	 * @return true si la sesión se inicia correctamente.
	 */
	static boolean inicioSesionCorrecto() {
		Scanner teclado = new Scanner(System.in);	// Entrada por consola.
		boolean todoCorrecto = false;				// Control de credenciales de usuario.
		Usuario usrSesion = null;					// Usuario en sesión.
		int intentos = MAX_INTENTOS_FALLIDOS;
		
		//....
		
		return false;
	}

	/**
	 * Apartado 8:
	 * Busca usuario dado su nif.
	 * @param idUsr - el nif del Usuario a buscar.
	 * @return - el Usuario encontrado o null si no existe.
	 */
	static Usuario buscarUsuario(String idUsr) {
		// Busca usuario coincidente con la credencial.
		//....
		return null;						// No encuentra.
	}
	
	/**
	 * Apartado 8:
	 * Ejecuta una simulación del juego de la vida en la consola.
	 * Utiliza la configuración por defecto.
	 */
	static void arrancarSimulacion() {
		//....
	}

	/**
	 * Apartado 8:
	 * Despliega en la consola el estado almacenado, corresponde
	 * a una generación del Juego de la vida.
	 */
	static void mostrarMundo() {
		//....
	}

	/**
	 * Apartado 8:
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * @return nuevoEstado, el array con los cambios de la siguiente generación.
	 */
	static byte[][] actualizarMundo()  {     					
		//....
		return null;
	}

	/**
	 * Apartado 7:
	 * Muestra por consola todos los usuarios almacenados.
	 */
	static void mostrarTodosUsuarios() {
		for (Usuario u: datosUsuarios) {
			System.out.println("\n" + u);
		}
	}

	/**
	 * Apartado 7:
	 * Genera datos de prueba válidos dentro 
	 * del almacén de datos.
	 */
	static void cargarUsuariosPrueba() {
		Usuario usuarioAux = new Usuario();
		usuarioAux.nif = "2344556K";
		usuarioAux.nombre = "Pepe"; 
		usuarioAux.apellidos = "López Pérez";
		usuarioAux.domicilio = "C/Luna, 27 30132 Murcia";
		usuarioAux.correo = "pepe@gmail.com";
		usuarioAux.fechaNacimiento = "1999.11.12";
		usuarioAux.fechaAlta = "2017.12.3";
		usuarioAux.claveAcceso = "Miau#0";
		usuarioAux.rol = "usuario normal";
		datosUsuarios[0] = usuarioAux;

		for (int i = 1; i < MAX_USUARIOS; i++) {
			usuarioAux = new Usuario();
			usuarioAux.nif = i + "344556K";
			usuarioAux.nombre = "Pepe" + i; 
			usuarioAux.apellidos = "López" + " Pérez" +i ;
			usuarioAux.domicilio = "C/Luna, 27 30132 Murcia";
			usuarioAux.correo = "pepe" + i + "@gmail.com";
			usuarioAux.fechaNacimiento = "1999.11.12";
			usuarioAux.fechaAlta = "2017.12.3";
			usuarioAux.claveAcceso = "Miau#" + i;
			usuarioAux.rol = "usuario normal";
			datosUsuarios[i] = usuarioAux;
		}
	}

	/**
	 * Apartado 5: 
	 * Pruebas de la clase SesionUsuario
	 */
	static void probarSesionUsuario() {

		// Datos para la prueba...
		Usuario usr = new Usuario();
		usr.nif = "23456790K";
		usr.nombre = "Pepe";
		usr.apellidos = "López Pérez";
		usr.domicilio = "C/Luna, 27 30132 Murcia";
		usr.correo = "pepe@gmail.com";
		usr.fechaNacimiento = "1999.11.12";
		usr.fechaAlta = "2017.12.3";
		usr.claveAcceso = "Miau#0";
		usr.rol = "usuario normal";
		
		// Prueba de la clase SesionUsuario
		SesionUsuario sesion1 = new SesionUsuario();
		sesion1.usr = usr;
		sesion1.fecha = "2017.12.3";
		System.out.println(sesion1);	
	}
	
	/**
	 * Apartado 4: 
	 * Pruebas de la clase Usuario
	 */
	static void probarUsuario() {
		Scanner teclado = new Scanner(System.in);

		// Prueba de la clase Usuario

		Usuario usr1;
		//Equivalencia
		//usr1.nombre = "Luis";
		//null.nombre = "Luis";	

		// Asignación entre referencias -no duplica objeto-
		Usuario usr2 = new Usuario();
		usr1 = usr2;

		usr2.nif = "23456790K";
		usr2.nombre = "Pepe";
		usr2.apellidos = "López Pérez";
		usr2.domicilio = "C/Luna, 27 30132 Murcia";
		usr2.correo = "pepe@gmail.com";
		usr2.fechaNacimiento = "1999.11.12";
		usr2.fechaAlta = "2017.12.3";
		usr2.claveAcceso = "miau";
		usr2.rol = "usuario normal";

		// Modifica también usr2, son el mismo objeto
		usr1.nombre = "Luis";
		System.out.println("usr1: " + usr1.nombre);
		System.out.println("usr2: " + usr2.nombre);

		// Así si duplica
		Usuario usr4 = new Usuario();
		usr4 .nif = usr2.nif;
		usr4.nombre = usr2.nombre;
		usr4.apellidos = usr2.apellidos;
		usr4.domicilio =  usr2.domicilio;
		usr4.correo = usr2.correo;
		usr4.fechaNacimiento = usr2.fechaNacimiento;
		usr4.fechaAlta = usr2.fechaAlta;
		usr4.fechaAlta = usr2.fechaAlta;
		usr4.rol = usr2.rol;

		// Son diferentes objetos.
		usr4.nombre = "Pedro";
		System.out.println(usr2.nombre);

		// Desde teclado...
		Usuario usr3 = new Usuario();
		System.out.println("Entrada de datos de usuario... ");
		System.out.print("nif: ");	
		usr3.nif = teclado.next();
		System.out.print("nombre: ");	
		usr3.nombre = teclado.next();
		System.out.print("apellidos: ");
		usr3.apellidos = teclado.next();
		System.out.print("domicilio: ");
		usr3.domicilio =  teclado.next();
		System.out.print("correo: ");
		usr3.correo = teclado.next();
		System.out.print("fechaNacimiento: ");
		usr3.fechaNacimiento = teclado.next();
		System.out.print("fechaAlta: ");
		usr3.fechaAlta = teclado.next();
		System.out.print("claveAcceso: ");
		usr3.claveAcceso = teclado.next();
		System.out.print("rol: ");
		usr3.rol = "NORMAL";

		// Si toString() de Usuario no está redefinido...
		System.out.println(usr1); 		// Muestra identificador único de objeto
		System.out.println(usr2);
		System.out.println(usr3);
		System.out.println(usr4);
	}

} //class
