package com.dcarvajal.drivequestrentals.models.usuarios;

public class Usuario {
    private String nombre;
    private String correo;
    private String telefono;

    public Usuario(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
}