package pl.appcoders.moxacontroller.inputs;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pl.appcoders.moxacontroller.database.AppDatabase;
import pl.appcoders.moxacontroller.database.dao.MappedInputDao;
import pl.appcoders.moxacontroller.database.entity.MappedInput;

public class MappedInputController {

    private final MappedInputDao mappedInputDao;
    private final List<MappedInputItem> mappedInputItems = new ArrayList<>();

    //TODO: create rest calls
    //private final DigitalInputService digitalInputService;

    MappedInputController(Context context) {
        this.mappedInputDao = AppDatabase.getInstance(context).mappedInputDao();
        updateMappedInputItemsFromDao();
    }

    List<MappedInputItem> getMappedInputItems() {
        return mappedInputItems;
    }

    MappedInputItem addMappedInput(String mappedName, int apiId) {
        MappedInput mappedInput = new MappedInput(mappedName, apiId);
        return new MappedInputItem(mappedInputDao.findById(mappedInputDao.insert(mappedInput)));
    }

    void updateMappedInput(MappedInputItem mappedInputItem) {
        MappedInput mappedInput = mappedInputDao.findById(mappedInputItem.getId());
        mappedInput.setMappedName(mappedInputItem.getMappedName());
        mappedInput.setApiIndex(mappedInputItem.getApiIndex());
        mappedInputDao.update(mappedInput);
    }

    void removeMappedInput(MappedInputItem mappedInputItem) {
        mappedInputDao.delete(mappedInputDao.findById(mappedInputItem.getId()));
    }

    private void updateMappedInputItemsFromDao() {
        for(MappedInput mappedInput : mappedInputDao.findAll()) {
            mappedInputItems.add(new MappedInputItem(mappedInput));
        }
    }


    public void refreshMappedInputs() {

    }
}
