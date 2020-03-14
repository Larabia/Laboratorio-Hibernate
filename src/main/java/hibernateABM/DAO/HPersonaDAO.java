package hibernateABM.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.Session;
import hibernateABM.HibernateABMUtil;
import hibernateABM.dto.PersonaEntity;

public class HPersonaDAO {

	public static void UpdatePersona(PersonaEntity per)
			throws ParseException, SQLException {
		
		Session session = HibernateABMUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(per);
		session.getTransaction().commit();
		HibernateABMUtil.shutdown();


	}

	public static void mostrarPersona(PersonaEntity per) {
		
		Session session = HibernateABMUtil.getSessionFactory().openSession();
		session.beginTransaction();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fNac = sdf.format(per.getFeNac());
		System.out.println("ID|NOMBRE|EDAD|F.NACIM");
		System.out.println(per.getId() + " " + per.getNombre() + " " + per.getEdad() + " " + fNac);
		HibernateABMUtil.shutdown();
	}

	public static PersonaEntity getPerXid(int id) {
		
		Session session = HibernateABMUtil.getSessionFactory().openSession();
		session.beginTransaction();
		PersonaEntity per =  (PersonaEntity) session.get(PersonaEntity.class, id);
		HibernateABMUtil.shutdown();
		return per;
		
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