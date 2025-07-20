package com.dcarvajal.drivequestrentals.services.files;

import com.dcarvajal.drivequestrentals.models.vehiculos.*;
import com.dcarvajal.drivequestrentals.services.GestorFlota;

import java.io.*;
import java.util.Map;

public class WriteArchivo {
    public static void guardarVehiculosEnArchivo(GestorFlota gestor) {
        String rutaArchivo = "src/main/java/com/dcarvajal/drivequestrentals/data/vehiculos.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Map.Entry<String, Vehiculo> entry : gestor.getFlota().entrySet()) {
                Vehiculo v = entry.getValue();
                StringBuilder sb = new StringBuilder();
                if (v instanceof VehiculoDeCarga) {
                    VehiculoDeCarga vc = (VehiculoDeCarga) v;
                    sb.append("CARGA,")
                            .append(vc.getPatente()).append(",")
                            .append(vc.getMarca()).append(",")
                            .append(vc.getModelo()).append(",")
                            .append(vc.getPrecio()).append(",")
                            .append(vc.getCapacidadCarga()).append(",")
                            .append(vc.getDiasArriendo()).append(",")
                            .append(vc.isArrendado()).append(",")
                            .append(vc.getCorreoUsuarioArrendado());
                } else if (v instanceof VehiculoDePasajeros) {
                    VehiculoDePasajeros vp = (VehiculoDePasajeros) v;
                    sb.append("PASAJEROS,")
                            .append(vp.getPatente()).append(",")
                            .append(vp.getMarca()).append(",")
                            .append(vp.getModelo()).append(",")
                            .append(vp.getPrecio()).append(",")
                            .append(vp.getCantidadPasajeros()).append(",")
                            .append(vp.getDiasArriendo()).append(",")
                            .append(vp.isArrendado()).append(",")
                            .append(vp.getCorreoUsuarioArrendado());
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        }
    }
}