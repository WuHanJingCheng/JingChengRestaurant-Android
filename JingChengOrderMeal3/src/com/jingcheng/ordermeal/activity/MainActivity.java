package com.jingcheng.ordermeal.activity;

import java.util.HashMap;

import com.example.jingchengordermeal3.R;
import com.jingcheng.ordermeal.bean.Dish;
import com.jingcheng.ordermeal.bean.Select;
import com.jingcheng.ordermeal.fragment.HotFragment;
import com.jingcheng.ordermeal.fragment.MenuFragment;
import com.jingcheng.ordermeal.fragment.ShopFragment;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout hot, menu, shop, exit;
	private TextView bar_tv;
	private HotFragment hotFragment;
	private MenuFragment menuFragment;
	private ShopFragment shopFragment;
	private Fragment mContent = null;
	public HashMap<String,Select> selectMap = new HashMap<String, Select>();
	private TextView count_show;
	private ImageView hot_iv;
	private ImageView menu_iv;
	private ImageView shop_iv;
	private TextView hot_tv;
	private TextView menu_tv;
	private TextView shop_tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		hot= (RelativeLayout) findViewById(R.id.hot);
		menu = (RelativeLayout) findViewById(R.id.menu);
		shop = (RelativeLayout) findViewById(R.id.shop);
		exit = (RelativeLayout) findViewById(R.id.exit);
		bar_tv = (TextView) findViewById(R.id.bar_tv);
		count_show = (TextView) findViewById(R.id.count);
		
		hot_iv = (ImageView) findViewById(R.id.hot_iv);
		menu_iv = (ImageView) findViewById(R.id.menu_iv);
		shop_iv = (ImageView) findViewById(R.id.shop_iv);
		
		hot_tv = (TextView) findViewById(R.id.hot_tv);
		menu_tv = (TextView) findViewById(R.id.menu_tv);
		shop_tv = (TextView) findViewById(R.id.shop_tv);
		hot.setOnClickListener(this);
		menu.setOnClickListener(this);
		shop.setOnClickListener(this);
		exit.setOnClickListener(this);
		
		hotFragment = new HotFragment();
		menuFragment = new MenuFragment();
		shopFragment = new ShopFragment();
		
		init();
	}

	private void init() {
		replceFragment(hotFragment);
		visiView(hot,0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.hot://�Ƽ�
			bar_tv.setText("�Ƽ�");
			replceFragment(hotFragment);
			visiView(hot,0);
			break;
		case R.id.menu://�˵�
			GoMenu();
			break;
		case R.id.shop://���ﳵ
			bar_tv.setText("���ﳵ");
			replceFragment(shopFragment);
			visiView(shop,2);
			break;
		case R.id.exit://�˳�
//			Toast.makeText(this, "ȷ�ϵ������������", 0).show();
//			finish();
			dialog1();
			break;

		default:
			break;
		}
	}
	public void GoMenu(){
		bar_tv.setText("�˵�");
		replceFragment(menuFragment);
		visiView(menu,1);
	}
	
	/**
	 * �滻fragment
	 * 
	 * @param fragment Ҫ�滻��fragment
	 * 
	 */
	private void replceFragment(Fragment fragment){
		if(mContent != fragment){
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			if(!fragment.isAdded()){
				if(mContent != null){
					ft.hide(mContent).add(R.id.framelayout, fragment).commit();
//					
				}else{
					ft.add(R.id.framelayout, fragment).commit();
				}
			}else{
				ft.hide(mContent).show(fragment).commit();
			}
			mContent = fragment;
		}
	}
	
	/**
	 * ѡ��ͼ�����
	 * @param view Ҫ��ʾ��view<br>
	 * @param iv ѡ�е����� ��0��ʾ�Ƽ�(hot);<br>
	 * 				1��ʾ����(menu);<br>
	 * 				2�����ʾ���ﳵ(shop);<br>
	 */
	private void visiView(RelativeLayout view, int iv){
//		hot_view.setVisibility(View.GONE);
//		menu_view.setVisibility(View.GONE);
//		shop_view.setVisibility(View.GONE);
//		view.setVisibility(View.VISIBLE);
//		hot.setBackgroundResource(R.drawable.menu_left_bg);
		hot.setBackgroundColor(Color.parseColor("#EAE0C5"));
		menu.setBackgroundColor(Color.parseColor("#EAE0C5"));
		shop.setBackgroundColor(Color.parseColor("#EAE0C5"));
		view.setBackgroundResource(R.drawable.sidebar_select);
		hot_iv.setImageResource(R.drawable.hot);
		menu_iv.setImageResource(R.drawable.menu);
		shop_iv.setImageResource(R.drawable.shop);
		hot_tv.setTextColor(Color.parseColor("#000000"));
		menu_tv.setTextColor(Color.parseColor("#000000"));
		shop_tv.setTextColor(Color.parseColor("#000000"));
		if(iv == 0){
			hot_iv.setImageResource(R.drawable.hot_down);
			hot_tv.setTextColor(Color.parseColor("#F6B559"));
		}else if(iv == 1){
			menu_iv.setImageResource(R.drawable.menu_down);
			menu_tv.setTextColor(Color.parseColor("#F6B559"));
		}else{
			shop_iv.setImageResource(R.drawable.shop_down);
			shop_tv.setTextColor(Color.parseColor("#F6B559"));
		}
	}

	public HashMap<String, Select> getSelectMap() {
		return selectMap;
	}

	public void setSelectMap(HashMap<String, Select> selectMap) {
		this.selectMap = selectMap;
	}
	
	public void countShow(){
		int count = 0;
		if(!selectMap.isEmpty()){
			for (Select v : selectMap.values()) {
				count += v.getCount();
			}
		}
		if(count == 0){
			count_show.setText("0");
			count_show.setVisibility(View.GONE);
		}else{
			count_show.setText(String.valueOf(count));
			count_show.setVisibility(View.VISIBLE);
		}
	}
	
//	protected void dialog() {
//		AlertDialog ad=new AlertDialog.Builder(this).create();  
//        ad.setTitle("�˳���¼");  
//        ad.setIcon(R.drawable.ic_launcher);  
//        ad.setMessage("��ȷ���˳���¼��");  
//        ad.setButton("ȷ��", new DialogInterface.OnClickListener() {  
//              
//            @Override  
//            public void onClick(DialogInterface dialog, int which) {  
//            	dialog.dismiss();
//                MainActivity.this.finish();
//                  
//            }  
//        });  
//        ad.setButton2("ȡ��", new DialogInterface.OnClickListener() {  
//              
//            @Override  
//            public void onClick(DialogInterface dialog, int which) {  
//                  dialog.dismiss();
//                  
//            }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
//        });  
//        ad.show();  
//		 }
	
	private void dialog1(){
		//�����ļ�ת��Ϊview����
        LayoutInflater inflaterDl = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout)inflaterDl.inflate(R.layout.layout_dialog, null );
       
        //�Ի���
        final Dialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);
       
       
        //ȡ����ť
        Button btnCancel = (Button) layout.findViewById(R.id.dialog_cancel);
        btnCancel.setOnClickListener(new OnClickListener() {
         
          @Override
          public void onClick(View v) {
        	 dialog.dismiss();
             Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();           
          }
        });
       
       
        //ȷ����ť
        Button btnOK = (Button) layout.findViewById(R.id.dialog_exit);
        btnOK.setOnClickListener(new OnClickListener() {
         
          @Override
          public void onClick(View v) {
        	  dialog.dismiss();
        	  setResult(10000);
              MainActivity.this.finish();
             Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();           
          }
        });
	}
}
