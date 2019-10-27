package com.bermet.dictionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MenuItem menuSetting;
    Toolbar toolbar;
    DBHelper dbHelper;
    DictionaryFragment dictionaryFragment;
    BookmarkFragment bookmarkFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DBHelper(this);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       dictionaryFragment = new DictionaryFragment();
        bookmarkFragment = new BookmarkFragment();

        goToFragment(dictionaryFragment, true);

        dictionaryFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void OnItemClick(String value) {
                String id = Global.getState(MainActivity.this, "dic_type");

                int dicType = id == null ? R.id.action_eng_rus : Integer.valueOf(id);

                goToFragment(DetailFragment.getNewInstance(value, dbHelper, dicType), false);
            }
        });
        bookmarkFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void OnItemClick(String value) {
                String id = Global.getState(MainActivity.this, "dic_type");

                int dicType = id == null ? R.id.action_eng_rus : Integer.valueOf(id);

                goToFragment(DetailFragment.getNewInstance(value, dbHelper, dicType), false);

            }
        });
        EditText edit_search = findViewById(R.id.edit_search);
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dictionaryFragment.filterValue(charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuSetting = menu.findItem(R.id.action_settings);
        String id = Global.getState(this, "dic_type");

        if (id != null)
            onOptionsItemSelected(menu.findItem(Integer.valueOf(id)));
        else {

            //   DB.getData(R.id.action_eng_rus);
            ArrayList<String> source = dbHelper.getWord(R.id.action_eng_rus);
            dictionaryFragment.resetDataSource(source);
        //    dictionaryFragment.resetDataSource(DB.getData(R.id.action_eng_rus));
        }
            return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null) {

            int id = item.getItemId();


            if (R.id.action_settings == id) return true;
            Global.saveState(this, "dic_type", String.valueOf(id));
            //String[]source = DB.getData(id);
            ArrayList<String> source = dbHelper.getWord(id);

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_eng_rus) {


                dictionaryFragment.resetDataSource(source);

                //menuSetting.setIcon(getDrawable(R.drawable.english_russian));
                ResourcesCompat.getDrawable(getResources(), R.drawable.english_russian, null);


            } else if (id == R.id.action_rus_eng) {

                dictionaryFragment.resetDataSource(source);

                //  menuSetting.setIcon(getDrawable(R.drawable.russian_english));
                ResourcesCompat.getDrawable(getResources(), R.drawable.russian_english, null);
         //   } else if (id == R.id.action_rus_rus) {
            //    dictionaryFragment.resetDataSource(source);

                // menuSetting.setIcon(getDrawable(R.drawable.russian));
            //    ResourcesCompat.getDrawable(getResources(), R.drawable.russian, null);


            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_bookmark) {
            String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
            if (!activeFragment.equals(BookmarkFragment.class.getSimpleName())) {
                goToFragment(bookmarkFragment, false);
            }


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void goToFragment(Fragment fragment, boolean isTop) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (!isTop)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
        if (activeFragment.equals((BookmarkFragment.class.getSimpleName()))) {
            menuSetting.setVisible(false);
            toolbar.findViewById(R.id.edit_search).setVisibility(View.GONE);
            toolbar.setTitle("Bookmark");

        } else {
            menuSetting.setVisible(true);
            toolbar.findViewById(R.id.edit_search).setVisibility(View.VISIBLE);
            toolbar.setTitle("");


        }
        return true;
    }

}



