package cn.crocro.datastructurer;

import java.util.ArrayList;

public class BinaryHeap {
	/**
	 * 初始化的保存数据的数组
	 */
	private ArrayList<Double> arrayList = new ArrayList<>();
	/**
	 * 堆排序的方向
	 */
	private Orientation orientation = Orientation.FROM_LARGE;

	/**
	 * 堆排序方向，是大堆还是小堆
	 */
	public enum Orientation {
		FROM_LARGE, FROM_SMALL;
	}

	/**
	 * 表示节点的左右子节点还是根节点的数据最大
	 */
	private enum NodePos {
		ROOT, LEFT, RIGHT
	}

	/**
	 * 构造器
	 */
	public BinaryHeap() {

	}

	/**
	 * 构造器
	 * 
	 * @param array
	 *            初始化的数组
	 * @param orientation
	 *            大堆还是小堆
	 */
	public BinaryHeap(double[] array, Orientation orientation) {
		for (double num : array) {
			arrayList.add(num);
		}
		this.orientation = orientation;
		sort();
	}

	/**
	 * 在堆中插入数据
	 * 
	 * @param num
	 *            需要插入的数据
	 */
	public void add(double num) {
		arrayList.add(num);
		int index = arrayList.size() - 1;
		swapAndUp(index);
	
	}

	/**
	 * 删除节点
	 * 
	 * @param i
	 *            删除不需要的节点
	 */
	public void delete(int i) {
		swapNode(arrayList.size() - 1, i);
		arrayList.remove(arrayList.size() - 1);
		swapAndDown(i);
		swapAndUp(i);
	}

	/**
	 * 返回排序后的序列
	 * 
	 * @return 排序后的序列
	 */
	public ArrayList<Double> getArrayList() {
		return arrayList;
	}

	/**
	 * 整体排序
	 * 
	 * @return 是否排序成功
	 */
	private boolean sort() {
		if (arrayList.size() == 0) {
			return false;
		}
		int lastIndex = arrayList.size() / 2 - 1;
		for (int i = lastIndex; i >= 0; i--) {
			swapAndDown(i);
		}
		return true;
	}

	/**
	 * 获得需要的极值
	 * 
	 * @return 需要的极值
	 */
	private double getExtValue() {
		if (orientation == Orientation.FROM_SMALL) {
			return Double.MAX_VALUE;
		} else
			return Double.MIN_VALUE;
	}

	/**
	 * 获得相对位置的子节点的序号
	 * 
	 * @param i
	 *            根节点序号
	 * @param pos
	 *            子节点位置
	 * @return 子节点序号
	 */
	private int getSubNum(int i, NodePos pos) {
		if (pos == NodePos.LEFT) {
			return (i + 1) * 2 - 1;
		} else
			return (i + 1) * 2;
	}

	/**
	 * 获取节点以及左右子节点中极值
	 * 
	 * @param i
	 *            需要进行获取极值的节点
	 * @return 极值的位置
	 */
	private NodePos getExtremeNum(int i) {
		double left = arrayList.get(getSubNum(i, NodePos.LEFT));
		double right = getExtValue();
		double root = arrayList.get(i);
		if (getSubNum(i, NodePos.RIGHT) < arrayList.size()) {
			right = arrayList.get(getSubNum(i, NodePos.RIGHT));
		}
		if (compareTwoNode(left, root) && compareTwoNode(left, right)) {
			return NodePos.LEFT;
		} else if (compareTwoNode(right, root) && compareTwoNode(right, left)) {
			return NodePos.RIGHT;
		}
		return NodePos.ROOT;
	}

	/**
	 * 根据排序的需要比较两个节点
	 * 
	 * @param node1
	 *            节点1
	 * @param node2
	 *            节点2
	 * @return 比较结果
	 */
	private boolean compareTwoNode(double node1, double node2) {
		if (orientation == Orientation.FROM_SMALL) {
			return node1 < node2;
		} else {
			return node1 > node2;
		}
	}

	/**
	 * 对某个节点进行下沉处理
	 * 
	 * @param i
	 *            节点位置
	 */
	private void swapAndDown(int i) {
		NodePos max = getExtremeNum(i);
		if (max != NodePos.ROOT) {
			swapNode(i, max);
			int nodeNum = getSubNum(i, max);
			if (((nodeNum + 1) * 2) <= arrayList.size()) {
				swapAndDown(nodeNum);
			}
		}

	}
	
	/**
	 * 向上进行搜索并且进行交换
	 * 
	 * @param index
	 *            需要搜索的位置
	 */
	private void swapAndUp(int index) {
		if (index == 0)
			return;
		int rootIndex = (index + 1) / 2 - 1;
		if (compareTwoNode(arrayList.get(index), arrayList.get(rootIndex))) {
			swapNode(rootIndex, index);
			swapAndUp(rootIndex);
		}
	}

	/**
	 * 直接根据位置进行交换
	 * 
	 * @param node1
	 *            位置1
	 * @param node2
	 *            位置2
	 */
	private void swapNode(int node1, int node2) {
		double temp = arrayList.get(node1);
		arrayList.set(node1, arrayList.get(node2));
		arrayList.set(node2, temp);
	}

	/**
	 * 这是根据相对的位置进行交换
	 * 
	 * @param i
	 *            根节点的位置
	 * @param pos
	 *            子节点的相对位置
	 */
	private void swapNode(int i, NodePos pos) {
		int nodeNum = getSubNum(i, pos);
		double temp = arrayList.get(nodeNum);
		arrayList.set(nodeNum, arrayList.get(i));
		arrayList.set(i, temp);
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BinaryHeap binaryHeap = new BinaryHeap(new double[] { 5, 7, 1, 1, 3, 5, 6, 8, 1, 9, 0 },
				BinaryHeap.Orientation.FROM_SMALL);
		binaryHeap.add(0.5);
		binaryHeap.delete(0);
		BinaryHeap binaryHeap2 = new BinaryHeap();
		binaryHeap2.add(23);
		binaryHeap2.add(33);
		binaryHeap2.add(3333);
		System.out.println(binaryHeap2.getArrayList());
	}

}
