package pl.appcoders.moxacontroller.main;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import pl.appcoders.moxacontroller.R;
import pl.appcoders.moxacontroller.settings.SettingsActivity;

/**
 * Created by mkowalik on 25.02.18.
 */

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setCustomActionBar();
        setNavigationItemSelectedListener();

        selectDeviceStatusOnStart(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                menuRefreshHandler();
                return true;
            case R.id.menu_settings:
                menuSettingsHandler();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void menuRefreshHandler() {
        final Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame);
        if(fragment instanceof OnRefreshActionListener) {
            ((OnRefreshActionListener) fragment).refreshAction();
        }
    }

    private void menuSettingsHandler() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void setCustomActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addToggleButton(toolbar);
    }

    private void addToggleButton(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setNavigationItemSelectedListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationItemSelectedListener(this, drawerLayout));
    }

    private void selectDeviceStatusOnStart(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.nav_device_status, 0);
        }
    }
}
