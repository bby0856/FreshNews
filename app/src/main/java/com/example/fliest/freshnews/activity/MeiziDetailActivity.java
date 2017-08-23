package com.example.fliest.freshnews.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.fliest.freshnews.R;
import com.example.fliest.freshnews.Utils.DensityUtil;
import com.example.fliest.freshnews.Utils.DeviceInfoUtil;
import com.example.fliest.freshnews.other.SwipeBackHelper;
import com.example.fliest.freshnews.task.SaveImageBitmapTask;
import com.example.fliest.freshnews.task.SaveImageCutsomSizeTask;
import com.example.fliest.freshnews.task.SaveImageFileTask;
import com.example.fliest.freshnews.view.IView;
import com.example.fliest.freshnews.widget.ZoomImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeiziDetailActivity extends BaseActivity implements IView<String>, ZoomImageView.ClickedListener, View.OnClickListener {

    @BindView(R.id.toolbar_detail_meizi)
    Toolbar toolbar;
    @BindView(R.id.imageview_detail_meizi)
    ZoomImageView image;
    @BindView(R.id.prograss_meizi_detail_activity)
    ProgressBar mProgressBar;
    @BindView(R.id.statuebar_meizi_detail)
    View statuebar;
    @BindView(R.id.image_left_back_mzd)
    ImageView mImageView;

    private String urlString;
    private SharedPreferences mSp;
    private PopupWindow mPopupWindow;
    private Bitmap mCurrentBitmap;
    public boolean mConnected = false;
    public ConnectivityManager.NetworkCallback mNetworkCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initNetworkListener();
    }

    @Override
    public void init() {
        mSp = getSharedPreferences("firstshow", Context.MODE_PRIVATE);
        mConnected = checkConnectity();
    }

    @Override
    int getViewResId() {
        return R.layout.activity_meizi_detail2;
    }

    @Override
    void getBaseWidget() {
        baseToolbar = toolbar;
        baseXRecyclerView = null;
        baseLeftBack = mImageView;
    }

    @Override
    public void initView() {
        super.initView();

        image.setClickedListener(this);

        if (!mConnected) {
            showProgressDialog();
        } else {
            firstShow();
        }
    }

    private void firstShow() {
        boolean firstShow = mSp.getBoolean("firstshow", true);
        if (firstShow) {
            Snackbar.make(image, "长按可以保存图片哦", Snackbar.LENGTH_SHORT).show();

            mSp.edit().putBoolean("firstshow", false).commit();
        }
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        urlString = intent.getStringExtra("url");

        loadData();
    }

    public void initNetworkListener() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            builder = new NetworkRequest.Builder();
            mNetworkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mConnected == false) {
                                mConnected = true;
                                hideProgressDialog();
                                loadData();
                            }

                        }
                    });
                }

                @Override
                public void onLost(Network network) {
                    super.onLost(network);

                    mConnected = false;
                }
            };
            cm.registerNetworkCallback(builder.build(), mNetworkCallback);
        }
    }

    public boolean checkConnectity() {
        boolean isNetAvailable = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            isNetAvailable = false;
        } else if (networkInfo.isAvailable()) {
            isNetAvailable = true;
        }

        return isNetAvailable;
    }


    private RequestListener loadListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            mCurrentBitmap = ((GlideBitmapDrawable) resource).getBitmap();

            Palette.Builder builder = Palette.from(mCurrentBitmap);
            builder.generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    Palette.Swatch swatch = palette.getMutedSwatch();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (swatch != null) {
                            //getWindow().setStatusBarColor(swatch.getRgb());
                            statuebar.setBackgroundColor(swatch.getRgb());
                        }
                    }
                }
            });
            return false;
        }
    };


    @Override
    public void showProgressDialog() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        if (!mConnected) {
            Snackbar.make(image, "请检查网络。", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadData() {
        updateItems(urlString);
    }

    @Override
    public void loadMoreData() {

    }

    @Override
    public void updateItems(String url) {
        if (mConnected) {
            Glide.with(this)
                    .load(url)
                    .listener(loadListener)
                    //.placeholder(R.drawable.default_pic)   加了placeholder图片显示不全
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(image);

            image.setImageSrc(url);
        } else {
            showProgressDialog();
        }
    }


    @Override
    public void onClicked(boolean isShow) {
        int height = toolbar.getHeight();
        System.out.println("isShow:" + isShow);
        if (isShow) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(toolbar, "Y", -height);
            objectAnimator.setDuration(1000);
            objectAnimator.start();
        } else {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(toolbar, "Y", 0 + DensityUtil.dip2px(this, 23));
            objectAnimator.setDuration(1000);
            objectAnimator.start();
        }
    }

    @Override
    public void onLongClicked() {
        //showDialog(this);

        showPopupWindow(this);
        setWindowAlphaWithAnim(1.0f, 0.5f, 300, true);
    }

    private void setWindowAlphaWithAnim(final float start, final float end, long duration, final boolean toAppear) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float pencentage = animation.getAnimatedFraction();
                float bgAlpha;
                if (toAppear) {
                    bgAlpha = start - (start - end) * pencentage;
                } else {
                    bgAlpha = end + (start - end) * pencentage;
                }
                backgroundAlpha(bgAlpha);
            }
        });

        valueAnimator.setDuration(duration);
        valueAnimator.start();

    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void showPopupWindow(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_save_image_popup_window, null, false);
        int height = DeviceInfoUtil.getDeviceHeight(context) / 2;

        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, height, true);

        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);

        mPopupWindow.setAnimationStyle(R.style.PopupWindowInAndOut);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlphaWithAnim(1.0f, 0.5f, 300, false);
            }
        });

        mPopupWindow.showAtLocation(image, Gravity.BOTTOM, 0, 0);

        Button currentImageBtn = (Button) view.findViewById(R.id.btn_current_image);
        Button originalImagBtn = (Button) view.findViewById(R.id.btn_original_image);
        Button imageCustomBtn = (Button) view.findViewById(R.id.btn_image_custom);

        currentImageBtn.setOnClickListener(this);
        originalImagBtn.setOnClickListener(this);
        imageCustomBtn.setOnClickListener(this);

    }

    private void showDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("小主好眼力，快收了她")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, ImageSaveActivity.class);
                        if (urlString != null) {
                            intent.putExtra("imagesrc", urlString);
                        }
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_current_image:
                SaveImageBitmapTask saveImageBitmapTask = new SaveImageBitmapTask(this);
                saveImageBitmapTask.execute(mCurrentBitmap);
                mPopupWindow.dismiss();
                break;
            case R.id.btn_original_image:
                SaveImageFileTask saveImageFileTask = new SaveImageFileTask(this);
                saveImageFileTask.execute(urlString);
                mPopupWindow.dismiss();
                break;
            case R.id.btn_image_custom:
                SaveImageCutsomSizeTask saveImageCutsomSizeTask = new SaveImageCutsomSizeTask(this, 103, 105);
                saveImageCutsomSizeTask.execute(urlString);
                mPopupWindow.dismiss();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cm.unregisterNetworkCallback(mNetworkCallback);
        }
    }

    public void showDownloadImageResult(boolean isSuccess) {
        if (isSuccess) {
            Snackbar.make(image, "保存成功", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(image, "保存失败，请检查网络或重试", Snackbar.LENGTH_SHORT).show();
        }
    }
}
