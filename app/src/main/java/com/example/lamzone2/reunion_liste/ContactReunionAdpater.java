package com.example.lamzone2.reunion_liste;


import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lamzone2.R;
import com.example.lamzone2.model.Reunion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class ContactReunionAdpater extends RecyclerView.Adapter<ContactReunionAdpater.ViewHolder>implements Filterable {
    // liste pour le recyclerView
    private List<Reunion> reunion ;
    //liste pour la recherche
    private final List<Reunion> reunionFull ;

    //obliger de créer un context et de le mettre dans le constructeur pas de context dans un adapteur seulement
    //dans une activité.

    public ContactReunionAdpater( List<Reunion> items){
        this.reunion=items;
        //on doit crée une nouvelle liste car sinon les modifs fait sur la list reunion
        //seront aussi fait sur la nouvelle list pour la recherche
        this.reunionFull=new ArrayList<>(items);
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


    //implement de la methode Filterable
    @Override
    public Filter getFilter() {
        return exempleFilter;
    }
    //replacer dans le service pour pouvoir l'appeler pour les tests
    private final Filter exempleFilter = new Filter() {
        //fonction qui s'éxécute en fond et permet de ne pas figer l'app
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            results.values=filterList(constraint,reunionFull);
            return results;
        }
        // réulstat de la recherche.
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            reunion.clear();
            reunion.addAll((Collection<? extends Reunion>) results.values);
            notifyDataSetChanged();
        }
    };

    //classe qui est obligé d'être crée quand on veut faire un recycler view.
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView avatar;
        private final TextView sujet;
        private final TextView heure;
        private final TextView email;
        private final  ImageButton delete;
        //private ImageView sepparation_bar;


        public ViewHolder(View itemView) {
            super(itemView);
            //pas dans une activity donc il faut utiliser le itemView pour rajouter une vue.
            avatar=itemView.findViewById(R.id.circle_img);
            heure=itemView.findViewById(R.id.heureSalle);
            sujet=itemView.findViewById(R.id.sujet);
            email=itemView.findViewById(R.id.email);
            delete=itemView.findViewById(R.id.delete_bouton);
        }
    }

     public List<Reunion> filterList(CharSequence constraint,List<Reunion> reunionFull){
        List<Reunion>filteredList =new ArrayList<>();
        if(constraint == null || constraint.length() == 0 ){
            filteredList.addAll(reunionFull);
        }else
        {
            for(Reunion items: reunionFull) {
                if (items.getSujet().toLowerCase().contains(constraint.toString().toLowerCase()) || items.getDatetime().toLowerCase().contains(constraint.toString().toLowerCase())) {
                    filteredList.add(items);
                }
            }
        }
        return filteredList;
    }

}
