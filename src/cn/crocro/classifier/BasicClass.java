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