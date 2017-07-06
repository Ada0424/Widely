package com.zqw.widely.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;
import com.zqw.widely.R;
import com.zqw.widely.bean.Province;
import com.zqw.widely.bean.Users;
import com.zqw.widely.common.Common;
import com.zqw.widely.common.PreferencesManager;
import com.zqw.widely.util.CityDataUtil;
import com.zqw.widely.util.GlideLoader;
import com.zqw.widely.util.ImageLoader;
import com.zqw.widely.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


public class DetailInfoActivity extends AppCompatActivity {


    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.civ_pho)
    CircleImageView civPho;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.com)
    Button com;
    @Bind(R.id.layout_photo)
    LinearLayout layoutPhoto;
    private ArrayList<Province> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private ArrayList<String> sexItems = new ArrayList<>();
    private ArrayList<String> ageItems = new ArrayList<>();
    private String photoUrl, sex, age, address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.textView, R.id.civ_pho, R.id.tv_sex, R.id.tv_age, R.id.tv_add, R.id.com,R.id.layout_photo})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_photo:
                showPho();
                break;
            case R.id.textView:
                break;
            case R.id.tv_sex:
                showSex();
                break;
            case R.id.tv_age:
                showAge();
                break;
            case R.id.tv_add:
                showAdd();
                break;
            case R.id.com:
                showCom();
                break;
        }
    }

    public static final int REQUEST_CODE = 1000;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);

            if (pathList.size() > 0) {
                //由于单选只需要回去第一个数据就好,获取图片URL并上传
                uploadPhotoForURL(pathList.get(0));
            } else {
                Toast.makeText(DetailInfoActivity.this, "选择图片失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadPhotoForURL(String path) {
        {
            final BmobFile bmobFile = new BmobFile(new File(path));
            bmobFile.uploadblock(DetailInfoActivity.this, new UploadFileListener() {

                @Override
                public void onSuccess() {
                    //bmobFile.getFileUrl(context)--返回的上传文件的完整地址
                    photoUrl = bmobFile.getFileUrl(DetailInfoActivity.this);
                    ImageLoader.getInstance().displayImageTarget(civPho, photoUrl);
                    ToastUtils.shortToast(DetailInfoActivity.this, getString(R.string.upload_photo_success) + photoUrl);
                }

                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }

                @Override
                public void onFailure(int code, String msg) {
                    ToastUtils.shortToast(DetailInfoActivity.this, getString(R.string.upload_failed) + msg);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        options1Items = CityDataUtil.getProvinceData();
        options2Items = CityDataUtil.getCityData();
        options3Items = CityDataUtil.getAreData();
    }

    private void showPho() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.colorPrimary))
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
                .crop(1, 1, 300, 300)
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                .requestCode(REQUEST_CODE)
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/Widely/Pictures")
                .build();

        ImageSelector.open(DetailInfoActivity.this, imageConfig);   // 开启图片选择器
    }


    private void showAdd() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);
                tvAdd.setText(tx);
            }
        }).setTitleText(getString(R.string.sel_city))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }

    private void showAge() {
        for (int i = 1; i < 200; i++) {
            ageItems.add(String.valueOf(i));
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvAge.setText(ageItems.get(options1));
            }
        })
                .setTitleText(getString(R.string.sel_age))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(ageItems);//一级选择器
        pvOptions.show();
    }

    private void showSex() {
        sexItems.add("男");
        sexItems.add("女");
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvSex.setText(sexItems.get(options1));
            }
        })
                .setTitleText(getString(R.string.sel_sex))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(sexItems);//一级选择器
        pvOptions.show();
    }

    private void showCom() {
        sex = tvSex.getText().toString();
        age = tvAge.getText().toString();
        address = tvAdd.getText().toString();
        if (!TextUtils.isEmpty(photoUrl)
                && !TextUtils.isEmpty(sex)
                && !TextUtils.isEmpty(age)
                && !TextUtils.isEmpty(address)) {
            Users newUser = new Users();
            newUser.setPhoto(photoUrl);
            newUser.setSex("男".equals(sex) ? true : false);
            newUser.setAge(Integer.valueOf(age));
            newUser.setAddress(address);
            Users bmobUser = BmobUser.getCurrentUser(DetailInfoActivity.this, Users.class);
            newUser.update(DetailInfoActivity.this, bmobUser.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    ToastUtils.shortToast(DetailInfoActivity.this, getString(R.string.update_userinfo_success));
                    PreferencesManager.getInstance(DetailInfoActivity.this).put(Common.USER_PHOTO, photoUrl);
                    DetailInfoActivity.this.finish();
                }

                @Override
                public void onFailure(int code, String msg) {
                    // TODO Auto-generated method stub
                    ToastUtils.shortToast(DetailInfoActivity.this, getString(R.string.update_userinfo_failed) + msg);
                }
            });
        } else {
            ToastUtils.shortToast(DetailInfoActivity.this, getString(R.string.checkinfo));
        }
    }

}
