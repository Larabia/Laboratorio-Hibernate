package hibernateABM;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import hibernateABM.DAO.HPersonaDAO;
import hibernateABM.dto.PersonaEntity;


public class AppHibernateABM {

	public static void main(String[] args) {

		System.out.println("SISTEMA DE PERSONAS (ABM)");
		System.out.println("=========================");

		Scanner sc = new Scanner(System.in);
		
		int opcion = mostrarMenu(sc);
		while (opcion != 0) {

			switch (opcion) {
			case 1:
				alta(sc);
				break;
			case 2:
				modificacion(sc);
				break;
			case 3:
				baja(sc);
				break;
			case 4:
				mostrarListado();
				break;
			case 5:
				buscarXnombre(sc);

				break;
			case 6:

				break;
			case 0:

				break;

			default:
				break;
			}
			opcion = mostrarMenu(sc);
		}

	}

	private static int mostrarMenu(Scanner sc) {
		System.out.println(
				"Menu opciones: 1- Alta |2- Modificacion |3- Baja |4- Listado |5- Buscar por nombre |6- Cargar venta |0- Salir");

		int opcion = sc.nextInt();
		return opcion;
	}

	// ALTA DE PERSONA

	private static void alta(Scanner sc) {

		System.out.println("Ingrese nombre:");
		String nombre = sc.next();

		System.out.println("Ingrese fecha de nacimiento (aaaa-mm-dd):");
		String fechaNacimientoStng = sc.next();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaNacimiento = null;

		try {
			fechaNacimiento = sdf.parse(fechaNacimientoStng);
			int edad = HibernateABMUtil.calcularEdad(fechaNacimiento);
			
			PersonaEntity per = new PersonaEntity();

			per.setNombre(nombre);
			per.setEdad(edad);
			per.setFechaNacimiento(fechaNacimiento);

			HPersonaDAO.updatePersona(per);
			System.out.println("Los datos fueron ingresados exitosamente");
			mostrarPersona(per);
		} catch (ParseException e) {
			System.out.println("La fecha ingresada es incorrecta.");
		}

	}

	// 2.MODIFICACION DE PERSONA(METODO)

	private static void modificacion(Scanner sc) {

		System.out.println("Ingrese el ID que desea modificar:");
		int id = sc.nextInt();
		
		PersonaEntity per = new PersonaEntity();
		per = HPersonaDAO.getPerXid(id);

		if (per == null) {
			System.out.println("El ID no existe elija una nuevo ID");
			modificacion(sc);

		} else {

			mostrarPersona(per);

			System.out.println("ingrese la columna que desea modificar");
			System.out.println("1.NOMBRE| 2.|FECHA_NACIMIENTO|3.Salir");
			int option = sc.nextInt();

			while (option != 3) {
				try {
					switch (option) {
					case 1:

						System.out.println("ingrese nuevo nombre:");
						String nomNew = sc.next();
						per.setNombre(nomNew);
						HPersonaDAO.updatePersona(per);

						break;

					case 2:
						System.out.println("Ingrese fecha nacimiento (aaaa-mm-dd):");
						String fechaNew = sc.next();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date fechaNacimiento = sdf.parse(fechaNew);
						per.setFechaNacimiento(fechaNacimiento);

						// actualiza edad de acuerdo a la nueva fecha de nacimiento
						int edad = HibernateABMUtil.calcularEdad(fechaNacimiento);
						per.setEdad(edad);

						HPersonaDAO.updatePersona(per);
					}

				} catch (ParseException e) {
					System.out.println("La fecha ingresada es incorrecta.");

					break;

				}

				System.out.println("El usuario ha sido modificado exitosamente");
				mostrarPersona(per);

				System.out.println("ingrese la columna que desea modificar");
				System.out.println("1.NOMBRE| 2.FECHA_NACIMIENTO|3.Salir");
				option = sc.nextInt();
			}
		}
	}

	// 3. BAJA DE PERSONA

	private static void baja(Scanner sc) {

		System.out.println("Ingrese el ID que desea borrar:");
		int id = sc.nextInt();
		
		PersonaEntity per = new PersonaEntity();
		per = HPersonaDAO.getPerXid(id);

		if (per == null) {
			System.out.println("El ID no existe elija una nuevo ID");
			modificacion(sc);
		} else {
			
			mostrarPersona(per);

			System.out.println("Esta seguro de que desea borrar estos datos?");
			System.out.println("1.SI| 2.NO");
			int op = sc.nextInt();

			if (op == 1) {

				HPersonaDAO.deletePersona(per);

				System.out.println("Los datos fueron borrados exitosamente");

			} else {
				mostrarMenu(sc);
			}
		}
	}

	//LISTADO
	private static void mostrarListado() {
		
		List<PersonaEntity>listadoPer = HPersonaDAO.getAllPersona();

		System.out.println("ID|NOMBRE|EDAD|F.NACIM");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for (PersonaEntity per : listadoPer) {		
			String fechaNacimiento = sdf.format(per.getFechaNacimiento());
			System.out.println(per.getPersonaId() + " " + per.getNombre() + " " + per.getEdad() + " " + fechaNacimiento);
		}
	}
	
	//BUSCAR POR NOMBRE
	private static void buscarXnombre(Scanner sc) {

			System.out.println();
			System.out.println("BUSQUEDA POR NOMBRE");
			System.out.println("-------------------");

			System.out.println("Ingrese el nombre o las primeras letras:");
			String busqueda = sc.next();
			
			List<PersonaEntity>resultadoBusqueda = HPersonaDAO.getPerXnombre(busqueda);
			
			System.out.println("ID|NOMBRE|EDAD|F.NACIM");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			for (PersonaEntity per : resultadoBusqueda) {
				String fechaNacimiento = sdf.format(per.getFechaNacimiento());
				System.out.println(per.getPersonaId() + " " + per.getNombre() + " " + per.getEdad() + " " + fechaNacimiento);
			}
	}
	
	private static void mostrarPersona(PersonaEntity per) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaNacimiento = sdf.format(per.getFechaNacimiento());

		System.out.println("ID|NOMBRE|EDAD|F.NACIM");
		System.out.println(per.getId() + " " + per.getNombre() + " " + per.getEdad() + " " + fechaNacimiento);
	}
}