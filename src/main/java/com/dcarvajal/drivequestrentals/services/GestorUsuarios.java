package com.dcarvajal.drivequestrentals.services;

import com.dcarvajal.drivequestrentals.models.usuarios.Usuario;
import java.io.*;
import java.util.*;

public class GestorUsuarios {
    private final Map<String, Usuario> usuarios = new HashMap<>();
    private final String rutaArchivo = "src/main/java/com/dcarvajal/drivequestrentals/data/usuarios.csv";

    public GestorUsuarios() { cargarUsuarios(); }

    public boolean agregarUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getCorreo())) return false;
        usuarios.put(usuario.getCorreo(), usuario);
        guardarUsuarios();
        return true;
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarios.get(correo);
    }

    private void cargarUsuarios() {
        usuarios.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    usuarios.put(datos[1], new Usuario(datos[0], datos[1], datos[2]));
                }
            }
        } catch (IOException ignored) {}
    }

    private void guardarUsuarios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Usuario u : usuarios.values()) {
                bw.write(u.getNombre() + "," + u.getCorreo() + "," + u.getTelefono());
                bw.newLine();
            }
        } catch (IOException ignored) {}
    }
}