package com.dcarvajal.drivequestrentals.models.boletas;

public class Boleta {
    private String correo;
    private String patente;
    private int diasArriendo;
    private double subtotal;
    private double descuento;
    private double iva;
    private double total;

    public Boleta(String correo, String patente, int diasArriendo, double subtotal, double descuento, double iva, double total) {
        this.correo = correo;
        this.patente = patente;
        this.diasArriendo = diasArriendo;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.iva = iva;
        this.total = total;
    }

    public String getCorreo() { return correo; }
    public String getPatente() { return patente; }
    public int getDiasArriendo() { return diasArriendo; }
    public double getSubtotal() { return subtotal; }
    public double getDescuento() { return descuento; }
    public double getIva() { return iva; }
    public double getTotal() { return total; }

    @Override
    public String toString() {
        return "Boleta para: " + correo +
                "\nPatente: " + patente +
                "\nDías: " + diasArriendo +
                "\nSubtotal: $" + subtotal +
                "\nDescuento: -$" + descuento +
                "\nIVA: +$" + iva +
                "\nTOTAL: $" + total;
    }
}