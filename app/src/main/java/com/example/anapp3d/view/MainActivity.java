package com.example.anapp3d.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anapp3d.R;
import com.example.anapp3d.enums.AwardAttribute;
import com.example.anapp3d.enums.DirectOrGroup;
import com.example.anapp3d.model.AwardNo3DModel;
import com.example.anapp3d.model.entity.AwardNo3DPo;
import com.example.anapp3d.model.entity.AwardNo3DVo;
import com.example.anapp3d.model.entity.QueryAwardParam;
import com.example.anapp3d.presenter.AwardPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AwardRecycleAdapter.OnItemClickListener {

    private Button btnAdd;
    private Button btnSwitchDirect;
    private Button btnSwitchGroup;
    private Button btnSwitchExport;

    private Button btnOddEven;
    private Button btnLargeSmall;
    private Button btnPageUp;
    private Button btnPageDown;
    private Button btnQishu;

    private RecyclerView rvData;
    private AwardRecycleAdapter awardRecycleAdapter;
    private AwardPresenter awardPresenter;

    private QueryAwardParam queryAwardParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);

        btnSwitchDirect = findViewById(R.id.btnSwitchDirect);
        btnSwitchGroup = findViewById(R.id.btnSwitchGroup);
        btnSwitchExport = findViewById(R.id.btnSwitchExport);

        btnOddEven = findViewById(R.id.btnOddEven);
        btnLargeSmall = findViewById(R.id.btnLargeSmall);
        btnPageUp = findViewById(R.id.btnPageUp);
        btnPageDown = findViewById(R.id.btnPageDown);
        btnQishu = findViewById(R.id.btnQishu);

        rvData = findViewById(R.id.rvData);

        btnAdd.setOnClickListener(this);
        btnSwitchDirect.setOnClickListener(this);
        btnSwitchGroup.setOnClickListener(this);
        btnSwitchExport.setOnClickListener(this);
        btnOddEven.setOnClickListener(this);
        btnLargeSmall.setOnClickListener(this);
        btnPageUp.setOnClickListener(this);
        btnPageDown.setOnClickListener(this);
        btnQishu.setOnClickListener(this);

        RecyclerView.LayoutManager rvLm =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvData.setLayoutManager(rvLm);

        awardRecycleAdapter = new AwardRecycleAdapter(this);
        awardRecycleAdapter.setOnItemClickListener(this);
        rvData.setAdapter(awardRecycleAdapter);

        queryAwardParam = new QueryAwardParam();
        btnQishu.setText(queryAwardParam.getPageSize()+"期");

        dealPreSelectView();

        awardPresenter = new AwardPresenter();
