package cn.crocro.classifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * ����һ��Point,��������������֣��������ࡣx��ʾ����Rά�ռ�������y��ʾ����ֵ��ֻ��-1��+1����
 * 
 * @author ����
 * 
 */
class Point {
	double[] x = new double[2];
	double y = 0;

	Point(double[] x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point() {

	}
}


/**
 * ��ʾ������״ֵ̬�����������޸������
 * @author ����
 *
 */
class FiniteStateData{
	int []data;
	int classfy;
	
	//�����������Ŀ�����ܳ������������
	private static Set<Integer> classfyNum;
	//ÿ��ά�ȵ���ֵ�����ܳ�������ֵ��Χ
	private static ArrayList<Set<Integer>> dataDimNumArray;
	public FiniteStateData(int [] data,int classfy){
		this.data=data;
		this.classfy=classfy;
		if(classfyNum==null){
			classfyNum=new HashSet<Integer>();
			dataDimNumArray=new ArrayList<Set<Integer>>();
			for(int i=0;i<data.length;i++){
				dataDimNumArray.add(new HashSet<Integer>());
			}
		}
		classfyNum.add(classfy);
		for(int i=0;i<data.length;i++){
			dataDimNumArray.get(i).add(data[i]);
		}
	}

	/**
	 * ��ȡ����ĸ���
	 * @return
	 */
	public static int getTotalClassNum(){
		return classfyNum.size();
	}
	/**
	 * ��ȡ����ά�ȵ���Ŀ����
	 * @param dim
	 * @return
	 */
	public static int getTotalDimDataNum(int dim){
		return dataDimNumArray.get(dim).size();
	}
	public static Set<Integer> getClassArray(){
		return classfyNum;
	}
}


/**
 * �����������
 * 
 * @author ����
 * 
 */
class Matrix {
	/**
	 * �����������
	 * 
	 * @param x1
	 *            ��������
	 * @param x2
	 *            ��������
	 * @return ������
	 */
	static public double DotProduct(double[] x1, double[] x2) {
		int len = x1.length;
		double sum = 0;
		for (int i = 0; i < len; i++) {
			sum += x1[i] * x2[i];
		}
		return sum;
	}
}