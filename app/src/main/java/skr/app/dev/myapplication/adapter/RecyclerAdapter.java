package skr.app.dev.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import skr.app.dev.myapplication.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {


    private Context context;
    private int totalCount;

    public RecyclerAdapter(Context context, int totalCount) {
        this.context = context;
        this.totalCount = totalCount;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutID = R.layout.recycler_item;
        View view = layoutInflater.inflate(layoutID, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvNumber.setText(String.valueOf(position+1));
        holder.tvNumberText.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return totalCount;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNumber, tvNumberText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_num);
            tvNumberText = itemView.findViewById(R.id.tv_num_txt);
        }
    }
}
