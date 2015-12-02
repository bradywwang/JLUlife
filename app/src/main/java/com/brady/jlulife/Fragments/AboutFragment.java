package com.brady.jlulife.Fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.brady.jlulife.R;
import com.brady.jlulife.Utils.ConstValue;
import com.brady.jlulife.Utils.Utils;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class AboutFragment extends BaseFragment {
    private static AboutFragment fragment;
    private Button mBtnShareWx;
    private TextView mTvVersion;
    private Context mContext;
    private IWXAPI wxapi;
    SendMessageToWX.Req req;
    private byte [] mBitmap;
    private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().

    getAbsolutePath();
    private static final int THUMB_SIZE = 150;

    public static AboutFragment getInstance() {
        if (fragment == null)
            fragment = new AboutFragment();
        return fragment;
    }

    public AboutFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        mContext = getActivity().getApplicationContext();
        initWxShare();
        mBtnShareWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImg();

            }
        });
    }

    private void initComponents(View view) {
        mBtnShareWx = (Button) view.findViewById(R.id.btn_share_wx);
        mTvVersion = (TextView) view.findViewById(R.id.about_version);
    }

    private void initWxShare() {
        wxapi = WXAPIFactory.createWXAPI(mContext, ConstValue.WX_APPID, false);
        wxapi.registerApp(ConstValue.WX_APPID);
    }

    private void sendImg() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.share_pic);
        WXImageObject imgObj = new WXImageObject(bmp);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Utils.Bitmap2Bytes(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        wxapi.sendReq(req);
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
