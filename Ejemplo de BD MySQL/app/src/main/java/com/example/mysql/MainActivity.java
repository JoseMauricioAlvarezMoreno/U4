package com.example.mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtnocontrol, edtnombre,edtbuscar;
    Button agregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtbuscar = findViewById(R.id.edtbuscar);
        edtnocontrol = findViewById(R.id.edtnocontrol);
        edtnombre = findViewById(R.id.edtnombre);
    }


    public void agregar (View view) {


        ejecutarservicio("http://192.168.64.2/phpmyadmin/db_structure/ejemplo.php");

        String URL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operacion Exitosa", Toast.LENGTH_SHORT.show();
            }
        }), new Response.ErrorListener() {
                    @Override
            public void onErrorRespose(VolleyError error){
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
        }}{

        @Override
                protected Map<String, String> getParams() throws AuthFailureError{
            Map<String, String > parametros = new HashMap<String, String>();
            parametros.put("codigo",edtbuscar.getText().toString());
            parametros.put("nombre",edtnombre.getText().toString());
            parametros.put("nocontrol",edtnocontrol.getText().toString());

            return super.getParams();
        }


    }
}