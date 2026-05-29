//package tp;
package arbolesGenericos;

public class Principal {
	public static void main(String[] args) {
		//Ejercicio AB
		ArbolBinario<Integer> ab = construirABEjemplo();

        String resultado = ab.nodosParidadPostOrden();

        System.out.println(resultado);
		
		//Ejercicio ABB
		construirABBEjemplo();
		
		
		/*
		ArbolBinarioBusqueda arbolBinarioBusqueda = new ArbolBinarioBusqueda();
		arbolBinarioBusqueda.agregar(35);
		arbolBinarioBusqueda.agregar(15);
		arbolBinarioBusqueda.agregar(10);
		arbolBinarioBusqueda.inOrder();
		arbolBinarioBusqueda.imprimirNivel(0);
		*/
		/*
		ArbolBinario<Integer> arbolBinario = new ArbolBinario<>();
		arbolBinario.agregarRaiz(10);
		arbolBinario.agregarIzq(10,56);
		arbolBinario.agregarDer(10,8);
		arbolBinario.agregarDer(8,9);
		arbolBinario.agregarDer(9,73);
		arbolBinario.agregarIzq(9,30);
		arbolBinario.imprimirNivel(3);
		*/
		// System.out.println(arbolBinario.buscar(80));
		
		//arbolBinario.imprimir();
		//System.out.println(arbolBinario.estaVacio());
		//int[] numeros = {5,6,7,8};
		//System.out.println(imprimirRec1(numeros,0));
		
		// TreeMap<Integer,String> capitulos = new TreeMap();
		
	}

	
 	public static ArbolBinario<Integer> construirABEjemplo() {

        ArbolBinario<Integer> arbol = new ArbolBinario<>();

        arbol.agregarRaiz(20);

        arbol.agregarIzq(20, 11);
        arbol.agregarDer(20, 63);

        arbol.agregarIzq(11, 13);
        arbol.agregarIzq(63, 31);
        arbol.agregarDer(63, 25);

        arbol.agregarIzq(13, 1);
        arbol.agregarIzq(25, 13);

        return arbol;
    }

	public static void construirABBEjemplo() {
		// Crear el árbol
        ArbolBinarioBusqueda<Integer> arbol = new ArbolBinarioBusqueda<>();
        arbol.agregarRaiz(20);
        arbol.agregarIzq(20, 5);
        arbol.agregarDer(20, 25);
        arbol.agregarIzq(5, 4);
        arbol.agregarDer(5, 10);
        arbol.agregarIzq(4, 2);
        arbol.agregarDer(25, 50);

        // Mostrar estructura del ABB
    // System.out.println("ABB en inOrden:");
    // arbol.inOrder();

    // Pruebas del método pertenecenComplementos
    System.out.println("\nPruebas pertenecenComplementos:");

    System.out.println("total = 50  -> " + arbol.pertenecenComplementos(50));
    System.out.println("total = 200 -> " + arbol.pertenecenComplementos(200));
    System.out.println("total = 100 -> " + arbol.pertenecenComplementos(100));
    }
	
	private static int imprimirRec1(int[] numeros,int i) {
		// caso base
		if(i==numeros.length-1) return numeros[i];
		return numeros[i] + imprimirRec(numeros, i+1);
	}

	private static int imprimirRec(int[] numeros,int i) {
		// caso base
		if(i==0) return numeros[i];
		return numeros[i] + imprimirRec(numeros, i-1);
	}

	private static void imprimir(int[] numeros) {
		for (int i = 0; i < numeros.length; i++) {
			System.out.println(numeros[i]);
		}		
	}
	

}
