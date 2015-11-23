package cn.crocro.classifier;

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