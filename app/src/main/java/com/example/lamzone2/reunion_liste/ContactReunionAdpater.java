package com.example.lamzone2.reunion_liste;


import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lamzone2.R;
import com.example.lamzone2.model.Reunion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class ContactReunionAdpater extends RecyclerView.Adapter<ContactReunionAdpater.ViewHolder> {
    // liste pour le recyclerView
    private List<Reunion> reunion ;


    //obliger de créer un context et de le mettre dans le constructeur pas de context dans un adapteur seulement
    //dans une activité.

    public ContactReunionAdpater( List<Reunion> items){
        this.reunion=items;
        //on doit crée une nouvelle liste car sinon les modifs fait sur la list reunion
        //seront aussi fait sur la nouvelle list pour la recherche
       // this.reunionFull=new ArrayList<>(items);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(  ViewGroup parent, int viewType) {
        //Viewgroup est le parent de tous les layoutFiles
        //Dans notre cas parent indique le ConstraintLaout qui incorpore notre Recyclerview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_items,parent,false);
        // le ViewHolder qui existe dans notre classe.
        //le holder de type ViewHolder va nous permettre d'instancier notre view
        return new ViewHolder(view);
        
    }


    @Override
    public void onBindViewHolder( ContactReunionAdpater.ViewHolder holder, int position) {
        //on peut se servir de textName même si c'est privée car notre classe contient la classe ViewHolder
        holder.sujet.setText(reunion.get(position).getSujet());
        holder.reunionNamer.setText(reunion.get(position).getReunionName());
        holder.heure.setText(reunion.get(position).getHeure());
        holder.email.setText(reunion.get(position).getEmail());

       

        //pour effacer réunion
        holder.delete.setOnClickListener(v -> {
            reunion.remove(holder.getAbsoluteAdapterPosition());
            notifyItemRemoved(position);
            notifyItemChanged(position);
            notifyItemRangeChanged(position,reunion.size());
        });
    }



    @Override
    //retourne la taille de la liste reunion
    public int getItemCount() {
        return reunion.size();
    }

    public void setReunion(List<Reunion> reunion) {
        this.reunion = reunion;
        //pour indiquer que la liste de data reçu a changé et doit être adapaté.
        notifyDataSetChanged();
    }



    //classe qui est obligé d'être crée quand on veut faire un recycler view.
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView sujet;
        private final TextView reunionNamer;
        private final TextView heure;
        private final TextView email;
        private final  ImageButton delete;
        //private ImageView sepparation_bar;


        public ViewHolder(View itemView) {
            super(itemView);
            //pas dans une activity donc il faut utiliser le itemView pour rajouter une vue.
            reunionNamer=itemView.findViewById(R.id.reunionName);
            heure=itemView.findViewById(R.id.heureSalle);
            sujet=itemView.findViewById(R.id.sujet);
            email=itemView.findViewById(R.id.email);
            delete=itemView.findViewById(R.id.delete_bouton);
        }
    }




}
