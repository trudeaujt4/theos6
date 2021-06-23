package com.kercer.kerkee.bridge.urd;

import java.net.URISyntaxException;
import java.util.HashMap;

import com.kercer.kerkee.net.uri.KCURI;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by liweisu on 15/9/6.
 * 
 * Optimization by zihong on 15/9/17
 * 
 */
public final class KCUriDispatcher
{
	private static String mDefaultScheme;
	private static HashMap<String, IUrlRegister> mUriRgisterMap = new HashMap<String, IUrlRegister>();

	private KCUriDispatcher()
	{
	}

	public static void dispatcher(String url)
	{
		KCURI uri = null;
		try
		{
			uri = KCURI.parse(url);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		if (uri != null)
		{
			IUrlRegister uriRegister = getUrlRegister(uri.getScheme());
			if (uriRegister != null)
			{
				uriRegister.dispatcher(uri);
			}
		}
	}

	/**
	 * before call defaultUriRegister,should set setDefaultScheme
	 * @param aScheme
	 */
	public static KCUriRegister markDefaultRegister(String aScheme)
	{
		KCUriRegister uriRegister = null;
		if (!TextUtils.isEmpty(aScheme))
		{
			mDefaultScheme = aScheme;			
			uriRegister = addUriRegisterWithScheme(aScheme);
		}
		
		return uriRegister;
	}

	/**
	 * if you call markDefaultRegister to set defaultUriRegister once before you call defaultUriRegister
	 */
	public static KCUriRegister defaultUriRegister()
	{
		if (TextUtils.isEmpty(mDefaultScheme) || !mUriRgisterMap.containsKey(mDefaultScheme))
		{
			return null;
		}
		
		KCUriRegister uriRegister = (KCUriRegister)mUriRgisterMap.get(mDefaultScheme);
		if (uriRegister == null)
		{
			uriRegister = markDefaultRegister(mDefaultScheme);
		}
		return uriRegister;
	}
	
	/**
	 * add Uri Register
	 */
	public static KCUriRegister addUriRegisterWithScheme(String aScheme)
	{
		KCUriRegister uriRegister = null;
		if (!TextUtils.isEmpty(aScheme))
		{
			uriRegister = new KCUriRegister(aScheme);
			mUriRgisterMap.put(aScheme, uriRegister);
		}
		return uriRegister;
	}

	/**
	 * return a Uri Register
	 */
	public static synchronized IUrlRegister getUrlRegister(String aScheme)
	{
		if (TextUtils.isEmpty(aScheme)) return null;
		
		return mUriRgisterMap.get(aScheme);		
	}
	
	public boolean addUriRegister(IUrlRegister aUriRegister)
	{
		if (aUriRegister != null)
		{
			String scheme = aUriRegister.scheme();
			if (!TextUtils.isEmpty(scheme))
			{
				mUriRgisterMap.put(scheme, aUriRegister);
				return true;
			}
		}
		return false;
	}
}
