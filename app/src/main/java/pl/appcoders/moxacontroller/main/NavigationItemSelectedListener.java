package pl.appcoders.moxacontroller.main;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import pl.appcoders.moxacontroller.R;
import pl.appcoders.moxacontroller.systeminfo.SystemInfoItemFragment;

class NavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

    private final Activity activity;
    private final DrawerLayout drawerLayout;

    NavigationItemSelectedListener(Activity activity, DrawerLayout drawerLayout) {
        this.activity = activity;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        drawerLayout.closeDrawers();

        Fragment fragment;

        switch (item.getItemId()) {
            case R.id.nav_device_status:
                fragment = new SystemInfoItemFragment();
                break;
            default:
                fragment = new SystemInfoItemFragment();
                break;
        }

        activity.getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        return true;
    }
}
