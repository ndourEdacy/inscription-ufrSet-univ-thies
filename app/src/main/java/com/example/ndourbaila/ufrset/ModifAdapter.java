package com.example.ndourbaila.ufrset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ndourbaila on 18/11/2017.
 */
public class ModifAdapter extends BaseAdapter {
    private ArrayList<Etudiant> listeEtudiant;
    private LayoutInflater layoutInflater;
    public ModifAdapter(Context context, ArrayList<Etudiant> liste){
        this.layoutInflater = LayoutInflater.from(context);
        this.listeEtudiant   = liste;
    }
    @Override

    public int getCount() {
        return this.listeEtudiant.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listeEtudiant.get(position);
    }
    public static class ViewHolder{
        EditText nom;
        EditText prenom;
        EditText ine;
        EditText adresse;
        EditText filiere;
        EditText niveau;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.etudiant_modif,null);
            viewHolder = new ViewHolder();
            viewHolder.nom = (EditText)convertView.findViewById(R.id.nom_modif);
            viewHolder.prenom = (EditText)convertView.findViewById(R.id.prenom_modif);
            viewHolder.ine = ( EditText)convertView.findViewById(R.id.ine_modif);
            viewHolder.filiere = (EditText)convertView.findViewById(R.id.fil_modif);
            viewHolder.niveau = (EditText)convertView.findViewById(R.id.niv_modif);
            viewHolder.adresse = ( EditText)convertView.findViewById(R.id.adress_modif);
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.ine.setText(listeEtudiant.get(position).getIne());
        viewHolder.prenom.setText(listeEtudiant.get(position).getPrenom());
        viewHolder.nom.setText(listeEtudiant.get(position).getNom());
        viewHolder.filiere.setText(listeEtudiant.get(position).getFiliere());
        viewHolder.niveau.setText(listeEtudiant.get(position).getNiveau());
        viewHolder.adresse.setText(listeEtudiant.get(position).getAdresse());


        return convertView;
    }
}
