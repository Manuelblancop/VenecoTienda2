package clases;

import java.util.LinkedList;

import singleton.Menu;

public class Sucursal{
	//Extender empleados
	private String ubicacion = "";
	private static LinkedList<Menu> Menu = new LinkedList<>();
	public Sucursal(String ubicacion) {
		super();
		this.ubicacion = ubicacion;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public static LinkedList<Menu> getMenu() {
		return Menu;
	}
	public static void setMenu(LinkedList<Menu> menu) {
		Menu = menu;
	}
	
	
	
}
