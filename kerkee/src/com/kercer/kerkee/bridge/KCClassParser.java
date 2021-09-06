package com.kercer.kerkee.bridge;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 *
 * @author zihong
 *
 */
public class KCClassParser
{
    public final static String CLZ = "clz";
    public final static String ARGS = "args";
    public final static String METHOD = "method";

    private JSONObject mArgsJSON;

    private String mJSClzName;
    private String mJSMethodName;
    private KCArgList mArgList = new KCArgList();

    public String getJSClzName()
    {
        return mJSClzName;
    }

    public String getJSMethodName()
    {
        return mJSMethodName;
    }

    public KCArgList getArgList()
    {
        return mArgList;
    }

    public JSONObject getArgsJSON()
    {
        return mArgsJSON;
    }

    protected boolean isNull(Object aObj)
    {
       return aObj == null || aObj == JSONObject.NULL;
    }

    @SuppressWarnings("rawtypes")
    public KCClassParser(String jsonStr)
    {
        try
        {
            JSONObject json = new JSONObject(jsonStr);
            mJSClzName = json.get(CLZ).toString();
            mJSMethodName = json.get(METHOD).toString();
            if (json.has(ARGS))
            {
                mArgsJSON = json.getJSONObject(ARGS);
                Iterator it = mArgsJSON.keys();
                while (it.hasNext())
                {
                    String key = (String) it.next();
                    if (key != null)
                    {
                        Object value = mArgsJSON.get(key);
                        if(KCJSNull.isNull(value))
                            value = new KCJSNull();
                        KCArg arg = null;

                        if(key.equals(KCJSDefine.kJS_callbackId))
                        {
                        	arg = new KCArg(key, new KCJSCallback(value.toString()), KCJSCallback.class);
                        }
                        else
                        {
                        	arg = new KCArg(key, value);
						}
                        mArgList.addArg(arg);
                    }
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
