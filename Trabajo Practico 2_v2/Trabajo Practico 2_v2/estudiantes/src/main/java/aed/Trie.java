package aed;

import java.util.*;

public class Trie<T> {

    //Invariante de Representación
    // No llego por dos claves al mismo nodo / los nodos tienen un solo padre salvo la raíz (que no tiene padre) / es un árbol
    //No hay nodos inútiles o (bien dicho) los nodos, si no tienen significado, tienen hijos.



   private Nodo raiz;
   private int cantidad;

   private class Nodo {
    T definicion; //O(1)
    ArrayList<Nodo> lista; //O(1)
   

    public Nodo() {
        this.definicion=null; //O(1)
        this.lista= new ArrayList<Nodo>() ; //O(1)

        for(int i=0; i<256; i++){ //O(256)
            this.lista.add(null); //O(1)
        } //O(1)*O(256) = 0(1)


    }

    public void poner (char ch, Nodo nodo){
        lista.set( (int) ch, nodo); //O(1)
    }

    public Nodo obtener (char ch){
        return lista.get((int) ch);  //O(1)
    }

    public boolean contiene(char ch){
        return (lista.get((int) ch)!=null);  //O(1)
    }

   }


   public Trie() {
    this.raiz= new Nodo();  //O(1)
    this.cantidad=0; //O(1)
   } //O(1)+ O(1) = O(1)


   public boolean definido (String palabra){
    Nodo actual=this.raiz;  //O(1)

    if (this.raiz==null){  //O(1)
        return false;  //O(1)+O(1) = O(1)
    } else{

        for (int i=0; i<palabra.length();i++){  //recorre la palabra por cararcter Ej: armario  O(|palabra|)
            if (!actual.contiene(palabra.charAt(i))){  //si en la posicion array[a]=null  O(1) + O(1) +  O(1) =  O(1)
                return false;   //O(1)+O(1)=O(1)*O(|palabra|) = O(|palabra|)
            }
            actual=actual.obtener(palabra.charAt(i));  //sino actual pasa a ser el nodo que esta en array[a]  //O(1)+O(1)+O(1)=O(1)
        }  //O(1) *  O(|palabra|)  =  O(|palabra|)
       //El peor caso seria en el que llegue a i==|palabra| y que en el nodo actual el valor no esté definido.
        if(actual.definicion==null){  //O(1)
            return false;  //O(1)
        } else{
            return true;  //O(1)
        }
    }
   //el pero caso seria que entre en el for
   }  //por lo tanto,la complejidad del peor caso es de O(|palabra|)+O(1)+O(1)= O(|palabra|)




   public void insertar(String palabra, T valor) {
    Nodo actual = this.raiz;  //O(1)
    for (int i=0; i<palabra.length();i++){ //recorre la palabra por caracter Ej: armario  //O(|palabra|)
        if (!actual.contiene(palabra.charAt(i))){  //si en la posicion array[a]=null  //O(1) + O(1) + O(1) = O(1)
            actual.poner(palabra.charAt(i), new Nodo());  //pongo en array[a] (donde a se convierte a numero) un nuevo nodo (referencia) O(1) + O(1) + O(1) = O(1)
        }
        actual=actual.obtener(palabra.charAt(i));  //sino actual pasa a ser el nodo que esta en array[a]     O(1) + O(1) + O(1) = O(1)
    } //(O(1)+ O(1) + O(1)) * O(|palabra|) = O(|palabra|)
    //el peor caso es cuando en el Trie todavia no hay ninguna palabra que empiece con la primer letra de la palabra a insertar.
    actual.definicion=valor;  //recorri toda la palabra e inserto el significado //O(1)
    this.cantidad++; //O(1)

   } //O(|palabra|)




   public void borrar (String palabra){
    Nodo actual=this.raiz; //O(1)
    Nodo otro=this.raiz; //O(1)
    Nodo ultimoUtil= this.raiz; //O(1)
    char letra=palabra.charAt(0); //O(1)
    int i=0; //O(1)

    while(i<palabra.length()){ //O(|palabra|)
        actual=actual.obtener(palabra.charAt(i));  //me muevo para abajo en ambos O(1)
        otro=otro.obtener(palabra.charAt(i)); //O(1)

        if(masDeUnHijo(otro) || otro.definicion!=null){  //si tiene mas de un hijo o tiene significado me guardo que nodo es y que letra //O(1)+O(1)+O(1) = O(1)
            ultimoUtil=otro;    //O(1)
            letra=palabra.charAt(i);    // guardo la siguiente letra de la palabra para borrar esa referencia //O(1)
        } //O(1)+O(1)+O(1) = O(1)

        i++; //O(1)
    } //O(|palabra)*(O(1)+O(1)+O(1)) = O(|palabra|)
    
    actual.definicion=null;  //elimino la definicion //O(1)
    if (masDeUnHijo(ultimoUtil)){ //O(1)
        ultimoUtil.lista.set( (int) letra, null);   // ya el ultimo util no hace referencia a los nodos que me sobraban O(1)
    } //O(1)+O(1)= O(1)
    cantidad--; //O(1)
   } //O(|palabra|)+O(1)+O(1)+O(1) = O(|palabra|)



   

   public T obtener (String palabra){
    Nodo actual= this.raiz; //O(1)
    for (int i=0;i<palabra.length();i++){ //O(|palabra|)
        actual=actual.obtener(palabra.charAt(i)); //O(1)+O(1)+O(1) = O(1)
    } //O(|palabra|)*O(1) = O(|palabra|)
    
    return actual.definicion; //O(1)
   } //O(1)+O(|palabra|)+O(1) = O(|palabra|)


   public int tamaño(){
    return this.cantidad; //O(1)
   }
  
   public void cambiarDefinicion(String palabra,T nuevo) {
    Nodo actual= this.raiz; //O(1)
    for (int i=0;i<palabra.length();i++){ //O(|palabra|)
        actual=actual.obtener(palabra.charAt(i)); //O(1)+O(1)+O(1) = O(1)
    }
    actual.definicion = nuevo ;

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
        char m; //O(1)
        int i=0; //O(1)

        while(i<nodo.lista.size() && nodo.lista.get(i)==null){ //O(256)
            i++; //O(1)
        } //O(1)*O(256) = O(1)
        m= (char) i; //O(1)
        return m; //O(1)
    } //O(1)

    private boolean noApuntaANadie(Nodo nodo){
        for (int i=0;i==nodo.lista.size();i++){ //O(256)
            if (nodo.lista.get(i)!=null){ //O(1)+O(1) = O(1)
                return false;//O(1)
            }
        }
        return true;//O(1)
    } //O(1)

    private boolean masDeUnHijo (Nodo nodo){ 
        int j=0; //O(1)
        for (int i=0;i<nodo.lista.size();i++){ //O(256)
            if (nodo.lista.get(i)!=null){ //O(1)+O(1)
                j++;//O(1)
            }
        }
        return (j>1); //O(1)
    } //O(1)

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
