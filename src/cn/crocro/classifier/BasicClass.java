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