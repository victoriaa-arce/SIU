package aed;

import java.util.*;
//InvRep : El valor de la refererencia al anterior al primero es nulo,y el valor de la referencia al siguiente al último tambien lo es.
//No existen ciclos dentro de la lista.
//No existe ningún nodo nulo.

public class ListaEnlazada<T> {
    private Nodo primero;
    private Nodo ultimo;
    private int longitud;

    private class Nodo {
        private T dato;
        private Nodo sig;
        private Nodo ant;

        public Nodo (T datos){
            this.dato=datos;
            this.sig=null;
            this.ant=null;
        }
    }


    public ListaEnlazada() {
        this.primero=null;
        this.ultimo=null;
        this.longitud=0;
    }

    public int longitud() {
        return this.longitud;
    }

    public void agregarAdelante(T elem) {
        Nodo nue= new Nodo(elem);
        if (this.primero==null){
            this.primero=nue;
            this.ultimo=nue;
        }
        this.primero.ant=nue;
        nue.sig=this.primero;
        this.primero=nue;
        this.longitud ++;
    }

    public void agregarAtras(T elem) {
        Nodo nue= new Nodo(elem);
        if (this.ultimo==null){
            this.primero=nue;
            this.ultimo=nue;
        }
        this.ultimo.sig=nue;
        nue.ant=this.ultimo;
        this.ultimo=nue;
        this.longitud ++;
    }

    public T obtener(int i) {
        Nodo aux = this.primero;
        for (int j=0; j<i;j++){
            aux=aux.sig;
        }
        return aux.dato;
         
    }

    public void eliminar(int i) {
        Nodo eliminar = this.primero;
        for (int j=0; j<i;j++){
            eliminar=eliminar.sig;
        }
        if (eliminar==this.primero){
            this.primero=this.primero.sig;
        
        }else {
            if (eliminar==this.ultimo){
                this.ultimo=this.ultimo.ant;
            }else{
                eliminar.ant.sig=eliminar.sig;
                eliminar.sig.ant=eliminar.ant;
            }
        }
        longitud--;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo aux = this.primero;
        for (int j=0; j<indice;j++){
            aux=aux.sig;
        }
        aux.dato=elem;
    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> nue= new ListaEnlazada<T>();
        Nodo aux = this.primero;
        for (int j=0; j<this.longitud;j++){
            nue.agregarAtras(aux.dato);
            aux=aux.sig;
        }
        return nue;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        ListaEnlazada<T> copia= lista.copiar();
        this.longitud=copia.longitud;
        this.primero=copia.primero;
        this.ultimo=copia.ultimo;
    }
    
    @Override
    public String toString() {
        Nodo aux= this.primero;
        StringBuffer cadena= new StringBuffer();
        cadena.append('[');
        int j=0;
        while (j<this.longitud-1){
            cadena.append(aux.dato);
            cadena.append(',');
            cadena.append(' ');
            aux=aux.sig;
            j++;
        }
        cadena.append(aux.dato);
        cadena.append(']');
        return cadena.toString();
    }

    private class ListaIterador {
    	private int dedo;
        private ListaEnlazada<T> l;

        public ListaIterador(ListaEnlazada<T> lista){
            dedo = 0; 
            l = lista;
        }

        public boolean haySiguiente() {
            return dedo != l.longitud;
        }
        
        public boolean hayAnterior() {
            return dedo != 0;
        }

        public T siguiente(){
            dedo++;
            return l.obtener(dedo-1);
        }
        

        public T anterior() {
	        dedo --;
            return l.obtener(dedo);
        }
    }

    public ListaIterador iterador() {
        return new ListaIterador(this);
    }


}

