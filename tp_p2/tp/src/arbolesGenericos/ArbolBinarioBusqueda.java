package arbolesGenericos;


public class ArbolBinarioBusqueda<T extends Comparable<T>> extends ArbolBinario<T> {

	public void agregar(T elem) {
		this.raiz = agregar(raiz, elem);
	}
	
	private Nodo<T> agregar(Nodo<T> nodo, T elem) {
		if (nodo == null) return new Nodo<>(elem);
		if (elem.compareTo(nodo.elemento) < 0)
			nodo.izq = agregar(nodo.izq, elem);
		else if (elem.compareTo(nodo.elemento) > 0)
			nodo.der = agregar(nodo.der, elem);
		return nodo;
	}
	
	//INORDER: izq raiz der
	public void inOrder() {
		inOrder(this.raiz);
	}
	
	private void inOrder(Nodo<T> nodo) {
		if(nodo==null) return;
		inOrder(nodo.izq);
		System.out.println(nodo);
		inOrder(nodo.der);
	}
	
    

}
