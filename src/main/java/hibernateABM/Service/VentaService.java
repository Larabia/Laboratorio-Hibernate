package hibernateABM.Service;

import hibernateABM.DAO.VentasDAO;

public class VentaService {
	
	private VentasDAO venta;

	public VentasDAO getVenta() {
		return venta;
	}

	public void setVenta(VentasDAO venta) {
		this.venta = venta;
	}

}
