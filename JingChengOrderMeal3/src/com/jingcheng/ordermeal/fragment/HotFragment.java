package com.jingcheng.ordermeal.fragment;

import com.example.jingchengordermeal3.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class HotFragment extends BaseFragment {
	private WebView webView;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_hot, container,false);
		webView = (WebView) root.findViewById(R.id.webView);
		init();
		return root;
	}
	
	private void init(){
		 //����֧��javascript
		 WebSettings settings = webView.getSettings();
		 settings.setJavaScriptEnabled(true);
        //�ҵ�Html�ļ�
         webView.loadUrl("file:///android_asset/rec/rec.html");  
		 
		 
      }
}
