package com.doadway.dwclient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

	private WebView mWebView = null;
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		
		mWebView = (WebView) findViewById(R.id.mobileWebView); 
		mWebView.getSettings().setJavaScriptEnabled(true); 
		mWebView.getSettings().setDefaultTextEncodingName("UTF-8") ;
		mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		//gl error from 0x502
		mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);;
        // 如果不设置这个，JS代码中的按钮会显示，但是按下去却不弹出对话框
		mWebView.setWebChromeClient(new WebChromeClient()
        {

            @Override
            public boolean onJsAlert(WebView view, String url, String message,JsResult result)
            {
                // TODO Auto-generated method stub
                return super.onJsAlert(view, url, message, result);
            }

        });
        
		
		 
		//调用客户端loadData方法 
		mWebView.loadUrl("file:///android_asset/index.html");
//		mWebView.setWebViewClient(new MyWebViewClient());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private class WebAppInterface{
		
        Context mContext;
 
        /** Instantiate the interface and set the context */
        WebAppInterface(Context c)
        {
            this.mContext = c;
   
            
        }
        /** Show a toast from the web page */
        // 如果target 大于等于API 17，则需要加上如下注解
         @JavascriptInterface
        public void showToast(String toast)
        {
            // Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
        }
       
	}
	
	private class MyWebViewClient extends WebViewClient {
		
		 @Override  
	      public void onPageFinished(WebView view, String url) {  
	          super.onPageFinished(view, url);  
	          //在这里执行你想调用的js函数 
//			  String api = "http://10.0.2.2:8080/dwcms/remote/hessianService";  
//			  String url = "http://www.doadway.com/remote/hessianService";  
//			  HessianProxyFactory factory = new HessianProxyFactory(); 
//	          factory.setReadTimeout(50000);
//			  HessianService hessianServer;
//			  try {
//				hessianServer = (HessianService)factory.create(HessianService.class, api,getClassLoader());
//				String ret = hessianServer.sayHello("zhoutanxin");  
//				view.loadUrl("javascript:loadData('"+ret+"');");
//			  }catch(MalformedURLException e){
//				  e.printStackTrace();
//			  }
	      }  
		 
	}

}
