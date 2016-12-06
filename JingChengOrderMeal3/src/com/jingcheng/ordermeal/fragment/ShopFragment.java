package com.jingcheng.ordermeal.fragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingchengordermeal3.R;
import com.jingcheng.ordermeal.activity.MainActivity;
import com.jingcheng.ordermeal.adapter.ListShopAdapter;
import com.jingcheng.ordermeal.adapter.ListShopAdapter.OnButtonShopClickListener;
import com.jingcheng.ordermeal.adapter.ListShopAdapter.ViewHolder;
import com.jingcheng.ordermeal.bean.Dish;
import com.jingcheng.ordermeal.bean.Select;

public class ShopFragment extends BaseFragment implements OnClickListener {
	public static final String shop = "shop.broadcast.action";
	private MainActivity context;
	private ListView shop_lv;
	public HashMap<String,Select> selectMap;
	private List<Select> selectList = new ArrayList<Select>();
	private ListShopAdapter adapter;
	private Button shop_allDelete, shop_commit, shop_again, 
	shop_null_again, shop_null_begin, mild, medium, peppery;
	private TextView all_price, shop_tableId;
	private RelativeLayout shop_null_rl, shop_rl;
	private SharedPreferences sp = null;
	private SharedPreferences.Editor editor = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_shop, container,false);
		shop_lv = (ListView) root.findViewById(R.id.shop_lv);
		shop_null_rl = (RelativeLayout) root.findViewById(R.id.shop_null_rl);
		shop_rl = (RelativeLayout) root.findViewById(R.id.shop_rl);
		all_price = (TextView) root.findViewById(R.id.all_price);
		shop_allDelete = (Button) root.findViewById(R.id.shop_allDelete);
		shop_commit = (Button) root.findViewById(R.id.shop_commit);
		shop_again = (Button) root.findViewById(R.id.shop_again);
		shop_null_again = (Button) root.findViewById(R.id.shop_null_again);
		shop_null_begin = (Button) root.findViewById(R.id.shop_null_begin);
		shop_tableId = (TextView) root.findViewById(R.id.shop_tableId);
		
		mild = (Button) root.findViewById(R.id.mild);
		medium = (Button) root.findViewById(R.id.medium);
		peppery = (Button) root.findViewById(R.id.peppery);
		
		context = (MainActivity) getActivity();
		
		shop_allDelete.setOnClickListener(this);
		shop_commit.setOnClickListener(this);
		shop_again.setOnClickListener(this);
		shop_null_again.setOnClickListener(this);
		shop_null_begin.setOnClickListener(this);
		mild.setOnClickListener(this);
		medium.setOnClickListener(this);
		peppery.setOnClickListener(this);
		initData();
		IntentFilter filter = new IntentFilter(shop);
		context.registerReceiver(broadcastReceiver, filter);
		
		
		return root;
	}
	
	private void initView() {
		adapter = new ListShopAdapter(selectList, new OnButtonShopClickListener() {
			
			@Override
			public void onButtonClick(View v) {
				ViewHolder holder = (ViewHolder) v.getTag();
				Select select = holder.select;
				int count = select.getCount();
				String value = String.valueOf(select.getDishId());
				switch (v.getId()) {
				case R.id.bt_add_shop://�Ӳ�
					count++;
					select.setCount(count);//����+1
//					holder.count.setText(String.valueOf(count));
					adapter.notifyDataSetChanged();//���½���
					selectMap.put(value, select);//����map����
					initData();//����list����
					context.sendBroadcast(new Intent(MenuFragment.menu).putExtra("data", 100));
					((MainActivity) context).countShow();
					break;
				case R.id.bt_cancel_shop://����
					if(count == 1){//����Ϊ1--��ʱҪ��Ϊ0
						selectMap.remove(value);//����map���� ɾ���ò�Ʒ
						context.sendBroadcast(new Intent(MenuFragment.menu).putExtra("data", 100));
					}else{
						count--;
						select.setCount(count);//����-1
						selectMap.put(value, select);//����map����
						context.sendBroadcast(new Intent(MenuFragment.menu).putExtra("data", 100));
					}
					initData();//����list����
					adapter.notifyDataSetChanged();//���½���
					((MainActivity) context).countShow();
					break;
		
				default:
					break;
				}
			
			}
		});
		shop_lv.setAdapter(adapter);
	}

	private void initData() {
		if(sp == null){
			sp = context.getSharedPreferences("OrderMeal", Activity.MODE_PRIVATE);
			editor = sp.edit();
		}
		String tableId = sp.getString("tableId", null);
		if(tableId == null){
			context.finish();
			Toast.makeText(context, "�ڲ�����δ��ȡ�����ţ������¿�����", 0).show();
		}
		shop_tableId.setText("���ţ�" + tableId);
		selectMap = context.getSelectMap();
		selectList.clear();
		if(selectMap.isEmpty() || selectMap == null || selectMap.size() == 0){
			shop_rl.setVisibility(View.GONE);
			shop_null_rl.setVisibility(View.VISIBLE);
		}else{
			shop_null_rl.setVisibility(View.GONE);
			shop_rl.setVisibility(View.VISIBLE);
			//��Map�е����ݴ洢��List��  ��ʼ��Adapter����
			for (Select v : selectMap.values()) {
				selectList.add(v);
			}
			statistics();
			initView();
		}
	}
	
	private void statistics() {
		int allCount = 0;
		float allPrice = 0f;
		for (int i = 0; i < selectList.size(); i++) {
			int count = selectList.get(i).getCount();
			allCount += count;
			float price = selectList.get(i).getPrice()*count;
			//������λС��
			price = new BigDecimal(price).setScale(2, 4).floatValue();   
			allPrice += price;
		}
		allPrice = new BigDecimal(allPrice).setScale(2, 4).floatValue();
//		all_count.setText(String.valueOf(allCount));
		all_price.setText(String.valueOf(allPrice)+"Ԫ");
		
	}
	
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			int code = intent.getIntExtra("data", 0);
			
			if(code == 200){
				initData();
				adapter.notifyDataSetChanged();
			}else{//��������洫�ݻ����Ĺ㲥
				int count = intent.getIntExtra("count", 0);
				String key = intent.getStringExtra("key");
				if(count == 0){
					selectMap.remove(key);
					initData();
					adapter.notifyDataSetChanged();
				}else{
					Dish dish = (Dish) intent.getSerializableExtra("dish");
					selectMap.put(String.valueOf(dish.getDishId()), 
							new Select(dish.getDishId(),
									dish.getDishName(), 
									dish.getDetails(), 
									dish.getPrice(), 
									dish.getImage(), 
									dish.isNew(), 
									dish.isHot(), 
									count));
					initData();
					adapter.notifyDataSetChanged();
				}
				show();
			}
			
		}
	};
	
	private void show(){
		context.countShow();
	}
	
	@Override
	public void onDestroyView() {
		context.unregisterReceiver(broadcastReceiver);
		super.onDestroyView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shop_allDelete:
			selectMap.clear();
			initData();
			adapter.notifyDataSetChanged();//���½���
			((MainActivity) context).countShow();
			Toast.makeText(context, "���ﳵ�����", 0).show();
			context.sendBroadcast(new Intent(MenuFragment.menu).putExtra("data", 100));
			break;
		case R.id.shop_again://���¿���
			context.finish();
			break;
		case R.id.shop_commit://�����µ�
			Toast.makeText(context, "֧���ӿ�δ��ͨ", 0).show();
			break;
		case R.id.shop_null_again://�չ��ﳵ-���¿���
			context.finish();
			break;
		case R.id.shop_null_begin://�չ��ﳵ -���
			context.GoMenu();
			break;
		case R.id.mild://΢��
			NeedSelect(mild);
			break;
		case R.id.medium://����
			NeedSelect(medium);
			break;
		case R.id.peppery://����
			NeedSelect(peppery);
			break;

		default:
			break;
		}
	}
	
	private void NeedSelect(Button view){
		mild.setBackground(getResources().getDrawable(R.drawable.need_bg, null));
		medium.setBackground(getResources().getDrawable(R.drawable.need_bg, null));
		peppery.setBackground(getResources().getDrawable(R.drawable.need_bg, null));
		view.setBackground(getResources().getDrawable(R.drawable.need_bg_down, null));
	}
}
