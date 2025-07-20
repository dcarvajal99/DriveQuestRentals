package com.dcarvajal.drivequestrentals.utils;

public class InputValidador {
    public static boolean esTipoValido(String tipo) {
        return tipo.equalsIgnoreCase("CARGA") || tipo.equalsIgnoreCase("PASAJEROS");
    }

    public static boolean esPatenteValida(String patente) {
        return patente != null && patente.matches("^[A-Za-z0-9]{6}$");
    }

    public static boolean esPrecioValido(String precio) {
        try {
            double p = Double.parseDouble(precio);
            return p > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean esCantidadPasajerosValida(String cantidad) {
        try {
            int c = Integer.parseInt(cantidad);
            return c > 0 && c < 100;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean esCapacidadCargaValida(String capacidad) {
        try {
            int c = Integer.parseInt(capacidad);
            return c > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean esCorreoValido(String correo) {
        return correo != null && correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}