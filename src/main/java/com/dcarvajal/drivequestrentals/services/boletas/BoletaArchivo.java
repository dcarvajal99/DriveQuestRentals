package com.dcarvajal.drivequestrentals.services.boletas;

import com.dcarvajal.drivequestrentals.models.boletas.Boleta;
import java.io.*;
import java.util.*;

public class BoletaArchivo {
    private static final String RUTA = "src/main/java/com/dcarvajal/drivequestrentals/data/boletas.csv";

    public static void guardarBoleta(Boleta boleta) {
        try {
            File file = new File(RUTA);
            boolean needsNewLine = false;
            if (file.exists() && file.length() > 0) {
                try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                    raf.seek(file.length() - 1);
                    int lastByte = raf.read();
                    if (lastByte != '\n') {
                        needsNewLine = true;
                    }
                }
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA, true))) {
                if (needsNewLine) {
                    bw.newLine();
                }
                bw.write(boleta.getCorreo() + "," + boleta.getPatente() + "," + boleta.getDiasArriendo() + "," +
                        boleta.getSubtotal() + "," + boleta.getDescuento() + "," + boleta.getIva() + "," + boleta.getTotal());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar boleta: " + e.getMessage());
        }
    }

    public static List<Boleta> buscarBoletasPorCorreo(String correo) {
        List<Boleta> boletas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                if (d.length == 7 && d[0].equalsIgnoreCase(correo)) {
                    boletas.add(new Boleta(d[0], d[1], Integer.parseInt(d[2]),
                            Double.parseDouble(d[3]), Double.parseDouble(d[4]),
                            Double.parseDouble(d[5]), Double.parseDouble(d[6])));
                }
            }
        } catch (IOException ignored) {}
        return boletas;
    }
}