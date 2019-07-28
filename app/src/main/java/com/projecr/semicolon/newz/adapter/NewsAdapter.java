package com.projecr.semicolon.newz.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.projecr.semicolon.newz.R;
import com.projecr.semicolon.newz.databinding.NetworkingDataBinding;
import com.projecr.semicolon.newz.databinding.NewsDataBinding;
import com.projecr.semicolon.newz.model.ArticlesItem;
import com.projecr.semicolon.newz.ui.WebViewActivity;
import com.projecr.semicolon.newz.util.AppUtil;
import com.projecr.semicolon.newz.util.NetworkState;

public class NewsAdapter extends PagedListAdapter<ArticlesItem, RecyclerView.ViewHolder> {
    private Context context;
    private NetworkState networkState;

    private static int TYPE_PROGRESS = 0;
    private static int TYPE_ITEM = 1;


    private static DiffUtil.ItemCallback<ArticlesItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<ArticlesItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull ArticlesItem oldItem, @NonNull ArticlesItem newItem) {
            return oldItem.getSource().getId().equals(newItem.getSource().getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ArticlesItem oldItem, @NonNull ArticlesItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    public NewsAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_PROGRESS) {
            NetworkingDataBinding networkingDataBinding = NetworkingDataBinding.inflate(layoutInflater,
                    parent, false);
            return new NetworkingViewHolder(networkingDataBinding);
        } else {
            //inflate list item.
            NewsDataBinding newsDataBinding = NewsDataBinding.inflate(layoutInflater,
                    parent, false);
            return new NewsViewHolder(newsDataBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {
            ((NewsViewHolder) holder).bindTo(getItem(position));
        } else {
            ((NetworkingViewHolder) holder).bind(networkState);
        }

    }

    private class NewsViewHolder extends RecyclerView.ViewHolder {
        private NewsDataBinding binding;

        public NewsViewHolder(NewsDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(ArticlesItem item) {
            binding.name.setText(item.getSource().getName());
            String date = AppUtil.getDate(item.getPublishedAt());
            String time = AppUtil.getTime(item.getPublishedAt());
            String timeString = String.format(context.getString(R.string.time_format), date, time);
            binding.date.setText(timeString);
            binding.title.setText(item.getTitle());

            Glide.with(context)
                    .load(item.getUrlToImage())
                    .into(binding.image);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(context, WebViewActivity.class);
                    in.putExtra("url", item.getUrl());
                    context.startActivity(in);
                }
            });


        }
    }

    private class NetworkingViewHolder extends RecyclerView.ViewHolder {
        private NetworkingDataBinding binding;

        public NetworkingViewHolder(NetworkingDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                binding.progressCircular.setVisibility(View.VISIBLE);
                binding.statusMessage.setVisibility(View.VISIBLE);
                binding.statusMessage.setText("Please wiat... Loading");
            } else {
                binding.progressCircular.setVisibility(View.GONE);
                binding.statusMessage.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                binding.statusMessage.setText(networkState.getMessage());
                binding.statusMessage.setVisibility(View.VISIBLE);
            } else {
//                binding.statusMessage.setVisibility(View.GONE);
            }
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        this.networkState = newNetworkState;
        NetworkState previousState = newNetworkState;
        boolean previousExtraRow = hasExtraRow();
        boolean newExtraRow = hasExtraRow();

        if (newExtraRow != previousExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }
}
