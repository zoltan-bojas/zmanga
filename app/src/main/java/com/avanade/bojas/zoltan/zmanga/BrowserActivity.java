package com.avanade.bojas.zoltan.zmanga;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class BrowserActivity extends AppCompatActivity implements TitlesFragment.OnListFragmentInteractionListener {

    private Fragment mUpdates;
    private FavoritesFragment mFavorites;
    private Fragment mFragmentToDisplay;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_mangas:
                    mFragmentToDisplay = mUpdates;
                    fragmentTransaction.replace(R.id.fragment_container, mFragmentToDisplay).addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_downloads:
                    mFragmentToDisplay = mFavorites;
                    fragmentTransaction.replace(R.id.fragment_container, mFragmentToDisplay).addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
            }
            fragmentTransaction.commit();
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mUpdates = new TitlesFragment();
        mFavorites =  new FavoritesFragment();
        if(savedInstanceState!=null){
           if(savedInstanceState.getString("fragmentToDisplay","new").equals("new")){
               mFragmentToDisplay = mUpdates;
           }
           else {
               mFragmentToDisplay = mFavorites;
           }
        }
        else {
            mFragmentToDisplay = mUpdates;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragmentToDisplay).commit();
    }

    @Override
    public void onListFragmentInteraction(MangaTitle item) {
        Intent mangaDetailsIntent = new Intent(BrowserActivity.this, DetailsActivity.class);
        mangaDetailsIntent.putExtra("url",item.url);
        startActivity(mangaDetailsIntent);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mFragmentToDisplay==mFavorites) {
            outState.putString("fragmentToDisplay","favorites");
        }
        super.onSaveInstanceState(outState);
    }

}
