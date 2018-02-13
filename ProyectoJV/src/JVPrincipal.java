/** 
 * Proyecto: Juego de la vida.
 * Pruebas iniciales de las clases Usuario y SesionUsuario del modelo1.
 * Implementación del control de inicio de sesión y ejecución de la simulación por defecto. 
 * En esta versión no se han aplicado la mayoría de los estándares 
 * de diseño OO dirigidos a conseguir un "código limpio".
 * Se pueden detectar varios defectos y antipatrones de diseño:
 *  	- Ausencia de encapsulación.
 *  	- Clase demasiado grande. 
 * @since: prototipo1.1
 * @source: JVPrincipal.java 
 * @version: 1.1 - 2018/01/10
 * @author: ajp
 */

import java.util.Date;
import java.util.Scanner;

public class JVPrincipal {

	static final int MAX_USUARIOS = 10;
	static final int MAX_SESIONES = 10;
	static final int MAX_INTENTOS_FALLIDOS = 3;
	static Usuario[] datosUsuarios = new Usuario[MAX_USUARIOS];
	static SesionUsuario[] datosSesiones = new SesionUsuario[MAX_SESIONES];
	static int sesionesRegistradas = 0;				// Control de sesiones registradas.
	static Usuario usrEnSesion;

	static final int TAMAÑO_MUNDO = 12;
	static final int CICLOS_SIMULACION = 35;
	static byte[][] espacioMundo;
	enum FormaEspacio { PLANO1, PLANO2, ESFERICO }
	static final FormaEspacio TIPO_MUNDO = FormaEspacio.ESFERICO;

	/**
	 * Secuencia principal del programa.
	 */
	public static void main(String[] args) {		
		// Apartados 2 y 3: Pruebas 
		//probarClaseUsuario();
		//probarSesionUsuario();

		cargarUsuariosPrueba();		
		mostrarTodosUsuarios();

		if (inicioSesionCorrecto()) {
			registrarSesion();	
			System.out.println("Sesión: " + sesionesRegistradas + '\n' + "Iniciada por: " 
					+ 	usrEnSesion.getNombre() + " " + usrEnSesion.getApellidos());	
			cargarMundoDemo();
			arrancarSimulacion();
		}
		else {
			System.out.println("\nDemasiados intentos fallidos...");
		}		
		System.out.println("Fin del programa.");
	}

	/**
	 * Ejecuta una simulación del juego de la vida en la consola.
	 */
	static void arrancarSimulacion() {
		cargarMundoDemo();
		int generacion = 0; 
		do {
			System.out.println("\nGeneración: " + generacion);
			mostrarMundo();
			actualizarMundo();
			generacion++;
		}
		while (generacion < CICLOS_SIMULACION);
	}

	/**
	 * Actualiza el estado del Juego de la Vida.
	 * Actualiza según la configuración establecida para la forma del espacio.
	 */
	private static void actualizarMundo() {
		if (TIPO_MUNDO == FormaEspacio.PLANO1) {
			actualizarMundoPlano1();
		}
		if (TIPO_MUNDO == FormaEspacio.PLANO2) {
			actualizarMundoPlano2();
		}
		if (TIPO_MUNDO == FormaEspacio.ESFERICO) {
			actualizarMundoEsferico();
		}
	}

	/**
	 * Despliega en la consola el estado almacenado, corresponde
	 * a una generación del Juego de la vida.
	 */
	static void mostrarMundo() {

		for (int i = 0; i < TAMAÑO_MUNDO; i++) {
			for (int j = 0; j < TAMAÑO_MUNDO; j++) {		
				System.out.print((espacioMundo[i][j] == 1) ? "|o" : "| ");
			}
			System.out.println("|");
		}
	}

