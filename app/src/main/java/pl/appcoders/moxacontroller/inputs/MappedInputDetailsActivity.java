package pl.appcoders.moxacontroller.inputs;

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
import pl.appcoders.moxacontroller.database.dao.MappedInputDao;
import pl.appcoders.moxacontroller.main.OnRestActionListener;
import retrofit2.Response;

public class MappedInputDetailsActivity extends AppCompatActivity implements OnRestActionListener {

    private MappedInputViewModel mappedInputViewModel;
    private MappedInputItem mappedInputItem;

    @Inject
    MappedInputDao mappedInputDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapped_input_details);
        App.getInstance().getApplicationComponent().inject(this);

        setResetCounterButtonListener();

        mappedInputItem = getIntent().getParcelableExtra(MappedInputItem.class.getCanonicalName());
        mappedInputViewModel = ViewModelProviders.of(this)
                .get(MappedInputViewModel.class);

        registerRestActionListener();
        observeMappedInputItemList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.input_details_menu, menu);
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
        mappedInputViewModel.refreshRestData();
    }

    private void menuRemoveHandler() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == DialogInterface.BUTTON_POSITIVE) {
                    mappedInputDao.deleteById(mappedInputItem.getId());
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
            mappedInputViewModel.registerOnRestActionListener((OnRestActionListener)this);
        } else {
            throw new RuntimeException(this.toString()
                    + " must implement OnRestActionListener!");
        }
    }

    private void observeMappedInputItemList() {
        mappedInputViewModel.getMappedInputItemList().observe(this, new Observer<List<MappedInputItem>>() {
            @Override
            public void onChanged(@Nullable List<MappedInputItem> mappedInputItems) {
                Log.i("MappedInputDetails", "MappedInputItemsChanged!");
                for(MappedInputItem item : mappedInputItems) {
                    if(item.getId() == mappedInputItem.getId()) {
                        setTextViewText(R.id.diNameValue, item.getMappedName());
                        setTextViewText(R.id.diIndexValue, "/api/slot/0/io/di/" + Integer.toString(item.getApiIndex()));
                        switch (item.getMode()) {
                            case INPUT:
                                setInputView();
                                setTextViewText(R.id.diModeValue, "Input");
                                setTextViewText(R.id.diStatusValue, getStatusString(item.getStatus()));
                                break;
                            case COUNTER:
                                setCounterView();
                                setTextViewText(R.id.diModeValue, "Counter");
                                setTextViewText(R.id.diCounterValue, Integer.toString(item.getCounterValue()));
                                break;
                        }
                    }
                }
            }
        });
    }

    private void setResetCounterButtonListener() {
        final Button resetCounterButton = findViewById(R.id.reset_counter_button);
        resetCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mappedInputViewModel.resetDiCounter(mappedInputItem.getApiIndex());
            }
        });
    }

    private void setTextViewText(int id, String text) {
        TextView textView = findViewById(id);
        textView.setText(text);
    }

    private void setInputView() {
        findViewById(R.id.diInputLayout).setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.diCounterLayout).setVisibility(LinearLayout.GONE);
        findViewById(R.id.reset_counter_layout).setVisibility(LinearLayout.GONE);
    }

    private void setCounterView() {
        findViewById(R.id.diInputLayout).setVisibility(LinearLayout.GONE);
        findViewById(R.id.diCounterLayout).setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.reset_counter_layout).setVisibility(LinearLayout.VISIBLE);
    }

    private String getStatusString(MappedInputItem.DigitalInputStatus status) {
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
}
