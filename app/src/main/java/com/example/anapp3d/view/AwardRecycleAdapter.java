package com.example.anapp3d.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anapp3d.R;
import com.example.anapp3d.model.entity.AwardNo3DVo;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AwardRecycleAdapter extends RecyclerView.Adapter<AwardRecycleAdapter.MyViewHolder> {

    private View view;
    private Context context;
    private List<AwardNo3DVo> awardNo3DVoList;
    private int mCreatedHolder=0;
    private int mBindingHolder=0;

    //构造方法
    public AwardRecycleAdapter(Context context){
        this.context = context;
        awardNo3DVoList = new ArrayList<>();
    }

    public void loadData(List<AwardNo3DVo> awardNo3DVoList, boolean isFresh) {
        if (awardNo3DVoList != null && awardNo3DVoList.size()>0) {
            if(isFresh){
                this.awardNo3DVoList = awardNo3DVoList;
            }else{
                this.awardNo3DVoList.addAll(awardNo3DVoList);
            }
            notifyDataSetChanged();
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mCreatedHolder++;
        Log.d("hahahehe", "onCreateViewHolder  num:"+mCreatedHolder);
        //创建ViewHolder，返回每一项的布局
        view = LayoutInflater.from(context).inflate(R.layout.adapter_award_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        mBindingHolder++;
        Log.d("hahahehe", "onBindViewHolder  num:"+mBindingHolder);

        holder.tvIssueNo.setText(awardNo3DVoList.get(position).getIssueNo());
        holder.tvAwardNo.setText(awardNo3DVoList.get(position).getAwardNo());

        holder.tvFirstOdd.setText(awardNo3DVoList.get(position).getFirstOdd());
        holder.tvFirstEven.setText(awardNo3DVoList.get(position).getFirstEven());

        holder.tvSecondOdd.setText(awardNo3DVoList.get(position).getSecondOdd());
        holder.tvSecondEven.setText(awardNo3DVoList.get(position).getSecondEven());

        holder.tvThirdOdd.setText(awardNo3DVoList.get(position).getThirdOdd());
        holder.tvThirdEven.setText(awardNo3DVoList.get(position).getThirdEven());
    }

    @Override
    public int getItemCount() {
        return awardNo3DVoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvIssueNo;
        TextView tvAwardNo;

        TextView tvFirstOdd;
        TextView tvFirstEven;

        TextView tvSecondOdd;
        TextView tvSecondEven;

        TextView tvThirdOdd;
        TextView tvThirdEven;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvIssueNo = (TextView) itemView.findViewById(R.id.tvIssueNo);
            tvAwardNo = (TextView) itemView.findViewById(R.id.tvAwardNo);

            tvFirstOdd = (TextView) itemView.findViewById(R.id.tvFirstOdd);
            tvFirstEven = (TextView) itemView.findViewById(R.id.tvFirstEven);

            tvSecondOdd = (TextView) itemView.findViewById(R.id.tvSecondOdd);
            tvSecondEven = (TextView) itemView.findViewById(R.id.tvSecondEven);

            tvThirdOdd = (TextView) itemView.findViewById(R.id.tvThirdOdd);
            tvThirdEven = (TextView) itemView.findViewById(R.id.tvThirdEven);

        }
    }
}
