package com.zqw.widely.view.activity;


import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zqw.widely.R;
import com.zqw.widely.bean.Users;
import com.zqw.widely.common.Common;
import com.zqw.widely.common.PreferencesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.widget.PopupWindow;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends AppCompatActivity {
    static String YES = "yes";
    static String NO = "no";
    static String name, pwd;
    private String isMemory = "";//isMemory变量用来判断SharedPreferences有没有数据，包括上面的YES和NO
    private String FILE = "saveUserNamePwd";//用于保存SharedPreferences的文件
    @Bind(R.id.et_uname)
    EditText etUname;
    @Bind(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.textInputLayout3)
    TextInputLayout textInputLayout3;
    @Bind(R.id.btn_loading)
    Button btnLoading;
    @Bind(R.id.tv_reg)
    TextView tvReg;
    @Bind(R.id.tv_forpas)
    TextView tvForpas;
    @Bind(R.id.tv_three)
    TextView tvThree;
    @Bind(R.id.cb_pwd)
    CheckBox cbPwd;
    @Bind(R.id.ch_re)
    CheckBox chRe;
    SharedPreferences sp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sp = getSharedPreferences(FILE, MODE_PRIVATE);
        isMemory = sp.getString("isMemory", NO);

        if (isMemory.equals(YES)) {
            name = sp.getString("name", "");
            pwd = sp.getString("pwd", "");
            etUname.setText(name);
            etPwd.setText(pwd);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, etUname.toString());
        editor.putString(pwd, etPwd.toString());
        editor.commit();
    }

    @OnClick({R.id.tv_reg, R.id.tv_forpas, R.id.tv_three, R.id.cb_pwd, R.id.ch_re})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reg:
                //注册
                registe();
                break;
            case R.id.tv_forpas:
                //忘记密码
                forgetPwd();
                break;
            case R.id.tv_three:
                //第三方登录
                loadThree();
                break;
            case R.id.cb_pwd:
                //密码可见
                dispwd();
                break;
            case R.id.btn_loading:
                loading();
                break;
        }
    }

    private void loading() {
        String name = etUname.getText().toString();  //获取控件的文本
        String pwd = etPwd.getText().toString();
        remenber();
        BmobUser bu2 = new BmobUser();
        bu2.setUsername(name);
        bu2.setPassword(pwd);                           //实例化用户对象
        bu2.login(LoginActivity.this,new SaveListener() {

            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                saveUserInfo(Common.LOGIN_TYPE_NORMAL, null);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                clearInput();
            }
        });
    }
    private void saveUserInfo(int loginType, BmobUser.BmobThirdUserAuth authInfo) {

        Users user = BmobUser.getCurrentUser(LoginActivity.this, Users.class);
        PreferencesManager preferences = PreferencesManager.getInstance(LoginActivity.this);
        preferences.put(Common.IS_LOGIN, true);
        preferences.put(Common.LOGINTYPE, loginType);
        preferences.put(Common.USER_NAME, user.getUsername());
        preferences.put(Common.USER_PHOTO, user.getPhoto());
        preferences.put(Common.USER_PWD, etPwd.getText().toString());
        if(authInfo != null){
            preferences.put(authInfo);
        }
        LoginActivity.this.finish();
    }

    private void clearInput() {
        etUname.setText("");
        etPwd.setText("");
    }

    private void remenber() {


    }

    private void loadThree() {
        startActivity(new Intent(this, LoginTActivity.class));
    }

    private void dispwd() {
        if (cbPwd.isChecked()) {
            //设置EditText的密码为可见的
            etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //设置密码为隐藏的
            etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void forgetPwd() {
        startActivity(new Intent(this, ForgetPwdActivity.class));
    }

    private void registe() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
