package pl.appcoders.moxacontroller.inputs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonObject;

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
import pl.appcoders.moxacontroller.main.OnRestActionListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MappedInputsViewModel extends ViewModel {
    private MediatorLiveData<List<MappedInputItem>> mappedInputItemListMediatorLiveData = new MediatorLiveData<>();
    private MutableLiveData<DigitalInputs> digitalInputsMutableLiveData;
    private LiveData<List<MappedInput>> mappedInputsLiveData;

    private OnRestActionListener onRestActionListener;

    @Inject
    MappedInputDao mappedInputDao;

    @Inject
    DigitalInputService digitalInputService;

    public MappedInputsViewModel() {
        App.getInstance().getApplicationComponent().inject(this);
        initializeMappedInputs();
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

    void refreshRestData() {
        if(isOnRestActionListenerRegistered()) {
            onRestActionListener.requestStartedAction("Refreshing...");
            Log.i("requested", "action");
        }
        digitalInputService.getDigitalInputs().enqueue(new Callback<DigitalInputs>() {
            @Override
            public void onResponse(Call<DigitalInputs> call, Response<DigitalInputs> response) {
                if(response.isSuccessful()) {
                    digitalInputsMutableLiveData.postValue(response.body());
                } else {
                    Log.w("GetDigitalInputResponse", response.message());
                }

                if(isOnRestActionListenerRegistered()) {
                    onRestActionListener.responseAction(response);
                }
            }

            @Override
            public void onFailure(Call<DigitalInputs> call, Throwable t) {
                Log.w("GetDigitalInputFailure", t.getMessage());
                if(isOnRestActionListenerRegistered()) {
                    onRestActionListener.failureAction(t);
                }
            }
        });
    }

    void resetDiCounter(Integer diIndex) {
        if(isOnRestActionListenerRegistered()) {
            onRestActionListener.requestStartedAction("Resetting counter...");
            Log.i("requested", "action");
        }

        JsonObject counterResetJsonObject = CounterResetJsonObjectBuilder.buildCounterResetJsonObject(diIndex);

        digitalInputService.resetDiCounter(diIndex.toString(), counterResetJsonObject).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    refreshRestData();
                } else {
                    Log.w("ResetDiCounterResponse", response.message());
                }

                if(isOnRestActionListenerRegistered()) {
                    onRestActionListener.responseAction(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.w("ResetDiCounterFailure", t.getMessage());
                if(isOnRestActionListenerRegistered()) {
                    onRestActionListener.failureAction(t);
                }
            }
        });
    }

    public void registerOnRestActionListener(OnRestActionListener onRestActionListener) {
        this.onRestActionListener = onRestActionListener;
    }

    public void unregisterOnRestActionListener() {
        this.onRestActionListener = null;
    }

    private boolean isOnRestActionListenerRegistered() {
        return this.onRestActionListener != null;
    }

    private void initializeMappedInputs() {
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

        mappedInputItemListMediatorLiveData.addSource(getDigitalInputs(), new Observer<DigitalInputs>() {
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
                mappedInputItem.setMode(MappedInputItem.DigitalInputMode.INPUT);
                mappedInputItem.setStatus(MappedInputItem.DigitalInputStatus.values()[digitalInput.getDiStatus()]);
                break;
            case 1:
                mappedInputItem.setMode(MappedInputItem.DigitalInputMode.COUNTER);
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
