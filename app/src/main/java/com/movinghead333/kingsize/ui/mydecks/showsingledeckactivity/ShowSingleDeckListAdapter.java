package com.movinghead333.kingsize.ui.mydecks.showsingledeckactivity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movinghead333.kingsize.R;
import com.movinghead333.kingsize.data.datawrappers.CardWithSymbol;
import com.movinghead333.kingsize.ui.CustomListItemClickListener;

import java.util.List;

public class ShowSingleDeckListAdapter extends RecyclerView.Adapter<ShowSingleDeckListAdapter.ViewHolder>{

    // listener for onClick events on listitems
    private CustomListItemClickListener listener;
    private List<CardWithSymbol> cardsInDeck;

    /**
     * constructor which receives a listener for onClick events to be handled in activity class
     * @param listener
     */
    ShowSingleDeckListAdapter(CustomListItemClickListener listener){
        this.listener = listener;
    }

    // ViewHolder class for list-elements of recyclerView
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView cardSymbol;
        TextView cardName;
        TextView cardType;
        TextView cardSource;

        // default constructor for initializing layout
        private ViewHolder(View itemView){
            super(itemView);
            this.cardSymbol = itemView.findViewById(R.id.li_card_in_deck_info_symbol);
            this.cardName = itemView.findViewById(R.id.li_card_in_deck_info_card_name);
            this.cardType = itemView.findViewById(R.id.li_card_in_deck_info_card_type);
            this.cardSource = itemView.findViewById(R.id.li_card_in_deck_info_card_source);
        }
    }

    @Override
    public ShowSingleDeckListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType){
        View listItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_card_in_deck_info, parent, false);

        final ViewHolder viewHolder = new ViewHolder(listItemLayoutView);

        listItemLayoutView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onItemClick(v, viewHolder.getLayoutPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        if(cardsInDeck != null){
            CardWithSymbol currentCard = cardsInDeck.get(position);
            //if(currentCard != null)
                Log.d("ShowSingleDeckAdapter","Message:"+currentCard.symbol+"X");
            Log.d("ShowSingleDeckAdapter","Message:"+cardsInDeck.size());

            viewHolder.cardSymbol.setText(currentCard.symbol);
            viewHolder.cardName.setText(currentCard.cardName);
            viewHolder.cardType.setText(currentCard.cardType);
            viewHolder.cardSource.setText(currentCard.cardSource);

        }
    }

    @Override
    public int getItemCount(){
        if(cardsInDeck == null){
            return 0;
        }else{
            return cardsInDeck.size();
        }
    }

    public void setCardsInDeck(List<CardWithSymbol> cardsInDeck) {
        this.cardsInDeck = cardsInDeck;
        notifyDataSetChanged();
    }
}
