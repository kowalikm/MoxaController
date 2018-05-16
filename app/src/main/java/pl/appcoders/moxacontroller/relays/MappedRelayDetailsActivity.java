package pl.appcoders.moxacontroller.relays;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import pl.appcoders.moxacontroller.App;
import pl.appcoders.moxacontroller.R;
import pl.appcoders.moxacontroller.database.dao.MappedRelayDao;
import pl.appcoders.moxacontroller.main.OnRestActionListener;
import retrofit2.Response;

/**
 * Created by mkowalik on 17.05.18.
 */

public class MappedRelayDetailsActivity extends AppCompatActivity implements OnRestActionListener {

    private MappedRelaysViewModel mappedRelaysViewModel;
    private MappedRelayItem mappedRelayItem;

    @Inject
    MappedRelayDao mappedRelayDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapped_relay_details);
        App.getInstance().getApplicationComponent().inject(this);

        setResetCounterButtonListener();

        mappedRelayItem = getIntent().getParcelableExtra(MappedRelayItem.class.getCanonicalName());
        mappedRelaysViewModel = ViewModelProviders.of(this)
                .get(MappedRelaysViewModel.class);

        registerRestActionListener();
        observeMappedRelayItemList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.relay_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshOption:
                menuRefreshHandler();
                return true;
            case R.id.removeOption:
                menuRemoveHandler();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        mappedRelaysViewModel.refreshRestData();
    }

    private void menuRemoveHandler() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == DialogInterface.BUTTON_POSITIVE) {
                    mappedRelayDao.deleteById(mappedRelayItem.getId());
                    finish();
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void registerRestActionListener() {
        if(this instanceof OnRestActionListener) {
            mappedRelaysViewModel.registerOnRestActionListener((OnRestActionListener)this);
        } else {
            throw new RuntimeException(this.toString()
                    + " must implement OnRestActionListener!");
        }
    }

    private void observeMappedRelayItemList() {
        mappedRelaysViewModel.getMappedRelayItemList().observe(this, new Observer<List<MappedRelayItem>>() {
            @Override
            public void onChanged(@Nullable List<MappedRelayItem> mappedRelayItems) {
                Log.i("MappedRelayDetails", "MappedRelayItemsChanged!");
                for(MappedRelayItem item : mappedRelayItems) {
                    if(item.getId() == mappedRelayItem.getId()) {
                        mappedRelayItem = item;
                        setTextViewText(R.id.relayNameValue, item.getMappedName());
                        setTextViewText(R.id.relayIndexValue, "/api/slot/0/io/relay/" + Integer.toString(item.getApiIndex()));
                        switch (item.getMode()) {
                            case RELAY:
                                setRelayView();
                                setTextViewText(R.id.relayModeValue, "Relay");
                                setTextViewText(R.id.relayStatusValue, getStatusString(item.getStatus()));
                                break;
                            case PULSE:
                                setCounterView();
                                setTextViewText(R.id.relayModeValue, "Pulse");
                                setTextViewText(R.id.relayPulseStatusValue, getPulseStatusString(item.getStatus()));
                                break;
                        }
                    }
                }
            }
        });
    }

    private void setResetCounterButtonListener() {
        final Button resetCounterButton = findViewById(R.id.change_relay_state_button);
        resetCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mappedRelaysViewModel.changeRelayState(mappedRelayItem.getApiIndex(),
                        mappedRelayItem.getStatus() == MappedRelayItem.RelayStatus.OFF ? 1 : 0);
            }
        });
    }

    private void setTextViewText(int id, String text) {
        TextView textView = findViewById(id);
        textView.setText(text);
    }

    private void setRelayView() {
        findViewById(R.id.relayRelayLayout).setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.relayPulseLayout).setVisibility(LinearLayout.GONE);
        findViewById(R.id.change_relay_state_layout).setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.change_pulse_state_layout).setVisibility(LinearLayout.GONE);
    }

    private void setCounterView() {
        findViewById(R.id.relayRelayLayout).setVisibility(LinearLayout.GONE);
        findViewById(R.id.relayPulseLayout).setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.change_relay_state_layout).setVisibility(LinearLayout.GONE);
        findViewById(R.id.change_pulse_state_layout).setVisibility(LinearLayout.VISIBLE);
    }

    private String getStatusString(MappedRelayItem.RelayStatus status) {
        String statusString = "";
        switch (status) {
            case ON:
                statusString = "ON";
                break;
            case OFF:
                statusString = "OFF";
                break;
        }

        return statusString;
    }

    private String getPulseStatusString(MappedRelayItem.RelayStatus status) {
        String statusString = "";
        switch (status) {
            case ON:
                statusString = "START";
                break;
            case OFF:
                statusString = "STOP";
                break;
        }

        return statusString;
    }
}