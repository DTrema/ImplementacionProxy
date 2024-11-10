import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DocumentoReal implements Documento{
    private String titulo;
    private boolean esConfidencial;
    private Grupo grupo;
    private String contenido;

    public DocumentoReal(String titulo, boolean esConfidencial, Grupo grupo){
        this.titulo = titulo;
        this.esConfidencial = esConfidencial;
        this.grupo = grupo;
        // Inicialmente vacío
        this.contenido = "";
        // Carha el documento desde el disco en la creación
        cargarDesdeDisco(titulo);
    }

    private void cargarDesdeDisco(String nombreArchivo){
        System.out.println("Cargando " + nombreArchivo + " desde disco.");
        StringBuilder contenidoBuilder = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(titulo))){
            String linea;
            while((linea = br.readLine()) != null){
                contenidoBuilder.append(linea).append("\n");
            }
        } catch (IOException e){
            System.out.println("Error al cargar el documento: " + e.getMessage());
        }
        this.contenido = contenidoBuilder.toString();
    }

    @Override
    public void cargar(){
        // Se cargó en el constructor
    }

    @Override
    public void mostrar(){
        System.out.println("Mostrando " + titulo + ": " + contenido);
    }

    @Override
    public String getTitulo(){
        return titulo;
    }

    @Override
    public Grupo getGrupo(){
        return this.grupo;
    }

    public boolean esConfidencial(){
        return esConfidencial;
    }
}