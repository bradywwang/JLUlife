package com.brady.jlulife.Entities.Weather;

/**
 * Created by brady on 15-12-15.
 */
public class WeatherInfo {
    private int errNum;
    private String errMsg;
    private WeatherData retData;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public WeatherData getRetData() {
        return retData;
    }

    public void setRetData(WeatherData retData) {
        this.retData = retData;
    }
}
