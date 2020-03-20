package hibernateABM.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import hibernateABM.HibernateABMUtil;
import hibernateABM.dto.PersonaEntity;
import hibernateABM.dto.VentasEntity;

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
