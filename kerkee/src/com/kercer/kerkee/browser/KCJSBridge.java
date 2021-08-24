package com.kercer.kerkee.browser;

import android.view.ViewGroup;

import com.kercer.kerkee.bridge.KCApiBridge;
import com.kercer.kerkee.bridge.KCClass;
import com.kercer.kerkee.bridge.KCJSExecutor;
import com.kercer.kerkee.util.KCAssetTool;
import com.kercer.kerkee.webview.KCWebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author zihong
 *
 */
public class KCJSBridge
{
    protected KCWebView mWebView;
    private final String VERSION_NAME = "1.0.0";

    public KCJSBridge(KCWebView aWebView)
    {
        this.mWebView = aWebView;

        if (!isExitAsset())
            copyAssetHtmlDir();

//        File wwwRoot = new File(getResRootPath() + "/");
//        if(!KCHttpServer.isRunning())
//        	KCHttpServer.startServer(KCHttpServer.getPort(), wwwRoot);
    }

    public String getVersion()
    {
    	return VERSION_NAME;
    }


    /********************************************************/
    /*
     * js opt
     */
    /********************************************************/

    public static KCClass registJSBridgeClient(Class<?> aClass)
    {
        return KCApiBridge.registJSBridgeClient(aClass);
    }

    public static boolean registClass(KCClass aClass)
    {
        return KCApiBridge.registClass(aClass);
    }

    public static KCClass registClass(String aJSObjectName, Class<?> aClass)
    {
        return KCApiBridge.registClass(aJSObjectName, aClass);
    }

    public static void removeClass(String aJSObjectName)
    {
        KCApiBridge.removeClass(aJSObjectName);
    }

    public static void callJSOnMainThread(final KCWebView aWebview, final String aJS)
    {
    	KCJSExecutor.callJSOnMainThread(aWebview, aJS);
    }

    public static void callJS(final KCWebView aWebview, final String aJS)
    {
    	KCJSExecutor.callJS(aWebview, aJS);
    }

    public static void callJSFunctionOnMainThread(final KCWebView aWebview, String aFunName, String aArgs)
    {
    	KCJSExecutor.callJSFunctionOnMainThread(aWebview, aFunName, aArgs);
    }

    public static void callbackJS(KCWebView aWebview, String aCallbackId)
    {
    	KCJSExecutor.callbackJS(aWebview, aCallbackId);
    }

    public static void callbackJS(KCWebView aWebview, String aCallbackId, String aStr)
    {
    	KCJSExecutor.callbackJS(aWebview, aCallbackId, aStr);
    }

    public static void callbackJS(KCWebView aWebview, String aCallbackId, JSONObject aJSONObject)
    {
    	KCJSExecutor.callbackJS(aWebview, aCallbackId, aJSONObject);
    }

    public static void callbackJS(KCWebView aWebview, String aCallbackId, JSONArray aJSONArray)
    {
    	KCJSExecutor.callbackJS(aWebview, aCallbackId, aJSONArray);
    }


    /********************************************************/
    /*
     *
     */
    /********************************************************/

    private boolean isExitAsset()
    {
        String cfgPath = mWebView.getWebPath().getCfgPath();
        File file = new File(cfgPath);
        if (file.exists())
            return true;
        return false;
    }

    private void copyAssetHtmlDir()
    {
        KCAssetTool assetTool = new KCAssetTool(mWebView.getContext());
        try
        {
            assetTool.copyDir("html", mWebView.getWebPath().getResRootPath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public KCWebView getWebView()
    {
        return mWebView;
    }

    public String getResRootPath()
    {
        return mWebView == null ? null : mWebView.getWebPath().getResRootPath();
    }

    public void destroy()
    {
        ViewGroup vg = (ViewGroup) mWebView.getParent();
        if (vg != null)
            vg.removeView(mWebView);
        mWebView.clearCache(true);
        mWebView.destroy();
        mWebView = null;
    }
}
