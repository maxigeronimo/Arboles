package arbolesGenericos;

public class Equipo implements Comparable<Equipo>{
	private String nombre;
	private int rating;
	public Equipo(String nombre, int rating) {
		super();
		this.nombre = nombre;
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "Equipo [nombre=" + nombre + ", rating=" + rating + "]";
	}
	@Override
	public int compareTo(Equipo otroEquipo) {
		// igual retorna 0
		// mayor 1
		// menor -1
		int aux = 0;
		if(this.rating > otroEquipo.rating) {
			aux = 1;
		} else if(this.rating < otroEquipo.rating) {
			aux = -1;
		}
		return aux;
	}
	
}
