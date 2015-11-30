package cn.crocro.datastructurer;

import java.util.ArrayList;

public class BinaryHeap {
	/**
	 * ��ʼ���ı������ݵ�����
	 */
	private ArrayList<Double> arrayList = new ArrayList<>();
	/**
	 * ������ķ���
	 */
	private Orientation orientation = Orientation.FROM_LARGE;

	/**
	 * ���������Ǵ�ѻ���С��
	 */
	public enum Orientation {
		FROM_LARGE, FROM_SMALL;
	}

	/**
	 * ��ʾ�ڵ�������ӽڵ㻹�Ǹ��ڵ���������
	 */
	private enum NodePos {
		ROOT, LEFT, RIGHT
	}

	/**
	 * ������
	 */
	public BinaryHeap() {

	}

	/**
	 * ������
	 * 
	 * @param array
	 *            ��ʼ��������
	 * @param orientation
	 *            ��ѻ���С��
	 */
	public BinaryHeap(double[] array, Orientation orientation) {
		for (double num : array) {
			arrayList.add(num);
		}
		this.orientation = orientation;
		sort();
	}

	/**
	 * �ڶ��в�������
	 * 
	 * @param num
	 *            ��Ҫ���������
	 */
	public void add(double num) {
		arrayList.add(num);
		int index = arrayList.size() - 1;
		swapAndUp(index);
	
	}

	/**
	 * ɾ���ڵ�
	 * 
	 * @param i
	 *            ɾ������Ҫ�Ľڵ�
	 */
	public void delete(int i) {
		swapNode(arrayList.size() - 1, i);
		arrayList.remove(arrayList.size() - 1);
		swapAndDown(i);
		swapAndUp(i);
	}

	/**
	 * ��������������
	 * 
	 * @return ����������
	 */
	public ArrayList<Double> getArrayList() {
		return arrayList;
	}

	/**
	 * ��������
	 * 
	 * @return �Ƿ�����ɹ�
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
	 * �����Ҫ�ļ�ֵ
	 * 
	 * @return ��Ҫ�ļ�ֵ
	 */
	private double getExtValue() {
		if (orientation == Orientation.FROM_SMALL) {
			return Double.MAX_VALUE;
		} else
			return Double.MIN_VALUE;
	}

	/**
	 * ������λ�õ��ӽڵ�����
	 * 
	 * @param i
	 *            ���ڵ����
	 * @param pos
	 *            �ӽڵ�λ��
	 * @return �ӽڵ����
	 */
	private int getSubNum(int i, NodePos pos) {
		if (pos == NodePos.LEFT) {
			return (i + 1) * 2 - 1;
		} else
			return (i + 1) * 2;
	}

	/**
	 * ��ȡ�ڵ��Լ������ӽڵ��м�ֵ
	 * 
	 * @param i
	 *            ��Ҫ���л�ȡ��ֵ�Ľڵ�
	 * @return ��ֵ��λ��
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
	 * �����������Ҫ�Ƚ������ڵ�
	 * 
	 * @param node1
	 *            �ڵ�1
	 * @param node2
	 *            �ڵ�2
	 * @return �ȽϽ��
	 */
	private boolean compareTwoNode(double node1, double node2) {
		if (orientation == Orientation.FROM_SMALL) {
			return node1 < node2;
		} else {
			return node1 > node2;
		}
	}

	/**
	 * ��ĳ���ڵ�����³�����
	 * 
	 * @param i
	 *            �ڵ�λ��
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
	 * ���Ͻ����������ҽ��н���
	 * 
	 * @param index
	 *            ��Ҫ������λ��
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
	 * ֱ�Ӹ���λ�ý��н���
	 * 
	 * @param node1
	 *            λ��1
	 * @param node2
	 *            λ��2
	 */
	private void swapNode(int node1, int node2) {
		double temp = arrayList.get(node1);
		arrayList.set(node1, arrayList.get(node2));
		arrayList.set(node2, temp);
	}

	/**
	 * ���Ǹ�����Ե�λ�ý��н���
	 * 
	 * @param i
	 *            ���ڵ��λ��
	 * @param pos
	 *            �ӽڵ�����λ��
	 */
	private void swapNode(int i, NodePos pos) {
		int nodeNum = getSubNum(i, pos);
		double temp = arrayList.get(nodeNum);
		arrayList.set(nodeNum, arrayList.get(i));
		arrayList.set(i, temp);
	}

	/**
	 * ����
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
