package pl.appcoders.moxacontroller.mainactivity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

/**
 * Created by mkowalik on 25.02.18.
 */

public class NavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

    private final DrawerLayout drawerLayout;

    public NavigationItemSelectedListener(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        drawerLayout.closeDrawers();

        //TODO: Fragments selection

        return true;
    }
}
