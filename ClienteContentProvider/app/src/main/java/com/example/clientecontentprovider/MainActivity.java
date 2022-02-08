package com.example.clientecontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnInsertar;
    Button btnLeer;
    Button btnActualiza;
    Button btnBorra;
    EditText txtID, txtNombre, txtApellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID = findViewById(R.id.txtID);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        btnLeer = findViewById(R.id.btnLeer);
        btnInsertar = findViewById(R.id.btnInsertar);
        btnActualiza = findViewById(R.id.btnActualiza);
        btnBorra = findViewById(R.id.btnBorrar);

        btnInsertar.setOnClickListener(view -> {
            insertaValor();
        });
        btnLeer.setOnClickListener(view -> {
            leeValores();
        });
        btnActualiza.setOnClickListener(view -> {
            actualizaDatos();
        });
        btnBorra.setOnClickListener(view -> {
            borraValor();
        });
    }

    private void borraValor() {
        String id = txtID.getText().toString();

        limpiaCajas();

        Uri nuevaUri = Uri.withAppendedPath(UsuarioContrato.CONTENT_URI, id);
        int resultado = getContentResolver().delete(nuevaUri, null, null);
        Log.d("MainActivity","Borrado: " + resultado);
    }

    private void actualizaDatos() {
        String id = txtID.getText().toString();
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();

        limpiaCajas();

        ContentValues valores = new ContentValues();
        valores.put(UsuarioContrato.COLUMN_FIRSTNAME, nombre);
        valores.put(UsuarioContrato.COLUMN_LASTNAME, apellido);

        Uri nuevaUri = Uri.withAppendedPath(UsuarioContrato.CONTENT_URI, id);
        int resultado = getContentResolver().update(nuevaUri, valores, null, null);
        Log.d("MainActivity","Actualizado: " + resultado);
    }

    private void limpiaCajas() {
        txtID.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
    }

    private void leeValores() {
        StringBuilder builder = new StringBuilder();
        Uri contentUri = UsuarioContrato.CONTENT_URI;
        String[] columnas = UsuarioContrato.COLUMNS_NAME;
        Cursor cursor = getContentResolver().query(contentUri, columnas,
                null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String s0 = cursor.getString(0);
                String s1 = cursor.getString(1);
                String s2 = cursor.getString(2);
                String datos = String.format("%s-%s-%s", s0, s1, s2);
                builder.append(datos);
                builder.append('\n');
            }
            Log.d("MainActivity", builder.toString());
        } else {
            Log.d("MainActivity", "Cursor nulo");
        }
    }

    private void insertaValor() {
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();

        limpiaCajas();

        ContentValues valores = new ContentValues();
        valores.put(UsuarioContrato.COLUMN_FIRSTNAME, nombre);
        valores.put(UsuarioContrato.COLUMN_LASTNAME, apellido);
        Uri nuevoUsuario = getContentResolver().insert(UsuarioContrato.CONTENT_URI, valores);
        Log.d("MainActivity","Insertado: " + nuevoUsuario.toString());
    }
}