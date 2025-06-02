package clases;

public class Empleado extends Usuario{
	
	private int Id = 0;
	private String[] cargo = {"Administrador", "Chef", "Repartidor"};
	public Empleado(String nombre, String pass, String rol, int id, String[] cargo) {
		super(nombre, pass, rol);
		Id = id;
		this.cargo = cargo;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String[] getCargo() {
		return cargo;
	}
	public void setCargo(String[] cargo) {
		this.cargo = cargo;
	}
	
	
}
