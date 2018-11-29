package com.dushyant.loginapplication.Activities;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dushyant.loginapplication.Adapters.UsersListAdapter;
import com.dushyant.loginapplication.Models.SingleUserModel;
import com.dushyant.loginapplication.Models.UserListModel;
import com.dushyant.loginapplication.Models.UserModel;
import com.dushyant.loginapplication.R;
import com.dushyant.loginapplication.Retrofit.APIInterface;
import com.dushyant.loginapplication.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    APIInterface apiInterface;
    UsersListAdapter usersListAdapter;
    List<UserModel> userModels;
    TextView displayName;
    ImageView profileImage;

    LinearLayout bottomSheetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userModels = new ArrayList<>();
        apiInterface = RetrofitClient.getRetrofitClient().create(APIInterface.class);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        usersListAdapter = new UsersListAdapter(this, null);
        recyclerView.setAdapter(usersListAdapter);

        displayName = findViewById(R.id.user_name);
        profileImage = findViewById(R.id.profile_image);

        bottomSheetLayout = findViewById(R.id.bottomSheetLayout);
        BottomSheetBehavior bottomSheetBehavior =BottomSheetBehavior.from(bottomSheetLayout);
//        bottomSheetBehavior.setPeekHeight(R.dimen.bottom_sheet_height);
//        bottomSheetBehavior.setFitToContents(true);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_DRAGGING);

        final Call<SingleUserModel> userModelCall = apiInterface.getSingleUser(2);
        userModelCall.enqueue(new Callback<SingleUserModel>() {
            @Override
            public void onResponse(Call<SingleUserModel> call, Response<SingleUserModel> response) {
                SingleUserModel singleUserModel = response.body();
                if (singleUserModel == null) {
                    Toast.makeText(MainActivity.this, "NO Data", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserModel userModel = singleUserModel.getUserModel();
                displayName.setText(String.format("%s %s", userModel.getFirstName(), userModel.getLastName()));
                Picasso.get().load(userModel.getAvatar()).into(profileImage);
            }

            @Override
            public void onFailure(Call<SingleUserModel> call, Throwable t) {
                userModelCall.cancel();
            }
        });

        final Call<UserListModel> userListModelCall = apiInterface.getListUsers(2);
        userListModelCall.enqueue(new Callback<UserListModel>() {
            @Override
            public void onResponse(Call<UserListModel> call, Response<UserListModel> response) {
                Log.d("TAG", response.code() + "");
                System.out.println("Response: " + response.body());

                String displayResponse = "";

                UserListModel resource = response.body();
                userModels = resource.getData();
                usersListAdapter.setUserModels(userModels);


                displayResponse += resource.getPage() + " Page\n" + resource.getTotal() + " Total\n" + resource.getTotalPages() + " Total Pages\n";

//                for (UserModel datum : list) {
//                    displayResponse += datum.getId() + " " + datum.getFirstName() + " " + datum.getLastName() + " " + datum.getAvatar() + "\n";
//                }

//                Toast.makeText(MainActivity.this, "Response: " + displayResponse, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<UserListModel> call, Throwable t) {
                userListModelCall.cancel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
