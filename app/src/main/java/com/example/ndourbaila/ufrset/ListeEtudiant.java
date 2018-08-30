package com.example.ndourbaila.ufrset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ndourbaila on 15/11/2017.
 */
public class ListeEtudiant extends BaseAdapter {
    private ArrayList<Etudiant> listeEtudiant;
    private LayoutInflater layoutInflater;
    public ListeEtudiant(Context context , ArrayList<Etudiant> liste){
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
        TextView nom;
        TextView prenom;
        TextView ine;
        TextView adresse;
        TextView filiere;
        TextView niveau;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.etudiant,null);
            viewHolder = new ViewHolder();
            viewHolder.nom = (TextView)convertView.findViewById(R.id.listenom);
            viewHolder.prenom = (TextView)convertView.findViewById(R.id.listeprenom);
            viewHolder.ine = (TextView)convertView.findViewById(R.id.listeine);
            viewHolder.filiere = (TextView)convertView.findViewById(R.id.listefliere);
            viewHolder.niveau = (TextView)convertView.findViewById(R.id.listeniveau);
            viewHolder.adresse = (TextView)convertView.findViewById(R.id.listeadresse);
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
