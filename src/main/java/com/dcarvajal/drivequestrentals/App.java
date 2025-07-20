package com.dcarvajal.drivequestrentals;

import com.dcarvajal.drivequestrentals.services.GestorFlota;
import com.dcarvajal.drivequestrentals.services.GestorUsuarios;
import com.dcarvajal.drivequestrentals.services.threads.VehicleLoaderThreads;
import com.dcarvajal.drivequestrentals.services.files.WriteArchivo;
import com.dcarvajal.drivequestrentals.services.boletas.BoletaArchivo;
import com.dcarvajal.drivequestrentals.models.vehiculos.*;
import com.dcarvajal.drivequestrentals.models.boletas.Boleta;
import com.dcarvajal.drivequestrentals.models.usuarios.Usuario;
import com.dcarvajal.drivequestrentals.utils.InputValidador;
import java.util.*;

public class App {
    public static void main(String[] args) {
        GestorFlota gestorFlota = new GestorFlota();
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        String ruta = "src/main/java/com/dcarvajal/drivequestrentals/data/vehiculos.csv";
        VehicleLoaderThreads.cargarVehiculosConHilos(gestorFlota, ruta);

        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Arrendar vehículo");
            System.out.println("2. Listar vehículos");
            System.out.println("3. Agregar vehículo manualmente");
            System.out.println("4. Buscar vehículo por patente");
            System.out.println("5. Eliminar vehículo");
            System.out.println("6. Cotizar arriendo");
            System.out.println("7. Mostrar vehículos con arriendos largos (>=7 días)");
            System.out.println("8. Buscar boletas por correo");
            System.out.println("9. Salir y guardar");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Opción inválida.");
                continue;
            }

