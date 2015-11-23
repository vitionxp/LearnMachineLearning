package cn.crocro.classifier;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 进行感知器分类
 * 
 * @author 鳄鱼
 * 
 */
public class PerceptronClassifier {

	/**
	 * 分类器参数
	 */
	private double[] w;
	private double b = 0;
	private double eta = 1;
	ArrayList<Point> arrayList;

	/**
	 * 初始化分类器，传入需要分组的数据
	 * 
	 * @param arrayList
	 *            需要分类的点
	 */
	public PerceptronClassifier(ArrayList<Point> arrayList, double eta) {
		// 分类器初始化
		this.arrayList = arrayList;
		w = new double[arrayList.get(0).x.length];
		this.eta = eta;
	}

	public PerceptronClassifier(ArrayList<Point> arrayList) {
		// 分类器初始化
		this.arrayList = arrayList;
		w = new double[arrayList.get(0).x.length];
		this.eta = 1;
	}

	/**
	 * 进行分类计算
	 * 
	 * @return 是否分类成功
	 */
	public boolean Classify() {
		// 这个标志表示是不是全部循环过了，都没有问题
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
	 * 进行学习得到的结果
	 * 
	 * @param point
	 *            需要进行学习的点
	 * @return
	 */
	private double LearnAnswer(Point point) {
		return point.y * (DotProduct(w, point.x) + b);
	}

	/**
	 * 进行w更新
	 * 
	 * @param point
	 *            需要根据样本来随机梯度下降来进行w更新
	 * @return 不需要返回值
	 */
	private void UpdateWAndB(Point point) {
		for (int i = 0; i < w.length; i++) {
			w[i] += eta * point.y * point.x[i];
		}
		b += eta * point.y;
		return;
	}

	/**
	 * 进行点乘
	 * 
	 * @param x1
	 *            乘数
	 * @param x2
	 *            乘数
	 * @return 点乘的积
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
	 * 主程序进行检测
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


