package aed;

import java.util.ArrayList;

import aed.SistemaSIU.CargoDocente;

public class Materia {
    private int[] docentes;
    private ListaEnlazada<String> alumnos;
    private ParCarreraMateria[] otrosNombres;  //(nombredeltrie,nombremateria)
    private ArrayList<Trie<Materia>> referencias; 


    public Materia(ParCarreraMateria[] info){
        this.docentes= new int[4]; //O(1)
        this.alumnos= new ListaEnlazada<>(); //O(1)
        this.otrosNombres= info; //O(1)
        this.referencias = new ArrayList<Trie<Materia>>(); // O(1)

    } //O(1)

    public void agregarReferencia(Trie<Materia> referencia){
        referencias.add(referencia); // O(1)
    }
    public ListaEnlazada<String> obtenerlistaAlumnos(){
        return alumnos; //O(1)
    } 

    
    public ArrayList<Trie<Materia>> darReferencia(){
        return referencias; // O(1)
    }
    public void agregarAlumno(String alumno){
        this.alumnos.agregarAtras(alumno); //O(1)
        this.alumnos.longitud(); //O(1)
    }


    public void agregarDocente(String cargo){
        if (cargo=="PROF"){ //O(1)
            this.docentes[0] ++; //O(1)
        }
        if (cargo=="JTP"){ //O(1)
            this.docentes[1] ++; //O(1)
        }
        if (cargo=="AY1"){ //O(1)
            this.docentes[2] ++; //O(1)
        }
        if (cargo=="AY2"){ //O(1)
            this.docentes[3] ++; //O(1)
        }
        //O(1)

    }

    public int[] obtenerDocentes(){
        return this.docentes; //O(1)
    }


    public ParCarreraMateria[] obtenerNombres(){
        return otrosNombres; //O(1)
    }

    public int obtenerCantidad(){
        return this.alumnos.longitud(); //O(1)
    }

    public boolean excede(){
        if(docentes[0]*250<obtenerCantidad()){ // O(1) + O(1) = O(1)
            return true; //O(1)
        }
        if(docentes[1]*100<obtenerCantidad()){ // O(1) + O(1) = O(1)
            return true; //O(1)
        }
        if(docentes[2]*20<obtenerCantidad()){ // O(1) + O(1) = O(1)
            return true; //O(1)
        }
        if(docentes[3]*30<obtenerCantidad()){ // O(1) + O(1) = O(1)
            return true; //O(1)
        }
        return false; //O(1)
    
    }

    


    
}

