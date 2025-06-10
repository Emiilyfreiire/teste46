package com.example.teste45;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    FloatingActionButton fab;
    ArrayList<cachorro> listaCachorros;
    CachorroAdapter adapter;
    CachorroDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        fab = findViewById(R.id.fabAdd);
        dbHelper = new CachorroDBHelper(this);

        carregarDados();

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            cachorro cachorro = listaCachorros.get(position);
            Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
            intent.putExtra("id", cachorro.getId());
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            cachorro cachorro = listaCachorros.get(position);
            confirmarExclusao(cachorro);
            return true;
        });
    }

    private void carregarDados() {
        listaCachorros = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cachorro", null);
        while (cursor.moveToNext()) {
            cachorro c = new cachorro();
            new cachorro();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setRaca(cursor.getString(2));
            c.setIdadeHumana(cursor.getInt(3));
            c.setIdadeCachorro(cursor.getInt(4));
            listaCachorros.add(c);
        }
        cursor.close();
        adapter = new CachorroAdapter(this, listaCachorros);
        listView.setAdapter(adapter);
    }

    private void confirmarExclusao(cachorro cachorro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir");
        builder.setMessage("Deseja excluir " + cachorro.getNome() + "?");
        builder.setPositiveButton("Sim", (dialog, which) -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("cachorro", "id = ?", new String[]{String.valueOf(cachorro.getId())});
            carregarDados(); // atualiza a lista
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarDados(); // recarrega a lista ao voltar da tela de cadastro
    }
}
