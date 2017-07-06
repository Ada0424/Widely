package com.zqw.widely.view.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zqw.widely.R;
import com.zqw.widely.bean.Users;
import com.zqw.widely.common.Common;
import com.zqw.widely.common.PreferencesManager;
import com.zqw.widely.util.LogUtils;
import com.zqw.widely.util.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.alipay.friends.Alipay;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginTActivity extends LoginActivity implements PlatformActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_t);
        ButterKnife.bind(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_face:
               loginByFace();
                break;
            case R.id.iv_qq:
                loginByQQ();
                break;
            case R.id.iv_qzone:
                loginByQQZone();
                break;
            case R.id.iv_wetach:
                loginByWechat();
                break;
            case R.id.iv_sina:
                loginBySina();
                break;
            case R.id.iv_pay:
                loginByPay();
                break;


        }
    }
    private void loginByQQ() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        qq.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                PlatformDb data = arg0.getDb();
                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth("qq", data.getToken(), String.valueOf(data.getExpiresIn()), data.getUserId());
                loginWithAuth(authInfo, data);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        });
        //authorize与showUser单独调用一个即可
        //weibo.authorize();//单独授权,OnComplete返回的hashmap是空的
        qq.showUser(null);//授权并获取用户信息
    }

    private void loginByWechat() {
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        wechat.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                PlatformDb data = arg0.getDb();
                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth("weixin", data.getToken(), String.valueOf(data.getExpiresIn()), data.getUserId());
                loginWithAuth(authInfo, data);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        });
        //authorize与showUser单独调用一个即可
        //weibo.authorize();//单独授权,OnComplete返回的hashmap是空的
        wechat.showUser(null);//授权并获取用户信息
    }

    private void loginBySina() {
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        weibo.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                PlatformDb data = arg0.getDb();
                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth("weibo", data.getToken(), String.valueOf(data.getExpiresIn()), data.getUserId());
                loginWithAuth(authInfo, data);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        });
        //authorize与showUser单独调用一个即可
        //weibo.authorize();//单独授权,OnComplete返回的hashmap是空的
        weibo.showUser(null);//授权并获取用户信息
    }

    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo, final PlatformDb data) {
        BmobUser.loginWithAuthData(LoginTActivity.this, authInfo, new OtherLoginListener() {

            @Override
            public void onSuccess(JSONObject userAuth) {
                // TODO Auto-generated method stub
                LogUtils.i(authInfo.getSnsType() + "登陆成功返回:" + userAuth);
                Users user = BmobUser.getCurrentUser(LoginTActivity.this, Users.class);
                //更新登录的账户信息
                updateUserInfo(user, data, authInfo);
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                ToastUtils.shortToast(LoginTActivity.this, "第三方登录失败:" + msg);
            }
        });
    }

    private void updateUserInfo(Users user, PlatformDb data, final BmobUser.BmobThirdUserAuth authInfo) {
        Users newUser = new Users();
        newUser.setPhoto(data.getUserIcon());
        newUser.setSex("男".equals(data.getUserGender()) ? true : false);
        newUser.setUsername(data.getUserName());
        Users bmobUser = BmobUser.getCurrentUser(LoginTActivity.this, Users.class);
        newUser.update(LoginTActivity.this, bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                ToastUtils.shortToast(LoginTActivity.this, getString(R.string.update_userinfo_success));
                //保存登录信息到本地
                saveUserInfo(Common.LOGIN_TYPE_THIRD, authInfo);
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                ToastUtils.shortToast(LoginTActivity.this, getString(R.string.update_userinfo_failed) + msg);
            }
        });
    }

    private void saveUserInfo(int loginType, BmobUser.BmobThirdUserAuth authInfo) {
        /*
         * TODO 把用户的登录信息保存到本地：sp\sqlite：（登录状态，登录类别，登录账户信息）
         * 注意:为了保证数据安全，一般对数据进行加密
         * 通过BmobUser user = BmobUser.getCurrentUser(context)获取登录成功后的本地用户信息
         * 如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(context,MyUser.class)获取自定义用户信息
         * */
        Users user = BmobUser.getCurrentUser(LoginTActivity.this, Users.class);
        PreferencesManager preferences = PreferencesManager.getInstance(LoginTActivity.this);
        preferences.put(Common.IS_LOGIN, true);
        preferences.put(Common.LOGINTYPE, loginType);
        preferences.put(Common.USER_NAME, user.getUsername());
        preferences.put(Common.USER_PHOTO, user.getPhoto());
        preferences.put(Common.USER_PWD,etPwd .getText().toString());
        if(authInfo != null){
            preferences.put(authInfo);
        }
        LoginTActivity.this.finish();
    }
    private void loginByFace() {
        Platform face= ShareSDK.getPlatform(Facebook.NAME);
        face.setPlatformActionListener(this);
        face.authorize();
    }

    private void loginByQQZone() {
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(this);
        qzone.authorize();
    }

    private void loginByPay() {
        Platform pay = ShareSDK.getPlatform(Alipay.NAME);
        pay.setPlatformActionListener(this);
        pay.authorize();
    }



    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Looper.prepare();
        PlatformDb userDB = platform.getDb();
        String result = platform.getDb().exportData();
        Log.i("TAG", result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

        //TODO 根据获取信息
        String userID = userDB.getUserId();
        String icon = userDB.getUserIcon();
        String token = userDB.getToken();
        String nickname = userDB.getUserName();

        Looper.loop();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(this, "取消授权", Toast.LENGTH_SHORT).show();
    }

}
