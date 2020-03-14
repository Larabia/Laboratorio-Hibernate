package hibernateABM.DAO;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import hibernateABM.HibernateABMUtil;
import hibernateABM.dto.PersonaEntity;

public class HPersonaDAO {

	public static void updatePersona(PersonaEntity per) {

		Session session = HibernateABMUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(per);
		session.getTransaction().commit();
		HibernateABMUtil.shutdown();

	}

	public static void deletePersona(PersonaEntity per) {

		Session session = HibernateABMUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(per);
		session.getTransaction().commit();
		HibernateABMUtil.shutdown();

	}

	public static PersonaEntity getPerXid(int id) {

		Session session = HibernateABMUtil.getSessionFactory().openSession();
		session.beginTransaction();
		PersonaEntity per = (PersonaEntity) session.get(PersonaEntity.class, id);
		HibernateABMUtil.shutdown();
		return per;

	}

	public static List<PersonaEntity> getAllPersona() {
		Session session = HibernateABMUtil.getSessionFactory().openSession();	
		List<PersonaEntity> listadoPer = new ArrayList<PersonaEntity>();
		listadoPer = session.createQuery("From PersonaEntity").list();
		session.close();
		HibernateABMUtil.shutdown();
		return listadoPer;
	}

}