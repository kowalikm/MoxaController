package pl.appcoders.moxacontroller.main;

import retrofit2.Response;

public interface OnRestActionListener {
    void requestStartedAction(String requestInfo);

    void responseAction(Response response);

    void failureAction(Throwable t);

}
