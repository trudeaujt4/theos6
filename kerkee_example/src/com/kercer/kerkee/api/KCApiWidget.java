package com.kercer.kerkee.api;

import org.json.JSONException;
import org.json.JSONObject;

import com.kercer.kerkee.bridge.KCArgList;
import com.kercer.kerkee.bridge.KCJSDefine;
import com.kercer.kerkee.browser.KCJSBridge;
import com.kercer.kerkee.log.KCLog;
import com.kercer.kerkee.webview.KCWebView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class KCApiWidget
{

	public static void toast(final KCWebView aWebView, KCArgList aArgList)
	{
		String str = aArgList.toString();
		Toast.makeText(aWebView.getContext(), str, Toast.LENGTH_SHORT).show();
		if (KCLog.DEBUG)
			KCLog.d(">>>>>> KCApiWidget toast called: " + aArgList.toString());
	}

	public static void commonApi(final KCWebView aWebView, KCArgList aArgList)
	{
		if (KCLog.DEBUG)
			KCLog.d(">>>>>> KCApiWidget commonApi called: " + aArgList.toString());
		String callbackId = (String) aArgList.getArgValue(KCJSDefine.kJS_callbackId);
		JSONObject jsonObject = null;
		try
		{
			jsonObject = new JSONObject("{'key'='value'}");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		KCJSBridge.callbackJS(aWebView, callbackId, jsonObject);
	}

	public static void onSetImage(final KCWebView aWebView, KCArgList aArgList)
	{
		if (KCLog.DEBUG)
			KCLog.d(">>>>>> KCApiWidget onSetImage called: " + aArgList.toString());
	}

	public static void showDialog(final KCWebView aWebView, KCArgList aArgList)
	{
		String jsonStr = aArgList.toString();
		final String callbackId = (String) aArgList.getArgValue(KCJSDefine.kJS_callbackId);
		KCLog.d(">>>>>> KCApiWidget showDialog called: " + callbackId + ",jsonStr>>>>>" + jsonStr);

		String title = aArgList.getArgValueString("title");
		String message = aArgList.getArgValueString("message");
		String okBtn = aArgList.getArgValueString("okBtn");
		String cancelBtn = aArgList.getArgValueString("cancelBtn");

		AlertDialog.Builder builder = new AlertDialog.Builder(aWebView.getContext());
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(okBtn, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				if (KCLog.DEBUG)
				{
					KCLog.d(">>>>>> KCApiWidget showDialog called: PositiveButton click");
				}
				KCJSBridge.callbackJS(aWebView, callbackId, "1");
			}
		});

		builder.setNegativeButton(cancelBtn, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				if (KCLog.DEBUG)
					KCLog.d(">>>>>> KCApiWidget showDialog called: NegativeButton click");
				KCJSBridge.callbackJS(aWebView, callbackId, "0");
			}
		});

		builder.show();
	}

	public static void alertDialog(final KCWebView aWebView, KCArgList aArgList)
	{
		String jsonStr = aArgList.toString();
		final String callbackId = (String) aArgList.getArgValue(KCJSDefine.kJS_callbackId);
		KCLog.d(">>>>>> KCApiWidget showDialog called: " + callbackId);

		/*
		 * if (KCLog.DEBUG) KCLog.d(">>>>>> NewApiTest showDialog called: " + jsonStr);
		 */

		String title = aArgList.getArgValueString("title");
		String message = aArgList.getArgValueString("message");
		String okBtn = aArgList.getArgValueString("okBtn");
		String cancelBtn = aArgList.getArgValueString("cancelBtn");

		AlertDialog.Builder builder = new AlertDialog.Builder(aWebView.getContext());
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(okBtn, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				if (KCLog.DEBUG)
				{
					KCLog.d(">>>>>> KCApiWidget showDialog called: PositiveButton click");
				}
				KCJSBridge.callbackJS(aWebView, callbackId, "1");
			}
		});

		builder.show();
	}

}
