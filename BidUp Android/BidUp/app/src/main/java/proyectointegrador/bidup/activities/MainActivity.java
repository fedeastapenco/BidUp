package proyectointegrador.bidup.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import proyectointegrador.bidup.fragments.CardFragment;
import proyectointegrador.bidup.fragments.FollowerFragment;
import proyectointegrador.bidup.fragments.LastAuctionsFragment;
import proyectointegrador.bidup.fragments.PublishedFragment;
import proyectointegrador.bidup.R;
import proyectointegrador.bidup.helpers.HttpConnectionHelper;
import proyectointegrador.bidup.helpers.HttpRequestMethod;
import proyectointegrador.bidup.models.Auction;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        , FollowerFragment.OnFragmentInteractionListener, PublishedFragment.OnFragmentInteractionListener
        ,CardFragment.OnFragmentInteractionListener, LastAuctionsFragment.OnFragmentInteractionListener {
    EditText editTextSearch;
    private ArrayAdapter<Auction> auctionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            Fragment fragment = new LastAuctionsFragment();
            getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .commit();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Boolean fragmentTransaction = false;
        if (id == R.id.nav_followers) {
            fragment = new FollowerFragment();
            fragmentTransaction = true;
        } else if (id == R.id.nav_published) {
            fragment = new PublishedFragment();
            fragmentTransaction = true;

        } else if (id == R.id.nav_cards) {
            fragment = new CardFragment();
            fragmentTransaction = true;

        }else if(id == R.id.nav_lastAuctions){
            fragment = new LastAuctionsFragment();
            fragmentTransaction = true;
        }
            if(fragmentTransaction){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .commit();
                getSupportActionBar().setTitle(item.getTitle());
            }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
