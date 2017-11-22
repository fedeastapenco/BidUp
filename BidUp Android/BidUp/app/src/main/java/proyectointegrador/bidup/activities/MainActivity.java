package proyectointegrador.bidup.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import proyectointegrador.bidup.fragments.CardFragment;
import proyectointegrador.bidup.fragments.FollowerFragment;
import proyectointegrador.bidup.fragments.LastAuctionsFragment;
import proyectointegrador.bidup.fragments.PublishedFragment;
import proyectointegrador.bidup.R;
import proyectointegrador.bidup.models.Auction;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        , FollowerFragment.OnFragmentInteractionListener, PublishedFragment.OnFragmentInteractionListener
        ,CardFragment.OnFragmentInteractionListener, LastAuctionsFragment.OnFragmentInteractionListener {
    EditText editTextSearch;
    private ArrayAdapter<Auction> auctionAdapter;
    public static final String PREFS_NAME = "MyPrefsFile";

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
        }else if(id == R.id.nav_user_logout){
            SharedPreferences sp = getSharedPreferences(PREFS_NAME,0);
            sp.edit().remove("currentAuthenticationToken").commit();
            Intent intentLogin = new Intent(this, LoginActivity.class);
            startActivity(intentLogin);
        }if(fragmentTransaction){
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