	/**
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * Las celdas periféricas son adyacentes con las del lado contrario.
	 * El mundo representado sería esférico cerrado sin límites para células de dos dimensiones.
	 */
	static void actualizarMundoEsferico()  {     					
		byte[][] nuevoEstado = new byte[TAMAÑO_MUNDO][TAMAÑO_MUNDO];

		for (int i = 0; i < TAMAÑO_MUNDO; i++) {
			for (int j = 0; j < TAMAÑO_MUNDO; j++) {

				int filaSuperior = i-1;
				int filaInferior = i+1;
				// Reajusta filas adyacentes.
				if (i == 0) {
					filaSuperior = TAMAÑO_MUNDO-1;
				}
				if (i == TAMAÑO_MUNDO-1) {
					filaInferior = 0;
				}

				int colAnterior = j-1;
				int colPosterior = j+1;
				// Reajusta columnas adyacentes.
				if (j == 0) {
					colAnterior = TAMAÑO_MUNDO-1;
				}
				if (j == TAMAÑO_MUNDO-1) {
					colPosterior = 0;
				}

				int vecinas = 0;							
				vecinas += espacioMundo[filaSuperior][colAnterior];		// Celda NO
				vecinas += espacioMundo[filaSuperior][j];					// Celda N		NO | N | NE
				vecinas += espacioMundo[filaSuperior][colPosterior];		// Celda NE   	-----------
				vecinas += espacioMundo[i][colPosterior];					// Celda E		 O | * | E
				vecinas += espacioMundo[filaInferior][colPosterior];		// Celda SE	  	----------- 
				vecinas += espacioMundo[filaInferior][j]; 					// Celda S		SO | S | SE
				vecinas += espacioMundo[filaInferior][colAnterior]; 		// Celda SO 
				vecinas += espacioMundo[i][colAnterior];					// Celda O           			                                     	

				actualizarCelda(nuevoEstado, i, j, vecinas);
			}
		}
		espacioMundo = nuevoEstado;
	}

	/**
	 * Apartado 8+:
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * Las celdas periféricas son los límites absolutos.
	 * El mundo representado sería plano cerrado con límites para células de dos dimensiones.
	 */
	static void actualizarMundoPlano2()  {     					
		byte[][] nuevoEstado = new byte[TAMAÑO_MUNDO][TAMAÑO_MUNDO];

		for (int i = 0; i < TAMAÑO_MUNDO; i++) {
			for (int j = 0; j < TAMAÑO_MUNDO; j++) {
				int vecinas = 0;							
				vecinas += celdaNoroeste(i, j);		
				vecinas += celdaNorte(i, j);		// 		NO | N | NE
				vecinas += celdaNoreste(i, j);		//    	-----------
				vecinas += celdaEste(i, j);			// 		 O | * | E
				vecinas += celdaSureste(i, j);		// 	  	----------- 
				vecinas += celdaSur(i, j); 			// 		SO | S | SE
				vecinas += celdaSuroeste(i, j); 	  
				vecinas += celdaOeste(i, j);		          			                                     	

				actualizarCelda(nuevoEstado, i, j, vecinas);
			}
		}
		espacioMundo = nuevoEstado;
	}

