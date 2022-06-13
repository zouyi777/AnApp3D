package com.example.anapp3d.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anapp3d.R;
import com.example.anapp3d.enums.AwardAttribute;
import com.example.anapp3d.enums.DataOperType;
import com.example.anapp3d.enums.DataType;
import com.example.anapp3d.enums.DirectOrGroup;
import com.example.anapp3d.model.AwardNo3DModel;
import com.example.anapp3d.model.entity.AwardNo3DPo;
import com.example.anapp3d.model.entity.AwardNo3DVo;
import com.example.anapp3d.model.entity.QueryAwardParam;
import com.example.anapp3d.presenter.AwardPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AwardRecycleAdapter.OnItemClickListener {

    public static String TAG = MainActivity.class.getName();

    public static int REQUEST_CODE_EXPORT_AWARD_NO = 10000;

    private Button btnAdd;
    private Button btnSwitchDirect;
    private Button btnSwitchGroup;
    private Button btnImport;
    private Button btnExport;

    private Button btnOddEven;
    private Button btnLargeSmall;
    private Button btnPageUp;
    private Button btnPageDown;
    private Button btnQishu;

    private TextView tvFirstLeft;
    private TextView tvFirstRight;
    private TextView tvSecondLeft;
    private TextView tvSecondRight;
    private TextView tvThirdLeft;
    private TextView tvThirdRight;

    private RecyclerView rvData;
    private AwardRecycleAdapter awardRecycleAdapter;
    private AwardPresenter awardPresenter;

    private QueryAwardParam queryAwardParam;
    private DataOperType dataOperType = DataOperType.EXPORT_AWARD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);

        btnSwitchDirect = findViewById(R.id.btnSwitchDirect);
        btnSwitchGroup = findViewById(R.id.btnSwitchGroup);
        btnImport = findViewById(R.id.btnImport);
        btnExport = findViewById(R.id.btnExport);

        btnOddEven = findViewById(R.id.btnOddEven);
        btnLargeSmall = findViewById(R.id.btnLargeSmall);
        btnPageUp = findViewById(R.id.btnPageUp);
        btnPageDown = findViewById(R.id.btnPageDown);
        btnQishu = findViewById(R.id.btnQishu);

        rvData = findViewById(R.id.rvData);

        btnAdd.setOnClickListener(this);
        btnSwitchDirect.setOnClickListener(this);
        btnSwitchGroup.setOnClickListener(this);
        btnImport.setOnClickListener(this);
        btnExport.setOnClickListener(this);

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

        intPreSelectView();

        awardPresenter = new AwardPresenter();
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
            case R.id.btnImport:
                dataOperType = DataOperType.IMPORT_AWARD;
                requestReadWritePermissions();
                break;
            case R.id.btnExport:
                dataOperType = DataOperType.EXPORT_AWARD;
                requestReadWritePermissions();
                break;
            case R.id.btnOddEven:
                queryAwardParam.setSelectAwardAttr(AwardAttribute.ODD_EVEN);
                fetchAwardNo();
                changePreSelectView();
                break;
            case R.id.btnLargeSmall:
                queryAwardParam.setSelectAwardAttr(AwardAttribute.LARGE_SMALL);
                fetchAwardNo();
                changePreSelectView();
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
     * 初始化预选行视图
     */
    private void intPreSelectView(){

        tvFirstLeft = findViewById(R.id.tvFirstLeft);
        tvFirstRight = findViewById(R.id.tvFirstRight);
        tvSecondLeft = findViewById(R.id.tvSecondLeft);
        tvSecondRight = findViewById(R.id.tvSecondRight);
        tvThirdLeft = findViewById(R.id.tvThirdLeft);
        tvThirdRight = findViewById(R.id.tvThirdRight);

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

    /**
     * 改变预选行状态
     */
    private void changePreSelectView(){
        if(AwardAttribute.ODD_EVEN.equals(queryAwardParam.getSelectAwardAttr())){
            tvFirstLeft.setText("奇");
            tvFirstRight.setText("偶");
            tvSecondLeft.setText("奇");
            tvSecondRight.setText("偶");
            tvThirdLeft.setText("奇");
            tvThirdRight.setText("偶");
        }else if(AwardAttribute.LARGE_SMALL.equals(queryAwardParam.getSelectAwardAttr())){
            tvFirstLeft.setText("大");
            tvFirstRight.setText("小");
            tvSecondLeft.setText("大");
            tvSecondRight.setText("小");
            tvThirdLeft.setText("大");
            tvThirdRight.setText("小");
        }

        tvFirstLeft.setTextColor(getResources().getColor(R.color.colorFont3,null));
        tvFirstRight.setTextColor(getResources().getColor(R.color.colorFont3,null));
        tvSecondLeft.setTextColor(getResources().getColor(R.color.colorFont3,null));
        tvSecondRight.setTextColor(getResources().getColor(R.color.colorFont3,null));
        tvThirdLeft.setTextColor(getResources().getColor(R.color.colorFont3,null));
        tvThirdRight.setTextColor(getResources().getColor(R.color.colorFont3,null));
    }

    /**
     * 导出奖号
     */
    private void requestReadWritePermissions(){
        //外部存储的读写权限
        String[] PM_MULTIPLE={ Manifest.permission.READ_EXTERNAL_STORAGE,
                               Manifest.permission.WRITE_EXTERNAL_STORAGE};
        try{
            //如果操作系统SDK级别在23之上（android6.0），就进行动态权限申请
            if(Build.VERSION.SDK_INT>=23){

                ArrayList<String> pmList=new ArrayList<>();

                //获取当前未授权的权限列表
                for(String permission:PM_MULTIPLE){
                    int nRet= ActivityCompat.checkSelfPermission(this,permission);
                    Log.i(TAG,"checkSelfPermission nRet="+nRet);
                    if(nRet!= PackageManager.PERMISSION_GRANTED){
                        pmList.add(permission);
                    }
                }

                //对未授权的权限进行申请
                if(pmList.size()>0){
                    Log.i(TAG,"进行权限申请...");
                    String[] sList=pmList.toArray(new String[0]);
                    ActivityCompat.requestPermissions(this,sList,REQUEST_CODE_EXPORT_AWARD_NO);
                }
                //有读写权限直接进行导出操作
                else{
                    importOrExportAward();
                }
            }
        }catch(Exception e){
            Log.e(TAG,"获取外部存储读写权限出错",e);
        }
    }

    private void importOrExportAward(){

        if(DataOperType.EXPORT_AWARD.equals(dataOperType)){

            String result = awardPresenter.exportAwardNo();
            Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();

        }else if(DataOperType.IMPORT_AWARD.equals(dataOperType)){

            try {
                String result = awardPresenter.importAwardNo(getResources().getAssets().open(AwardNo3DModel.EXPORT_FILE_NAME));
                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                fetchAwardNo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(REQUEST_CODE_EXPORT_AWARD_NO == requestCode){
            //允许再次询问申请的权限列表（权限上次是否被拒绝过，但可继续请求）
            ArrayList<String> requestAgainList=new ArrayList<>();
            //被禁止的权限列表，即使再次请求也不会弹框。（1、对话框中选择了 Don’t ask again 选项；2、设备规范禁止应用具有该权限）
            ArrayList<String> biddenList=new ArrayList<>();

            for(int i=0;i<permissions.length;i++){

                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.i(TAG,"【"+permissions[i]+"】权限授权成功");
                }
                else{
                    //判断是否允许重新申请该权限
                    boolean nRet=ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[i]);
                    Log.i(TAG,"shouldShowRequestPermissionRationale nRet="+nRet);
                    if(nRet){//允许重新申请
                        requestAgainList.add(permissions[i]);
                    }
                    else{//禁止申请
                        biddenList.add(permissions[i]);
                    }
                }
            }

            //优先对禁止列表进行判断,告知权限的作用，并提示用户手动申请
            if(biddenList.size()>0){//告知该权限作用，要求手动授予权限
                showFinishedDialog();
            }
            //告知权限的作用，并重新申请
            else if(requestAgainList.size()>0){
                showTipDialog(requestAgainList);
            }
            //权限都申请通过
            else{
                importOrExportAward();
            }
        }
    }

    /**
     * 告知用户权限被禁止，需要手动开启
     */
    public void showFinishedDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("警告")
                .setMessage("请前往设置中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定", (dialog1, which) -> {
                    // 一般情况下如果用户不授权的话，功能是无法运行的，做退出处理
                    dialog1.dismiss();
                })
                .create();
        dialog.show();
    }

    /**
     * 再次申请权限弹框
     * @param pmList
     */
    public void showTipDialog(ArrayList<String> pmList){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("【"+pmList.toString()+"】权限为应用必要权限，请授权")
                .setPositiveButton("确定", (dialog1, which) -> {
                    String[] sList=pmList.toArray(new String[0]);
                    //重新申请该权限
                    ActivityCompat.requestPermissions(MainActivity.this,sList,10000);
                })
                .create();
        dialog.show();
    }
}