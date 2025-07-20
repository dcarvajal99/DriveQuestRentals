package com.dcarvajal.drivequestrentals.services.threads;

import com.dcarvajal.drivequestrentals.models.vehiculos.*;
import com.dcarvajal.drivequestrentals.services.GestorFlota;

import java.io.*;
import java.util.Queue;
import java.util.concurrent.*;

public class VehicleLoaderThreads implements Runnable {
    private final GestorFlota gestorFlota;
    private final Queue<String> tareas;

    public VehicleLoaderThreads(GestorFlota gestorFlota, Queue<String> tareas) {
        this.gestorFlota = gestorFlota;
        this.tareas = tareas;
    }

    @Override
    public void run() {
        while (true) {
            String linea = tareas.poll();
            if (linea == null) break;
            procesarLinea(linea);
        }
        System.out.println("Hilo " + Thread.currentThread().getName() + " finalizó.");
    }

    private void procesarLinea(String linea) {
        try {
            String[] partes = linea.split(",");
            if (partes.length < 6) return;

            String tipo = partes[0];
            String patente = partes[1];
            String marca = partes[2];
            String modelo = partes[3];
            String precio = partes[4];
            int diasArriendo = (partes.length > 6) ? Integer.parseInt(partes[6]) : 0;
            boolean isArrendado = (partes.length > 7) ? Boolean.parseBoolean(partes[7]) : false;
            String correoUsuarioArrendado = (partes.length > 8) ? partes[8] : "";

            if (tipo.equalsIgnoreCase("CARGA")) {
                String capacidad = partes[5];
                VehiculoDeCarga v = new VehiculoDeCarga(patente, marca, modelo, precio, capacidad, diasArriendo, isArrendado, correoUsuarioArrendado);
                gestorFlota.agregarVehiculo(v);
            } else if (tipo.equalsIgnoreCase("PASAJEROS")) {
                int pasajeros = Integer.parseInt(partes[5]);
                VehiculoDePasajeros v = new VehiculoDePasajeros(patente, marca, modelo, precio, pasajeros, diasArriendo, isArrendado, correoUsuarioArrendado);
                gestorFlota.agregarVehiculo(v);
            }
        } catch (Exception e) {
            System.out.println("Error procesando línea: " + linea + " - " + e.getMessage());
        }
    }

    public static void cargarVehiculosConHilos(GestorFlota gestorFlota, String rutaArchivo) {
        Queue<String> tareas = new ConcurrentLinkedQueue<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                tareas.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        int numHilos = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(numHilos);
        for (int i = 0; i < numHilos; i++) {
            pool.execute(new VehicleLoaderThreads(gestorFlota, tareas));
        }
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[INFO] Carga de vehículos finalizada.");
    }
}