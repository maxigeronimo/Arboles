//package tp;
package arbolesGenericos;

public class Principal {
	public static void main(String[] args) {
		
		ArbolBinarioBusqueda<Equipo> arbolEquipos = new ArbolBinarioBusqueda<>();
		arbolEquipos.agregar(new Equipo("Arg",1875));
		arbolEquipos.agregar(new Equipo("Fra",1877));
		arbolEquipos.agregar(new Equipo("Chi",1455));
		// arbolEquipos.imprimirNivel(1);
		arbolEquipos.inOrder();
		
		
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