	/**
	 * Aplica las leyes del mundo a la celda indicada dada la cantidad de células adyacentes vivas.
	 * @param nuevoEstado
	 * @param fila
	 * @param col
	 * @param vecinas
	 */
	static void actualizarCelda(byte[][] nuevoEstado, int fila, int col, int vecinas) {
		if (vecinas < 2) {
			nuevoEstado[fila][col] = 0; 				// Subpoblación, muere...
		}
		if (vecinas > 3) {
			nuevoEstado[fila][col] = 0; 				// Sobrepoblación, muere...
		}
		if (vecinas == 3) {
			nuevoEstado[fila][col] = 1; 				// Pasa a estar viva o se mantiene.
		}
		if (vecinas == 2 && espacioMundo[fila][col] == 1) {
			nuevoEstado[fila][col] = 1; 				// Se mantiene viva...
		}	
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Oeste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Oeste.
	 */
	static byte celdaOeste(int fila, int col) {
		if (col-1 >= 0) {
			return espacioMundo[fila][col-1]; 	// Celda O.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Suroeste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Suroeste.
	 */
	static byte celdaSuroeste(int fila, int col) {
		if (fila+1 < TAMAÑO_MUNDO && col-1 >= 0) {
			return espacioMundo[fila+1][col-1]; 	// Celda SO.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Sur de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Sur.
	 */
	static byte celdaSur(int fila, int col) {
		if (fila+1 < TAMAÑO_MUNDO) {
			return espacioMundo[fila+1][col]; 	// Celda S.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Sureste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Sureste.
	 */
	static byte celdaSureste(int fila, int col) {
		if (fila+1 < TAMAÑO_MUNDO && col+1 < TAMAÑO_MUNDO) {
			return espacioMundo[fila+1][col+1]; 	// Celda SE.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Este de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Este.
	 */
	static byte celdaEste(int fila, int col) {
		if (col+1 < TAMAÑO_MUNDO) {
			return espacioMundo[fila][col+1]; 		// Celda E.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Noreste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Noreste.
	 */
	static byte celdaNoreste(int fila, int col) {
		if (fila-1 >= 0 && col+1 < TAMAÑO_MUNDO) {
			return espacioMundo[fila-1][col+1]; 		// Celda NE.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al NO de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda NO.
	 */
	static byte celdaNorte(int fila, int col) {
		if (fila-1 >= 0) {
			return espacioMundo[fila-1][col]; 		// Celda N.
		}
		return 0;
	}

	/**
	 * Obtiene el estado o valor de la celda vecina situada al Noroeste de la indicada por la coordenada. 
	 * @param fila de la celda evaluada.
	 * @param col de la celda evaluada.
	 * @return el estado o valor de la celda Noroeste.
	 */
	static byte celdaNoroeste(int fila, int col) {
		if (fila-1 >= 0 && col-1 >= 0) {
			return espacioMundo[fila-1][col-1]; 		// Celda NO.
		}
		return 0;
	}

	/**
	 * Actualiza el estado almacenado del Juego de la Vida.
	 * Las celdas periféricas son los límites absolutos.
	 * El mundo representado sería plano cerrado con límites para células de dos dimensiones.
	 */
	static void actualizarMundoPlano1()  {     					
		byte[][] nuevoEstado = new byte[TAMAÑO_MUNDO][TAMAÑO_MUNDO];
		byte[][] matrizConteo;

		for (int i = 0; i < TAMAÑO_MUNDO; i++) {
			for (int j = 0; j < TAMAÑO_MUNDO; j++) {

				matrizConteo = replicarMatrizConteo(i, j);

				int vecinas = 0;							
				vecinas += matrizConteo[0][0];		// Celda NO
				vecinas += matrizConteo[0][1];		// Celda N		NO | N | NE
				vecinas += matrizConteo[0][2];		// Celda NE   	-----------
				vecinas += matrizConteo[1][2];		// Celda E		 O | * | E
				vecinas += matrizConteo[2][2];		// Celda SE	  	----------- 
				vecinas += matrizConteo[2][1]; 		// Celda S		SO | S | SE
				vecinas += matrizConteo[2][0]; 		// Celda SO 
				vecinas += matrizConteo[1][0];		// Celda O           			                                     	

				actualizarCelda(nuevoEstado, i, j, vecinas);
			}
		}
		espacioMundo = nuevoEstado;
	}

	/**
	 * Obtiene una submatriz con las celdas adyacentes a cualquier celda del mundo.
	 * @param fila de la celda central de la submatriz
	 * @param col de la celda central de la submatriz
	 * @return matrizCopia
	 */
	static byte[][] replicarMatrizConteo(int fila, int col) {
		byte[][] matrizCopia = { 
				{ 0, 0, 0 }, 
				{ 0, 0, 0 }, 
				{ 0, 0, 0 }
		};

		// Zona central.
		if (fila-1 >= 0 && fila+1 < TAMAÑO_MUNDO && col-1 >= 0 && col+1 < TAMAÑO_MUNDO) {
			System.arraycopy(espacioMundo[fila-1], col-1, matrizCopia[0], 0, 3);
			System.arraycopy(espacioMundo[fila], col-1, matrizCopia[1], 0, 3);
			System.arraycopy(espacioMundo[fila+1], col-1, matrizCopia[2], 0, 3);
			return matrizCopia;
		}

		// Banda periférica superior.
		if (fila == 0 && col-1 >= 0 && col+1 < TAMAÑO_MUNDO)	{
			System.arraycopy(espacioMundo[fila], col-1, matrizCopia[1], 0, 3);
			System.arraycopy(espacioMundo[fila+1], col-1, matrizCopia[2], 0, 3);
			return matrizCopia;
		}
		
		// Banda periférica inferior.
		if (fila == TAMAÑO_MUNDO-1 && col-1 >= 0 && col+1 < TAMAÑO_MUNDO) {
			System.arraycopy(espacioMundo[fila-1], col-1, matrizCopia[0], 0, 3);
			System.arraycopy(espacioMundo[fila], col-1, matrizCopia[1], 0, 3);
			return matrizCopia;
		}
		
		// Banda periférica izquierda.
		if (col == 0 && fila-1 >= 0 && fila+1 < TAMAÑO_MUNDO) {
			System.arraycopy(espacioMundo[fila-1], col, matrizCopia[0], 1, 2);
			System.arraycopy(espacioMundo[fila], col, matrizCopia[1], 1, 2);
			System.arraycopy(espacioMundo[fila+1], col, matrizCopia[2], 1, 2);
			return matrizCopia;
		}
		
		// Banda periférica derecha.
		if (col == TAMAÑO_MUNDO-1 && fila-1 >= 0 && fila+1 < TAMAÑO_MUNDO) {
			System.arraycopy(espacioMundo[fila-1], col-1, matrizCopia[0], 0, 2);
			System.arraycopy(espacioMundo[fila], col-1, matrizCopia[1], 0, 2);
			System.arraycopy(espacioMundo[fila+1], col-1, matrizCopia[2], 0, 2);
			return matrizCopia;
		}

		// Esquinas superior izquierda.
		if (fila == 0 && col == 0)	{
			System.arraycopy(espacioMundo[fila], col, matrizCopia[1], 1, 2);
			System.arraycopy(espacioMundo[fila+1], col, matrizCopia[2], 1, 2);
			return matrizCopia;
		}

		// Esquinas superior derecha.
		if (fila == 0 && col == TAMAÑO_MUNDO-1)	{
			System.arraycopy(espacioMundo[fila], col-1, matrizCopia[1], 0, 2);
			System.arraycopy(espacioMundo[fila+1], col-1, matrizCopia[2], 0, 2);
			return matrizCopia;
		}

		// Esquinas inferior izquierda.
		if (fila == TAMAÑO_MUNDO-1 && col == TAMAÑO_MUNDO-1)	{
			System.arraycopy(espacioMundo[fila-1], col-1, matrizCopia[1], 0, 2);
			System.arraycopy(espacioMundo[fila], col-1, matrizCopia[2], 0, 2);
			return matrizCopia;
		}

		// Esquinas inferior derecha.
		if (fila == TAMAÑO_MUNDO-1 && col == 0)	{
			System.arraycopy(espacioMundo[fila-1], col, matrizCopia[1], 1, 2);
			System.arraycopy(espacioMundo[fila], col, matrizCopia[2], 1, 2);
			return matrizCopia;
		}

		return matrizCopia;
	}


	/**
	 * Carga datos demo en la matriz que representa el mundo. 
	 */
	static void cargarMundoDemo() {
		// En este array los 0 indican celdas con células muertas y los 1 vivas.
		espacioMundo = new byte[][] { 
			{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0 }, //
			{ 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 }, // 
			{ 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 }, //
			{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0 }, // 
			{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Planeador
			{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1x Flip-Flop
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } // 1x Still Life
		};
	}

	/**
	 * Controla el acceso de usuario.
	 * @return true si la sesión se inicia correctamente.
	 */
	static boolean inicioSesionCorrecto() {
		Scanner teclado = new Scanner(System.in);	// Entrada por consola.
		int intentosPermitidos = MAX_INTENTOS_FALLIDOS;

		do {
			// Pide usuario y contraseña.
			System.out.print("Introduce el nif de usuario: ");
			String nif = teclado.nextLine();
			System.out.print("Introduce clavapellidose acceso: ");
			String clave = teclado.nextLine();

			// Busca usuario coincidente con las credenciales.
			usrEnSesion = buscarUsuario(nif);

			// Encripta clave tecleada utilizando un objeto temporal
			// que ejecutará automáticamente el método privado.
			Usuario aux = new Usuario();
			aux.setClaveAcceso(clave);
			clave = aux.getClaveAcceso();
			
			if (usrEnSesion != null && usrEnSesion.getClaveAcceso().equals(clave)) {
				return true;
			} else {
				intentosPermitidos--;
				System.out.print("Credenciales incorrectas: ");
				System.out.println("Quedan " + intentosPermitidos + " intentos... ");
			} 
		} while (intentosPermitidos > 0);

		return false;
	}

	/**
	 * Busca usuario dado su nif.
	 * @param idUsr - el nif del Usuario a buscar.
	 * @return - el Usuario encontrado o null si no existe.
	 */
	static Usuario buscarUsuario(String idUsr) {
		// Busca usuario coincidente con la credencial.
		for (Usuario usr : datosUsuarios) {
			if (usr.getNif().equalsIgnoreCase(idUsr)) {
				return usr;	// Devuelve el usuario encontrado.
			}
		}
		return null;						// No encuentra.
	}

	/**
	 * Registro de la sesión de usuario.
	 */
	static void registrarSesion() {
		SesionUsuario sesion = new SesionUsuario();
		sesion.setUsr(usrEnSesion);
		sesion.setFecha(new Date().toString());  // Hoy

		// Registra sesion de usuario a partir de la última posición ocupada.
		datosSesiones[sesionesRegistradas] = sesion;  
		sesionesRegistradas++; 							
	}

	/**
	 * Muestra por consola todos los usuarios almacenados.
	 */
	static void mostrarTodosUsuarios() {
		for (Usuario u: datosUsuarios) {
			System.out.println("\n" + u);
		}
	}

	/**
	 * Genera datos de prueba válidos dentro 
	 * del almacén de datos.
	 */
	static void cargarUsuariosPrueba() {	
		for (int i = 0; i < MAX_USUARIOS; i++) {
			datosUsuarios[i] = new Usuario("1234567" + i + "K", "Pepe", "López Pérez", "C/ Luna 27 30132 Murcia",
					"pepe" + i + "@gmail.com", "1999.11.12", "2018.01.03", "Miau#" + i, Usuario.ROLES[0]);
		}
	}

	/**
	 * Pruebas de la clase SesionUsuario
	 */
	static void probarSesionUsuario() {

		// Datos para la prueba...
		Usuario usr = new Usuario("23456790K", "Pepe", "López Pérez",
				"C/ Luna 27 30132 Murcia", "pepe@gmail.com",  "1999.11.12",
				"2018.12.03", "miau", "usuario normal");

		// Prueba de la clase SesionUsuario
		SesionUsuario sesion1 = new SesionUsuario();
		sesion1.setUsr(usr);
		sesion1.setFecha("2018.01.23");
		System.out.println(sesion1);	
	}

	/**
	 * Apartado 2:
	 * Pruebas de la clase Usuario
	 */
	static void probarClaseUsuario() {	

		// Prueba de constructores

		Usuario usr1 = new Usuario();
		System.out.println("constructor por defecto");
		System.out.println(usr1);
		
		Usuario usr2 = new Usuario("23456790K", "Pepe", "López Pérez",
		"C/ Luna 27 30132 Murcia", "pepe@gmail.com",  "1999.11.12",
		"2018.01.03", "miau", "usuario normal");
		System.out.println("constructor convencional");
		System.out.println(usr2);

		Usuario usr3 = new Usuario(usr2);
		System.out.println("\nconstructor copia");
		System.out.println(usr3 != usr2);
		System.out.println(usr3.getNif() != usr2.getNif());
		System.out.println(usr3.getNombre() != usr2.getNombre());
		
		// ...
		
		// Prueba de métodos get...()
		System.out.println("\nget...()");
		System.out.println(usr2.getNif().equals("12345678K"));
		
		// ...
		
		// Prueba de métodos set...()
		System.out.println("\nset...()");
		usr1.setNif("12345678K");
		System.out.println(usr1.getNif().equals("12345678K"));
		
		//...
		
		// Prueba de método privado generIdUsr()
		System.out.println("\ngenerarIdUsr()");
		Usuario usr4 = new Usuario("12345678K", "José", "Pérez Pérez",
		"C/Luna, 27 30132 Murcia", "pepe@gmail.com",  "1999.11.12",
		"2018.12.03", "miau", "usuario normal");
		System.out.println(usr4.getIdUsr().equals("JPP8K"));
		
		// Prueba de método privado encriptarCesar()
		System.out.println("\nencriptarCesar()");
		Usuario usr5 = new Usuario("12345678K", "José", "Pérez Pérez",
		"C/Luna, 27 30132 Murcia", "pepe@gmail.com",  "1999.11.12",
		"2018.12.03", "Hola3", "usuario normal");
		System.out.println(usr5.getClaveAcceso().equals("Lsoe7"));
	}

} //class
