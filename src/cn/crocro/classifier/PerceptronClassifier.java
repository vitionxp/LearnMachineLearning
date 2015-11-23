package cn.crocro.classifier;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ���и�֪������
 * 
 * @author ����
 * 
 */
public class PerceptronClassifier {

	/**
	 * ����������
	 */
	private double[] w;
	private double b = 0;
	private double eta = 1;
	ArrayList<Point> arrayList;

	/**
	 * ��ʼ����������������Ҫ���������
	 * 
	 * @param arrayList
	 *            ��Ҫ����ĵ�
	 */
	public PerceptronClassifier(ArrayList<Point> arrayList, double eta) {
		// ��������ʼ��
		this.arrayList = arrayList;
		w = new double[arrayList.get(0).x.length];
		this.eta = eta;
	}

	public PerceptronClassifier(ArrayList<Point> arrayList) {
		// ��������ʼ��
		this.arrayList = arrayList;
		w = new double[arrayList.get(0).x.length];
		this.eta = 1;
	}

	/**
	 * ���з������
	 * 
	 * @return �Ƿ����ɹ�
	 */
	public boolean Classify() {
		// �����־��ʾ�ǲ���ȫ��ѭ�����ˣ���û������
		boolean flag = false;
		while (!flag) {
			for (int i = 0; i < arrayList.size(); i++) {
				if (LearnAnswer(arrayList.get(i)) <= 0) {
					UpdateWAndB(arrayList.get(i));
					break;
				}
				if (i == (arrayList.size() - 1)) {
					flag = true;
				}
			}
		}
		System.out.println(Arrays.toString(w));
		System.out.println(b);
		return true;
	}

	/**
	 * ����ѧϰ�õ��Ľ��
	 * 
	 * @param point
	 *            ��Ҫ����ѧϰ�ĵ�
	 * @return
	 */
	private double LearnAnswer(Point point) {
		return point.y * (DotProduct(w, point.x) + b);
	}

	/**
	 * ����w����
	 * 
	 * @param point
	 *            ��Ҫ��������������ݶ��½�������w����
	 * @return ����Ҫ����ֵ
	 */
	private void UpdateWAndB(Point point) {
		for (int i = 0; i < w.length; i++) {
			w[i] += eta * point.y * point.x[i];
		}
		b += eta * point.y;
		return;
	}

	/**
	 * ���е��
	 * 
	 * @param x1
	 *            ����
	 * @param x2
	 *            ����
	 * @return ��˵Ļ�
	 */
	private double DotProduct(double[] x1, double[] x2) {
		int len = x1.length;
		double sum = 0;
		for (int i = 0; i < len; i++) {
			sum += x1[i] * x2[i];
		}
		return sum;
	}

	/**
	 * ��������м��
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Point p1 = new Point(new double[] { 3, 3 }, 1);
		Point p2 = new Point(new double[] { 4, 3 }, 1);
		Point p3 = new Point(new double[] { 1, 1 }, -1);
		ArrayList<Point> list = new ArrayList<Point>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		PerceptronClassifier classifier = new PerceptronClassifier(list);
		classifier.Classify();
	}

}


