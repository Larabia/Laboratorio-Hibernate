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

		Session session = HibernateABMUtil.getSessionFactory().openSession();
		Scanner sc = new Scanner(System.in);

		int opcion = mostrarMenu(sc);
		while (opcion != 0) {

			switch (opcion) {
			case 1:
				alta(session, sc);
				break;
			case 2:
				modificacion(session, sc);
				break;
			case 3:

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

		HibernateABMUtil.shutdown();

	}

	private static int mostrarMenu(Scanner sc) {
		System.out.println(
				"Menu opciones: 1- Alta |2- Modificacion |3- Baja |4- Listado |5- Buscar por nombre |6- Cargar venta |0- Salir");

		int opcion = sc.nextInt();
		return opcion;
	}

	// ALTA DE PERSONA

	private static void alta(Session session, Scanner sc) {

		PersonaEntity per = new PersonaEntity();

		System.out.println("Ingrese nombre:");
		String nombre = sc.next();

		System.out.println("Ingrese fecha de nacimiento (aaaa-mm-dd):");
		String feNacStng = sc.next();

		try {

			HPersonaDAO.ingresarPersona(session, feNacStng, nombre, per);

			System.out.println("Los datos se cargaron exitosamente:");
			HPersonaDAO.mostrarPersona(session, per);

		} catch (ParseException e) {
			System.out.println("La fecha ingresada es incorrecta.");
		} catch (SQLException e) {
			System.out.println("Error de coneccion con la base.");
			e.printStackTrace();
		}

	}

	// 2.MODIFICACION DE PERSONA(METODO)

	private static void modificacion(Session session, Scanner sc) {

		System.out.println("Ingrese el ID que desea modificar:");
		int id = sc.nextInt();

		HPersonaDAO.mostrarXid(session, id);

		System.out.println("ingrese la columna que desea modificar");
		System.out.println("1.NOMBRE| 2.EDAD| 3.FECHA_NACIMIENTO|4.Salir");
		int col = sc.nextInt();
		
		try {
			while (col != 4) {

				switch (col) {
				case 1:

					System.out.println("ingrese nuevo nombre:");
					String nomNew = sc.next();
					HPersonaDAO.updateNombre(session, id, nomNew);

					break;

				case 2:
					System.out.println("ingrese nueva edad:");
					int edadNew = sc.nextInt();
					HPersonaDAO.updateEdad(session, id, edadNew);

					// verifica si la fecha de nacimiento coincide con la edad nueva
					Date fechaNac = HPersonaDAO.getFechaNac(session, id);
					int edadBase = MetodosAccesorios.calcularEdad(fechaNac);
					if (edadBase != edadNew) {
						System.out.println("Actualice la fecha de nacimiento (aaaa-mm-dd):");
						String feNew = sc.next();
						HPersonaDAO.updateFechaNac(session, id, feNew);
						Date feNac = HPersonaDAO.getFechaNac(session, id);
						int edad = MetodosAccesorios.calcularEdad(feNac);
						HPersonaDAO.updateEdad(session, id, edad);
					}

					break;

				case 3:
					System.out.println("Ingrese fecha nacimiento (aaaa-mm-dd):");
					String feNew = sc.next();
					HPersonaDAO.updateFechaNac(session, id, feNew);

					// actualiza edad de acuerdo a la fecha de nacimiento
					Date feNac = HPersonaDAO.getFechaNac(session, id);
					int edad = MetodosAccesorios.calcularEdad(feNac);
					HPersonaDAO.updateEdad(session, id, edad);

					break;

				default:
					break;

				}

				System.out.println("El usuario ha sido modificado exitosamente");
				HPersonaDAO.mostrarXid(session, id);
				
				System.out.println("ingrese la columna que desea modificar");
				System.out.println("1.NOMBRE| 2.EDAD| 3.FECHA_NACIMIENTO|4.Salir");
				col = sc.nextInt();

			}


		} catch (ParseException e) {
			System.out.println("La fecha ingresada es incorrecta.");
			modificacion(session, sc);
		}

	}

}