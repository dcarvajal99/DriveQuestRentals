package com.dcarvajal.drivequestrentals.models.vehiculos;

import com.dcarvajal.drivequestrentals.interfaces.Descuentos;

public class VehiculoDeCarga extends Vehiculo implements Descuentos {
    private String capacidadCarga;

    public VehiculoDeCarga() {}

    public VehiculoDeCarga(String patente, String marca, String modelo, String precio, String capacidadCarga, int diasArriendo, boolean isArrendado, String correoUsuarioArrendado) {
        super(patente, marca, modelo, precio, diasArriendo, isArrendado, correoUsuarioArrendado);
        this.capacidadCarga = capacidadCarga;
    }

    public VehiculoDeCarga(String patente, String marca, String modelo, String precio, String capacidadCarga) {
        this(patente, marca, modelo, precio, capacidadCarga, 0, false, "");
    }

    public String getCapacidadCarga() { return capacidadCarga; }
    public void setCapacidadCarga(String capacidadCarga) { this.capacidadCarga = capacidadCarga; }

    @Override
    public void mostrarDatos() {
        System.out.println("Tipo: CARGA | Patente: " + getPatente() +
                " | Marca: " + getMarca() +
                " | Modelo: " + getModelo() +
                " | Precio/día: $" + getPrecio() +
                " | Capacidad: " + capacidadCarga +
                " | Días arrendados: " + getDiasArriendo() +
                (isArrendado() ? " | Arrendado a: " + getCorreoUsuarioArrendado() : ""));
    }

    @Override
    public String toString() {
        return "VehiculoDeCarga{" +
                "capacidadCarga='" + capacidadCarga + '\'' +
                "} " + super.toString();
    }

    @Override
    public void calcularYMostrarBoleta(int diasArriendo) {
        double precioBase = Double.parseDouble(getPrecio()) * diasArriendo;
        double descuento = precioBase * DESCUENTO_CARGA;
        double iva = (precioBase - descuento) * IVA;
        double total = precioBase - descuento + iva;
        System.out.println("--- Boleta Vehículo de Carga ---");
        System.out.println("Patente: " + getPatente());
        System.out.println("Días de arriendo: " + diasArriendo);
        System.out.println("Subtotal: $" + precioBase);
        System.out.println("Descuento (7%): -$" + descuento);
        System.out.println("IVA (19%): +$" + iva);
        System.out.println("TOTAL: $" + total);
    }
}