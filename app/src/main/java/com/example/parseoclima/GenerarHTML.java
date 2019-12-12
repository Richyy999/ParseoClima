package com.example.parseoclima;

import java.util.List;

public class GenerarHTML {

    public static String crearTabla(List<Contaminante> lista, int numColumnas) {
        String tabla = "<table border=\"1px solid blue\"><tr><th>Magnitud</th><th>Provincia</th>";
        for (int i = 1; i <= numColumnas; i++) {
            String hora = String.valueOf(i);
            if (hora.length() < 2)
                hora = "0" + hora;
            tabla += "<th>H" + hora + "</th>";
        }
        tabla += "</tr>";
        for (Contaminante cont : lista) {
            if (!cont.getMagnitud().equals(""))
                tabla += cont.toHTML();
        }
        tabla += "</table>";
        return tabla;
    }
}
