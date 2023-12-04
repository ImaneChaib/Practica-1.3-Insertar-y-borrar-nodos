/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyCompany;

import org.w3c.dom.*;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;
import java.io.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class AccesoDOM {
    
    Document doc;
    
    public int abrirXMLaDOM (File file){
        try{
            System.out.println("Abriendo archivo XML file y generando DOM....");

            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

           
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            
            
            DocumentBuilder builder=factory.newDocumentBuilder();
            doc=builder.parse(file);
            
            
            System.out.println("DOM creado con éxito.\n");
            return 0;
        }catch(Exception ex){
            System.out.println("Error" +ex);
            ex.printStackTrace();
            return -1;
        }
    }
    
    public void recorreDOMyMuestra(){
        String[] data = new String[3]; 
        Node node = null;
        Node root = doc.getFirstChild();
        NodeList libros = root.getChildNodes(); 
        
        for(int i=0;i<libros.getLength();i++){
            node = libros.item(i); 
            if (node.getNodeType() == Node.ELEMENT_NODE){ 
                Node tempnode = null;
                int count = 1;
                
                data[0] = node.getAttributes().item(0).getNodeValue();
                
                NodeList titulos_autores = node.getChildNodes();
                for (int j=0;j<titulos_autores.getLength();j++){
                    tempnode = titulos_autores.item(j);
                    if (tempnode.getNodeType() == Node.ELEMENT_NODE){
                        
                        data[count] = tempnode.getTextContent();
                        
                        count++;
                    }
                }
               
                System.out.println(data[0] + "--" + data[2] + "--" + data[1]);
            }
        }
        System.out.println("");
    }
    
    public int insertarLibroEnDOM(String titulo, String autor, String publicado){
        System.out.println("Añadiendo libro al DOM con los datos: "+titulo
            +"; "+autor+"; "+publicado);
        try{
            
            Node nodeTitulo = doc.createElement("Titulo"); 
            Node nodeTitulo_text = doc.createTextNode(titulo); 
            nodeTitulo.appendChild(nodeTitulo_text);
           
            Node nodeAutor = doc.createElement("Autor");
            Node nodeAutor_text = doc.createTextNode(autor);
            nodeAutor.appendChild(nodeAutor_text);
           
            Node nodeLibro = doc.createElement("Libro");
            ((Element)nodeLibro).setAttribute("publicado", publicado);
            nodeLibro.appendChild(nodeTitulo);
            nodeLibro.appendChild(nodeAutor);
            
            
            nodeLibro.appendChild(doc.createTextNode("\n"));
            Node root = doc.getFirstChild();
            root.appendChild(nodeLibro);
            System.out.println("Libro agregado exitosamente.\n");
            return 0;
            
        } catch (Exception ex){
            System.out.println("Error: " +ex);
            ex.printStackTrace();
            return -1;
        }
    }
    
    public int borrarLibro(String titulo){
        System.out.println("Se va a borrar el libro " + titulo + "...");
        try {
           
            NodeList nlTitulo = doc.getElementsByTagName("Titulo"); 
            Node node;
            for (int i=0;i<nlTitulo.getLength();i++){
                node = nlTitulo.item(i);
                
                if(node.getChildNodes().item(0).getNodeValue().equals(titulo)){
                    System.out.println("Borrando libro con título: " +titulo+ "...");
                   
                    node.getParentNode().getParentNode().removeChild(node.getParentNode());
                }
            }
            System.out.println("Libro borrado.\n");
            return 0;
        } catch (Exception ex){
            System.out.println("Error: " +ex);
            ex.printStackTrace();
            return -1;
        }
    }
    
    public void guardarDOMaArchivo (String archivo) {
        try{
            Source source = new DOMSource(doc); 
            StreamResult result = new StreamResult (new File(archivo)); 
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, (javax.xml.transform.Result) result);
            
            System.out.println("Archivo creado con éxito.");
        } catch (Exception ex) {
            System.out.println("Error: "+ex);
            ex.printStackTrace();
        }
    }
    
}
