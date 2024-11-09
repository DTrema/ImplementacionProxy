import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        Scanner sc = new Scanner(System.in);
        
        //Registro inicial de algunos usuarios
        sistema.registrarUsuario("Luis", Rol.ADMINISTRADOR, Grupo.GRUPO_A);
        sistema.registrarUsuario("Marta", Rol.GERENTE, Grupo.GRUPO_B);
        sistema.registrarUsuario("Pedro", Rol.EMPLEADO, Grupo.GRUPO_C);

        Usuario usuarioActual = null;

        while(true){
            System.out.println("\n--- Sistema de Gestión de Documentos ---");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrar usuario (solo administrador)");
            System.out.println("3. Agregar documento (solo administrador)");
            System.out.println("4. Acceder a documento");
            System.out.println("5. Salir");
            System.out.println("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese su nombre: ");
                    String nombre = sc.nextLine();
                    usuarioActual = sistema.buscarUsuario(nombre).orElse(null);
                    if(usuarioActual != null){
                        System.out.println("Bienvenido, " + nombre + ". Su rol es " + usuarioActual.getRol());
                    } else {
                        System.out.println("Usuario no encontrado");
                    }
                    break;
                case 2:
                    if(usuarioActual != null && usuarioActual.getRol() == Rol.ADMINISTRADOR){
                        System.out.println("Nombre del nuevo usuario: ");
                        String nuevoNOmbre = sc.nextLine();
                        System.out.println("Rol del nuevo usuario (EMPLEADO, GERENTE, ADMINISTRADOR): ");
                        Rol nuevoROl = Rol.valueOf(sc.nextLine().toUpperCase());
                        System.out.println("Grupo del nuevo usuario (GRUPO_A, GRUPO_B, GRUPO_C, GRUPO_D): ");
                        Grupo nuevoGrupo = Grupo.valueOf(sc.nextLine().toUpperCase());
                        sistema.registrarUsuario(nuevoNOmbre, nuevoROl, nuevoGrupo);
                    } else {
                        System.out.println("Acceso denegado. Solo los administradores pueden registar usuarios.");
                    }
                    break;
                    
                case 3:
                    if(usuarioActual != null && usuarioActual.getRol() == Rol.ADMINISTRADOR){
                        System.out.println("Nombre del nuevo documento: ");
                        String nuevoDoc = sc.nextLine();
                        System.out.println("¿Es confidencial (true / false): ");
                        boolean esConfidencial = sc.nextBoolean();
                        sc.nextLine();
                        System.out.println("Grupo del documento (GRUPO_A, GRUPO_B, GRUPO_C, GRUPO_D): ");
                        Grupo grupoDoc = Grupo.valueOf(sc.nextLine().toUpperCase());
                        sistema.agregarDocumento(nuevoDoc, esConfidencial, grupoDoc, usuarioActual);
                    } else {
                        System.out.println("Acceso denegado. Solo los administradores pueden agregar documentos");
                    }
                    break;

                case 4:
                if(usuarioActual != null){
                    sistema.listarDocumentos();
                    System.out.println("Nombre del documento a acceder: ");
                    String nombreAcceso = sc.nextLine();
                    sistema.accederDocumento(nombreAcceso, usuarioActual);
                } else {
                    System.out.println("Debe inicial sesión primero");
                }
                break;
                
                case 5:
                    System.out.println("Saliendo del sistema.");
                    sc.close();
                default:
                    System.out.println("Opción no válida");
                break;
            }
        }


    }
}
