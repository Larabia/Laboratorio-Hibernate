package hibernateABM;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.hibernate.Session;

import hibernateABM.dto.PersonaEntity;




public class AppHibernateABM {

	public static void main(String[] args) {
		
		Session session = HibernateABMUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		PersonaEntity per = new PersonaEntity();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Ingrese nombre:");
		String nombre = sc.nextLine();
		
		per.setNombre(nombre);
		
		System.out.println("Ingrese fecha de nacimiento (aaaa-mm-dd):");
		String feNacStng = sc.nextLine();
		
		try {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  Date feNac = sdf.parse(feNacStng);
		  int edad = MetodosAccesorios.calcularEdad(feNac);
		  
		  per.setEdad(edad);
		  per.setFeNac(feNac);
		  
		} catch (ParseException e) {
			System.out.println("La fecha ingresada es incorrecta.");
			
		}
		
		
		

		session.saveOrUpdate(per);
		
		session.getTransaction().commit();
		HibernateABMUtil.shutdown();

	}
}