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

        this.alumnos= new Trie<>(); //O(1)
        for(int i=0;i<libretasUniversitarias.length;i++){  //O(|libretasUniversitarias|)
            //inserto alumno al trie con valor 0 (materias anotadas)
            alumnos.insertar(libretasUniversitarias[i], 0);   //O(|LU|)
        } //(O(|libretasUniversitarias|)*O(|LU|))

        //Creamos el diccionario de carreras con sus materias
        this.carreras= new Trie<>(); //O(1)
        for(int i=0; i<infoMaterias.length;i++){  //recorro la lista [[(carrera,materia),(carrera2,materia)][(,),(,)]] cada loop es una materia O(|infoMaterias|)
            ParCarreraMateria[] paresCarrerasMaterias=infoMaterias[i].getParesCarreraMateria(); //FALTA
            Materia DatosDeLaMateria= new Materia(paresCarrerasMaterias); //O(1)
            for (int j=0;j<paresCarrerasMaterias.length;j++){     //recorro cada tupla O(|paresCarrerasMaterias|)
                String carrera= paresCarrerasMaterias[j].getCarrera();  //obtengo la carrera O(1)+O(1)=O(1)
                String materia=paresCarrerasMaterias[j].getNombreMateria();  //obtengo el nombre de la materia O(1)+O(1)=O(1)
                 

                if(!carreras.definido(carrera)){     //si la carrera no existe O(|carrera|)
                    Trie <Materia> materiasDeLaCarrera = new Trie<>();    //creo su trie de materias O(1)
                    materiasDeLaCarrera.insertar(materia,DatosDeLaMateria);  //le inserto la materia con sus datos O(|materia|) 
                    carreras.insertar(carrera, materiasDeLaCarrera);      // inserto el trie de materias de la carrera O(|carrera|)
                //este if tiene complejidad O(|carrera|)+ O(1)+O(|materia|)+O(|carrera|) = O(|materia|)+O(|carrera|)
                } else{
                    carreras.obtener(carrera).insertar(materia, DatosDeLaMateria); //O(|carrera|)+O(|materia|)
                } 


            } //O(|paresCarrerasMaterias|)*(O(1)+O(1)+O(|carrera|)+O(|materia|)) = O(|paresCarrerasMaterias|)*(O(|carrera|)+O(|materia|))


        } //O(|infoMaterias|)*(O(|paresCarrerasMaterias|)*(O(|carrera|)+O(|materia|))
        
        	    
    } //(O(|libretasUniversitarias|)*O(|LU|))+ O(|infoMaterias|)*(O(|paresCarrerasMaterias|)*(O(|carrera|)+O(|materia|))
 //comentario: revise esta complejidad con la que nos piden y todo lo que es materias y carreras da igual,pero tengo duda con el de libretas universitarias*lu 


    public void inscribir(String estudiante, String carrera, String materia){

        //Anoto al alumno en la materia
        Materia informacion=carreras.obtener(carrera).obtener(materia); //O(|carrera|)+O(|materia|)
        informacion.agregarAlumno(estudiante); 

        //Le agrego al alumno la materia esta bien? NO FUNCIONA
        //vcomentario: capaz no es necesario hacer eso y solo podriamos incrementar el valor del alumno en el trie y sumar un inscripto a la materia
        //otro comentario: si hacemos lo que esta a continuacion la complejidad no se va a cumplir pq tendriamos O(|carrera|)+O(|materia|)+O(|alumnos|)
        int cantidadMateriasDelAlumno=alumnos.obtener(estudiante);
        alumnos.insertar(estudiante,cantidadMateriasDelAlumno++);

    }


    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        Materia informacion=carreras.obtener(carrera).obtener(materia); //O(|carrera|)+O(|materia|)
        informacion.agregarDocente(cargo.name()); //O(1)
    }

    public int[] plantelDocente(String materia, String carrera){
        Materia informacion=carreras.obtener(carrera).obtener(materia); //O(|carrera|)+O(|materia|)
        return informacion.obtenerDocentes(); //O(1)
        
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
       int cantidadInscriptas=alumnos.obtener(estudiante);	//O(|estudiante|) la complejidad no da
       return cantidadInscriptas;
    }
}
