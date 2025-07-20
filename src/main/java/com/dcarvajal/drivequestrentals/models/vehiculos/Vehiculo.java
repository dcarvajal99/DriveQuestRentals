package com.dcarvajal.drivequestrentals.models.vehiculos;

public abstract class Vehiculo {
    private String patente;
    private String marca;
    private String modelo;
    private String precio;
    private int diasArriendo;
    private boolean isArrendado;
    private String correoUsuarioArrendado;

    public Vehiculo() {}

    public Vehiculo(String patente, String marca, String modelo, String precio, int diasArriendo, boolean isArrendado, String correoUsuarioArrendado) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.diasArriendo = diasArriendo;
        this.isArrendado = isArrendado;
        this.correoUsuarioArrendado = correoUsuarioArrendado;
    }

    public Vehiculo(String patente, String marca, String modelo, String precio) {
        this(patente, marca, modelo, precio, 0, false, "");
    }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getPrecio() { return precio; }
    public void setPrecio(String precio) { this.precio = precio; }
    public int getDiasArriendo() { return diasArriendo; }
    public void setDiasArriendo(int diasArriendo) { this.diasArriendo = diasArriendo; }
    public boolean isArrendado() { return isArrendado; }
    public void setArrendado(boolean arrendado) { isArrendado = arrendado; }
    public String getCorreoUsuarioArrendado() { return correoUsuarioArrendado; }
    public void setCorreoUsuarioArrendado(String correo) { this.correoUsuarioArrendado = correo; }

    public abstract void mostrarDatos();
    public abstract void calcularYMostrarBoleta(int diasArriendo);

    @Override
    public String toString() {
        return "Vehiculo{" +
                "patente='" + patente + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", precio='" + precio + '\'' +
                ", diasArriendo=" + diasArriendo +
                '}';
    }
}