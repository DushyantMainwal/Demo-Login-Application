package com.dushyant.loginapplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dushyant.loginapplication.Models.UserModel;
import com.dushyant.loginapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private Context context;
    private List<UserModel> userModels;

    public UsersListAdapter(Context context, List<UserModel> userModels) {
        this.context = context;
        this.userModels = userModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.friends_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        UserModel userModel = userModels.get(i);

        viewHolder.displayName.setText(String.format("%s %s", userModel.getFirstName(), userModel.getLastName()));
        Picasso.get().load(userModel.getAvatar()).into(viewHolder.circleImageView);
        viewHolder.itemLayout.setOnClickListener(null);
    }

    @Override
    public int getItemCount() {
        return userModels == null ? 0 : userModels.size();
    }

    public void setUserModels(List<UserModel> userModels) {
        this.userModels = userModels;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemLayout;
        CircleImageView circleImageView;
        TextView displayName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.list_item_layout);
            displayName = itemView.findViewById(R.id.display_name);
            circleImageView = itemView.findViewById(R.id.profile_image);
        }
    }
}
