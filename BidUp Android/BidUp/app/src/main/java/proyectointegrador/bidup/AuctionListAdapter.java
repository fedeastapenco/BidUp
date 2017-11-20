package proyectointegrador.bidup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

import proyectointegrador.bidup.models.Auction;

/**
 * Created by user on 20/11/2017.
 */

public class AuctionListAdapter extends ArrayAdapter<Auction> {
    private final Activity context;
    private final ArrayList<Auction> auctions;

    public AuctionListAdapter(Activity context, ArrayList<Auction> auctionArrayList) {
        super(context, R.layout.auction_list, auctionArrayList);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.auctions = auctionArrayList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.auction_list, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(auctions.get(position).getObjectName());
        if(auctions.get(position).getPhotosUrl() != null && auctions.get(position).getPhotosUrl().length > 0){
            Picasso.with(context).load(auctions.get(position).getPhotosUrl()[0]).into(imageView);
        }
        extratxt.setText("Descripción: "+auctions.get(position).getInitialAmount());
        return rowView;

    };
}
