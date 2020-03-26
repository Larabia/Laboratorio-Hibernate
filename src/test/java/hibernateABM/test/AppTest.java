package hibernateABM.test;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import hibernateABM.DAO.HPersonaDAO;
import hibernateABM.Entity.PersonaEntity;
import hibernateABM.Util.DateUtil;
import hibernateABM.Util.HibernateABMUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	public void test1() {
		System.out.println("Test getAllPersona");
		List<PersonaEntity> list = HPersonaDAO.getAllPersona();
		boolean tieneRegistros = list.size() > 0;
		assertTrue("No devuelve registros", tieneRegistros);
	}

	public void test2() {
		System.out.println("Test saveOrUpdatePersona, currentDate, calcularEdad");
		PersonaEntity perMock = new PersonaEntity();
		
		perMock.setNombre("TEST");
		perMock.setFechaNacimiento(DateUtil.currentDate());
		perMock.setEdad(HibernateABMUtil.calcularEdad(DateUtil.currentDate()));
		
		HPersonaDAO.saveOrUpdatePersona(perMock);
		List<PersonaEntity> list = HPersonaDAO.getPerXnombre("TEST");
		
		boolean PersonaTESTregistrada = list.size() > 0;
		HPersonaDAO.deletePersona(perMock);

		assertTrue("No devuelve registros", PersonaTESTregistrada);
	}

	public void test3() throws ParseException {
		System.out.println("delete, formatParse, getPerXid");
		List<PersonaEntity> list = HPersonaDAO.getAllPersona();
		int listaSinMock = list.size();
		
		PersonaEntity perMock = new PersonaEntity();
		String fechaString = "17/06/1986";
		Date fechaDate = DateUtil.formatParse(DateUtil.PATTERN_D2_M2_Y4, fechaString);

		perMock.setNombre("TEST");
		perMock.setFechaNacimiento(fechaDate);
		perMock.setEdad(HibernateABMUtil.calcularEdad(fechaDate));
		
		HPersonaDAO.saveOrUpdatePersona(perMock);
		
		int id = perMock.getId();
		
		List<PersonaEntity> list2 = HPersonaDAO.getAllPersona();
		int listaMasMock = list.size();
			
		HPersonaDAO.deletePersona(HPersonaDAO.getPerXid(id));
		
		List<PersonaEntity> list3 = HPersonaDAO.getAllPersona();
		int listaMenosMock = list.size();
		
		assertTrue(listaSinMock == listaMenosMock);
	}
}
