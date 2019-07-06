package com.bwei.hming20190706;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bwei.hming20190706.entity.RequestEntity;
import com.bwei.hming20190706.mvp.IUserContract;
import com.bwei.hming20190706.mvp.IUserPresenter;
import com.hb.dialog.myDialog.ActionSheetDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements IUserContract.IUserView {


    private Unbinder bind;
    private IUserContract.IUserPresenter presenter;
    @BindView(R.id.iv_main)
    ImageView iv;
    @BindView(R.id.tv_name)
    TextView nameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /* if (!SPUtils.getInstance().getString("Image", null).equals("")) {
            String image = SPUtils.getInstance().getString("Image", null);
            Glide.with(MainActivity.this).load(image).into(iv);
        }*/

        inidView();
        initEvent();
    }

    /**
     * 绑定事件
     */
    private void initEvent() {
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionSheetDialog builder = new ActionSheetDialog(MainActivity.this).builder().setTitle("请选择")
                        .addSheetItem("相机", null, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int i) {
                                PictureSelector.create(MainActivity.this)
                                        .openGallery(PictureMimeType.ofImage())
                                        .compress(true)
                                        .forResult(PictureConfig.CHOOSE_REQUEST);
                            }
                        }).addSheetItem("相册", null, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int i) {
                                PictureSelector.create(MainActivity.this)
                                        .openCamera(PictureMimeType.ofImage())
                                        .compress(true)
                                        .forResult(PictureConfig.CHOOSE_REQUEST);
                            }
                        });
                builder.show();
            }
        });
    }

    /**
     * 初始化对象
     */
    private void inidView() {
        bind = ButterKnife.bind(this);

        presenter = new IUserPresenter();
        presenter.attach(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (bind != null) {
            bind.unbind();
            bind = null;
        }
        // 解绑
        presenter.detach();
    }

    @Override
    public void showData(RequestEntity entity) {
        if (entity.message.equals("上传成功")) {
            Toast.makeText(this, "上传状态===" + entity.message, Toast.LENGTH_SHORT).show();
            PictureFileUtils.deleteCacheDirFile(MainActivity.this);
            Glide.with(MainActivity.this).load(entity.headPath).into(iv);
            SPUtils.getInstance().clear();
            SPUtils.getInstance().put("Image", entity.headPath);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                    String path = list.get(0).getCompressPath();
                    File file = new File(path);

                    // RequestBody requestBody = MultipartBody.create(MediaType.parse("image/*"), file);
                    //                    MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                    RequestBody body = MultipartBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), body);
                    HashMap<String, String> parms = new HashMap<>();
                    parms.put("userId", "3464");
                    parms.put("sessionId", "15624004035723464");

                    presenter.postImage(parms, part);
                    break;
            }
        }
    }
}
