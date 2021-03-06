package com.example.anapp3d.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
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
    public static int REQUEST_CODE_MANAGE_ALL_FILES_PERMISSION = 1024;

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
        btnQishu.setText(queryAwardParam.getPageSize()+"???");

        intPreSelectView();

        awardPresenter = new AwardPresenter();
        fetchAwardNo();
        switchBtnDirectGroupStyle();
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
                switchBtnDirectGroupStyle();
                break;
            case R.id.btnSwitchGroup:
                queryAwardParam.setSelectDirectOrGroup(DirectOrGroup.GROUP);
                fetchAwardNo();
                switchBtnDirectGroupStyle();
                break;
            case R.id.btnImport:
                dataOperType = DataOperType.IMPORT_AWARD;
                importAwardDialog();
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

    /**
     * ??????????????????????????????
     */
    private void switchBtnDirectGroupStyle(){
        if(DirectOrGroup.DIRECT.equals(queryAwardParam.getSelectDirectOrGroup())){
            btnSwitchDirect.setTextColor(getResources().getColor(R.color.colorTimes,null));
            btnSwitchGroup.setTextColor(getResources().getColor(R.color.white,null));
        }else if(DirectOrGroup.GROUP.equals(queryAwardParam.getSelectDirectOrGroup())){
            btnSwitchDirect.setTextColor(getResources().getColor(R.color.white,null));
            btnSwitchGroup.setTextColor(getResources().getColor(R.color.colorTimes,null));
        }
    }

    private void fetchAwardNo(){
        List<AwardNo3DVo> awardNo3DVoList =  awardPresenter.getAwardNoVoList(queryAwardParam);
        if(awardNo3DVoList.size()>0){
            awardRecycleAdapter.loadData(awardNo3DVoList,true);
        }
    }

    /**
     * ???????????????
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
            tvTitle.setText("??????");
            etIssueNo.setText(awardNo3DVo.getIssueNo());
            etAwardNo.setText(awardNo3DVo.getAwardNo());
        }else{
            etIssueNo.setText(String.valueOf(awardPresenter.getLastAwardNo().getIssueNo()+1));
        }

        btnOK.setOnClickListener(v -> {
            String issueNo = etIssueNo.getText().toString().trim();
            if(TextUtils.isEmpty(issueNo)){
                Toast.makeText(MainActivity.this,"???????????????",Toast.LENGTH_SHORT).show();
                return;
            }
            String awardNoStr = etAwardNo.getText().toString().trim();
            if(TextUtils.isEmpty(awardNoStr)){
                Toast.makeText(MainActivity.this,"???????????????",Toast.LENGTH_SHORT).show();
                return;
            }
            char[] awardNo = awardNoStr.toCharArray();
            if(awardNo.length !=3){
                Toast.makeText(MainActivity.this,"?????????????????????",Toast.LENGTH_SHORT).show();
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

        dialog.setCancelable(false);//?????????????????????????????????????????????????????????????????????????????????????????????
        dialog.show();
    }

    /**
     * ????????????
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
                Toast.makeText(MainActivity.this,"???????????????",Toast.LENGTH_SHORT).show();
                return;
            }
            queryAwardParam.setPageSize(Integer.valueOf(editQishu));
            btnQishu.setText(editQishu+"???");

            fetchAwardNo();

            dialog.dismiss();
        });
        btnCancle.setOnClickListener(v -> dialog.dismiss());

        dialog.setCancelable(false);//?????????????????????????????????????????????????????????????????????????????????????????????
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
     * ????????????????????????
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
     * ?????????????????????
     */
    private void changePreSelectView(){
        if(AwardAttribute.ODD_EVEN.equals(queryAwardParam.getSelectAwardAttr())){
            tvFirstLeft.setText("???");
            tvFirstRight.setText("???");
            tvSecondLeft.setText("???");
            tvSecondRight.setText("???");
            tvThirdLeft.setText("???");
            tvThirdRight.setText("???");
        }else if(AwardAttribute.LARGE_SMALL.equals(queryAwardParam.getSelectAwardAttr())){
            tvFirstLeft.setText("???");
            tvFirstRight.setText("???");
            tvSecondLeft.setText("???");
            tvSecondRight.setText("???");
            tvThirdLeft.setText("???");
            tvThirdRight.setText("???");
        }

        tvFirstLeft.setTextColor(getResources().getColor(R.color.colorFont3,null));
        tvFirstRight.setTextColor(getResources().getColor(R.color.colorFont3,null));
        tvSecondLeft.setTextColor(getResources().getColor(R.color.colorFont3,null));
        tvSecondRight.setTextColor(getResources().getColor(R.color.colorFont3,null));
        tvThirdLeft.setTextColor(getResources().getColor(R.color.colorFont3,null));
        tvThirdRight.setTextColor(getResources().getColor(R.color.colorFont3,null));
    }

    /**
     * ??????????????????
     */
    private void requestReadWritePermissions(){
        //???????????????????????????
        String[] PM_MULTIPLE={ Manifest.permission.READ_EXTERNAL_STORAGE,
                               Manifest.permission.WRITE_EXTERNAL_STORAGE,
                               Manifest.permission.MANAGE_EXTERNAL_STORAGE};
        try{
            //??????????????????SDK?????????23?????????android6.0?????????????????????????????????
            if(Build.VERSION.SDK_INT>=23){

                ArrayList<String> pmList=new ArrayList<>();

                //????????????????????????????????????
                for(String permission:PM_MULTIPLE){

                    int nRet= ActivityCompat.checkSelfPermission(this,permission);

                    if(nRet!= PackageManager.PERMISSION_GRANTED){
                        if(permission == Manifest.permission.MANAGE_EXTERNAL_STORAGE){
                            if(!hasManageExternalStorage()){
                                pmList.add(permission);
                            }
                        }else{
                            pmList.add(permission);
                        }
                    }
                }

                //?????????????????????????????????
                if(pmList.size()>0){
                    Log.i(TAG,"??????????????????...");
                    String[] sList=pmList.toArray(new String[0]);
                    ActivityCompat.requestPermissions(this,sList,REQUEST_CODE_EXPORT_AWARD_NO);
                }
                //???????????????????????????????????????
                else{
                    importOrExportAward();
                }
            }
        }catch(Exception e){
            Log.e(TAG,"????????????????????????????????????",e);
        }
    }

    private void importOrExportAward(){

        if(DataOperType.EXPORT_AWARD.equals(dataOperType)){

            String result = awardPresenter.exportAwardNo();
            Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();

        }else if(DataOperType.IMPORT_AWARD.equals(dataOperType)){
            //???Assets??????????????????
            //String result = awardPresenter.importAwardNo(getResources().getAssets().open(AwardNo3DModel.EXPORT_FILE_NAME));
            String result = awardPresenter.importAwardNo();
            Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
            fetchAwardNo();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(REQUEST_CODE_EXPORT_AWARD_NO == requestCode){
            //????????????????????????????????????????????????????????????????????????????????????????????????
            ArrayList<String> requestAgainList=new ArrayList<>();
            //??????????????????????????????????????????????????????????????????1???????????????????????? Don???t ask again ?????????2?????????????????????????????????????????????
            ArrayList<String> biddenList=new ArrayList<>();

            for(int i=0;i<permissions.length;i++){

                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.i(TAG,"???"+permissions[i]+"?????????????????????");
                }
                else{
                    //???????????????????????????????????????
                    boolean nRet=ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[i]);

                    if(nRet){//??????????????????
                        requestAgainList.add(permissions[i]);
                    }else{//????????????
                        biddenList.add(permissions[i]);
                    }
                }
            }

            //?????????????????????????????????,???????????????????????????????????????????????????
            if(biddenList.size()>0){//????????????????????????????????????????????????
                showFinishedDialog();
            }
            //???????????????????????????????????????
            else if(requestAgainList.size()>0){
                showTipDialog(requestAgainList);
            }
            //?????????????????????
            else{
                importOrExportAward();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_MANAGE_ALL_FILES_PERMISSION) {
            // ?????????????????????????????????????????????
            if(hasManageExternalStorage()){
                importOrExportAward();
            }else{
                Toast.makeText(MainActivity.this,"????????????",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * ?????????????????????????????????????????????????????????
     * @return
     */
    public boolean hasManageExternalStorage(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if(Environment.isExternalStorageManager()){
                return true;
            }else{
                return false;
            }
        }
        return true;
    }

    /**
     * ?????????????????????
     */
    public void importAwardDialog(){
        String title = "??????Documents???????????????"+AwardNo3DModel.EXPORT_ROOT_DIRECT+"?????????????????????"+AwardNo3DModel.EXPORT_FILE_NAME+"????????????";
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("??????")
                .setMessage(title)
                .setPositiveButton("??????", (dialog1, which) -> {
                    requestReadWritePermissions();
                    dialog1.dismiss();
                })
                .setNegativeButton("??????",(dialog1, which) ->{
                    dialog1.dismiss();
                })
                .create();
        dialog.show();
    }

    /**
     * ????????????????????????????????????????????????
     */
    public void showFinishedDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("??????")
                .setMessage("????????????????????????????????????????????????????????????????????????")
                .setPositiveButton("??????", (dialog1, which) -> {

                    // ???targetSdkVersion = 30?????????????????????????????????????????????????????????????????????
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    MainActivity.this.startActivityForResult(intent, REQUEST_CODE_MANAGE_ALL_FILES_PERMISSION);

                    dialog1.dismiss();
                })
                .setNegativeButton("??????",(dialog1, which) -> {
                    dialog1.dismiss();
                })
                .create();
        dialog.show();
    }

    /**
     * ????????????????????????
     * @param pmList
     */
    public void showTipDialog(ArrayList<String> pmList){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("??????")
                .setMessage("???"+pmList.toString()+"??????????????????????????????????????????")
                .setPositiveButton("??????", (dialog1, which) -> {
                    String[] sList=pmList.toArray(new String[0]);
                    //?????????????????????
                    ActivityCompat.requestPermissions(MainActivity.this,sList,10000);
                })
                .create();
        dialog.show();
    }
}