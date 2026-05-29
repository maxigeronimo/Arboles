package arbolesGenericos;

public class ArbolBinario<T extends Comparable<T>> {
	protected Nodo<T> raiz;
	public ArbolBinario() {
		this.raiz = null;
	}
	public void imprimirNivel(int nivel) {
		if (this.estaVacio()) return;
		imprimirNivel(raiz,nivel,0);
	}
	private void imprimirNivel(Nodo<T> nodo, int nivel, int nivelAcutal) {
		if(nodo == null) return;
		if(nivel==nivelAcutal) System.out.println(nodo.elemento);
		imprimirNivel(nodo.izq, nivel, nivelAcutal+1);
		imprimirNivel(nodo.der, nivel, nivelAcutal+1);
	}
	

	public final void agregarRaiz(T numero) {
		Nodo<T> nodo = new Nodo<>(numero);
		this.raiz = nodo;
	}
	public final void agregarIzq(T aBuscar, T numero) {
		Nodo<T> nodo = this.buscar(raiz, aBuscar);
		if(nodo==null) return;
		if(nodo.izq != null ) return;
		nodo.izq = new Nodo<>(numero);
	} 
	public final void agregarDer(T aBuscar, T numero) {
		Nodo<T> nodo = this.buscar(raiz, aBuscar);
		if(nodo==null) return;
		if(nodo.der != null ) return;
		nodo.der = new Nodo<>(numero);
	} 
	
	
	
	public boolean estaVacio() {
		return this.raiz == null;
	}
	
	public boolean buscar(T aBuscar) {
		return buscar(this.raiz, aBuscar) == null ? false:true;
	}
	private Nodo<T> buscar(Nodo<T> nodo, T aBuscar) {
		if(nodo==null) return null;
		if(nodo.elemento == aBuscar) return nodo;
		Nodo<T> aux = buscar(nodo.izq,aBuscar);
		if( aux == null) aux = buscar(nodo.der,aBuscar);
		return aux;
	}
		
	
	public void imprimir() {
		imprimir(this.raiz);
	}
	private void imprimir(Nodo<T> nodo) {
		if(nodo==null) return;
		System.out.println(nodo);
		imprimir(nodo.izq);
		imprimir(nodo.der);
	}
}


class Nodo<T extends Comparable<T>> {
	T elemento;
	Nodo<T> izq;
	Nodo<T> der;
	public Nodo(T elemento) {
		this.elemento = elemento;
	}
	@Override
	public String toString() {
		return "Nodo [elemento=" + elemento + "]";
	}	
	
	
	
	
	
}