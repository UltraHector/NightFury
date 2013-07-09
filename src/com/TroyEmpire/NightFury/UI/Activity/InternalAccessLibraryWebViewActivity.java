package com.TroyEmpire.NightFury.UI.Activity;

import com.TroyEmpire.NightFury.Constant.Constant;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InternalAccessLibraryWebViewActivity extends Activity {

	private WebView libWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置窗口模式，因为需要显示进度条在标题栏 
		requestWindowFeature(Window.FEATURE_PROGRESS);  
        setProgressBarVisibility(true); 
		setContentView(R.layout.activity_internal_access_library_web_view);

		libWebView = (WebView) findViewById(R.id.webView);

		//libWebView.getSettings().setJavaScriptEnabled(true); // enable the java script
		libWebView.getSettings().setSupportZoom(true);

		libWebView.setWebViewClient(new WebViewClient()); // set a webview
															// client to handle
															// the request
		libWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				// Activity和Webview根据加载程度决定进度条的进度大小
				// 当加载到100%的时候 进度条自动消失
				InternalAccessLibraryWebViewActivity.this.setProgress(progress * 100);
			}
		});
		String url = Constant.LibraryUrl;
		libWebView.loadUrl(url);

	}

	// 返回键的处理
	public boolean onKeyDown(int keyCoder, KeyEvent event) {

		if (libWebView.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
			libWebView.goBack(); 	// goBack()表示返回webView的上一页面
			return true;
		}
		return super.onKeyDown(keyCoder, event); 	// 没有后退时，返回main activity
	}

}
