package aed;
import java.util.ArrayList;

import javax.swing.plaf.metal.MetalBorders.PaletteBorder;

public class Trie<T> {

    //Invariante de Representación
    // No llego por dos claves al mismo nodo / los nodos tienen un solo padre salvo la raíz (que no tiene padre) / es un árbol
    //No hay nodos inútiles o (bien dicho) los nodos, si no tienen significado, tienen hijos.



   private Nodo<T> raiz;
   private int cantidad;

   private class Nodo<T> {
    T definicion;
    ArrayList<Nodo<T>> lista;
    //boolean estaDefinido;

    public Nodo() {
        nodo.definicion=null;
        nodo.lista= new Array<>(127); //27
    }

    public void poner (char ch, Nodo nodo){
        lista[ch] = nodo;
    }

    public Nodo obtener (char ch){
        return lista[ch];
    }

    public boolean contiene(chr ch){
        return (lista[ch]!=null);
    }

   }





   public nuevoTrie() {
    this.raiz= new Nodo();
    this.cantidad=0;
   }



   public boolean definido (String palabra){
    Nodo actual=this.raiz;

    if (this.raiz==null){
        return false;
    } else{

        for (int i=0; palabra.length();i++){           //recorre la palabra por cararcter Ej: armario
            if (!actual.contiene(palabra.charAt(i))){  //si en la posicion array[a]=null
                return false;  
            }
            actual=actual.obtener(palabra.charAt(i));  //sino actual pasa a ser el nodo que esta en array[a]
        } 

        if(actual.definicion==null){
            return false;
        } else{
            return true;
        }
    }

   }




   public insertar(String palabra, T valor) {
    Nodo actual = this.raiz;
    for (int i=0; palabra.length();i++){ //recorre la palabra por cararcter Ej: armario
        if (!actual.contiene(palabra.charAt(i))){  //si en la posicion array[a]=null
            actual.poner(palabra.charAt(i), new Nodo());  //pongo en array[a] (donde a se convierte a numero) un nuevo nodo (referencia)
        }
        actual=actual.obtener(palabra.charAt(i));  //sino actual pasa a ser el nodo que esta en array[a]
    }
    actual.definicion=definicion;  //recorri toda la palabra inserto el significado
    this.cantidad++;

   }




   public borrar (String palabra){
    Nodo actual=this.raiz;
    Nodo otro=this.raiz;
    Nodo ultimoUtil= this.raiz;
    char letra=palabra.charAt(0);

    //busco y elimino significado
    for (int i=0; palabra.length();i++){  //recorro palabra
        if(masDeUnHijo(otro) || otro.definicion!=null){  //si tiene mas de un hijo o tiene significado me guardo que nodo es y que letra 
           ultimoUtil=otro;
           letra=palabra.charAt(i+1);                  // guardo la siguiente letra de la palabra para borrar esa referencia
        }
        actual=actual.obtener(palabra.charAt(i));  //me muevo para abajo en ambos
        otro=otro.obtener(palabra.charAt(i));
    }  
    actual.definicion=null;                         //elimino la definicion
    ultimoUtil.lista[letra]=null;              // ya el ultimo util no hace referencia a los nodos que me sobraban
    cantidad--;
   }



   public obtener (String palabra){
    Nodo actual= this.raiz;
    for (int i=0; palabra.length();i++){
        actual=actual.obtener(palabra.charAt(i));
    }

    return actual.definicion;
   }



   //esta mal
    public recorrerRecu (StringBuffer palabra, Nodo nodo, ArrayList listado){
        if (noApuntaANadie(actual) && listado.length-1==this.cantidad){
            return listado;
        }
        if (noApuntaANadie(actual)){
            listado.add(palabra);
        }
        if (masDeUnHijo(actual)) {
            recorrer(actual.obtener(primeroConReferencia(actual)));
            
        }
        palabra.append(primeroConReferencia(actual));


        <
        listado.add(primeroConReferencia(actual));
        recorrer(actual.obtener(primeroConReferencia(actual)));

    }






    //FUNCIONES AUXILIARES

    private char primeroConReferencia(Nodo nodo){  //Devuelve cual es la primera referencia (orden lexicografico?) Hay que fijarse que pasa con los numeros
        for (int i=0;nodo.lista.length();i++){
            if (nodo.lista[i]!=null){
                return (char) i;
            }
        }
    }
    private noApuntaANadie(Nodo nodo){
        for (int i=0;nodo.lista.length();i++){
            if (nodo.lista[i]!=null){
                return false;
            }
        }
        return true;
    }

    private masDeUnHijo (Nodo nodo){ 
        int j=0;
        for (int i=0;nodo.lista.length();i++){
            if (nodo.lista[i]!=null){
                j++;
            }
        }
        return (j>1);
    }





}

