package com.aiiistudent.paintmaster;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{
    private Context myContext;
    private int[] myImageId = { 
    		R.drawable.q1_icon, R.drawable.q2_icon, R.drawable.q3_icon,
			R.drawable.q4_icon, R.drawable.q5_icon, R.drawable.q6_icon,
			R.drawable.q7_icon, R.drawable.q8_icon, R.drawable.q9_icon,
			R.drawable.q10_icon, R.drawable.q11_icon, R.drawable.q12_icon
			, R.drawable.q13_icon, R.drawable.q14_icon

    };
   public ImageAdapter(Context c){
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
