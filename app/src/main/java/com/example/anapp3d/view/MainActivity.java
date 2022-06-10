package com.example.anapp3d.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.anapp3d.R;
import com.example.anapp3d.model.AwardNo3DModel;
import com.example.anapp3d.model.entity.AwardNo3DPo;
import com.example.anapp3d.model.entity.AwardNo3DVo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvData;
    private AwardRecycleAdapter awardRecycleAdapter;
    private AwardNo3DModel awardNo3DModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvData = findViewById(R.id.rvData);

        RecyclerView.LayoutManager rvLm =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvData.setLayoutManager(rvLm);

        awardRecycleAdapter = new AwardRecycleAdapter(this);
        rvData.setAdapter(awardRecycleAdapter);

        awardNo3DModel = new AwardNo3DModel();
        for(int i=0;i<10;i++){
            AwardNo3DPo awardNo3D = new AwardNo3DPo();
            awardNo3D.setIssueNo("202201"+i);
            awardNo3D.setHundredth(i);
            awardNo3D.setTen(i);
            awardNo3D.setTheUnit(i);
            awardNo3DModel.addAwardNo3d(awardNo3D);
        }

        List<AwardNo3DVo> awardNo3DVoList =  awardNo3DModel.getAwardNoByPage(0,10);
        awardRecycleAdapter.loadData(awardNo3DVoList,true);
    }
}