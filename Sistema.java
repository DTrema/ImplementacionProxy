import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Sistema {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<DocumentoProxy> documentos = new ArrayList<>();

    public void registrarUsuario(String nombre, Rol rol, Grupo grupo){
        Usuario usuario = new Usuario(nombre, rol, grupo);
        usuarios.add(usuario);
        System.out.println("Usuario registrado: " + nombre + " - Rol: " + rol + " - Grupo: " + grupo);
    }

    public void agregarDocumento(String titulo, boolean esConfidencial, Grupo grupo, Usuario usuario){
        if(usuario.getRol() == Rol.ADMINISTRADOR){
            DocumentoProxy proxy = new DocumentoProxy(titulo + ".txt", usuario.getRol(), grupo, esConfidencial);
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
        for(DocumentoProxy doc : documentos){
            if(doc.getTitulo().equals(titulo)){
                if(usuario.getRol() != Rol.ADMINISTRADOR && usuario.getGrupo() != doc.getGrupo()) {
                    System.out.println("Acceso denegado, este documento es del grupo " + doc.getGrupo());
                    return;
                }
                doc.cargar();
                doc.mostrar();
                return;
            }
        }
        System.out.println("Documento no encontrado: " + titulo);
    }

    public void listarDocumentos(){
        System.out.println("\nDocumentos dispobibles");
        for(DocumentoProxy doc : documentos){
            System.out.println("- " + doc.getTitulo() + ", " + doc.getGrupo().name());
        }
    }

    public void listaUsuarios(){
        System.out.println("\nUsuarios");
        for(Usuario usuario : usuarios){
            System.out.println("- " + usuario.getNombre());
        }
    }
}
