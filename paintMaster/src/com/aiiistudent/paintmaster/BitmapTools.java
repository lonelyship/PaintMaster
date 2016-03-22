package com.aiiistudent.paintmaster;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapTools {

	public BitmapTools() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param resources
	 *            ��Դ�ļ�
	 * @param resId
	 *            ����λͼ��id
	 * @param reqWith
	 *            ָ�����λͼ�Ŀ��
	 * @param reqHeight
	 *            ָ�����λͼ�ĸ߶�
	 * @return
	 */
	public static Bitmap decodeBitmap(Resources resources, int resId,
			int reqWith, int reqHeight) {
		// ��λͼ���н���Ĳ�������
		BitmapFactory.Options options = new BitmapFactory.Options();
		// �ڶ�λͼ���н���Ĺ����У����������ڴ�ռ�
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(resources, resId, options);
		// ��ͼƬ����һ��������ѹ������
		options.inSampleSize = calculateInSimpleSize(options, reqWith,
				reqHeight);
		options.inJustDecodeBounds = false;// �������λͼ
		return BitmapFactory.decodeResource(resources, resId, options);
	}

	/**
	 * 
	 * @param options
	 * @param reqWith
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSimpleSize(BitmapFactory.Options options,
			int reqWith, int reqHeight) {
		// ���ͼƬ��ԭʼ���
		int imageHeight = options.outHeight;
		int imageWidth = options.outWidth;
		int inSimpleSize = 1;// ѹ������
		if (imageHeight > reqHeight || imageWidth > reqWith) {
			final int heightRatio = Math.round((float) imageHeight
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) imageWidth
					/ (float) reqWith);
			inSimpleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSimpleSize;
	}
}
