package com.longke.flag.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.longke.flag.R;

import java.io.File;
import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 创建病历档案
 */
public class PublishFlagActivity extends AppCompatActivity {


   /* @InjectView(R.id.radioGroupID)
    RadioGroup radioGroupID;
    @InjectView(R.id.name_tv)
    EditText nameTv;
    @InjectView(R.id.time_tv)
    TextView timeTv;
    @InjectView(R.id.time_layout)
    RelativeLayout timeLayout;
    private ArrayList<String> mPhotoList;
    private ArrayList<String> selectPhotoList;
    private ImageShowAdapter mImagePathAdapter;
    private LayoutInflater mInflater;

    private ArrayList<String> paths;
    private ImageView back_img_btn;
    private TextView send_msg_btn;
    private EditText et_content;
    private String patientId;
    private String questId;
    protected AbLoadDialogFragment mDialogFragment;
    private BingLi bingLi;
    private String title;
    private TextView titleText;
    private TextView countText;
    public final static int REQUEST_CODE = 1;
    public final static int REQUEST_ADD_CODE = 2;
    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;*/

    ArrayList<String> selectedPhotos = new ArrayList<>();
    private File PHOTO_DIR;
    private int diseaseType=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_case_activity);
        ButterKnife.inject(this);


    }


   /* public void previewPhoto(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void initEvents() {
        back_img_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String photo_dir = AbFileUtil
                .getImageDownloadDir(PublishFlagActivity.this);
        if (AbStrUtil.isEmpty(photo_dir)) {
            AbToastUtil.showToast(PublishFlagActivity.this, "�洢��������");
        } else {
            PHOTO_DIR = new File(photo_dir);
        }
        send_msg_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(nameTv.getText().toString().trim())){
                    AbToastUtil
                            .showToast(PublishFlagActivity.this,
                                    "请先填写报告名称");
                    return;
                }
                if(TextUtils.isEmpty(timeTv.getText().toString().trim())){
                    AbToastUtil
                            .showToast(PublishFlagActivity.this,
                                    "请先填写检验时间/采用时间");
                    return;
                }
                if (TextUtils.isEmpty(et_content.getText()
                        .toString().trim()) && selectedPhotos.size() == 1) {
                    AbToastUtil
                            .showToast(PublishFlagActivity.this,
                                    "请填写描述或上传病历图片");
                    return;
                }
                send_msg_btn.setEnabled(false);
                upDate();

            }
        });
    }


    private void initData() {
        mPhotoList = new ArrayList<String>();
        paths = new ArrayList<String>();
        selectPhotoList = new ArrayList<String>();
        bingLi = (BingLi) getIntent().getSerializableExtra("bingLi");
        patientId = getIntent().getStringExtra("patientId");
        questId = getIntent().getStringExtra("questId");
        title = getIntent().getStringExtra("title");
        //titleText.setText(title);
        AbAppUtil.closeSoftInput(PublishFlagActivity.this);


    }

    private void initView() {
        selectedPhotos.add(String.valueOf(R.drawable.cam_photo));
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
        back_img_btn = (ImageView) findViewById(R.id.back_img_btn);
        send_msg_btn = (TextView) findViewById(R.id.send_msg_btn);
        et_content = (EditText) findViewById(R.id.detailInfo);
        titleText = (TextView) findViewById(R.id.current_section);
        countText = (TextView) findViewById(R.id.count_tv);
        mInflater = LayoutInflater.from(PublishFlagActivity.this);
        et_content.addTextChangedListener(watcher);
        radioGroupID.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.femaleGroupID){
                    diseaseType=1;
                }else{
                    diseaseType=2;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(PublishFlagActivity.class.getName());
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(PublishFlagActivity.class.getName());
        MobclickAgent.onPause(this);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == RESULT_OK) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            if (requestCode == REQUEST_ADD_CODE) {
                if (selectedPhotos.contains(String.valueOf(R.drawable.cam_photo))) {
                    selectedPhotos.remove(String.valueOf(R.drawable.cam_photo));
                }
                if (photos != null) {
                    selectedPhotos.addAll(photos);
                }
                if (selectedPhotos.size() < 20) {
                    selectedPhotos.add(String.valueOf(R.drawable.cam_photo));
                }


            } else {
                selectedPhotos.clear();
                if (photos != null) {
                    selectedPhotos.addAll(photos);
                }
                if (selectedPhotos.size() < 20) {
                    selectedPhotos.add(String.valueOf(R.drawable.cam_photo));
                }
            }
            photoAdapter.notifyDataSetChanged();

        }
    }

    public static File getFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }
    public File compressImage(String path) {
        try {
            File saveFile = new File(getExternalCacheDir(), "compress_" + System.currentTimeMillis() + ".jpg");
            Bitmap bitmap = BitmapFactory.decodeFile(path);

            Log.e("===compressImage===", "====开始==压缩==saveFile==" + saveFile.getAbsolutePath());
            NativeUtil.compressBitmap(bitmap, saveFile.getAbsolutePath());
            Log.e("===compressImage===", "====完成==压缩==saveFile==" + saveFile.getAbsolutePath());
            return  saveFile;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }


    *//**
     * ��ȡ����
     *//*
    public void upDate() {
        if (!AppUtils.isNetworkAvailable(UILApplication.getInstance())) {
            AbToastUtil.showToast(UILApplication.getInstance(), getString(R.string.wangluoyichang));
            return;
        }
        RequestParams params = new RequestParams();
        HttpUtils http = new HttpUtils();
        params.addBodyParameter("userId",
                AbSharedUtil.getInt(PublishFlagActivity.this, Constants.USER_ID)
                        + "");

        if (bingLi != null) {
            params.addBodyParameter("oid",
                    bingLi.getOid() + "");
        }

        params.addBodyParameter("detailInfo", et_content.getText().toString());
        params.addBodyParameter("disease_type", diseaseType+"");
        params.addBodyParameter("check_time", timeTv.getText().toString());
        params.addBodyParameter("title", nameTv.getText().toString());
        if (patientId != null) {
            params.addBodyParameter("patientId", patientId);
        }
        for (int i = 0; i < selectedPhotos.size(); i++) {
            if (!selectedPhotos.get(i).equals(String.valueOf(R.drawable.cam_photo))) {
                try {
                    params.addBodyParameter("fileUrl" + i,
                            compressImage(selectedPhotos.get(i)));
                } catch (Exception e) {

                }
            }
            ;

        }
        http.send(HttpRequest.HttpMethod.POST, Urls.DISEASE_SAVE, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        SVProgressHUD.showWithStatus(PublishFlagActivity.this, getString(R.string.send_case_ing));
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        SVProgressHUD.getProgressBar(PublishFlagActivity.this).setProgress((int) (current * 100 / total));
                        SVProgressHUD.getInstance(PublishFlagActivity.this).getmSharedView().setText(getString(R.string.shangchuan) + (current * 100 / total) + "%");
                        //SVProgressHUD.showInfoWithStatus(PublishFlagActivity.this,);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject obj = new JSONObject(responseInfo.result);
                            send_msg_btn.setEnabled(true);
                            if (obj.has("success")) {
                                if (obj.getBoolean("success")) {
                                    SVProgressHUD.dismiss(PublishFlagActivity.this);
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                            if (obj.has("content")) {

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        send_msg_btn.setEnabled(true);
                        SVProgressHUD.dismiss(PublishFlagActivity.this);
                        AbToastUtil.showToast(PublishFlagActivity.this, getString(R.string.wangluoyichang));
                    }
                });
    }



    public String getPath(Uri uri) {
        if (AbStrUtil.isEmpty(uri.getAuthority())) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        return path;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UILApplication.remove(PublishFlagActivity.this);
    }

    @OnClick(R.id.time_layout)
    public void onViewClicked() {
        TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                timeTv.setText(time.replace(" 00:00",""));
            }
        }, "1900-01-01 00:00", "2025-12-31 00:00");
        timeSelector.setMode(TimeSelector.MODE.YMD);//只显示 年月日
        timeSelector.show();
    }
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            countText.setText(s.length()+"/200");

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };*/
}
