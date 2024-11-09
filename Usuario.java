// Clase Usuario
public class Usuario {
    private String nombre;
    private Rol rol; // "EMPLEADO", "GERENTE" o "ADMINISTRADOR"
    private Grupo grupo; // Grupo de trabajo del usuario (GRUPO_A, GRUPO_B, etc.)

    public Usuario(String nombre, Rol rol, Grupo grupo) {
        this.nombre = nombre;
        this.rol = rol;
        this.grupo = grupo;
    }

    public Rol getRol() {
        return rol;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public String getNombre() {
        return nombre;
    }

    // Métodos para actualizar rol y grupo
    public void setRol(Rol nuevoRol) {
        this.rol = nuevoRol;
    }

    public void setGrupo(Grupo nuevoGrupo) {
        this.grupo = nuevoGrupo;
    }
}
