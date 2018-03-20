package pl.appcoders.moxacontroller.inputs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import pl.appcoders.moxacontroller.App;
import pl.appcoders.moxacontroller.database.dao.MappedInputDao;
import pl.appcoders.moxacontroller.database.entity.MappedInput;
import pl.appcoders.moxacontroller.inputs.dto.Di;
import pl.appcoders.moxacontroller.inputs.dto.DigitalInputs;
import pl.appcoders.moxacontroller.inputs.service.DigitalInputService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MappedInputViewModel extends ViewModel {
    private MediatorLiveData<List<MappedInputItem>> mappedInputItemListMediatorLiveData = new MediatorLiveData<>();
    private MutableLiveData<DigitalInputs> digitalInputsMutableLiveData;
    private LiveData<List<MappedInput>> mappedInputsLiveData;

    @Inject
    MappedInputDao mappedInputDao;

    @Inject
    DigitalInputService digitalInputService;

    public MappedInputViewModel() {
        App.getInstance().getApplicationComponent().inject(this);
        initalizeMappedInputs();
    }

    LiveData<List<MappedInputItem>> getMappedInputItemList() {
        return mappedInputItemListMediatorLiveData;
    }

    LiveData<DigitalInputs> getDigitalInputs() {
        if(digitalInputsMutableLiveData == null) {
            digitalInputsMutableLiveData = new MutableLiveData<>();
            refreshRestData();
        }
        return digitalInputsMutableLiveData;
    }

    LiveData<List<MappedInputItem>> getMappedInputList() {
        return mappedInputItemListMediatorLiveData;
    }

    void refreshRestData() {
        digitalInputService.getDigitalInputs().enqueue(new Callback<DigitalInputs>() {
            @Override
            public void onResponse(Call<DigitalInputs> call, Response<DigitalInputs> response) {
                if(response.isSuccessful()) {
                    digitalInputsMutableLiveData.postValue(response.body());
                } else {
                    Log.w("GetDigitalInputResponse", response.message());
                }
            }

            @Override
            public void onFailure(Call<DigitalInputs> call, Throwable t) {
                Log.w("GetSDigitalInputFailure", t.getMessage());
            }
        });
    }

    private void initalizeMappedInputs() {
        mappedInputsLiveData = mappedInputDao.findAll();
        mappedInputItemListMediatorLiveData.addSource(mappedInputsLiveData, new Observer<List<MappedInput>>() {
            @Override
            public void onChanged(@Nullable List<MappedInput> mappedInputs) {
                final List<MappedInput> mappedInputList = mappedInputs;
                final DigitalInputs digitalInput = digitalInputsMutableLiveData.getValue();
                if(digitalInput != null) {
                    final List<Di> diList = digitalInput.getIo().getDi();
                    updateMappedInputsMediatorLiveData(mappedInputList, diList);
                }
            }
        });

        mappedInputItemListMediatorLiveData.addSource(digitalInputsMutableLiveData, new Observer<DigitalInputs>() {
            @Override
            public void onChanged(@Nullable DigitalInputs digitalInputs) {
                final List<MappedInput> mappedInputList = mappedInputsLiveData.getValue();
                final List<Di> digitalInputList = digitalInputs.getIo().getDi();
                updateMappedInputsMediatorLiveData(mappedInputList, digitalInputList);
            }
        });
    }

    private void updateMappedInputsMediatorLiveData(List<MappedInput> mappedInputList, List<Di> digitalInputs) {
        List<MappedInputItem> mappedInputItemList = new ArrayList<>();
        for(MappedInput mappedInput : mappedInputList) {
            final MappedInputItem mappedInputItem = new MappedInputItem(mappedInput);
            Iterator<Di> iterator = findDigitalInputs(digitalInputs, mappedInputItem).iterator();

            if(iterator.hasNext()) {
                updateMappedInputItem(mappedInputItem, iterator.next());
            }
            mappedInputItemList.add(mappedInputItem);
        }

        mappedInputItemListMediatorLiveData.postValue(mappedInputItemList);
    }

    private void updateMappedInputItem(MappedInputItem mappedInputItem, Di digitalInput) {
        switch (digitalInput.getDiMode()) {
            case 0:
                mappedInputItem.setMode(MappedInputItem.DigitalInputMode.INPUT_MODE);
                mappedInputItem.setStatus(MappedInputItem.DigitalInputStatus.values()[digitalInput.getDiStatus()]);
                break;
            case 1:
                mappedInputItem.setMode(MappedInputItem.DigitalInputMode.COUNTER_MODE);
                mappedInputItem.setStatus(MappedInputItem.DigitalInputStatus.values()[digitalInput.getDiCounterStatus()]);
                mappedInputItem.setCounterValue(digitalInput.getDiCounterValue());
                break;
        }
    }

    @NonNull
    private Collection<Di> findDigitalInputs(List<Di> digitalInputs, final MappedInputItem mappedInputItem) {
        return (Collection<Di>) CollectionUtils.select(digitalInputs, new Predicate() {
                            @Override
                            public boolean evaluate(Object object) {
                                return ((Di)object).getDiIndex().equals(mappedInputItem.getApiIndex());
                            }
                        });
    }


}
