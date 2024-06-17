package aed;

import java.util.*;

public class Trie<T> {

    //Invariante de Representación
    // No llego por dos claves al mismo nodo / los nodos tienen un solo padre salvo la raíz (que no tiene padre) / es un árbol
    //No hay nodos inútiles o (bien dicho) los nodos, si no tienen significado, tienen hijos.



   private Nodo raiz;
   private int cantidad;

   private class Nodo {
    T definicion;
    ArrayList<Nodo> lista;
   

    public Nodo() {
        this.definicion=null;
        this.lista= new ArrayList<Nodo>() ;

        for(int i=0; i<256; i++){
            this.lista.add(null);
        }


    }

    public void poner (char ch, Nodo nodo){
        lista.set( (int) ch, nodo);
    }

    public Nodo obtener (char ch){
        return lista.get((int) ch);
    }

    public boolean contiene(char ch){
        return (lista.get((int) ch)!=null);
    }

   }


   public Trie() {
    this.raiz= new Nodo();
    this.cantidad=0;
   }


   public boolean definido (String palabra){
    Nodo actual=this.raiz;

    if (this.raiz==null){
        return false;
    } else{

        for (int i=0; i<palabra.length();i++){           //recorre la palabra por cararcter Ej: armario
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




   public void insertar(String palabra, T valor) {
    Nodo actual = this.raiz;
    for (int i=0; i<palabra.length();i++){ //recorre la palabra por cararcter Ej: armario
        if (!actual.contiene(palabra.charAt(i))){  //si en la posicion array[a]=null
            actual.poner(palabra.charAt(i), new Nodo());  //pongo en array[a] (donde a se convierte a numero) un nuevo nodo (referencia)
        }
        actual=actual.obtener(palabra.charAt(i));  //sino actual pasa a ser el nodo que esta en array[a]
    }
    actual.definicion=valor;  //recorri toda la palabra inserto el significado
    this.cantidad++;

   }




   public void borrar (String palabra){
    Nodo actual=this.raiz;
    Nodo otro=this.raiz;
    Nodo ultimoUtil= this.raiz;
    char letra=palabra.charAt(0);
    int i=0;

    while(i<palabra.length()){
        actual=actual.obtener(palabra.charAt(i));  //me muevo para abajo en ambos
        otro=otro.obtener(palabra.charAt(i));

        if(masDeUnHijo(otro) || otro.definicion!=null){  //si tiene mas de un hijo o tiene significado me guardo que nodo es y que letra 
            ultimoUtil=otro;
            letra=palabra.charAt(i);                  // guardo la siguiente letra de la palabra para borrar esa referencia
        }

        i++;
    }
    
    actual.definicion=null;                     //elimino la definicion
    if (masDeUnHijo(ultimoUtil)){
        ultimoUtil.lista.set( (int) letra, null);   // ya el ultimo util no hace referencia a los nodos que me sobraban
    }
    cantidad--;
   }



   

   public T obtener (String palabra){
    Nodo actual= this.raiz;
    for (int i=0;i<palabra.length();i++){
        actual=actual.obtener(palabra.charAt(i));
    }

    return actual.definicion;
   }


   public int tamaño(){
    return this.cantidad;
   }



   //esta mal
   /*  public recorrerRecu (StringBuffer palabra, Nodo nodo, ArrayList listado){
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


        
        listado.add(primeroConReferencia(actual));
        recorrer(actual.obtener(primeroConReferencia(actual)));

    } */
    /*public recRecorrido() {
        if(raiz==null) {
            return "" ;

        }
        else {
            recorrido(raiz,null,0) ;
        }
    }
    public recorrido(Nodo actual,StringBuffer palabra,int tamaño) {
        StringBuffer prefijo = new StringBuffer() ;
        ArrayList res = new ArrayList<StringBuffer>() ;
        if(actual.definicion != null) {
            res.add(prefijo) ;
            return res ;
        }
        else {
            for(int i=0 ; i<255 ;i++) {
                if(actual.lista[i] != null) {
                    c = (char) i ;
                    prefijo.append(c) ;
                    recorrido(actual.lista[i],prefijo,tamaño) ;

                }

            }        }
    } */
  





    //FUNCIONES AUXILIARES


    private char primeroConReferencia(Nodo nodo){  //Devuelve cual es la primera referencia (orden lexicografico?) Hay que fijarse que pasa con los numeros
        char m;
        int i=0;

        while(i<nodo.lista.size() && nodo.lista.get(i)==null){
            i++;
        }
        m= (char) i;
        return m;
    }

    private boolean noApuntaANadie(Nodo nodo){
        for (int i=0;i==nodo.lista.size();i++){
            if (nodo.lista.get(i)!=null){
                return false;
            }
        }
        return true;
    }

    private boolean masDeUnHijo (Nodo nodo){ 
        int j=0;
        for (int i=0;i<nodo.lista.size();i++){
            if (nodo.lista.get(i)!=null){
                j++;
            }
        }
        return (j>1);
    }

    public class Trie_Iterador  {
        ArrayList<String> palabras;
        int cont;

        public Trie_Iterador(){
            // Crear el iterador ---> O( largo de cada clave )

            this.cont = 0;
            this.palabras = new ArrayList<String>(); 

            cargarClaves( raiz, "" ); // O( largo de cada clave )
        }

        public void cargarClaves( Nodo N, String claveActual ){
            // Recorre todo el trie en orden, recursivamente.
            // Almacenando en cada iteracion la clave del recorrido.

            // O( 256 ) * O( largo de cada clave ) = O( largo de cada clave )

            // Si este nodo tenia una clave definida
            if(N.definicion != null){
                this.palabras.add(claveActual);
            }

            // Nos fijamos en las letras de este nodo a ver si continua
            for( int i=0; i<256; i++ ){
                Nodo caracter = N.obtener( (char) i);

                // Si es nulo no lo consideramos
                if( caracter != null ){
                    cargarClaves(caracter, claveActual + String.valueOf( (char) i));
                }
            }

        }

        public boolean haySiguiente() {
            // O(1)            
            return (this.cont < this.palabras.size());
        }
    
        public String siguiente() {
            // O(1)
            this.cont += 1;
            return this.palabras.get(cont-1);
        }
    }

    public Trie_Iterador iterador() {
        return new Trie_Iterador();
    }

}
