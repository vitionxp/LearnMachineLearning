package cn.crocro.classifier;

import java.util.ArrayList;

/**
 * 朴素贝叶斯分类器，只能针对有限个情况的分类下面是实例代码
 * 
 * @author 鳄鱼
 *
 */
public class NaiveBayesClassifier {
	/**
	 * 进行训练的内部数据
	 */
	private ArrayList<FiniteStateData> datas = new ArrayList<>();
	/*
	 * 
	 * 拉普拉斯平滑数值
	 */
	private double landa = 1;

	/**
	 * 进行初始化
	 * 
	 * @param datas
	 *            初始化的数据
	 */
	public NaiveBayesClassifier(ArrayList<FiniteStateData> datas) {
		this.datas = datas;
	}

	/**
	 * 默认进行初始化
	 */
	public NaiveBayesClassifier() {

	}

	public void setLanda(double landa) {
		this.landa = landa;
	}

	/**
	 * 增加数据
	 * 
	 * @param data
	 *            增加一组数据
	 */
	public void addData(FiniteStateData data) {
		datas.add(data);
	}

	/**
	 * 获取对应的类的默认概率
	 * 
	 * @param classNum
	 *            想获取的分类
	 * @return
	 */
	private double getClassPercent(int classNum) {
		int num = 0;
		for (FiniteStateData data : datas) {
			if (data.classfy == classNum) {
				num++;
			}
		}
		return (double) (num + landa) / (datas.size() + landa * FiniteStateData.getTotalClassNum());
	}

	/**
	 * 获取某个维度某个值的概率
	 * 
	 * @param dim
	 *            维度
	 * @param value
	 *            值
	 * @return 概率
	 */
	private double getValuePercent(int dim, int value, int classfy) {
		int num = 0;
		int classfyNum = 0;
		for (FiniteStateData singleData : datas) {
			if (singleData.data[dim] == value && singleData.classfy == classfy) {
				num++;
			}
			if (singleData.classfy == classfy) {
				classfyNum++;
			}
		}

		return (double) (num + landa) / (classfyNum + landa * FiniteStateData.getTotalDimDataNum(dim));
	}

	/**
	 * 获取未知数据的内部分类概率
	 * 
	 * @param data
	 *            未知数据
	 * @param classfy
	 *            可能分类
	 * @return 概率
	 */
	public double getClassifyPercent(int[] data, int classfy) {
		double total = 0;
		for (int oneClassfy : FiniteStateData.getClassArray()) {
			total += getLocalClassifyPercent(data, oneClassfy);
		}
		return getLocalClassifyPercent(data, classfy) / total;
	}

	/**
	 * 获取未知数据的内部分类概率
	 * 
	 * @param data
	 *            未知数据
	 * @param classfy
	 *            可能分类
	 * @return 概率
	 */
	private double getLocalClassifyPercent(int[] data, int classfy) {
		double percent = getClassPercent(classfy);
		for (int i = 0; i < data.length; i++) {
			percent = percent * getValuePercent(i, data[i], classfy);
		}
		return percent;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 设置分类的数值可能
		int[] data1 = new int[] { 1, 3, 4, 5 };
		int[] data2 = new int[] { 2, 2, 2, 2 };
		int[] data3 = new int[] { 2, 2, 2, 2 };
		FiniteStateData finiteStateData = new FiniteStateData(data1, 1);
		FiniteStateData finiteStateData1 = new FiniteStateData(data2, 2);
		FiniteStateData finiteStateData2 = new FiniteStateData(data3, 1);
		NaiveBayesClassifier bayesClassifier = new NaiveBayesClassifier();
		bayesClassifier.addData(finiteStateData);
		bayesClassifier.addData(finiteStateData1);
		bayesClassifier.addData(finiteStateData2);
		System.out.println(bayesClassifier.getClassPercent(2));
		System.out.println(bayesClassifier.getValuePercent(0, 2, 1));
		System.out.println(bayesClassifier.getClassifyPercent(data1, 1));
	}

}
