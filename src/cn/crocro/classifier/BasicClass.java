package cn.crocro.classifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 定义一个Point,里面包含两个部分，用来分类。x表示输入R维空间向量，y表示分类值，只有-1和+1两类
 * 
 * @author 鳄鱼
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
 * 表示样本的状态值样本都是有限个数情况
 * @author 鳄鱼
 *
 */
class FiniteStateData{
	int []data;
	int classfy;
	
	//分类的种类数目，不能超出这个的种类
	private static Set<Integer> classfyNum;
	//每个维度的数值，不能超出此数值范围
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
	 * 获取分类的个数
	 * @return
	 */
	public static int getTotalClassNum(){
		return classfyNum.size();
	}
	/**
	 * 获取单个维度的数目个数
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
 * 矩阵相关运算
 * 
 * @author 鳄鱼
 * 
 */
class Matrix {
	/**
	 * 点积矩阵运算
	 * 
	 * @param x1
	 *            被乘向量
	 * @param x2
	 *            被乘向量
	 * @return 点积结果
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