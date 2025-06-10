package com.example.teste45;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CachorroAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<cachorro> lista;

    public CachorroAdapter(Context context, ArrayList<cachorro> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.activity_main, parent, false);
        }

        cachorro c = lista.get(position);

        TextView tvNome = view.findViewById(R.id.tvNome);
        TextView tvRaca = view.findViewById(R.id.tvRaca);
        TextView tvIdade = view.findViewById(R.id.tvIdade);

        tvNome.setText(c.getNome());
        tvRaca.setText(c.getRaca());
        tvIdade.setText("Idade (cachorro): " + c.getIdadeCachorro() + " anos");

        return view;
    }
}
