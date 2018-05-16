package pl.appcoders.moxacontroller.inputs;

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
import pl.appcoders.moxacontroller.database.dao.MappedInputDao;
import pl.appcoders.moxacontroller.database.entity.MappedInput;
import pl.appcoders.moxacontroller.inputs.dto.Di;
import pl.appcoders.moxacontroller.inputs.dto.DigitalInputs;

public class MapInputActivity extends AppCompatActivity {

    private MappedInputsViewModel mappedInputsViewModel;
    private EditText mappedNameEditText;
    private Spinner spinner;

    @Inject
    MappedInputDao mappedInputDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_input);
        App.getInstance().getApplicationComponent().inject(this);

        mappedInputsViewModel = ViewModelProviders.of(this)
                .get(MappedInputsViewModel.class);

        mappedNameEditText = findViewById(R.id.mappedNameEditText);
        spinner = findViewById(R.id.diSpinner);
        mappedInputsViewModel.getDigitalInputs().observe(this, new Observer<DigitalInputs>() {
                    @Override
                    public void onChanged(@Nullable DigitalInputs digitalInputs) {
                        if(digitalInputs != null) {
                            ArrayAdapter<Di> spinnerArrayAdapter =
                                    new ArrayAdapter<>(MapInputActivity.this,
                                            android.R.layout.simple_spinner_item,
                                            digitalInputs.getIo().getDi());
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
            if(mappedInputDao.findByName(mappedName) != null) {
                mappedNameEditText.setError("Mapped name already used");
                return true;
            }
            Di selectedDi = (Di) spinner.getSelectedItem();
            mappedInputDao.insert(new MappedInput(mappedName, selectedDi.getDiIndex()));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
