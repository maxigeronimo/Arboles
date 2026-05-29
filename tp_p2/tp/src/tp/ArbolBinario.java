package tp;

public class ArbolBinario {
	Nodo raiz;
	public ArbolBinario() {
		this.raiz = null;
	}

	public void imprimirNivel(int nivel) {
		if (this.estaVacio()) return;
		imprimirNivel(raiz,nivel,0);
	}

	private void imprimirNivel(Nodo nodo, int nivel, int nivelAcutal) {
		if(nodo == null) return;
		if(nivel==nivelAcutal) System.out.println(nodo.elemento);
		imprimirNivel(nodo.izq, nivel, nivelAcutal+1);
		imprimirNivel(nodo.der, nivel, nivelAcutal+1);
	}
	

	public final void agregarRaiz(int numero) {
		Nodo nodo = new Nodo(numero);
		this.raiz = nodo;
	}
    
	public final void agregarIzq(int aBuscar, int numero) {
		Nodo nodo = this.buscar(raiz, aBuscar);
		if(nodo==null) return;
		if(nodo.izq != null ) return;
		nodo.izq = new Nodo(numero);
	} 
	public final void agregarDer(int aBuscar, int numero) {
		Nodo nodo = this.buscar(raiz, aBuscar);
		if(nodo==null) return;
		if(nodo.der != null ) return;
		nodo.der = new Nodo(numero);
	} 
	
	
	
	public boolean estaVacio() {
		return this.raiz == null;
	}
	
	public boolean buscar(int aBuscar) {
		return buscar(this.raiz, aBuscar) == null ? false:true;
	}
	private Nodo buscar(Nodo nodo, int aBuscar) {
		if(nodo==null) return null;
		if(nodo.elemento == aBuscar) return nodo;
		Nodo aux = buscar(nodo.izq,aBuscar);
		if( aux == null) aux = buscar(nodo.der,aBuscar);
		return aux;
	}
		
	
	public void imprimir() {
		imprimir(this.raiz);
	}
	private void imprimir(Nodo nodo) {
		if(nodo==null) return;
		System.out.println(nodo);
		imprimir(nodo.izq);
		imprimir(nodo.der);
	}
}


class Nodo {
	int elemento;
	Nodo izq;
	Nodo der;
	public Nodo(int elemento) {
		this.elemento = elemento;
	}
	@Override
	public String toString() {
		return "Nodo [elemento=" + elemento + "]";
	}
	
}