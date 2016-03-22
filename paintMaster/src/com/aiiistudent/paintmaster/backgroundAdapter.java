package com.aiiistudent.paintmaster;
 


import com.aiiistudent.paintmaster.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class backgroundAdapter extends BaseAdapter{
    private Context myContext;
    private int[] myImageId = { 
    		R.drawable.bg0_icon,R.drawable.bg1_icon,R.drawable.bg2_icon,
    		R.drawable.bg3_icon,R.drawable.bg4_icon,R.drawable.bg5_icon,
    		R.drawable.bg6_icon,R.drawable.bg7_icon,R.drawable.bg8_icon,
    		R.drawable.bg9_icon,R.drawable.bg10_icon,R.drawable.bg11_icon

    };
   public backgroundAdapter(Context c){
       this.myContext=c;
    }
   @Override
   public int getCount() {
   // TODO Auto-generated method stub
   return this.myImageId.length;
   }
   @Override
   public Object getItem(int position) {
   // TODO Auto-generated method stub
   return position;
   }
   @Override
   public long getItemId(int position) {
   // TODO Auto-generated method stub
   return position;
   } 
   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
   // TODO Auto-generated method stub
       //建立一個ImageView物件
       ImageView i = new ImageView(this.myContext);
       //設定ImageView物件的資料來源
       i.setImageResource(this.myImageId[position]);
       i.setScaleType(ImageView.ScaleType.FIT_CENTER);
       //設定ImageView的寬高，單位為dip
   i.setLayoutParams(new Gallery.LayoutParams(150, 75));
 
       return i;
    } 
   //根據，距離中央的位移量，利用getScale回傳view的大小
   public float getScale(boolean focused, int offset){
   return Math.max(0, 1.0f/(float)Math.pow(2, Math.abs(offset)));
   }
  }
