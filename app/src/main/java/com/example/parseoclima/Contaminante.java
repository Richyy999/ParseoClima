package com.example.parseoclima;

import java.util.ArrayList;
import java.util.HashMap;

public class Contaminante {

    private String estacion;
    private String magnitud;
    private ArrayList<String> horas;

    public Contaminante(String provincia, String municipio, String estacion, String magnitud, ArrayList<String> horas) {
        this.estacion = getEstacion(provincia, municipio, estacion);
        this.magnitud = getMagnitud(magnitud);
        this.horas = horas;
    }

    public int getIndex(){
        return this.horas.size();
    }

    public String getMagnitud(){
        return this.magnitud;
    }

    private String getMagnitud(String magnitud) {
        String mag = "";
        switch (magnitud) {
            case "83":
                mag = "Temperatura";
                break;
            case "89":
                mag = "Precipitaciones";
                break;
        }
        return mag;
    }

    private String getEstacion(String provincia, String municipio, String estacion) {
        return getEstaciones().get(provincia + municipio + estacion);
    }

    public String toHTML() {
        String tabla = "<tr><td>" + this.magnitud + "</td><td>" + this.estacion + "</td>";
        for (String estring : horas) {
            tabla += "<td>";
            tabla += estring;
            tabla += "</td>";
        }
        tabla += "</tr>";
        return tabla;
    }

    private HashMap<String, String> getEstaciones() {
        HashMap<String, String> estaciones = new HashMap<String, String>();
        estaciones.put("28079102", "J.M.D. Moratalaz");
        estaciones.put("28079103", "J.M.D. Villaverde");
        estaciones.put("28079104", "E.D.A.R. La China");
        estaciones.put("28079106", "Centro Mpal. De Acústica");
        estaciones.put("28079107", "J.M.D. Hortaleza");
        estaciones.put("28079108", "Peñagrande");
        estaciones.put("28079109", "J.M.D.Chamberí");
        estaciones.put("28079110", "J.M.D.Centro");
        estaciones.put("28079111", "J.M.D.Chamartin");
        estaciones.put("28079112", "J.M.D.Vallecas 1");
        estaciones.put("28079113", "J.M.D.Vallecas 2");
        estaciones.put("28079114", "Matadero 01");
        estaciones.put("28079115", "Matadero 02");
        estaciones.put("28079004", "Plaza España");
        estaciones.put("28079008", "Escuelas Aguirre");
        estaciones.put("28079016", "Arturo Soria");
        estaciones.put("28079018", "Farolillo");
        estaciones.put("28079024", "Casa de Campo");
        estaciones.put("28079035", "Plaza del Carmen");
        estaciones.put("28079036", "Moratalaz");
        estaciones.put("28079038", "Cuatro Caminos");
        estaciones.put("28079039", "Barrio del Pilar");
        estaciones.put("28079054", "Ensanche de Vallecas");
        estaciones.put("28079056", "Plaza Elíptica");
        estaciones.put("28079058", "El Pardo");
        estaciones.put("28079059", "Juan Carlos I");
        return estaciones;
    }
}
