package pl.appcoders.moxacontroller.inputs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import pl.appcoders.moxacontroller.R;

public class MappedInputDetailsActivity extends AppCompatActivity {

    private MappedInputViewModel mappedInputViewModel;
    private MappedInputItem mappedInputItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapped_input_details);
        mappedInputViewModel = ViewModelProviders.of(this)
                .get(MappedInputViewModel.class);
        mappedInputItem = getIntent().getParcelableExtra(MappedInputItem.class.getCanonicalName());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.input_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                menuRefreshHandler();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void menuRefreshHandler() {
        mappedInputViewModel.refreshRestData();
    }

    private void setTextViewText(int id, String text) {
        TextView textView = findViewById(id);
        textView.setText(text);
    }

    private void setInputView() {
        findViewById(R.id.diInputLayout).setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.diCounterLayout).setVisibility(LinearLayout.GONE);
    }

    private void setCounterView() {
        findViewById(R.id.diInputLayout).setVisibility(LinearLayout.GONE);
        findViewById(R.id.diCounterLayout).setVisibility(LinearLayout.VISIBLE);
    }

    private String getStatusString(MappedInputItem.DigitalInputStatus status) {
        String statusString = "";
        switch (status) {
            case ON:
                statusString = "ON";
            case OFF:
                statusString = "OFF";
        }

        return statusString;
    }
}
