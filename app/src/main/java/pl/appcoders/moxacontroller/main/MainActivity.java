package pl.appcoders.moxacontroller.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import pl.appcoders.moxacontroller.R;
import pl.appcoders.moxacontroller.inputs.MappedInputDetailsActivity;
import pl.appcoders.moxacontroller.inputs.MappedInputItem;
import pl.appcoders.moxacontroller.inputs.MappedInputsFragment;
import pl.appcoders.moxacontroller.relays.MappedRelayDetailsActivity;
import pl.appcoders.moxacontroller.relays.MappedRelayItem;
import pl.appcoders.moxacontroller.relays.MappedRelaysFragment;
import pl.appcoders.moxacontroller.settings.SettingsActivity;
import pl.appcoders.moxacontroller.systeminfo.SystemInfoItemFragment;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements MappedInputsFragment.OnListFragmentInteractionListener,
        MappedRelaysFragment.OnListFragmentInteractionListener, OnRestActionListener {

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

    @Override
    public void onListFragmentInteraction(MappedInputItem item) {
        final Intent intent = new Intent(this, MappedInputDetailsActivity.class);
        intent.putExtra(item.getClass().getCanonicalName(), item);
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(MappedRelayItem item) {
        final Intent intent = new Intent(this, MappedRelayDetailsActivity.class);
        intent.putExtra(item.getClass().getCanonicalName(), item);
        startActivity(intent);
    }

    @Override
    public void requestStartedAction(String requestInfo) {
        Toast.makeText(this, requestInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void responseAction(Response response) {
        if(!response.isSuccessful()) {
            Toast.makeText(this, response.message(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void failureAction(Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void menuRefreshHandler() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(fragment instanceof OnRefreshActionListener) {
            ((OnRefreshActionListener)fragment).refreshAction();
            Log.i("MenuRefreshHandler", "Refresh fragment.");
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
        navigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_device_status:
                        fragment = new SystemInfoItemFragment();
                        break;
                    case R.id.nav_inputs:
                        fragment = new MappedInputsFragment();
                        break;
                    case R.id.nav_relays:
                        fragment = new MappedRelaysFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();
                return true;
            }
        });
    }

    private void selectDeviceStatusOnStart(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.nav_device_status, 0);
        }
    }
}
