package com.example.androidproject.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.MainActivity;
import com.example.androidproject.R;

public class LoginActivity extends AppCompatActivity{
    private Boolean login_state=false;
    private TextView mregister;
    private ImageView mimageView;
    private ImageButton mimageButton;
    private EditText muesername, mpassword;
    private Button mconfirm;
    private Userdata data;
    private CheckBox checkBox;
    private String LOCAL_USER_NAME="paul";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        muesername = findViewById(R.id.et_username);
        mpassword = findViewById(R.id.et_password);
        mconfirm = findViewById(R.id.btn_confirm);
        mregister = findViewById(R.id.btn_register);
        checkBox=findViewById(R.id.cb_rm);
        data=new Userdata(LoginActivity.this);

        initLogin();
        mregister.setOnClickListener(new View.OnClickListener() {
            //注册按钮监听
            @Override
            public void onClick(View v) {
                {
                    if(data.getRegister(muesername.getText().toString(),mpassword.getText().toString()))
                    {
                        Toast.makeText(LoginActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"注册失败！用户名重复或者为空！",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        mconfirm.setOnClickListener(new View.OnClickListener() {
            //登录按钮监听
            @Override
            public void onClick(View v) {
                if(data.verifyPassword(muesername.getText().toString(),mpassword.getText().toString()))
                {
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    saveLogin(login_state);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"登陆失败！请检查用户是否存在或者密码错误！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //记住密码状态记录
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                login_state=isChecked;
            }
        });
    }
    private void saveLogin(boolean flag)
    {
        //保存登录状态
        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("ACCOUNT_REMEMBER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String secreat_name = muesername.getText().toString();
        String secreat_password=mpassword.getText().toString();
        if(sharedPreferences.getBoolean("flag",false))
        {
            return;
            //验证通过，不需要再次保存了
        }
        if(flag) {
            secreat_password=Base64.encodeToString(secreat_password.getBytes(),Base64.NO_WRAP);
            editor.putString("name",secreat_name);
            editor.putString("password", secreat_password);
            editor.putBoolean("flag", flag);
            editor.commit();
        }
        else{
            editor.clear();
            editor.putString("name",secreat_name);
            editor.putBoolean("flag",false);
            editor.commit();
        }

    }
    private void cleanState()
    {
        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("ACCOUNT_REMEMBER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
    private void initLogin()
            //初始化登录状态
    {
        SharedPreferences sharedPreferences=LoginActivity.this.getSharedPreferences("ACCOUNT_REMEMBER", MODE_PRIVATE);//指定文件名称
        if(sharedPreferences.getBoolean("flag",false)){
            String decode_password=sharedPreferences.getString("password","");
            decode_password= new String(Base64.decode(decode_password.getBytes(),Base64.NO_WRAP));
            muesername.setText(sharedPreferences.getString("name",""));
            mpassword.setText(decode_password);
            checkBox.setChecked(true);
        }
        else {
            muesername.setText(sharedPreferences.getString("name",""));
            checkBox.setChecked(false);
        }
    }
}


