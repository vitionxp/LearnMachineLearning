package cn.crocro.classifier;

import java.util.ArrayList;

/**
 * ���ر�Ҷ˹��������ֻ��������޸�����ķ���������ʵ������
 * 
 * @author ����
 *
 */
public class NaiveBayesClassifier {
	/**
	 * ����ѵ�����ڲ�����
	 */
	private ArrayList<FiniteStateData> datas = new ArrayList<>();
	/*
	 * 
	 * ������˹ƽ����ֵ
	 */
	private double landa = 1;

	/**
	 * ���г�ʼ��
	 * 
	 * @param datas
	 *            ��ʼ��������
	 */
	public NaiveBayesClassifier(ArrayList<FiniteStateData> datas) {
		this.datas = datas;
	}

	/**
	 * Ĭ�Ͻ��г�ʼ��
	 */
	public NaiveBayesClassifier() {

	}

	public void setLanda(double landa) {
		this.landa = landa;
	}

	/**
	 * ��������
	 * 
	 * @param data
	 *            ����һ������
	 */
	public void addData(FiniteStateData data) {
		datas.add(data);
	}

	/**
	 * ��ȡ��Ӧ�����Ĭ�ϸ���
	 * 
	 * @param classNum
	 *            ���ȡ�ķ���
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
	 * ��ȡĳ��ά��ĳ��ֵ�ĸ���
	 * 
	 * @param dim
	 *            ά��
	 * @param value
	 *            ֵ
	 * @return ����
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
	 * ��ȡδ֪���ݵ��ڲ��������
	 * 
	 * @param data
	 *            δ֪����
	 * @param classfy
	 *            ���ܷ���
	 * @return ����
	 */
	public double getClassifyPercent(int[] data, int classfy) {
		double total = 0;
		for (int oneClassfy : FiniteStateData.getClassArray()) {
			total += getLocalClassifyPercent(data, oneClassfy);
		}
		return getLocalClassifyPercent(data, classfy) / total;
	}

	/**
	 * ��ȡδ֪���ݵ��ڲ��������
	 * 
	 * @param data
	 *            δ֪����
	 * @param classfy
	 *            ���ܷ���
	 * @return ����
	 */
	private double getLocalClassifyPercent(int[] data, int classfy) {
		double percent = getClassPercent(classfy);
		for (int i = 0; i < data.length; i++) {
			percent = percent * getValuePercent(i, data[i], classfy);
		}
		return percent;
	}

	/**
	 * ����
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// ���÷������ֵ����
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
