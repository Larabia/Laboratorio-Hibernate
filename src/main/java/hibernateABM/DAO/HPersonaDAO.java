package hibernateABM.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;

import hibernateABM.MetodosAccesorios;
import hibernateABM.dto.PersonaEntity;

public class HPersonaDAO {

	public static void ingresarPersona(Session session, String feNacStng, String nombre, PersonaEntity per)
			throws ParseException, SQLException {
		
		session.beginTransaction();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date feNac = sdf.parse(feNacStng);
		int edad = MetodosAccesorios.calcularEdad(feNac);

		per.setNombre(nombre);
		per.setEdad(edad);
		per.setFeNac(feNac);

		session.saveOrUpdate(per);
		session.getTransaction().commit();

	}

	public static void mostrarPersona(Session session, PersonaEntity per) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fNac = sdf.format(per.getFeNac());
		System.out.println("ID|NOMBRE|EDAD|F.NACIM");
		System.out.println(per.getId() + " " + per.getNombre() + " " + per.getEdad() + " " + fNac);
	}

	public static void mostrarXid(Session session, int id) {

		PersonaEntity per =  (PersonaEntity) session.get(PersonaEntity.class, id);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fNac = sdf.format(per.getFeNac());

		System.out.println("ID|NOMBRE|EDAD|F.NACIM");
		System.out.println(per.getId() + " " + per.getNombre() + " " + per.getEdad() + " " + fNac);
	}

	public static void updateNombre(Session session, int id, String nomNew) {
		session.beginTransaction();
		PersonaEntity per =  (PersonaEntity) session.get(PersonaEntity.class, id);
		per.setNombre(nomNew);
		session.saveOrUpdate(per);
		session.getTransaction().commit();
	}

	public static void updateEdad(Session session, int id, int edadNew) {
		session.beginTransaction();
		PersonaEntity per =  (PersonaEntity) session.get(PersonaEntity.class, id);
		per.setEdad(edadNew);
		session.saveOrUpdate(per);
		session.getTransaction().commit();
	}

	public static Date getFechaNac(Session session, int id) {
		session.beginTransaction();
		PersonaEntity per =  (PersonaEntity) session.get(PersonaEntity.class, id);
		Date fNac = per.getFeNac();
		return fNac;
	}

	public static void updateFechaNac(Session session, int id, String feNew) throws ParseException {
		
		session.beginTransaction();
		PersonaEntity per =  (PersonaEntity) session.get(PersonaEntity.class, id);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date feNac = sdf.parse(feNew);

		per.setFeNac(feNac);
		
		session.saveOrUpdate(per);
		session.getTransaction().commit();
	}
}