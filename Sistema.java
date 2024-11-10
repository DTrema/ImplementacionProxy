import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Sistema {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<DocumentoProxy> documentos = new ArrayList<>();

    public void registrarUsuario(String nombre, Rol rol, Grupo grupo) {
        if (nombre == null || nombre.isEmpty()) {
            System.out.println("Error: El nombre del usuario no puede estar vacío.");
            return;
        }
        if (rol == null || grupo == null) {
            System.out.println("Error: Rol y grupo no pueden ser nulos.");
            return;
        }
        // Verificar si el usuario ya existe
        if (usuarios.stream().anyMatch(u -> u.getNombre().equals(nombre))) {
            System.out.println("Error: El usuario con nombre '" + nombre + "' ya está registrado.");
            return;
        }
        Usuario usuario = new Usuario(nombre, rol, grupo);
        usuarios.add(usuario);
        System.out.println("Usuario registrado: " + nombre + " - Rol: " + rol + " - Grupo: " + grupo);
    }

    public void agregarDocumento(String titulo, boolean esConfidencial, Grupo grupo, Usuario usuario) {
        if (titulo == null || titulo.isEmpty()) {
            System.out.println("Error: El título del documento no puede estar vacío.");
            return;
        }
        if (usuario == null || grupo == null) {
            System.out.println("Error: Usuario y grupo no pueden ser nulos.");
            return;
        }
        if (usuario.getRol() != Rol.ADMINISTRADOR) {
            System.out.println("Acceso denegado: solo los administradores pueden agregar documentos.");
            return;
        }
        // Verificar si el documento ya existe
        if (documentos.stream().anyMatch(doc -> doc.getTitulo().equals(titulo + ".txt"))) {
            System.out.println("Error: El documento con título '" + titulo + "' ya existe.");
            return;
        }
        DocumentoProxy proxy = new DocumentoProxy(titulo + ".txt", usuario.getRol(), grupo, esConfidencial);
        documentos.add(proxy);
        System.out.println("Documento agregado: " + titulo + " - Confidencial: " + esConfidencial + " - Grupo: " + grupo);
    }

    public Optional<Usuario> buscarUsuario(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            System.out.println("Error: El nombre del usuario no puede estar vacío.");
            return Optional.empty();
        }
        return usuarios.stream().filter(u -> u.getNombre().equals(nombre)).findFirst();
    }

    public void accederDocumento(String titulo, Usuario usuario) {
        if (titulo == null || titulo.isEmpty()) {
            System.out.println("Error: El título del documento no puede estar vacío.");
            return;
        }
        if (usuario == null) {
            System.out.println("Error: Usuario no puede ser nulo.");
            return;
        }
        DocumentoProxy documento = documentos.stream()
                .filter(doc -> doc.getTitulo().equals(titulo))
                .findFirst()
                .orElse(null);

        if (documento == null) {
            System.out.println("Documento no encontrado: " + titulo);
            return;
        }
        if (usuario.getRol() != Rol.ADMINISTRADOR && usuario.getGrupo() != documento.getGrupo()) {
            System.out.println("Acceso denegado: este documento es del grupo " + documento.getGrupo());
            return;
        }
        documento.cargar();
        documento.mostrar();
    }

    public void listarDocumentos() {
        if (documentos.isEmpty()) {
            System.out.println("No hay documentos disponibles.");
            return;
        }
        System.out.println("\nDocumentos disponibles:");
        for (DocumentoProxy doc : documentos) {
            System.out.println("- " + doc.getTitulo() + " (Grupo: " + doc.getGrupo().name() + ")");
        }
    }

    public void listaUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        System.out.println("\nUsuarios registrados:");
        for (Usuario usuario : usuarios) {
            System.out.println("- " + usuario.getNombre() + " (Rol: " + usuario.getRol() + ", Grupo: " + usuario.getGrupo() + ")");
        }
    }
}

