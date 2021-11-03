package com.cobi.puresurveyandroid.webservice.ServiceInterfaces;


import com.cobi.puresurveyandroid.model.MyCpdData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PlumlineInterface {

    @GET("{username}/cpd")
    Call<MyCpdData> getUserCpd(@Header("X-Auth") String xAuth, @Path(value = "username", encoded = true) String username);


}
