package com.dcarvajal.drivequestrentals.services.files;

import com.dcarvajal.drivequestrentals.models.vehiculos.*;
import com.dcarvajal.drivequestrentals.services.GestorFlota;

import java.io.*;

public class ReadArchivo {
    public static void cargarVehiculosDesdeArchivo(GestorFlota gestor) {
        String rutaArchivo = "src/main/java/com/dcarvajal/drivequestrentals/data/vehiculos.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 6) continue;
                String tipo = datos[0];
                String patente = datos[1];
                String marca = datos[2];
                String modelo = datos[3];
                String precio = datos[4];
                int diasArriendo = (datos.length > 6) ? Integer.parseInt(datos[6]) : 0;
                boolean isArrendado = (datos.length > 7) ? Boolean.parseBoolean(datos[7]) : false;
                String correoUsuarioArrendado = (datos.length > 8) ? datos[8] : "";
                if (tipo.equals("CARGA")) {
                    String capacidad = datos[5];
                    VehiculoDeCarga vc = new VehiculoDeCarga(patente, marca, modelo, precio, capacidad, diasArriendo, isArrendado, correoUsuarioArrendado);
                    gestor.agregarVehiculo(vc);
                } else if (tipo.equals("PASAJEROS")) {
                    int cantidad = Integer.parseInt(datos[5]);
                    VehiculoDePasajeros vp = new VehiculoDePasajeros(patente, marca, modelo, precio, cantidad, diasArriendo, isArrendado, correoUsuarioArrendado);
                    gestor.agregarVehiculo(vp);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
    }
}