package com.hrawat.paginglibrary.listactivity;

import android.arch.paging.PagedListAdapter;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrawat.paginglibrary.R;
import com.hrawat.paginglibrary.listactivity.db.User;

public class UserAdapter extends PagedListAdapter<User, UserAdapter.UserItemViewHolder> {

    UserAdapter() {
        super(DIFF_CALLBACK);
    }

    private ClickListener clickListener;

    void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    interface ClickListener {

        void onLongClick(long position);
    }

    @Override
    public UserItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user_list, parent, false);
        return new UserItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserItemViewHolder holder, int position) {
        User user = getItem(position);
        if (user != null) {
            holder.bindTo(user);
            holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (clickListener != null)
                        clickListener.onLongClick(user.userId);
                    return false;
                }
            });
        }
    }

    private static DiffCallback<User> DIFF_CALLBACK = new DiffCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.userId == newItem.userId;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };

    static class UserItemViewHolder extends RecyclerView.ViewHolder {

        TextView userName, userId, userAddress, userAge;
        LinearLayout linearLayout;

        UserItemViewHolder(View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.userId);
            userName = itemView.findViewById(R.id.userName);
            userAddress = itemView.findViewById(R.id.userAddress);
            userAge = itemView.findViewById(R.id.userAge);
            linearLayout = itemView.findViewById(R.id.item_linear_layout);
        }

        void bindTo(User user) {
            userName.setText(user.firstName);
            userId.setText(String.valueOf(user.userId));
            userAddress.setText(String.valueOf(user.address));
            userAge.setText(String.valueOf(user.age));
        }
    }
}
