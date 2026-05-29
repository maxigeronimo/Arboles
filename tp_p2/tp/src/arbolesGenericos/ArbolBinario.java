package arbolesGenericos;
public class ArbolBinario<T extends Comparable<T>> {
    // Nodo raiz del arbol
    protected Nodo<T> raiz;

    // Constructor
    public ArbolBinario() {
        this.raiz = null;
    }

	// Devuelve los nodos cuya cantidad de hijos coincide con la paridad del nivel
	// Recorrido en PostOrden
	// Si el árbol está vacío retorna null
	public String nodosParidadPostOrden() {

		// Caso base: árbol vacío
		if (this.estaVacio())
			return null;

		return "{ " + nodosParidadPostOrden(this.raiz, 1) + "}";
	}
	private String nodosParidadPostOrden(Nodo<T> nodo, int nivel) {

		// Caso base
		if (nodo == null)
			return "";

		// POSTORDEN: izquierda -> derecha -> nodo
		String textoIzq = nodosParidadPostOrden(nodo.izq, nivel + 1);
		String textoDer = nodosParidadPostOrden(nodo.der, nivel + 1);

		// Contar hijos
		int hijos = 0;

		if (nodo.izq != null)
			hijos++;

		if (nodo.der != null)
			hijos++;

		// Paridad del nivel
		boolean nivelPar = (nivel % 2 == 0);

		String actual = "";

		// Nivel par → 0 hijos
		// Nivel impar → 1 hijo
		if ((nivelPar && hijos == 0) || (!nivelPar && hijos == 1)) {
			actual = nodo.elemento + " ";
		}

		// Concatenación en postorden
		return textoIzq + textoDer + actual;
	}

	// Metodo publico: inicia el proceso de intercambio desde la raiz
	public void intercambiarConMayorHijo() {
		intercambiarConMayorHijo(raiz);
	}
	// Metodo recursivo que recorre el arbol y realiza intercambios
	private void intercambiarConMayorHijo(Nodo<T> nodo) {
		// Caso base: si el nodo es null no hay nada que hacer
		if (nodo == null) return;
		// Recorrido postorden:
		// primero hijos, luego el nodo actual
		intercambiarConMayorHijo(nodo.izq);
		intercambiarConMayorHijo(nodo.der);
		// Solo se puede comparar si el nodo tiene ambos hijos
		if (nodo.izq != null && nodo.der != null) {
			// Obtenemos valores de los hijos
			T valIzq = nodo.izq.elemento;
			T valDer = nodo.der.elemento;
			// Comparamos los hijos para ver cuál es mayor
			if (valIzq.compareTo(valDer) > 0) {
				// Intercambia el nodo con el hijo izquierdo
				T temp = nodo.elemento;
				nodo.elemento = valIzq;
				nodo.izq.elemento = temp;
			} else {
				// Intercambia el nodo con el hijo derecho
				T temp = nodo.elemento;
				nodo.elemento = valDer;
				nodo.der.elemento = temp;
			}
		}
	}

    // Imprime todos los nodos de un nivel especifico
    public void imprimirNivel(int nivel) {
        // Si el arbol esta vacio no hace nada
        if (this.estaVacio()) return;
        imprimirNivel(raiz, nivel, 0);
    }
    // Metodo recursivo para imprimir un nivel
    private void imprimirNivel(Nodo<T> nodo, int nivel, int nivelActual) {
        // Caso base
        if (nodo == null) return;
        // Si llego al nivel buscado imprime el nodo
        if (nivel == nivelActual)
            System.out.println(nodo.elemento);
        // Recorre subarbol izquierdo
        imprimirNivel(nodo.izq, nivel, nivelActual + 1);
        // Recorre subarbol derecho
        imprimirNivel(nodo.der, nivel, nivelActual + 1);
    }

    // Agrega la raiz del arbol
    public final void agregarRaiz(T numero) {
        Nodo<T> nodo = new Nodo<>(numero);
        this.raiz = nodo;
    }

    // Agrega un hijo izquierdo a un nodo buscado
    public final void agregarIzq(T aBuscar, T numero) {
        // Busca el nodo padre
        Nodo<T> nodo = this.buscar(raiz, aBuscar);
        // Si no existe termina
        if (nodo == null) return;
        // Si ya tiene hijo izquierdo termina
        if (nodo.izq != null) return;
        // Agrega nuevo nodo izquierdo
        nodo.izq = new Nodo<>(numero);
    }

    // Agrega un hijo derecho a un nodo buscado
    public final void agregarDer(T aBuscar, T numero) {
        // Busca el nodo padre
        Nodo<T> nodo = this.buscar(raiz, aBuscar);
        // Si no existe termina
        if (nodo == null) return;
        // Si ya tiene hijo derecho termina
        if (nodo.der != null) return;
        // Agrega nuevo nodo derecho
        nodo.der = new Nodo<>(numero);
    }

    // Verifica si el arbol esta vacio
    public boolean estaVacio() {
        return this.raiz == null;
    }

    // Metodo publico de busqueda
    public boolean buscar(T aBuscar) {
        // Retorna true si encuentra el nodo
        return buscar(this.raiz, aBuscar) == null? false: true;
    }
    // Metodo recursivo de busqueda
    private Nodo<T> buscar(Nodo<T> nodo, T aBuscar) {
        // Caso base
        if (nodo == null) return null;
        // Si encuentra el elemento retorna el nodo
        // compareTo == 0 significa iguales
		// igual retorna 0
		// mayor 1
		// menor -1
        if (nodo.elemento.compareTo(aBuscar) == 0)
            return nodo;
        // Busca primero en el subarbol izquierdo
        Nodo<T> aux = buscar(nodo.izq, aBuscar);
        // Si no lo encuentra busca en el derecho
        if (aux == null)
            aux = buscar(nodo.der, aBuscar);
        return aux;
    }

    // Imprime el arbol completo
    public void imprimir() {
        imprimir(this.raiz);
    }
    // Recorrido PREORDER:
    // raiz -> izquierda -> derecha
    private void imprimir(Nodo<T> nodo) {
        // Caso base
        if (nodo == null) return;
        // Imprime la raiz
        System.out.println(nodo);
        // Recorre izquierda
        imprimir(nodo.izq);
        // Recorre derecha
        imprimir(nodo.der);
    }
}


// Clase Nodo
class Nodo<T extends Comparable<T>> {

    // Elemento almacenado
    T elemento;
    // Referencia al hijo izquierdo
    Nodo<T> izq;
    // Referencia al hijo derecho
    Nodo<T> der;

    // Constructor
    public Nodo(T elemento) {
        this.elemento = elemento;
    }

    // Representacion del nodo en texto
    @Override
    public String toString() {
        return "Nodo [elemento=" + elemento + "]";
    }
}