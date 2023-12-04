
package MyCompany;

import java.io.File;


public class Main {

 
    public static void main(String[] args) {
        AccesoDOM acceso = new AccesoDOM();
        File file = new File("./Libros.xml");
        acceso.abrirXMLaDOM(file);
        acceso.recorreDOMyMuestra();
        acceso.insertarLibroEnDOM("Astérix & Obélix", "Uderzo", "1959");
        acceso.recorreDOMyMuestra();
        acceso.borrarLibro("Don Quijote");
        acceso.recorreDOMyMuestra();
        acceso.guardarDOMaArchivo("./LibrosDOM.xml");
    }
    
}
