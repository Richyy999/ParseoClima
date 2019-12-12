package com.example.parseoclima;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Parsear {

    private static final String ns = null;

    private static int numMaxColumnas = 0;

    public List<Contaminante> parseContaminante(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<Contaminante> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "Datos");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Dato_Horario")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private Contaminante readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Dato_Horario");
        String provincia = null;
        String municipio = null;
        String estacion = null;
        String magnitud = null;
        int hora = 0;
        ArrayList<String> listaHoras = new ArrayList<String>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("provincia")) {
                provincia = readTag(parser, name);
            } else if (name.equals("municipio")) {
                municipio = readTag(parser, name);
            } else if (name.equals("estacion")) {
                estacion = readTag(parser, name);
            } else if (name.equals("magnitud")) {
                magnitud = readTag(parser, name);
            } else if (name.startsWith("H")) {
                String horaFinal = readTag(parser, name);
                if (!horaFinal.startsWith("0"))
                    listaHoras.add(horaFinal);
            } else {
                skip(parser);
            }
        }
        Contaminante c = new Contaminante(provincia, municipio, estacion, magnitud, listaHoras);
        numMaxColumnas = numMaxColumnas > c.getIndex() ? numMaxColumnas : c.getIndex();
        return c;
    }

    private String readTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    public int getNumColumnas(){
        return numMaxColumnas;
    }
}
