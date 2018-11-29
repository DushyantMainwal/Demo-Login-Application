package com.dushyant.loginapplication.Retrofit;

import com.dushyant.loginapplication.Models.SingleUserModel;
import com.dushyant.loginapplication.Models.UserListModel;
import com.dushyant.loginapplication.Models.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/api/users")
    Call<UserListModel> getListUsers(@Query("page") int page);

    @GET("/api/users/{id}")
    Call<SingleUserModel> getSingleUser(@Path("id") int userId);

}
