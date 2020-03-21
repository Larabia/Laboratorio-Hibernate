package hibernateABM.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import hibernateABM.Entity.PersonaEntity;
import hibernateABM.Entity.VentasEntity;
import hibernateABM.Util.HibernateABMUtil;

public class VentasDAO {

	public static void saveOrUpdateVenta(VentasEntity venta) {

		Session session = HibernateABMUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(venta);
		session.getTransaction().commit();
		HibernateABMUtil.shutdown();

	}
	
	public static List<VentasEntity> getAllVentas() {
		Session session = HibernateABMUtil.getSessionFactory().openSession();	
		List<VentasEntity> listadoVentas = new ArrayList<VentasEntity>();
		listadoVentas = session.createQuery("From VentasEntity").list();
		session.close();
		HibernateABMUtil.shutdown();
		return listadoVentas;
	}
}