            switch (opcion) {
                case 1:
                    // Arrendar vehículo
                    String tipoVeh = "";
                    while (true) {
                        System.out.print("Tipo (1 CARGA - 2 PASAJEROS): ");
                        String tipoInput = scanner.nextLine().trim();
                        if (tipoInput.equals("1")) {
                            tipoVeh = "CARGA";
                            break;
                        } else if (tipoInput.equals("2")) {
                            tipoVeh = "PASAJEROS";
                            break;
                        } else {
                            System.out.println("Tipo inválido. Ingrese 1 para CARGA o 2 para PASAJEROS.");
                        }
                    }

                    // Listar vehículos disponibles
                    List<Vehiculo> disponibles = new ArrayList<>();
                    for (Vehiculo v : gestorFlota.getFlota().values()) {
                        if (!v.isArrendado() && ((tipoVeh.equals("CARGA") && v instanceof VehiculoDeCarga) ||
                                (tipoVeh.equals("PASAJEROS") && v instanceof VehiculoDePasajeros))) {
                            disponibles.add(v);
                        }
                    }
                    if (disponibles.isEmpty()) {
                        System.out.println("No hay vehículos disponibles de ese tipo.");
                        break;
                    }
                    System.out.println("Vehículos disponibles:");
                    for (Vehiculo v : disponibles) {
                        v.mostrarDatos();
                    }

                    String patenteArr;
                    Vehiculo vehiculoArr;
                    while (true) {
                        System.out.print("Ingrese la patente del vehículo a arrendar: ");
                        patenteArr = scanner.nextLine().toUpperCase().trim();
                        if (!InputValidador.esPatenteValida(patenteArr)) {
                            System.out.println("Patente inválida (debe ser 6 caracteres alfanuméricos).");
                            continue;
                        }
                        vehiculoArr = gestorFlota.buscarVehiculoPorPatente(patenteArr);
                        if (vehiculoArr == null || vehiculoArr.isArrendado() ||
                                !((tipoVeh.equals("CARGA") && vehiculoArr instanceof VehiculoDeCarga) ||
                                        (tipoVeh.equals("PASAJEROS") && vehiculoArr instanceof VehiculoDePasajeros))) {
                            System.out.println("Vehículo no disponible o no existe.");
                            continue;
                        }
                        break;
                    }

                    // Consultar correo primero
                    String correo;
                    Usuario usuario = null;
                    while (true) {
                        System.out.print("Correo: ");
                        correo = scanner.nextLine().trim();
                        if (!InputValidador.esCorreoValido(correo)) {
                            System.out.println("Correo inválido.");
                            continue;
                        }
                        usuario = gestorUsuarios.buscarPorCorreo(correo);
                        if (usuario != null) {
                            System.out.println("Usuario encontrado:");
                            System.out.println("Nombre: " + usuario.getNombre());
                            System.out.println("Teléfono: " + usuario.getTelefono());
                        } else {
                            System.out.print("Nombre: ");
                            String nombre = scanner.nextLine().trim();
                            System.out.print("Teléfono: ");
                            String telefono = scanner.nextLine().trim();
                            usuario = new Usuario(nombre, correo, telefono);
                            gestorUsuarios.agregarUsuario(usuario);
                        }
                        break;
                    }

                    // Consultar días de arriendo
                    int diasArriendo = 0;
                    while (true) {
                        System.out.print("¿Cuántos días desea arrendar?: ");
                        try {
                            diasArriendo = Integer.parseInt(scanner.nextLine());
                            if (diasArriendo < 1) {
                                System.out.println("Debe ser al menos 1 día.");
                                continue;
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Ingrese un número válido.");
                        }
                    }

                    vehiculoArr.setArrendado(true);
                    vehiculoArr.setCorreoUsuarioArrendado(correo);
                    vehiculoArr.setDiasArriendo(vehiculoArr.getDiasArriendo() + diasArriendo);

                    WriteArchivo.guardarVehiculosEnArchivo(gestorFlota);

                    // Calcula los valores antes de mostrar la boleta
                    double precioBase = Double.parseDouble(vehiculoArr.getPrecio()) * diasArriendo;
                    double descuento = (vehiculoArr instanceof VehiculoDeCarga)
                            ? precioBase * 0.07
                            : precioBase * 0.12;
                    double iva = (precioBase - descuento) * 0.19;
                    double total = precioBase - descuento + iva;

                    // Mostrar boleta
                    vehiculoArr.calcularYMostrarBoleta(diasArriendo);

                    // Guardar boleta
                    Boleta boleta = new Boleta(correo, vehiculoArr.getPatente(), diasArriendo, precioBase, descuento, iva, total);
                    BoletaArchivo.guardarBoleta(boleta);

                    System.out.println("¡Vehículo arrendado exitosamente!");
                    break;
                case 2:
                    gestorFlota.listarVehiculos();
                    break;
                case 3:
                    String tipo = "";
                    while (true) {
                        System.out.print("Tipo (1 CARGA - 2 PASAJEROS): ");
                        String tipoInput = scanner.nextLine().trim();
                        if (tipoInput.equals("1")) {
                            tipo = "CARGA";
                            break;
                        } else if (tipoInput.equals("2")) {
                            tipo = "PASAJEROS";
                            break;
                        } else {
                            System.out.println("Tipo inválido. Ingrese 1 para CARGA o 2 para PASAJEROS.");
                        }
                    }

                    String patente;
                    while (true) {
                        System.out.print("Patente (6 caracteres alfanuméricos): ");
                        patente = scanner.nextLine().toUpperCase().trim();
                        if (!InputValidador.esPatenteValida(patente)) {
                            System.out.println("Patente inválida (debe ser 6 caracteres alfanuméricos).");
                        } else if (!gestorFlota.validarPatenteUnica(patente)) {
                            System.out.println("Error: Patente duplicada.");
                        } else {
                            break;
                        }
                    }

                    System.out.print("Marca: ");
                    String marca = scanner.nextLine().trim();

                    System.out.print("Modelo: ");
                    String modelo = scanner.nextLine().trim();

                    String precio;
                    while (true) {
                        System.out.print("Precio por día: ");
                        precio = scanner.nextLine().trim();
                        if (!InputValidador.esPrecioValido(precio)) {
                            System.out.println("Precio inválido. Debe ser un número mayor a 0.");
                        } else {
                            break;
                        }
                    }

                    if (tipo.equals("CARGA")) {
                        int capacidad = 0;
                        while (true) {
                            System.out.print("Capacidad de carga (KG): ");
                            String capacidadInput = scanner.nextLine().trim();
                            try {
                                capacidad = Integer.parseInt(capacidadInput);
                                if (capacidad > 0) break;
                                else System.out.println("Debe ser un número mayor a 0.");
                            } catch (NumberFormatException e) {
                                System.out.println("Capacidad inválida. Ingrese solo números.");
                            }
                        }
                        gestorFlota.agregarVehiculo(new VehiculoDeCarga(patente, marca, modelo, precio, String.valueOf(capacidad)));
                    } else {
                        int pasajeros = 0;
                        while (true) {
                            System.out.print("Cantidad de pasajeros: ");
                            String pasajerosInput = scanner.nextLine().trim();
                            if (!InputValidador.esCantidadPasajerosValida(pasajerosInput)) {
                                System.out.println("Cantidad de pasajeros inválida.");
                            } else {
                                pasajeros = Integer.parseInt(pasajerosInput);
                                break;
                            }
                        }
                        gestorFlota.agregarVehiculo(new VehiculoDePasajeros(patente, marca, modelo, precio, pasajeros));
                    }
                    WriteArchivo.guardarVehiculosEnArchivo(gestorFlota);
                    System.out.println("Vehículo agregado y guardado.");
                    break;
                case 4:
                    System.out.print("Ingrese la patente a buscar: ");
                    String patBuscar = scanner.nextLine().toUpperCase().trim();
                    Vehiculo vBuscar = gestorFlota.buscarVehiculoPorPatente(patBuscar);
                    if (vBuscar == null) {
                        System.out.println("Vehículo no encontrado.");
                    } else {
                        vBuscar.mostrarDatos();
                    }
                    break;
                case 5:
                    System.out.print("Ingrese la patente a eliminar: ");
                    String patDel = scanner.nextLine().toUpperCase().trim();
                    boolean eliminado = gestorFlota.eliminarVehiculo(patDel);
                    if (eliminado) {
                        WriteArchivo.guardarVehiculosEnArchivo(gestorFlota);
                        System.out.println("Vehículo eliminado y guardado.");
                    } else {
                        System.out.println("Vehículo no encontrado.");
                    }
                    break;
                case 6:
                    System.out.println("Vehículos disponibles para cotizar:");
                    gestorFlota.listarVehiculos();

                    System.out.print("Ingrese la patente: ");
                    String patCotizar = scanner.nextLine().toUpperCase().trim();
                    Vehiculo vCotizar = gestorFlota.buscarVehiculoPorPatente(patCotizar);
                    if (vCotizar == null) {
                        System.out.println("Vehículo no encontrado.");
                        break;
                    }
                    System.out.print("Ingrese días de arriendo: ");
                    int diasCotizar;
                    try {
                        diasCotizar = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Días inválidos.");
                        break;
                    }
                    if (diasCotizar < 7) {
                        System.out.println("Debe cotizar por al menos 7 días.");
                        break;
                    }
                    vCotizar.calcularYMostrarBoleta(diasCotizar);
                    break;
                case 7:
                    List<Vehiculo> largos = gestorFlota.obtenerVehiculosConArriendosLargos(7);
                    if (largos.isEmpty()) {
                        System.out.println("No hay vehículos con arriendos largos.");
                    } else {
                        System.out.println("Vehículos con arriendos >= 7 días:");
                        for (Vehiculo ve : largos) {
                            ve.mostrarDatos();
                        }
                    }
                    break;
                case 8:
                    System.out.print("Ingrese el correo del usuario: ");
                    String correoBuscar = scanner.nextLine().trim();
                    List<Boleta> boletas = BoletaArchivo.buscarBoletasPorCorreo(correoBuscar);
                    if (boletas.isEmpty()) {
                        System.out.println("No hay boletas para ese correo.");
                    } else {
                        for (Boleta b : boletas) {
                            System.out.println(b);
                            System.out.println("-----------------------------");
                        }
                    }
                    break;
                case 9:
                    WriteArchivo.guardarVehiculosEnArchivo(gestorFlota);
                    System.out.println("Datos guardados. Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 9);

        scanner.close();
    }
}