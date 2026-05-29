package tp;

public class ArbolBinarioBusqueda extends ArbolBinario {

	public void agregar(int elem) {
		this.raiz = agregar(raiz, elem);
	}
	
	private Nodo agregar(Nodo nodo, int elem) {
		if (nodo == null)
		return new Nodo(elem);
		if (nodo.elemento > elem)
		nodo.izq = agregar(nodo.izq, elem);
		else if (nodo.elemento < elem)
		nodo.der = agregar(nodo.der, elem);
		return nodo;
	}
	
	public void inOrder() {
		inOrder(this.raiz);
	}
	
	private void inOrder(Nodo nodo) {
		if(nodo==null) return;
		inOrder(nodo.izq);
		System.out.println(nodo);
		inOrder(nodo.der);
	}
	
	
}