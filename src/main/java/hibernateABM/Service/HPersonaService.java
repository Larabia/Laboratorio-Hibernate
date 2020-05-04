package hibernateABM.Service;

import hibernateABM.DAO.HPersonaDAO;

public class HPersonaService {

	private HPersonaDAO persona;

	public HPersonaDAO getPersona() {
		return persona;
	}

	public void setPersona(HPersonaDAO persona) {
		this.persona = persona;
	} 
}
