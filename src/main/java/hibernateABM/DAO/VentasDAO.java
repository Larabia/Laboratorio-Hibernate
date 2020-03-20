package hibernateABM.DAO;

import org.hibernate.Session;

import hibernateABM.HibernateABMUtil;
import hibernateABM.dto.VentasEntity;

public class VentasDAO {

	public static void saveOrUpdateVenta(VentasEntity venta) {

		Session session = HibernateABMUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(venta);
		session.getTransaction().commit();
		HibernateABMUtil.shutdown();

	}
}
