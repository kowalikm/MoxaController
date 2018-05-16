package pl.appcoders.moxacontroller.relays;

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
import pl.appcoders.moxacontroller.database.dao.MappedRelayDao;
import pl.appcoders.moxacontroller.database.entity.MappedRelay;
import pl.appcoders.moxacontroller.main.OnRestActionListener;
import pl.appcoders.moxacontroller.relays.dto.Relay;
import pl.appcoders.moxacontroller.relays.dto.Relays;
import pl.appcoders.moxacontroller.relays.service.RelaysService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mkowalik on 16.05.18.
 */

public class MappedRelaysViewModel extends ViewModel {
    private MediatorLiveData<List<MappedRelayItem>> mappedRelayItemListMediatorLiveData = new MediatorLiveData<>();
    private MutableLiveData<Relays> relaysMutableLiveData;
    private LiveData<List<MappedRelay>> mappedRelaysLiveData;

    private OnRestActionListener onRestActionListener;

    @Inject
    MappedRelayDao mappedRelayDao;

    @Inject
    RelaysService relaysService;

    public MappedRelaysViewModel() {
        App.getInstance().getApplicationComponent().inject(this);
        initializeMappedRelays();
    }

    LiveData<List<MappedRelayItem>> getMappedRelayItemList() {
        return mappedRelayItemListMediatorLiveData;
    }

    LiveData<Relays> getRelays() {
        if(relaysMutableLiveData == null) {
            relaysMutableLiveData = new MutableLiveData<>();
            refreshRestData();
        }
        return relaysMutableLiveData;
    }

    void refreshRestData() {
        if(isOnRestActionListenerRegistered()) {
            onRestActionListener.requestStartedAction("Refreshing...");
            Log.i("requested", "action");
        }
        relaysService.getRelays().enqueue(new Callback<Relays>() {
            @Override
            public void onResponse(Call<Relays> call, Response<Relays> response) {
                if(response.isSuccessful()) {
                    relaysMutableLiveData.postValue(response.body());
                } else {
                    Log.w("GetRelaysResponse", response.message());
                }

                if(isOnRestActionListenerRegistered()) {
                    onRestActionListener.responseAction(response);
                }
            }

            @Override
            public void onFailure(Call<Relays> call, Throwable t) {
                Log.w("GetRelaysFailure", t.getMessage());
                if(isOnRestActionListenerRegistered()) {
                    onRestActionListener.failureAction(t);
                }
            }
        });
    }


    public void changeRelayState(Integer relayIndex, Integer newState) {
        if(isOnRestActionListenerRegistered()) {
            onRestActionListener.requestStartedAction("Changing relay state...");
            Log.i("ChangeRelayState", "Changing relay state");
        }
        JsonObject changeRelayStateJsonObject =
                ChangeRelayStateJsonObjectBuilder.buildChangeRelayStateJsonObject(relayIndex, newState);

        relaysService.changeRelayState(relayIndex.toString(), changeRelayStateJsonObject).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    refreshRestData();
                } else {
                    Log.w("ChngRelayStateResponse", response.message());
                }

                if(isOnRestActionListenerRegistered()) {
                    onRestActionListener.responseAction(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.w("ChangeRelayStateFailure", t.getMessage());
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

    private void initializeMappedRelays() {
        mappedRelaysLiveData = mappedRelayDao.findAll();
        mappedRelayItemListMediatorLiveData.addSource(mappedRelaysLiveData, new Observer<List<MappedRelay>>() {
            @Override
            public void onChanged(@Nullable List<MappedRelay> mappedRelays) {
                final List<MappedRelay> mappedRelayList = mappedRelays;
                final Relays relays = relaysMutableLiveData.getValue();
                if(relays != null) {
                    final List<Relay> relayList = relays.getIo().getRelay();
                    updateMappedRelaysMediatorLiveData(mappedRelayList, relayList);
                }
            }
        });

        mappedRelayItemListMediatorLiveData.addSource(getRelays(), new Observer<Relays>() {
            @Override
            public void onChanged(@Nullable Relays relays) {
                final List<MappedRelay> mappedRelayList = mappedRelaysLiveData.getValue();
                final List<Relay> relayList = relays.getIo().getRelay();
                updateMappedRelaysMediatorLiveData(mappedRelayList, relayList);
            }
        });
    }

    private void updateMappedRelaysMediatorLiveData(List<MappedRelay> mappedRelayList, List<Relay> relays) {
        List<MappedRelayItem> mappedRelayItemList = new ArrayList<>();
        for(MappedRelay mappedRelay : mappedRelayList) {
            final MappedRelayItem mappedRelayItem = new MappedRelayItem(mappedRelay);
            Iterator<Relay> iterator = findRelays(relays, mappedRelayItem).iterator();

            if(iterator.hasNext()) {
                updateMappedRelayItem(mappedRelayItem, iterator.next());
            }
            mappedRelayItemList.add(mappedRelayItem);
        }

        mappedRelayItemListMediatorLiveData.postValue(mappedRelayItemList);
    }

    private void updateMappedRelayItem(MappedRelayItem mappedRelayItem, Relay relay) {
        switch (relay.getRelayMode()) {
            case 0:
                mappedRelayItem.setMode(MappedRelayItem.RelayMode.RELAY);
                mappedRelayItem.setStatus(MappedRelayItem.RelayStatus.values()[relay.getRelayStatus()]);
                break;
            case 1:
                mappedRelayItem.setMode(MappedRelayItem.RelayMode.PULSE);
                mappedRelayItem.setStatus(MappedRelayItem.RelayStatus.values()[relay.getRelayPulseStatus()]);
                break;
        }
    }

    @NonNull
    private Collection<Relay> findRelays(List<Relay> relays, final MappedRelayItem mappedRelayItem) {
        return (Collection<Relay>) CollectionUtils.select(relays, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((Relay)object).getRelayIndex().equals(mappedRelayItem.getApiIndex());
            }
        });
    }
}
