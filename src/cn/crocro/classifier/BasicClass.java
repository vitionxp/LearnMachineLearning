package cn.crocro.classifier;

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