package proyectointegrador.bidup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import proyectointegrador.bidup.models.Card;

/**
 * Created by user on 25/11/2017.
 */

public class CardListBaseAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<Card> cards;
    LayoutInflater inflter;
    public CardListBaseAdapter(Context context, ArrayList<Card> cardArrayList) {
        // TODO Auto-generated   constructor stub
        this.context=context;
        this.cards = cardArrayList;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int i) {
        return cards.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.card_list_base_adapter, null);
        TextView txtLastFour = (TextView)view.findViewById(R.id.card_list_last_four);
        txtLastFour.setText("Tarjeta: **** **** **** " + cards.get(position).getLastFour());
//        TextView txtExpirationDate = (TextView)view.findViewById(R.id.card_list_expiration_date);
//        DateFormat dateFormat = DateFormat.getDateInstance();
//        txtExpirationDate.setText("Fecha vencimiento:" + dateFormat.format(cards.get(position).getExpirationDate()));
        return view;
    }

}
