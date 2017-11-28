package proyectointegrador.bidup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;

import proyectointegrador.bidup.models.Card;

/**
 * Created by user on 26/11/2017.
 */

public class CardListAdapter extends ArrayAdapter<Card>{
    private final Activity context;
    private final ArrayList<Card> cards;

    public CardListAdapter(Activity context, ArrayList<Card> auctionArrayList) {
        super(context, R.layout.card_list_adapter, auctionArrayList);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.cards = auctionArrayList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.card_list_adapter, null,true);
        TextView textViewLastFour = (TextView)rowView.findViewById(R.id.card_list_last_four);
        TextView textViewExpiration = (TextView)rowView.findViewById(R.id.card_list_expiration_date);
        textViewLastFour.setText("Tarjeta: **** **** **** " + cards.get(position).getLastFour());
        DateFormat dateFormat = DateFormat.getDateInstance();
        textViewExpiration.setText("Fecha vencimiento: " + dateFormat.format(cards.get(position).getExpirationDate()));
        return rowView;
    }

}
