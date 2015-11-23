/**
 * 
 */
package cn.crocro.classifier;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 感知机学习方法的对偶形式
 * 
 * @author 鳄鱼
 * 
 */
public class PerceptronClassifierDualForm {

	/**
	 * 相关参数
	 */
	private double eta = 0;
	private double[][] gram;
	private double[] w;
	private double b = 0;
	private double[] alpha;

	private ArrayList<Point> arrayList;

	/**
	 * 感知机构造函数
	 * 
	 * @param arrayList
	 *            表示输入数组
	 * @param eta
	 *            表示学习率
	 */
	public PerceptronClassifierDualForm(ArrayList<Point> arrayList, double eta) {
		this.arrayList = arrayList;
		this.eta = eta;
		w = new double[arrayList.get(0).x.length];
		alpha = new double[arrayList.size()];
	}

	/**
	 * 默认学习率的构造函数
	 * 
	 * @param arrayList
	 *            表示输入数组
	 */
	public PerceptronClassifierDualForm(ArrayList<Point> arrayList) {
		this.arrayList = arrayList;
		this.eta = 1;
		w = new double[arrayList.get(0).x.length];
		alpha = new double[arrayList.size()];
	}

	/**
	 * 感知机对偶形式测试
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
		PerceptronClassifierDualForm classifier = new PerceptronClassifierDualForm(
				list, 1);
		classifier.Classify();
		System.out.println(Arrays.toString(classifier.w));
		System.out.println(classifier.b);
	}

	/**
	 * gram矩阵运算
	 */
	private void GramMatrix() {
		gram = new double[arrayList.size()][arrayList.size()];
		for (int i = 0; i < arrayList.size(); i++) {
			for (int j = 0; j < arrayList.size(); j++) {
				gram[i][j] = Matrix.DotProduct(arrayList.get(i).x,
						arrayList.get(j).x);
			}
		}
	}

	/**
	 * 分类器的学习算法
	 * 
	 * @return 是否分类成功
	 */
	private boolean Classify() {
		GramMatrix();
		boolean flag = false;
		while (!flag) {
			for (int i = 0; i < arrayList.size(); i++) {
				double sum = 0;
				for (int j = 0; j < arrayList.size(); j++) {
					sum += alpha[j] * arrayList.get(j).y * gram[i][j];
				}
				sum = arrayList.get(i).y * (sum + b);
				if (sum <= 0) {
					alpha[i] += eta;
					b += eta * arrayList.get(i).y;
					break;
				}
				if (i == (arrayList.size() - 1)) {
					flag = true;
				}
			}
		}
		for (int i = 0; i < arrayList.get(0).x.length; i++) {
			for (int j = 0; j < arrayList.size(); j++) {
				w[i] += arrayList.get(j).x[i] * alpha[j] * arrayList.get(j).y;
			}

		}

		return true;
	}

}
