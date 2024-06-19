package aed;

import java.util.ArrayList;
/* InvRep: La cantidad de apariciones en las distintas materias de cada alumno será igual al valor asociado con ese alumno en el Trie.

*/
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

        //Creamos el diccionario de alumnos

        this.alumnos= new Trie<>(); //O(1)
        for(int i=0;i<libretasUniversitarias.length;i++){  //O(|libretasUniversitarias|)
            //inserto alumno al trie con valor 0 (materias anotadas)
            alumnos.insertar(libretasUniversitarias[i], 0);   //O(|LU|)
        } //(O(|libretasUniversitarias|)*O(|LU|)),y como |LU| está acotado,O(|LU|) = O(1),luego la complejidad es O(|libretasUniversitarias|)

        //Creamos el diccionario de carreras con sus materias
        this.carreras= new Trie<>(); //O(1)
        for(int i=0; i<infoMaterias.length;i++){  //recorro la lista [[(carrera,materia),(carrera2,materia)][(,),(,)]] cada loop es una materia O(|infoMaterias|)
            ParCarreraMateria[] paresCarrerasMaterias=infoMaterias[i].getParesCarreraMateria(); //O(1)
            Materia DatosDeLaMateria= new Materia(paresCarrerasMaterias); //O(1)
            for (int j=0;j<paresCarrerasMaterias.length;j++){     //recorro cada tupla O(|paresCarrerasMaterias|)
                String carrera= paresCarrerasMaterias[j].getCarrera();  //obtengo la carrera O(1)+O(1)=O(1)
                String materia=paresCarrerasMaterias[j].getNombreMateria();  //obtengo el nombre de la materia O(1)+O(1)=O(1)

                if(!carreras.definido(carrera)){     //si la carrera no existe O(|carrera|)
                    Trie <Materia> materiasDeLaCarrera = new Trie<>();    //creo su trie de materias O(1)
                    DatosDeLaMateria.agregarReferencia(materiasDeLaCarrera); //Agrego una referencia de este trie de materias a la materia
                    //Esto lo hacemos para tener almacenadas en cada materia,las referencias del Trie que contiene la misma materia pero con distinto nombre,esto nos va a servir si cierra la materia.
                    materiasDeLaCarrera.insertar(materia,DatosDeLaMateria);  //le inserto la materia con sus datos O(|materia|) 
                    carreras.insertar(carrera, materiasDeLaCarrera);      // inserto el trie de materias de la carrera O(|carrera|)
                    
                //este if tiene complejidad O(|carrera|)+ O(1)+O(|materia|)+O(|carrera|) = O(|materia|)+O(|carrera|)
                } else{
                    carreras.obtener(carrera).insertar(materia, DatosDeLaMateria); //O(|carrera|)+O(|materia|)
                    DatosDeLaMateria.agregarReferencia(carreras.obtener(carrera)); //O(|carrera|)+O(1)= O(|carrera|)
                } //este else tiene complejidad O(|carrera|)+O(|materia|)


            } //O(|paresCarrerasMaterias|)*(O(1)+O(1)+O(|carrera|)+O(|materia|)) = O(|paresCarrerasMaterias|)*(O(|carrera|)+O(|materia|))


        } //O(|infoMaterias|)*(O(|paresCarrerasMaterias|)*(O(|carrera|)+O(|materia|))
        
        	    
    } //O(|libretasUniversitarias|)+ O(|infoMaterias|)*(O(|paresCarrerasMaterias|)*(O(|carrera|)+O(|materia|))
     


    public void inscribir(String estudiante, String carrera, String materia){

        //Anoto al alumno en la materia
        Materia informacion=carreras.obtener(carrera).obtener(materia); //O(|carrera|)+O(|materia|)
        informacion.agregarAlumno(estudiante); //O(1) Agrego el estudiante a la informacion de la materia.
        int cantidadMateriasDelAlumno=alumnos.obtener(estudiante); //obtengo la cantidad de materias que está cursando el estudiante O(|estudiante|)= O(|LU|)=O(1)
        cantidadMateriasDelAlumno ++ ; //le sumo una nueva materia O(1)
        alumnos.cambiarDefinicion(estudiante,cantidadMateriasDelAlumno); //O(|estudiante|)= O(|LU|)=O(1)
        

    } //O(|carrera|)+O(|materia|)


    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        Materia informacion=carreras.obtener(carrera).obtener(materia); //O(|carrera|)+O(|materia|)
        informacion.agregarDocente(cargo.name()); //O(1)
    } //O(|carrera|)+O(|materia|)

    public int[] plantelDocente(String materia, String carrera){
        Materia informacion=carreras.obtener(carrera).obtener(materia); //O(|carrera|)+O(|materia|)
        return informacion.obtenerDocentes(); //O(1)
        
    } //O(|carrera|)+O(|materia|)

    public void cerrarMateria(String materia, String carrera){
        Materia info = this.carreras.obtener(carrera).obtener(materia); //O(|materia|)+O(|carrera|)

        for(int i = 0; i < info.darReferencia().size(); i++){ // O(|info.referencias|)= O(CantidadDeNombresDeLaMateria)

            Trie<Materia> TrieAborrar = info.darReferencia().get(i); //O(1)
            TrieAborrar.borrar(info.obtenerNombres()[i].getNombreMateria()); //O(|NombredeLaMateriaABorrar|) 
        } // O(CantidadDeNombresDeLaMateria)* O(|NombredeLaMateriaABorrar|) 
        for (int i = 0; i <info.obtenerCantidad(); i++){  //O(cantidadDeAlumnosInscriptosALaMateria)
            String alumno = info.obtenerlistaAlumnos().obtener(i); // O(1)+O(CantidadDeAlumnosInscriptosALaMateria)
            int cantidadDeMaterias = alumnos.obtener(alumno); //O(|alumno|) = O(1)
            alumnos.cambiarDefinicion(alumno, cantidadDeMaterias - 1); //O(|alumno|) = O(1)

        } //O(CantidadDeAlumnosInscriptosALaMateria)
    }// O(|materia|)+O(|carrera|) + O(CantidadDeNombresDeLaMateria)* O(|NombredeLaMateriaABorrar|) + O(CantidadDeAlumnosInscriptosALaMateria)

    public int inscriptos(String materia, String carrera){
        Materia informacion=carreras.obtener(carrera).obtener(materia); //O(|carrera|)+O(|materia|)
        return informacion.obtenerCantidad(); // O(1)
           
    } //O(|carrera|)+O(|materia|)

    public boolean excedeCupo(String materia, String carrera){
        Materia informacion=carreras.obtener(carrera).obtener(materia); //O(|carrera|)+O(|materia|)
        return informacion.excede(); //O(1)

    } //O(|carrera|)+O(|materia|)

    public String[] carreras(){
        String[] carreraslista = new String[carreras.tamaño()]; //Creamos un arreglo con la longitud igual a la cantidad de carreras. O(1)
        this.carreras.iterador().palabras.toArray(carreraslista); //Recorremos el trie carreras con el iterador,y pasamos el recorrido de un ArrayList a un arreglo.
        //O(|carrera|) por cada carrera del Trie de Carreras.
        return carreraslista; //O(1)
    } //O(|carrera|) por cada carrera del Trie de Carreras.

    public String[] materias(String carrera){
        String[] materiaslista = new String[carreras.obtener(carrera).tamaño()]; //Creamos un arreglo de longitud igual a la cantidad de materias de la carrera.
        //O(1)+O(1)+O(|carrera|)
        this.carreras.obtener(carrera).iterador().palabras.toArray(materiaslista); //Buscamos en el Trie carreras la carrera,recorremos el Trie Materias de esa carrera con el iterador y pasamos el recorrido de un ArrayList a un arreglo.
        // O(|carrera|)+ Por cada materia del Trie Materias,O(|materia|)
        return materiaslista; //O(1)
    } // O(|carrera|)+ Por cada materia del Trie Materias,O(|materia|)

    public int materiasInscriptas(String estudiante){
       int cantidadInscriptas=alumnos.obtener(estudiante);	//A simple vista seria O(|estudiante|) pero como cada libreta universitaria tiene como maximo 6 caracteres,la complejidad es O(1)
       return cantidadInscriptas; //O(1)
    } //O(1)
}
