public class DocumentoProxy implements Documento{
    private DocumentoReal documento;
    private String nombreArchivo;
    private Rol rolUsuario;
    private Grupo grupoUsuario;

    public DocumentoProxy(String nombreArchivo, Rol rolUsuario, Grupo grupoUsuario, boolean esConfidencial){
        this.nombreArchivo = nombreArchivo;
        this.rolUsuario = rolUsuario;
        this.grupoUsuario = grupoUsuario;
        if(tienePermiso()){
            this.documento = new DocumentoReal(nombreArchivo, esConfidencial, grupoUsuario);
        }
    }

    private boolean tienePermiso(){
        if(rolUsuario == Rol.ADMINISTRADOR){
            return true;
        } else if(rolUsuario == Rol.GERENTE && documento != null){
            return nombreArchivo.contains(grupoUsuario.name());
        } else if (rolUsuario == Rol.EMPLEADO && documento != null && !documento.esConfidencial()){
            return nombreArchivo.contains(grupoUsuario.name());
        }
        return false;
    }

    @Override
    public void cargar(){
        if(tienePermiso()){
            if(documento == null) documento = new DocumentoReal(nombreArchivo, true, grupoUsuario);
            documento.cargar();
        } else {
            System.out.println("Acceso Denegado: Permisos insuficientes");
        }
    }

    @Override
    public void mostrar(){
        if(tienePermiso()){
            documento.mostrar();
        } else {
            System.out.println("Acceso Denegado: Permisos Insuficientes");
        }
    }

    @Override
    public String getTitulo() {
        return documento != null ? documento.getTitulo() : nombreArchivo;
    }

    // @Override
    // public void modificarContenido(String nuevoContenido) {
    //     if(rolUsuario == Rol.ADMINISTRADOR){
    //         if(documento != null){
    //             documento.modificarContenido(nuevoContenido);
    //         }
    //     } else {
    //         System.out.println("Acceso Denegado: Solo los administradores pueden modificar el contenido de los documentos.");
    //     }
    // }
}
