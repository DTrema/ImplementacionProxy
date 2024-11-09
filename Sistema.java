import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Sistema {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Documento> documentos = new ArrayList<>();

    public void registrarUsuario(String nombre, Rol rol, Grupo grupo){
        Usuario usuario = new Usuario(nombre, rol, grupo);
        usuarios.add(usuario);
        System.out.println("Usuario registrado: " + nombre + " - Rol: " + rol + " - Grupo: " + grupo);
    }

    public void agregarDocumento(String titulo, boolean esConfidencial, Grupo grupo, Usuario usuario){
        if(usuario.getRol() == Rol.ADMINISTRADOR){
            DocumentoReal doc = new DocumentoReal(titulo, esConfidencial, grupo);
            DocumentoProxy proxy = new DocumentoProxy(titulo, usuario.getRol(), grupo, esConfidencial);
            documentos.add(proxy);
            System.out.println("Documento agregado: " + titulo + " - Condifencial: " + esConfidencial + " - Grupo: " + grupo);
        } else {
            System.out.println("Acceso denegado: solo los administradores pueden agregar documentos.");
        }
    }

    public Optional<Usuario> buscarUsuario(String nombre){
        return usuarios.stream().filter(u -> u.getNombre().equals(nombre)).findFirst();
    }

    public void accederDocumento(String titulo, Usuario usuario){
        for(Documento doc : documentos){
            if(doc.getTitulo().equals(titulo)){
                doc.cargar();
                doc.mostrar();
                return;
            }
        }
        System.out.println("Documento no encontrado: " + titulo);
    }

    public void listarDocumentos(){
        System.out.println("Documentos dispobibles");
        for(Documento doc : documentos){
            System.out.println("- " + doc.getTitulo());
        }
    }
}
