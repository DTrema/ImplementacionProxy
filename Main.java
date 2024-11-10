import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        Scanner sc = new Scanner(System.in);

        // Registro inicial de algunos usuarios
        Usuario aux = new Usuario("aux", Rol.ADMINISTRADOR, Grupo.GRUPO_A);
        sistema.registrarUsuario("Luis", Rol.ADMINISTRADOR, Grupo.GRUPO_A);
        sistema.registrarUsuario("Marta", Rol.GERENTE, Grupo.GRUPO_B);
        sistema.registrarUsuario("Pedro", Rol.EMPLEADO, Grupo.GRUPO_D);
        sistema.registrarUsuario("Juan", Rol.GERENTE, Grupo.GRUPO_D);

        sistema.agregarDocumento("Doc1", false, Grupo.GRUPO_A, aux);
        sistema.agregarDocumento("Doc2", true, Grupo.GRUPO_B, aux);
        sistema.agregarDocumento("Doc3", false, Grupo.GRUPO_C, aux);
        sistema.agregarDocumento("Doc4", true, Grupo.GRUPO_D, aux);

        int caso = 0;
        Usuario usuarioActual = null;

        while (caso == 0) {
            System.out.println("\n--- Sistema de Gestión de Documentos ---");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrar usuario (solo administrador)");
            System.out.println("3. Agregar documento (solo administrador)");
            System.out.println("4. Acceder a documento");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            
            // Manejo de entrada para evitar errores de formato
            if (!sc.hasNextInt()) {
                System.out.println("Opción no válida, por favor ingrese un número.");
                sc.next(); // Limpia el buffer
                continue;
            }
            
            int opcion = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer
            
            switch (opcion) {
                case 1:
                    System.out.print("\nIngrese su nombre: ");
                    sistema.listaUsuarios();
                    String nombre = sc.nextLine().trim();
                    usuarioActual = sistema.buscarUsuario(nombre).orElse(null);
                    if (usuarioActual != null) {
                        System.out.println("\nBienvenido, " + nombre + ". Su rol es " + usuarioActual.getRol() + ". Grupo: " + usuarioActual.getGrupo());
                    } else {
                        System.out.println("Usuario no encontrado");
                    }
                    break;

                case 2:
                    if (usuarioActual != null && usuarioActual.getRol() == Rol.ADMINISTRADOR) {
                        System.out.print("\nNombre del nuevo usuario: ");
                        String nuevoNombre = sc.nextLine().trim();
                        
                        // Manejo de entrada para el rol
                        System.out.print("Rol del nuevo usuario (EMPLEADO, GERENTE, ADMINISTRADOR): ");
                        String rolInput = sc.nextLine().trim().toUpperCase();
                        Rol nuevoRol;
                        try {
                            nuevoRol = Rol.valueOf(rolInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Rol inválido. Intente nuevamente.");
                            break;
                        }
                        
                        // Manejo de entrada para el grupo
                        System.out.print("Grupo del nuevo usuario (GRUPO_A, GRUPO_B, GRUPO_C, GRUPO_D): ");
                        String grupoInput = sc.nextLine().trim().toUpperCase();
                        Grupo nuevoGrupo;
                        try {
                            nuevoGrupo = Grupo.valueOf(grupoInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Grupo inválido. Intente nuevamente.");
                            break;
                        }
                        
                        sistema.registrarUsuario(nuevoNombre, nuevoRol, nuevoGrupo);
                    } else {
                        System.out.println("Acceso denegado. Solo los administradores pueden registrar usuarios.");
                    }
                    break;

                case 3:
                    if (usuarioActual != null && usuarioActual.getRol() == Rol.ADMINISTRADOR) {
                        System.out.print("\nNombre del nuevo documento: ");
                        String nuevoDoc = sc.nextLine().trim();

                        // Manejo de entrada para confidencialidad
                        System.out.print("¿Es confidencial (true / false): ");
                        boolean esConfidencial;
                        try {
                            esConfidencial = sc.nextBoolean();
                        } catch (Exception e) {
                            System.out.println("Entrada inválida. Debe ser true o false.");
                            sc.nextLine(); // Limpiar buffer en caso de error
                            break;
                        }
                        sc.nextLine(); // Limpiar buffer después de booleano

                        // Manejo de entrada para el grupo del documento
                        System.out.print("Grupo del documento (GRUPO_A, GRUPO_B, GRUPO_C, GRUPO_D): ");
                        String grupoDocInput = sc.nextLine().trim().toUpperCase();
                        Grupo grupoDoc;
                        try {
                            grupoDoc = Grupo.valueOf(grupoDocInput);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Grupo inválido. Intente nuevamente.");
                            break;
                        }

                        sistema.agregarDocumento(nuevoDoc, esConfidencial, grupoDoc, usuarioActual);
                    } else {
                        System.out.println("Acceso denegado. Solo los administradores pueden agregar documentos.");
                    }
                    break;

                case 4:
                    if (usuarioActual != null) {
                        sistema.listarDocumentos();
                        System.out.print("\nNombre del documento a acceder: ");
                        String nombreAcceso = sc.nextLine().trim();
                        sistema.accederDocumento(nombreAcceso, usuarioActual);
                    } else {
                        System.out.println("Debe iniciar sesión primero.");
                    }
                    break;

                case 5:
                    System.out.println("Saliendo del sistema.");
                    caso++;
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
        sc.close();
    }
}

