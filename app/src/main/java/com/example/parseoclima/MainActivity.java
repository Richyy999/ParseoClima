package com.example.parseoclima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    private StringRequest sReq;
    private RequestQueue cola;

    private List<Contaminante> lista;

    public static final String URL ="http://www.mambiente.madrid.es/opendata/meteorologia.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarVista();
    }

    private void cargarVista() {
        webView = findViewById(R.id.webView);
        pedir();
    }

    private void pedir() {
        cola = Volley.newRequestQueue(this);
        sReq = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               Log.d("RespuestaOK",response.trim());
               System.out.println("Parsea");
               parsearXML(response.trim());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Respuestako",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/xml");
               return headers;
            }
        };
        cola.add(sReq);
    }

    private void parsearXML(String xml) {
        InputStream in = new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8")));
        Parsear parser = new Parsear();
        try {
            lista = parser.parseContaminante(in);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cargarWeb(lista, parser.getNumColumnas());
    }

    private void cargarWeb(List<Contaminante> lista, int numColumnas) {
        String html = GenerarHTML.crearTabla(lista, numColumnas);
        webView.loadData(html, "text/html", "UTF-8");
    }
}
