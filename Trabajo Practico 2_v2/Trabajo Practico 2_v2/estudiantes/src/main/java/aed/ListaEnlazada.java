package aed;

import java.util.*;
//InvRep : El valor de la refererencia al anterior al primero es nulo,y el valor de la referencia al siguiente al último tambien lo es.
//No existen ciclos dentro de la lista.
//cuando la longitud no es 0, no existe ningun nodo nulo. La cantidad de nodos esta asociada a los nodos por los cuales inevitablemente se 
//accede para llegar al ultimo. De lo contrario, al inicializar la lista, primero y ultimo son nulos.


public class ListaEnlazada<T> {
    private Nodo primero;
    private Nodo ultimo;
    private int longitud;

    private class Nodo {
        private T dato;
        private Nodo sig;
        private Nodo ant;

        public Nodo (T datos){
            this.dato=datos; //O(1)
            this.sig=null;  //O(1)
            this.ant=null;  //O(1)
        }
    }


    public ListaEnlazada() {
        this.primero=null; //O(1)
        this.ultimo=null;   //O(1)
        this.longitud=0;    //O(1)
    }//O(1)


    public int longitud() {
        return this.longitud; //O(1)
    }//O(1)


    public void agregarAdelante(T elem) {
        Nodo nue= new Nodo(elem);
        if (this.primero==null){ //O(1)
            this.primero=nue;    //O(1)
            this.ultimo=nue;    //O(1)
        }
        this.primero.ant=nue;   //O(1)
        nue.sig=this.primero;   //O(1)
        this.primero=nue;       //O(1)
        this.longitud ++;       //O(1)
    } //O(1)

    public void agregarAtras(T elem) {
        Nodo nue= new Nodo(elem); //O(1)
        if (this.ultimo==null){  //O(1)
            this.primero=nue;   //O(1)
            this.ultimo=nue;    //O(1)
        }
        this.ultimo.sig=nue;    //O(1)
        nue.ant=this.ultimo;    //O(1)
        this.ultimo=nue;        //O(1)
        this.longitud ++;       //O(1)
    } //O(1)

    public T obtener(int i) {
        Nodo aux = this.primero;
        for (int j=0; j<i;j++){ //O(|ListaEnlazada|)
            aux=aux.sig;
        }
        return aux.dato;    //O(1 + |ListaEnlazada|) = O(|ListaEnlazada|)    
    }

    public void eliminar(int i) {
        Nodo eliminar = this.primero; //O(1)
        for (int j=0; j<i;j++){ //O(|ListaEnlazada|)
            eliminar=eliminar.sig;  //O(1)
        }
        if (eliminar==this.primero){ //O(1)
            this.primero=this.primero.sig; //O(1)
        
        }else {
            if (eliminar==this.ultimo){ //O(1)
                this.ultimo=this.ultimo.ant; // O(1)
            }else{
                eliminar.ant.sig=eliminar.sig;  //O(1)
                eliminar.sig.ant=eliminar.ant;  //O(1)
            }
        }
        longitud--; //O(1)
    } //O(|ListaEnlazada|)

    public void modificarPosicion(int indice, T elem) {
        Nodo aux = this.primero; //O(1)
        for (int j=0; j<indice;j++){ //O(|ListaEnlazada|)
            aux=aux.sig; //O(1)
        }
        aux.dato=elem; //O(1)
    } //O(|ListaEnlazada|)

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> nue= new ListaEnlazada<T>();   //O(1)
        Nodo aux = this.primero;    //O(1)
        for (int j=0; j<this.longitud;j++){ //O(|ListaEnlazada|)
            nue.agregarAtras(aux.dato); //O(1)
            aux=aux.sig;    //O(1)
        }
        return nue;
    } //O(|ListaEnlazada|)

    public ListaEnlazada(ListaEnlazada<T> lista) {
        ListaEnlazada<T> copia= lista.copiar(); //O(|ListaEnlazada|)
        this.longitud=copia.longitud;           //O(1)
        this.primero=copia.primero;             //O(1) 
        this.ultimo=copia.ultimo;               //O(1)
    } //O(|ListaEnlazada|)
    
    @Override
    public String toString() {
        Nodo aux= this.primero;                          //O(1)
        StringBuffer cadena= new StringBuffer();        //O(1)
        cadena.append('[');                           //O(1)
        int j=0;                                        //O(1)
        while (j<this.longitud-1){ //O(|ListaEnlazada|)
            cadena.append(aux.dato);                    //O(1)
            cadena.append(',');                       //O(1)
            cadena.append(' ');                       //O(1)
            aux=aux.sig;                                //O(1)
            j++;                                        //O(1)
        }
        cadena.append(aux.dato);                        //O(1)
        cadena.append(']');                           //O(1)
        return cadena.toString();                       //O(1)
    } //O(|ListaEnlazada|)

    private class ListaIterador {
    	private int dedo;
        private ListaEnlazada<T> l;

        public ListaIterador(ListaEnlazada<T> lista){
            dedo = 0; //O(1)
            l = lista; //O(1)
        } //O(1)

        public boolean haySiguiente() {
            return dedo != l.longitud;  //O(1)
        }//O(1)
        
        public boolean hayAnterior() {
            return dedo != 0;   //O(1)
        }//O(1)

        public T siguiente(){
            dedo++; //O(1)
            return l.obtener(dedo-1); //O(|ListaEnlazada|)
        }//O(|ListaEnlazada|)
        

        public T anterior() {
	        dedo --; //O(1)
            return l.obtener(dedo); //O(|ListaEnlazada|)
        } //O(|ListaEnlazada|)
    }

    public ListaIterador iterador() {
        return new ListaIterador(this); //O(1)
    } //O(1)


}

