package com.zqw.widely.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zqw.widely.R;
import com.zqw.widely.bean.Users;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {
    @Bind(R.id.et_uname)
    EditText etUname;
    @Bind(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.textInputLayout3)
    TextInputLayout textInputLayout3;
    @Bind(R.id.btn_reg)
    Button btnReg;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.tv_info)
    TextView tvInfo;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        Bmob.initialize(this, "a340adcdaedbaeda7d35a24c20f69eda");

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reg:
                register();
                break;
            case R.id.checkBox:
                checkpwd();
                break;
            case R.id.tv_info:
                startActivity(intent = new Intent(this, DetailInfoActivity.class));
                break;

        }

    }

    private void checkpwd() {
        if (checkBox.isChecked()) {
            //设置EditText的密码为可见的
            etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //设置密码为隐藏的
            etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void register() {
        String name = etUname.getText().toString();
        String password = etPwd.getText().toString();
        if (name.equals("") || password.equals("")) {
            Toast.makeText(this, "帐号或密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "密码不安全", Toast.LENGTH_LONG).show();
            return;
        }
        Users users = new Users();
        users.setUsername(name);
        users.setPassword(password);
        users.signUp(RegisterActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

       /*     @Override
            public void done(Users s, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //验证码正确 添加用户信息
                    String name = etUname.getText().toString();
                    String password = etPwd.getText().toString();

                    Users user = new Users();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String arg0, BmobException arg1) {
                            // TODO Auto-generated method stub
                            if (arg1 == null) {
                                return;

                            } else {
                                return;
                            }
                        }
                    });
                }
            }
        });
    }

}*/
