package aed;

public class Materia {
    private int[] docentes;
    private int cantidadEstudiantes;
    private ListaEnlazada<String> alumnos;
    private InfoMateria otrosNombres;  //(nombredeltrie,nombremateria)

    public Materia(InfoMateria info){
        this.docentes= new int[4];
        this.cantidadEstudiantes=0;
        this.alumnos= new ListaEnlazada<>();
        this.otrosNombres= info;
    }

    public void agregarAlumno(String alumno){
        this.alumnos.agregarAtras(alumno);
        this.cantidadEstudiantes++;
    }



    public void agregarDocente(String cargoDocente){
        if (cargoDocente=="PROF"){
            this.docentes[0] ++;
        }
        if (cargoDocente=="JTP"){
            this.docentes[1] ++;
        }
        if (cargoDocente=="AY1"){
            this.docentes[2] ++;
        }
        if (cargoDocente=="AY2"){
            this.docentes[3] ++;
        }

    }

    public InfoMateria obtenerNombres(){
        return otrosNombres;
    }

    public int obtenerCantidad(){
        return cantidadEstudiantes;
    }

    public boolean excede(){
        if(docentes[0]*250<cantidadEstudiantes){
            return false;
        }
        if(docentes[1]*100<cantidadEstudiantes){
            return false;
        }
        if(docentes[2]*20<cantidadEstudiantes){
            return false;
        }
        if(docentes[4]*30<cantidadEstudiantes){
            return false;
        }
        return true;
    
    }


    
}

