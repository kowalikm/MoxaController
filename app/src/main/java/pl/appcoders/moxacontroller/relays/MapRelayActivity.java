package pl.appcoders.moxacontroller.relays;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import javax.inject.Inject;

import pl.appcoders.moxacontroller.App;
import pl.appcoders.moxacontroller.R;
import pl.appcoders.moxacontroller.database.dao.MappedRelayDao;
import pl.appcoders.moxacontroller.database.entity.MappedRelay;
import pl.appcoders.moxacontroller.relays.dto.Relay;
import pl.appcoders.moxacontroller.relays.dto.Relays;

/**
 * Created by mkowalik on 17.05.18.
 */

public class MapRelayActivity extends AppCompatActivity {

    private MappedRelaysViewModel mappedRelaysViewModel;
    private EditText mappedNameEditText;
    private Spinner spinner;

    @Inject
    MappedRelayDao mappedRelayDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_relay);
        App.getInstance().getApplicationComponent().inject(this);

        mappedRelaysViewModel = ViewModelProviders.of(this)
                .get(MappedRelaysViewModel.class);

        mappedNameEditText = findViewById(R.id.mappedNameEditText);
        spinner = findViewById(R.id.relaySpinner);
        mappedRelaysViewModel.getRelays().observe(this, new Observer<Relays>() {
            @Override
            public void onChanged(@Nullable Relays relays) {
                if(relays != null) {
                    ArrayAdapter<Relay> spinnerArrayAdapter =
                            new ArrayAdapter<>(MapRelayActivity.this,
                                    android.R.layout.simple_spinner_item,
                                    relays.getIo().getRelay());
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                            .simple_spinner_dropdown_item);
                    spinner.setAdapter(spinnerArrayAdapter);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.send_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sendOption) {
            String mappedName = mappedNameEditText.getText().toString();
            if(mappedRelayDao.findByName(mappedName) != null) {
                mappedNameEditText.setError("Mapped name already used");
                return true;
            }
            Relay selectedRelay = (Relay) spinner.getSelectedItem();
            mappedRelayDao.insert(new MappedRelay(mappedName, selectedRelay.getRelayIndex()));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}