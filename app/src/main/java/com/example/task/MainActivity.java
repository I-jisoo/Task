package com.example.task;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import cn.com.newland.nle_sdk.requestEntity.SignIn;
import cn.com.newland.nle_sdk.responseEntity.User;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private MyDBHelper dbHelper;
    private EditText username;
    private EditText userpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDBHelper(this, "UserStore.db", null, 1);
    }

    //点击注册按钮进入注册页面
    public void logonClicked(View view) {
        finish();
        Intent intent = new Intent(MainActivity.this, Main0Activity.class);
        startActivity(intent);
    }

    //点击登录按钮
    public  void loginClicked(View view) {
        String platformAddress = "http:api.nlecloud.com/Users/Login";
        username = (EditText) findViewById(R.id.editText3);
        userpassword = (EditText) findViewById(R.id.editText4);
        String userName = username.getText().toString().trim();
        String passWord = userpassword.getText().toString().trim();
        if(CheckIsDataAlreadyInDBorNot(userName)) {
            if (login(userName, passWord)) {
                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
              /*  final NetWorkBusiness netWorkBusiness = new NetWorkBusiness("",platformAddress);
                netWorkBusiness.signIn(new SignIn(userName, passWord), new NCallBack<BaseResponseEntity<User>>(){
             /*    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<User>> call, @NonNull Response<BaseResponseEntity<User>> response) {
                        BaseResponseEntity<User> baseResponseEntity ;	//获得响应体
                        if (baseResponseEntity != null) {
                            if (baseResponseEntity.getStatus() == 0) {
                                //需要传输秘钥
                                String accessToken = baseResponseEntity.getResultObj().getAccessToken();        //json数据返回
                                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("accessToken", accessToken);
                                intent.putExtras(bundle);
                                startActivity(intent);
                               finish();
                            } else {
                                Toast.makeText(MainActivity.this, baseResponseEntity.getMsg(), Toast.LENGTH_SHORT).show();  //返回为空...
                            }
                        }
                    }*/

               /*     @Override
                    protected void onResponse(BaseResponseEntity<User> response) {
                        BaseResponseEntity<User> baseResponseEntity = new BaseResponseEntity<>();    //获得响应体
                                //需要传输秘钥
                                String accessToken = baseResponseEntity.getResultObj().getAccessToken();        //json数据返回
                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("accessToken", accessToken);
                                intent.putExtras(bundle);
                                startActivity(intent);
                             //   finish();
                    }
                });*/
//                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent2);
            } else {
                Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this,"账号不存在",Toast.LENGTH_SHORT).show();
        }
    }

    //验证登录（账号和密码是否一致）
    public boolean login(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from userData where name=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }

    //验证登录（账号是否存在）
    public boolean CheckIsDataAlreadyInDBorNot(String value){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String Query = "Select * from userData where name =?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }


  /* private void login_api(String username,String password){
        String urlpath = "http:api.nlecloud.com/Users/Login";
        URL url;
        try {
            url = new URL(urlpath);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",username);
            jsonObject.put("password",password);
            //将参数put到json
            String content = String.valueOf(jsonObject);
            //开启连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);//允许写出
            conn.setDoInput(true);//允许读入
            conn.setRequestMethod("POST");//提交方式
            conn.setRequestProperty("Content-Type", "application/json");//设置参数类型是json格式
            //写输出流，将要转的参数写入流
            OutputStream os = conn.getOutputStream();
            os.write(content.getBytes());
            os.close();
            int code = conn.getResponseCode();
            if (code == 200) {
                //读取返回的json
                InputStream inputStream = conn.getInputStream();
                //调用NetUtils() 将流转成String类型
                String json = NetUtils.readString(inputStream);
                Gson gson = new Gson();
                b = gson.fromJson(json, bean.class);
            } else {
                Toast.makeText(getApplicationContext(), "数据提交失败", Toast.LENGTH_SHORT).show();
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class NetUtils {
        public static byte[] readBytes(InputStream is){
            try {
                byte[] buffer = new byte[1024];
                int len = -1 ;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while((len = is.read(buffer)) != -1){
                    baos.write(buffer, 0, len);
                }
                baos.close();
                return baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null ;
        }
        public static String readString(InputStream is){
            return new String(readBytes(is));
        }

    }*/
}
