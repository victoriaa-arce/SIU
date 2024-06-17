package aed;

import aed.SistemaSIU.CargoDocente;

public class Materia {
    private int[] docentes;
    private ListaEnlazada<String> alumnos;
    private ParCarreraMateria[] otrosNombres;  //(nombredeltrie,nombremateria)


    public Materia(ParCarreraMateria[] info){
        this.docentes= new int[4];
        this.alumnos= new ListaEnlazada<>();
        this.otrosNombres= info;
    }

    public void agregarAlumno(String alumno){
        this.alumnos.agregarAtras(alumno);
        this.alumnos.longitud();
    }


    public void agregarDocente(String cargo){
        if (cargo=="PROF"){
            this.docentes[0] ++;
        }
        if (cargo=="JTP"){
            this.docentes[1] ++;
        }
        if (cargo=="AY1"){
            this.docentes[2] ++;
        }
        if (cargo=="AY2"){
            this.docentes[3] ++;
        }

    }

    public int[] obtenerDocentes(){
        return this.docentes;
    }


    public ParCarreraMateria[] obtenerNombres(){
        return otrosNombres;
    }

    public int obtenerCantidad(){
        return this.alumnos.longitud();
    }

    public boolean excede(){
        if(docentes[0]*250<obtenerCantidad()){
            return true;
        }
        if(docentes[1]*100<obtenerCantidad()){
            return true;
        }
        if(docentes[2]*20<obtenerCantidad()){
            return true;
        }
        if(docentes[3]*30<obtenerCantidad()){
            return true;
        }
        return false;
    
    }

    


    
}

