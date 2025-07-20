package com.dcarvajal.drivequestrentals.services;

import com.dcarvajal.drivequestrentals.models.vehiculos.Vehiculo;

import java.util.*;

public class GestorFlota {
    private final Map<String, Vehiculo> flota = Collections.synchronizedMap(new HashMap<>());

    public boolean agregarVehiculo(Vehiculo vehiculo) {
        synchronized (flota) {
            if (flota.containsKey(vehiculo.getPatente())) {
                System.out.println("Error: La patente ya existe.");
                return false;
            }
            flota.put(vehiculo.getPatente(), vehiculo);
            return true;
        }
    }

    public boolean eliminarVehiculo(String patente) {
        synchronized (flota) {
            if (flota.containsKey(patente)) {
                flota.remove(patente);
                return true;
            }
            return false;
        }
    }

    public void listarVehiculos() {
        synchronized (flota) {
            if (flota.isEmpty()) {
                System.out.println("No hay vehículos registrados.");
            } else {
                for (Vehiculo v : flota.values()) {
                    v.mostrarDatos();
                }
            }
        }
    }

    public Vehiculo buscarVehiculoPorPatente(String patente) {
        synchronized (flota) {
            return flota.get(patente);
        }
    }

    public List<Vehiculo> obtenerVehiculosConArriendosLargos(int diasMinimos) {
        List<Vehiculo> resultado = new ArrayList<>();
        synchronized (flota) {
            for (Vehiculo v : flota.values()) {
                if (v.getDiasArriendo() >= diasMinimos) {
                    resultado.add(v);
                }
            }
        }
        return resultado;
    }

    public boolean validarPatenteUnica(String patente) {
        synchronized (flota) {
            return !flota.containsKey(patente);
        }
    }

    public Map<String, Vehiculo> getFlota() {
        synchronized (flota) {
            return Collections.unmodifiableMap(new HashMap<>(flota));
        }
    }
}