package hibernateABM;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import org.hibernate.Session;

import hibernateABM.DAO.HPersonaDAO;
import hibernateABM.dto.PersonaEntity;

public class AppHibernateABM {

	public static void main(String[] args) {

		System.out.println("SISTEMA DE PERSONAS (ABM)");
		System.out.println("=========================");

		Scanner sc = new Scanner(System.in);
		PersonaEntity per = new PersonaEntity();

		int opcion = mostrarMenu(sc);
		while (opcion != 0) {

			switch (opcion) {
			case 1:
				alta(per, sc);
				break;
			case 2:
				modificacion(per, sc);
				break;
			case 3:
				baja(per, sc);
				break;
			case 4:

				break;
			case 5:

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

	private static void alta(PersonaEntity per, Scanner sc) {

		System.out.println("Ingrese nombre:");
		String nombre = sc.next();

		System.out.println("Ingrese fecha de nacimiento (aaaa-mm-dd):");
		String feNacStng = sc.next();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date feNac = null;

		try {
			feNac = sdf.parse(feNacStng);
			int edad = HibernateABMUtil.calcularEdad(feNac);

			per.setNombre(nombre);
			per.setEdad(edad);
			per.setFeNac(feNac);

			HPersonaDAO.updatePersona(per);
			System.out.println("Los datos fueron ingresados exitosamente");
			mostrarPersona(sc, per);
		} catch (ParseException e) {
			System.out.println("La fecha ingresada es incorrecta.");
		}

	}

	// 2.MODIFICACION DE PERSONA(METODO)

	private static void modificacion(PersonaEntity per, Scanner sc) {

		System.out.println("Ingrese el ID que desea modificar:");
		int id = sc.nextInt();

		per = HPersonaDAO.getPerXid(id);

		if (per != null) {
			System.out.println("El ID no existe elija una nuevo ID");
			modificacion(per, sc);

		} else {

			mostrarPersona(sc, per);

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
						String feNew = sc.next();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date feNac = sdf.parse(feNew);
						per.setFeNac(feNac);

						// actualiza edad de acuerdo a la nueva fecha de nacimiento
						int edad = HibernateABMUtil.calcularEdad(feNac);
						per.setEdad(edad);

						HPersonaDAO.updatePersona(per);
					}

				} catch (ParseException e) {
					System.out.println("La fecha ingresada es incorrecta.");

					break;

				}

				System.out.println("El usuario ha sido modificado exitosamente");
				mostrarPersona(sc, per);

				System.out.println("ingrese la columna que desea modificar");
				System.out.println("1.NOMBRE| 2.FECHA_NACIMIENTO|3.Salir");
				option = sc.nextInt();
			}
		}
	}

	// 3. BAJA DE PERSONA

	private static void baja(PersonaEntity per, Scanner sc) {

		System.out.println("Ingrese el ID que desea borrar:");
		int id = sc.nextInt();

		per = HPersonaDAO.getPerXid(id);

		if (per != null) {
			System.out.println("El ID no existe elija una nuevo ID");
			modificacion(per, sc);
		} else {
			
			mostrarPersona(sc, per);

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

	private static void mostrarPersona(Scanner sc, PersonaEntity per) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fNac = sdf.format(per.getFeNac());

		System.out.println("ID|NOMBRE|EDAD|F.NACIM");
		System.out.println(per.getId() + " " + per.getNombre() + " " + per.getEdad() + " " + fNac);
	}
}