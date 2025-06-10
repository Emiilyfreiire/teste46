package com.example.teste45;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class cadastroactivity extends AppCompatActivity {

    EditText editNome, editRaca, editIdade;
    Button btnSalvar;
    CachorroDBHelper dbHelper;

    int idCachorro = -1; // -1 indica novo cadastro

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNome = findViewById(R.id.editNome);
        editRaca = findViewById(R.id.editRaca);
        editIdade = findViewById(R.id.editIdade);
        btnSalvar = findViewById(R.id.btnSalvar);

        dbHelper = new CachorroDBHelper(this);

        // Verifica se veio para editar
        if (getIntent().hasExtra("id")) {
            idCachorro = getIntent().getIntExtra("id", -1);
            carregarDados(idCachorro);
        }

        btnSalvar.setOnClickListener(v -> salvarCachorro());
    }

    private void carregarDados(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM cachorro WHERE id = ?";
        String[] args = { String.valueOf(id) };
        var cursor = db.rawQuery(query, args);

        if (cursor.moveToFirst()) {
            editNome.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            editRaca.setText(cursor.getString(cursor.getColumnIndexOrThrow("raca")));
            editIdade.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("idadeHumana"))));
        }
        cursor.close();
    }

    private void salvarCachorro() {
        String nome = editNome.getText().toString().trim();
        String raca = editRaca.getText().toString().trim();
        String idadeStr = editIdade.getText().toString().trim();

        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(raca) || TextUtils.isEmpty(idadeStr)) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int idadeHumana;
        try {
            idadeHumana = Integer.parseInt(idadeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Idade inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        int idadeCachorro = idadeHumana * 7; // cálculo simples

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", nome);
        cv.put("raca", raca);
        cv.put("idadeHumana", idadeHumana);
        cv.put("idadeCachorro", idadeCachorro);

        if (idCachorro == -1) {
            long result = db.insert("cachorro", null, cv);
            if (result != -1) {
                Toast.makeText(this, "Cachorro cadastrado!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
            }
        } else {
            int count = db.update("cachorro", cv, "id = ?", new String[]{String.valueOf(idCachorro)});
            if (count > 0) {
                Toast.makeText(this, "Cachorro atualizado!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao atualizar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
