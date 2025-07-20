package com.dcarvajal.drivequestrentals.models.vehiculos;

import com.dcarvajal.drivequestrentals.interfaces.Descuentos;

public class VehiculoDePasajeros extends Vehiculo implements Descuentos {
    private int cantidadPasajeros;

    public VehiculoDePasajeros(String patente, String marca, String modelo, String precio, int cantidadPasajeros, int diasArriendo, boolean isArrendado, String correoUsuarioArrendado) {
        super(patente, marca, modelo, precio, diasArriendo, isArrendado, correoUsuarioArrendado);
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public VehiculoDePasajeros(String patente, String marca, String modelo, String precio, int cantidadPasajeros) {
        this(patente, marca, modelo, precio, cantidadPasajeros, 0, false, "");
    }

    public int getCantidadPasajeros() { return cantidadPasajeros; }
    public void setCantidadPasajeros(int cantidadPasajeros) { this.cantidadPasajeros = cantidadPasajeros; }

    @Override
    public void mostrarDatos() {
        System.out.println("Tipo: PASAJEROS | Patente: " + getPatente() +
                " | Marca: " + getMarca() +
                " | Modelo: " + getModelo() +
                " | Precio/día: $" + getPrecio() +
                " | Pasajeros: " + cantidadPasajeros +
                " | Días arrendados: " + getDiasArriendo() +
                (isArrendado() ? " | Arrendado a: " + getCorreoUsuarioArrendado() : ""));
    }

    @Override
    public String toString() {
        return "VehiculoDePasajeros{" +
                "cantidadPasajeros=" + cantidadPasajeros +
                "} " + super.toString();
    }

    @Override
    public void calcularYMostrarBoleta(int diasArriendo) {
        double precioBase = Double.parseDouble(getPrecio()) * diasArriendo;
        double descuento = precioBase * DESCUENTO_PASAJEROS;
        double iva = (precioBase - descuento) * IVA;
        double total = precioBase - descuento + iva;
        System.out.println("--- Boleta Vehículo de Pasajeros ---");
        System.out.println("Patente: " + getPatente());
        System.out.println("Días de arriendo: " + diasArriendo);
        System.out.println("Subtotal: $" + precioBase);
        System.out.println("Descuento (12%): -$" + descuento);
        System.out.println("IVA (19%): +$" + iva);
        System.out.println("TOTAL: $" + total);
        System.out.println("-----------------------------------");
    }
}