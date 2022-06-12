package com.example.anapp3d.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anapp3d.R;
import com.example.anapp3d.enums.DataType;
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
    private OnItemClickListener onItemClickListener;

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

        holder.tvFirstLeft.setText(awardNo3DVoList.get(position).getFirstLeft());
        holder.tvFirstRight.setText(awardNo3DVoList.get(position).getFirstRight());

        holder.tvSecondLeft.setText(awardNo3DVoList.get(position).getSecondLeft());
        holder.tvSecondRight.setText(awardNo3DVoList.get(position).getSecondRight());

        holder.tvThirdLeft.setText(awardNo3DVoList.get(position).getThirdLeft());
        holder.tvThirdRight.setText(awardNo3DVoList.get(position).getThirdRight());

        resetTvColor(new TextView[]{holder.tvIssueNo,holder.tvAwardNo,holder.tvFirstLeft,
                holder.tvFirstRight,holder.tvSecondLeft,holder.tvSecondRight,holder.tvThirdLeft,
                holder.tvThirdRight});

        //奖号类型
        if(DataType.AWARD_NO.equals(awardNo3DVoList.get(position).getDataType())){
            //给所有的奇数列上色
            if(!TextUtils.isEmpty(awardNo3DVoList.get(position).getFirstLeft())){
                holder.tvFirstLeft.setBackgroundResource(R.drawable.shape_corner_left);
            }
            if(!TextUtils.isEmpty(awardNo3DVoList.get(position).getSecondLeft())){
                holder.tvSecondLeft.setBackgroundResource(R.drawable.shape_corner_left);
            }
            if(!TextUtils.isEmpty(awardNo3DVoList.get(position).getThirdLeft())){
                holder.tvThirdLeft.setBackgroundResource(R.drawable.shape_corner_left);
            }

            //给所有的偶数列上色
            if(!TextUtils.isEmpty(awardNo3DVoList.get(position).getFirstRight())){
                holder.tvFirstRight.setBackgroundResource(R.drawable.shape_corner_right);
            }
            if(!TextUtils.isEmpty(awardNo3DVoList.get(position).getSecondRight())){
                holder.tvSecondRight.setBackgroundResource(R.drawable.shape_corner_right);
            }
            if(!TextUtils.isEmpty(awardNo3DVoList.get(position).getThirdRight())){
                holder.tvThirdRight.setBackgroundResource(R.drawable.shape_corner_right);
            }

            //注册一个点击监听器，点击弹出修改界面
            holder.itemView.setOnClickListener(v -> {
                onItemClickListener.onItemClick(awardNo3DVoList.get(position));
            });
        }

        //出现次数类型
        if(DataType.APPEAR_TIMES.equals(awardNo3DVoList.get(position).getDataType())){
            holder.tvIssueNo.setTextColor(context.getResources().getColor(R.color.colorTimes,null));
            holder.tvAwardNo.setTextColor(context.getResources().getColor(R.color.colorTimes,null));

            holder.tvFirstLeft.setTextColor(context.getResources().getColor(R.color.colorTimes,null));
            holder.tvFirstRight.setTextColor(context.getResources().getColor(R.color.colorTimes,null));

            holder.tvSecondLeft.setTextColor(context.getResources().getColor(R.color.colorTimes,null));
            holder.tvSecondRight.setTextColor(context.getResources().getColor(R.color.colorTimes,null));

            holder.tvThirdLeft.setTextColor(context.getResources().getColor(R.color.colorTimes,null));
            holder.tvThirdRight.setTextColor(context.getResources().getColor(R.color.colorTimes,null));
        }

    }

    /**
     * 重置一下颜色
     * @param tvArr
     */
    private void resetTvColor(TextView[] tvArr){
        for(int i=0;i<tvArr.length;i++){
            tvArr[i].setTextColor(context.getResources().getColor(R.color.colorFont3,null));
            tvArr[i].setBackgroundResource(R.drawable.shape_corner_wihite);
        }
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
        View itemView;
        TextView tvIssueNo;
        TextView tvAwardNo;

        TextView tvFirstLeft;
        TextView tvFirstRight;

        TextView tvSecondLeft;
        TextView tvSecondRight;

        TextView tvThirdLeft;
        TextView tvThirdRight;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvIssueNo = itemView.findViewById(R.id.tvIssueNo);
            tvAwardNo = itemView.findViewById(R.id.tvAwardNo);

            tvFirstLeft = itemView.findViewById(R.id.tvFirstLeft);
            tvFirstRight = itemView.findViewById(R.id.tvFirstRight);

            tvSecondLeft = itemView.findViewById(R.id.tvSecondLeft);
            tvSecondRight = itemView.findViewById(R.id.tvSecondRight);

            tvThirdLeft = itemView.findViewById(R.id.tvThirdLeft);
            tvThirdRight = itemView.findViewById(R.id.tvThirdRight);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(AwardNo3DVo awardNo3DVo);
    }
}
