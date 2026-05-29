package arbolesGenericos;


public class ArbolBinarioBusqueda<T extends Comparable<T>>
        extends ArbolBinario<T> {

    // Metodo publico para agregar un elemento al ABB
    public void agregar(T elem) {
        this.raiz = agregar(raiz, elem);
    }

    // Metodo recursivo para insertar un nodo
    private Nodo<T> agregar(Nodo<T> nodo, T elem) {
        // Si el nodo es null, crea uno nuevo
        if (nodo == null)
            return new Nodo<>(elem);
        // Si el elemento es menor, va a la izquierda
		// igual retorna 0
		// mayor 1
		// menor -1
        if (elem.compareTo(nodo.elemento) < 0)
            nodo.izq = agregar(nodo.izq, elem);
        // Si el elemento es mayor, va a la derecha
        else if (elem.compareTo(nodo.elemento) > 0)
            nodo.der = agregar(nodo.der, elem);
        // Retorna el nodo actual
        return nodo;
    }

    // INORDER: izquierda -> raiz -> derecha
    // En un ABB imprime los elementos ordenados
    public void inOrder() {
        inOrder(this.raiz);
    }

    // Recorrido recursivo inOrder
    private void inOrder(Nodo<T> nodo) {
        // Caso base
        if (nodo == null) return;
        // Recorre subarbol izquierdo
        inOrder(nodo.izq);
        // Imprime la raiz
        System.out.println(nodo);
        // Recorre subarbol derecho
        inOrder(nodo.der);
    }
}