//        for(int i=0;i<30;i++){
//            AwardNo3DPo awardNo3D = new AwardNo3DPo();
//            int num = 0;
//            if(i<10){
//                awardNo3D.setIssueNo(Long.valueOf("202200"+i));
//                num = i;
//            }else if(i>=10 && i<20){
//                awardNo3D.setIssueNo(Long.valueOf("20220"+i));
//                num = i-10;
//            }else if(i>=20 && i<30){
//                awardNo3D.setIssueNo(Long.valueOf("20220"+i));
//                num = i-20;
//            }
//            awardNo3D.setHundredth(num);
//            awardNo3D.setTen(num);
//            awardNo3D.setTheUnit(num);
//            awardPresenter.addAwardNo3d(awardNo3D);
//        }

        fetchAwardNo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd:
                addOrEditAwardNoView(false,null);
                break;
            case R.id.btnSwitchDirect:
                queryAwardParam.setSelectDirectOrGroup(DirectOrGroup.DIRECT);
                fetchAwardNo();
                break;
            case R.id.btnSwitchGroup:
                queryAwardParam.setSelectDirectOrGroup(DirectOrGroup.GROUP);
                fetchAwardNo();
                break;
            case R.id.btnSwitchExport:
                break;
            case R.id.btnOddEven:
                queryAwardParam.setSelectAwardAttr(AwardAttribute.ODD_EVEN);
                fetchAwardNo();
                break;
            case R.id.btnLargeSmall:
                queryAwardParam.setSelectAwardAttr(AwardAttribute.LARGE_SMALL);
                fetchAwardNo();
                break;
            case R.id.btnPageUp:
                queryAwardParam.startPageSelfAdd();
                fetchAwardNo();
                break;
            case R.id.btnPageDown:
                queryAwardParam.startPageSelfSub();
                fetchAwardNo();
                break;
            case R.id.btnQishu:
                editQishuView();
                break;
            default:
                break;
        }
    }

    private void fetchAwardNo(){
        List<AwardNo3DVo> awardNo3DVoList =  awardPresenter.getAwardNoVoList(queryAwardParam);
        if(awardNo3DVoList.size()>0){
            awardRecycleAdapter.loadData(awardNo3DVoList,true);
        }
    }

    /**
     * 新增或修改
     * @param isEdit
     * @param awardNo3DVo
     */
    private void addOrEditAwardNoView(boolean isEdit,AwardNo3DVo awardNo3DVo){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this.getBaseContext()).inflate(R.layout.dialog_add_edit_award,
                null,false);
        dialog.setView(view);


        TextView tvTitle = view.findViewById(R.id.tvTitle);
        EditText etIssueNo = view.findViewById(R.id.etIssueNo);
        EditText etAwardNo = view.findViewById(R.id.etAwardNo);
        Button btnOK = view.findViewById(R.id.btnOK);
        Button btnCancle = view.findViewById(R.id.btnCancle);

        if(isEdit && null !=awardNo3DVo){
            tvTitle.setText("修改");
            etIssueNo.setText(awardNo3DVo.getIssueNo());
            etAwardNo.setText(awardNo3DVo.getAwardNo());
        }else{
            etIssueNo.setText(String.valueOf(awardPresenter.getLastAwardNo().getIssueNo()+1));
        }

        btnOK.setOnClickListener(v -> {
            String issueNo = etIssueNo.getText().toString().trim();
            if(TextUtils.isEmpty(issueNo)){
                Toast.makeText(MainActivity.this,"请输入期号",Toast.LENGTH_SHORT).show();
                return;
            }
            String awardNoStr = etAwardNo.getText().toString().trim();
            if(TextUtils.isEmpty(awardNoStr)){
                Toast.makeText(MainActivity.this,"请输入奖号",Toast.LENGTH_SHORT).show();
                return;
            }
            char[] awardNo = awardNoStr.toCharArray();
            if(awardNo.length !=3){
                Toast.makeText(MainActivity.this,"请输入三位整数",Toast.LENGTH_SHORT).show();
                return;
            }
            int hundredth = awardNo[0]-'0';
            int ten = awardNo[1]-'0';
            int theUnit = awardNo[2]-'0';
            AwardNo3DPo awardNo3D = new AwardNo3DPo();
            awardNo3D.setIssueNo(Long.valueOf(issueNo));
            awardNo3D.setHundredth(hundredth);
            awardNo3D.setTen(ten);
            awardNo3D.setTheUnit(theUnit);

            if(isEdit && null!=awardNo3DVo){
                awardNo3D.setId(awardNo3DVo.getId());
                awardPresenter.editAwardNo3d(awardNo3D);
            }else{
                awardPresenter.addAwardNo3d(awardNo3D);
            }

            fetchAwardNo();

            dialog.dismiss();
        });
        btnCancle.setOnClickListener(v -> dialog.dismiss());

        dialog.setCancelable(false);//调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        dialog.show();
    }

    /**
     * 修改期数
     */
    private void editQishuView(){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this.getBaseContext()).inflate(R.layout.dialog_edit_qishu,
                null,false);
        dialog.setView(view);


        EditText etEditQishu = view.findViewById(R.id.etEditQishu);
        Button btnOK = view.findViewById(R.id.btnOK);
        Button btnCancle = view.findViewById(R.id.btnCancle);

        btnOK.setOnClickListener(v -> {
            String editQishu = etEditQishu.getText().toString().trim();
            if(TextUtils.isEmpty(editQishu)){
                Toast.makeText(MainActivity.this,"请输入期数",Toast.LENGTH_SHORT).show();
                return;
            }
            queryAwardParam.setPageSize(Integer.valueOf(editQishu));
            btnQishu.setText(editQishu+"期");

            fetchAwardNo();

            dialog.dismiss();
        });
        btnCancle.setOnClickListener(v -> dialog.dismiss());

        dialog.setCancelable(false);//调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        awardPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onItemClick(AwardNo3DVo awardNo3DVo) {
        addOrEditAwardNoView(true,awardNo3DVo);
    }

    /**
     * 预选行视图处理
     */
    private void dealPreSelectView(){

        TextView tvFirstLeft = findViewById(R.id.tvFirstLeft);
        TextView tvFirstRight = findViewById(R.id.tvFirstRight);
        TextView tvSecondLeft = findViewById(R.id.tvSecondLeft);
        TextView tvSecondRight = findViewById(R.id.tvSecondRight);
        TextView tvThirdLeft = findViewById(R.id.tvThirdLeft);
        TextView tvThirdRight = findViewById(R.id.tvThirdRight);

        tvFirstLeft.setOnClickListener(v ->{
                    tvFirstLeft.setTextColor(getResources().getColor(R.color.colorTimes,null));
                    tvFirstRight.setTextColor(getResources().getColor(R.color.colorFont3,null));
                }
        );
        tvFirstRight.setOnClickListener(v ->{
                    tvFirstRight.setTextColor(getResources().getColor(R.color.colorTimes,null));
                    tvFirstLeft.setTextColor(getResources().getColor(R.color.colorFont3,null));
                }
        );

        tvSecondLeft.setOnClickListener(v ->{
                    tvSecondLeft.setTextColor(getResources().getColor(R.color.colorTimes,null));
                    tvSecondRight.setTextColor(getResources().getColor(R.color.colorFont3,null));
                }
        );
        tvSecondRight.setOnClickListener(v ->{
                    tvSecondRight.setTextColor(getResources().getColor(R.color.colorTimes,null));
                    tvSecondLeft.setTextColor(getResources().getColor(R.color.colorFont3,null));
                }
        );

        tvThirdLeft.setOnClickListener(v ->{
                    tvThirdLeft.setTextColor(getResources().getColor(R.color.colorTimes,null));
                    tvThirdRight.setTextColor(getResources().getColor(R.color.colorFont3,null));
                }
        );
        tvThirdRight.setOnClickListener(v ->{
                    tvThirdRight.setTextColor(getResources().getColor(R.color.colorTimes,null));
                    tvThirdLeft.setTextColor(getResources().getColor(R.color.colorFont3,null));
                }
        );
    }
}