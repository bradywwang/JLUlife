package com.brady.jlulife.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.brady.jlulife.Utils.ConstValue;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @author chenzheng
 * @ClassName: WXEntryActivity
 * @Description: 微信分享工具类
 * @date 2014-7-24 下午1:54:13
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, ConstValue.WX_APPID, false);
        api.registerApp(ConstValue.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法


    @Override
    public void onResp(BaseResp baseResp) {
        String  result;

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "用户取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "认证失败";
                break;
            default:
                result = "未知错误";
                break;
        }

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

}
