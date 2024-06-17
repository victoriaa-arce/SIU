package aed;

import java.util.ArrayList;

public class SistemaSIU {
    private Trie<Integer> alumnos;
    private Trie<Trie <Materia>> carreras;



    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){

        //Creamos el dicionario de alumnos

        this.alumnos= new Trie<>();
        for(int i=0;i<libretasUniversitarias.length;i++){  
            //inserto alumno al trie con valor 0 (materias anotadas)
            alumnos.insertar(libretasUniversitarias[i], 0);    
        }

        //Creamos el diccionario de carreras con sus materias
        this.carreras= new Trie<>();
        for(int i=0; i<infoMaterias.length;i++){  //recorro la lista [[(carrera,materia),(carrera2,materia)][(,),(,)]] cada loop es una materia
            ParCarreraMateria[] paresCarrerasMaterias=infoMaterias[i].getParesCarreraMateria();
            Materia DatosDeLaMateria= new Materia(paresCarrerasMaterias);
            for (int j=0;j<paresCarrerasMaterias.length;j++){     //recorro cada tupla
                String carrera= paresCarrerasMaterias[j].getCarrera();  //obtengo la carrera
                String materia=paresCarrerasMaterias[j].getNombreMateria();  //obtengo el nombre de la materia
                 

                if(!carreras.definido(carrera)){     //si la carrera no existe
                    Trie <Materia> materiasDeLaCarrera = new Trie<>();    //creo su trie de materias  
                    materiasDeLaCarrera.insertar(materia,DatosDeLaMateria);  //le inserto la materia con sus datos
                    carreras.insertar(carrera, materiasDeLaCarrera);      // inserto el trie de materias de la carrera 

                } else{
                    carreras.obtener(carrera).insertar(materia, DatosDeLaMateria);
                }


            }


        }
        
        	    
    }



    public void inscribir(String estudiante, String carrera, String materia){

        //Anoto al alumno en la materia
        Materia informacion=carreras.obtener(carrera).obtener(materia);
        informacion.agregarAlumno(estudiante);

        //Le agrego al alumno la materia esta bien? NO FUNCIONA
        
        int cantidadMateriasDelAlumno=alumnos.obtener(estudiante);
        alumnos.insertar(estudiante,cantidadMateriasDelAlumno++);

    }


    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        Materia informacion=carreras.obtener(carrera).obtener(materia);
        informacion.agregarDocente(cargo.name());
    }

    public int[] plantelDocente(String materia, String carrera){
        Materia informacion=carreras.obtener(carrera).obtener(materia);
        return informacion.obtenerDocentes();
        
    }

    public void cerrarMateria(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int inscriptos(String materia, String carrera){
        Materia informacion=carreras.obtener(carrera).obtener(materia);
        return informacion.obtenerCantidad();
           
    }

    public boolean excedeCupo(String materia, String carrera){
        Materia informacion=carreras.obtener(carrera).obtener(materia);
        return informacion.excede();

    }

    public String[] carreras(){
        throw new UnsupportedOperationException("Método no implementado aún");   
    }

    public String[] materias(String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int materiasInscriptas(String estudiante){
       int cantidadInscriptas=alumnos.obtener(estudiante);	    
       return cantidadInscriptas;
    }
}
