package skr.app.dev.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import skr.app.dev.myapplication.R;
import skr.app.dev.myapplication.utils.NewsPojo;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<NewsPojo> newsArrayList;
    private int totalCount;
    private NewsTitleClickListener newsTitleClickListener;

    public NewsAdapter(Context context, ArrayList<NewsPojo> newsArrayList, int totalCount,
                       NewsTitleClickListener clickListener) {
        this.context = context;
        this.totalCount = totalCount;
        this.newsArrayList = newsArrayList;
        this.newsTitleClickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutID = R.layout.recycler_item_news;
        View view = layoutInflater.inflate(layoutID, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_title.setText(newsArrayList.get(position).getTitle());
        holder.tv_desp.setText(newsArrayList.get(position).getDescription());
        Glide.with(context)
                .load(newsArrayList.get(position).getUrlToImage())
                .into(holder.iv_bk);

        ViewCompat.setTransitionName(holder.iv_bk, newsArrayList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return totalCount;
    }

    public interface NewsTitleClickListener{
        void onNewsTitleClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_title, tv_desp;
        ImageView iv_bk;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desp = itemView.findViewById(R.id.tv_desp);
            iv_bk = itemView.findViewById(R.id.iv_bk);
            iv_bk.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case  R.id.iv_bk:
                    newsTitleClickListener.onNewsTitleClick(v,getAdapterPosition());
                    break;
            }
        }
    }

    //totalCount--;
    public void removeItem(int position){
        newsArrayList.remove(position);
        notifyItemRemoved(position);
    }
}
