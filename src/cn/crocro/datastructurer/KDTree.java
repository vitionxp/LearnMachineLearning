package cn.crocro.datastructurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import cn.crocro.datastructurer.BinaryHeap;
//TODO ���ӷ���ı�ǩ
//TODO KDNODE����ģ�壬�Ƚ����ɽӿ�
//TODO �ĳ�K���ڷ���ӿ�
import cn.crocro.datastructurer.BinaryHeap.Orientation;

/**
 * ����KD���Ĺ���
 * 
 * @author ����
 *
 */
public class KDTree {

	/**
	 * KD���Ľڵ��ڲ���
	 * 
	 * @author ����
	 *
	 */
	public static class KDNode {
		/**
		 * kd������ڵ�
		 */
		private KDNode left = null;
		/**
		 * kd�����ҽڵ�
		 */
		private KDNode right = null;
		/**
		 * ��ʾKD���ķָ�ά��
		 */
		public int dim;
		/**
		 * ��ʾKD���ķָ�ά�ȵ�ֵ
		 */
		public double splitValue;
		/**
		 * ������ָ�ά�ȵ���ֵ���飬��Ϊ�кܶ��ķָ��ֵ����һ����
		 */
		public ArrayList<Data> nodes;
		/**
		 * �����δ�ָ��ֵ
		 */
		public ArrayList<Data> leftUnClassifyNode;
		/**
		 * �ұ���δ�ָ��ֵ
		 */
		public ArrayList<Data> rightUnClassifyNode;

		/**
		 * KDnode�ڵ㹹����
		 * 
		 * @param node
		 *            ����ڵ������
		 * @param left
		 *            ���û�з��������
		 * @param right
		 *            �ұ߻�û�з��������
		 * @param dim
		 *            �����ά��
		 * @param splitValue
		 *            �������ֵ
		 */
		public KDNode(ArrayList<Data> node, ArrayList<Data> left, ArrayList<Data> right, int dim, double splitValue) {
			this.nodes = node;
			this.leftUnClassifyNode = left;
			this.rightUnClassifyNode = right;
			this.dim = dim;
			this.splitValue = splitValue;
		}

		public KDNode getLeft() {
			return left;
		}

		public void setLeft(KDNode left) {
			this.left = left;
		}

		public KDNode getRight() {
			return right;
		}

		public void setRight(KDNode right) {
			this.right = right;
		}

		@Override
		public String toString() {
			String string = new String();
			for (Data data : nodes) {
				string += Arrays.toString(data.innerData) + "  ";
			}
			string += "\n �ָ�ֵ" + splitValue;
			string += "\n �ָ���" + dim;
			return string;
		}

	}

	/**
	 * ���ڵ��ڲ��洢������
	 * 
	 * @author ����
	 *
	 */
	public static class Data {
		@Override
		public String toString() {
			String string = Arrays.toString(innerData);
			return string;
		}

		public double[] innerData;

		public Data(double[] data) {
			this.innerData = data;
		}
	}

	/**
	 * �ڵ�Ƚ�����ͨ�����벻ͬ��ά�Ƚ��бȽ�
	 * 
	 * @author ����
	 *
	 */
	private class DataComparator implements Comparator<Data> {
		public int dim = 0;

		/**
		 * ����ά�ȹ�����
		 * 
		 * @param dim
		 *            ��Ҫ�Ƚϵ�ά��
		 */
		public DataComparator(int dim) {
			this.dim = dim;
		}

		@Override
		public int compare(Data o1, Data o2) {
			if (o1.innerData[dim] > o2.innerData[dim]) {
				return 1;
			} else if (o1.innerData[dim] < o2.innerData[dim]) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * ��ʾ�����ж���ά
	 */
	private int dimension;
	/**
	 * ��ʾ���ڵ�
	 */
	private KDNode rootNode;
	/**
	 * ��Ҫ���з���ĵ�ļ���
	 */
	private ArrayList<Data> list = new ArrayList<>();

	/**
	 * �Ƿ��ཻ���жϣ�����ཻ���ͷ�����һ�ߵĽڵ�������
	 * 
	 * @param node
	 *            ��Ҫ�жϵķָ���
	 * @param data
	 *            ��Ҫ�жϵ�����
	 * @param dim
	 *            �жϵ�ά��
	 * @param value
	 *            �жϵ���ֵ
	 * @return �ཻ����һ�ߵķ����
	 */
	private KDNode isInterset(KDNode node, Data data, int dim, double value) {
		// ����ƥ�䣬��ʾ�ཻ
		if (Math.abs(data.innerData[dim] - node.nodes.get(0).innerData[dim]) < value) {
			// �ཻ֮������ǽ�����ڵ㻹���ҽڵ�
			if (data.innerData[dim] > node.nodes.get(0).innerData[dim] && node.left != null) {
				return node.left;
			} else if (data.innerData[dim] < node.nodes.get(0).innerData[dim] && node.right != null) {
				return node.right;
			}
		}
		return null;
	}

	/**
	 * �������������ݵľ���
	 * 
	 * @param data1
	 *            ����1
	 * @param data2
	 *            ����2
	 * @return ����
	 */
	private double calDistance(Data data1, Data data2) {
		double dis = 0;
		for (int i = 0; i < data1.innerData.length; i++) {
			dis += Math.pow(data1.innerData[i] - data2.innerData[i], 2.0);
		}
		return Math.sqrt(dis);
	}

	/**
	 * ��������
	 * 
	 * @param list
	 *            ��Ҫ����KD��������
	 */
	public KDTree(ArrayList<Data> list) {
		this.list = list;
		dimension = list.get(0).innerData.length;
	}

	/**
	 * ��Ҫ��KD����������ĳ������
	 * 
	 * @param data
	 *            ��Ҫ����������
	 * @return ����Ľڵ�
	 */
	public KDNode searchNearestNode(Data data) {
		ArrayList<KDNode> nodeList = new ArrayList<>();
		findNodeAndRecord(data, rootNode, nodeList);
		double value = Double.MAX_VALUE;
		KDNode nearestNode = rootNode;
		while (nodeList.size() != 0) {
			KDNode node = nodeList.get(0);
			nodeList.remove(0);
			if (value > calDistance(data, node.nodes.get(0))) {
				value = calDistance(data, node.nodes.get(0));
				nearestNode = node;
			}
			KDNode intersetNode = isInterset(node, data, node.dim, value);
			if (intersetNode != null) {
				findNodeAndRecord(data, intersetNode, nodeList);
			}

		}
		System.out.println("���������" + value);
		return nearestNode;
	}

	/**
	 * ͨ��BBF�㷨����k�����������������ô󶥶ѽ�������
	 * 
	 * @param data
	 *            ��Ҫ����������
	 * @param k
	 *            ��ҪѰ�ҵ�����ڸ���
	 * @param max_search_nodes
	 *            �����������
	 * @param max_search_mills
	 *            �������ʱ��
	 * @return
	 */
	public ArrayList<Double> searchNearestNodeByBBF(Data data, int k, int searchNodes, int millseconds) {
		ArrayList<KDNode> nodeList = new ArrayList<>();
		double[] initArray = new double[k];
		for (int i = 0; i < k; i++) {
			initArray[i] = Double.MAX_VALUE;
		}
		nodeList.add(rootNode);
		BinaryHeap binaryHeap = new BinaryHeap(initArray, Orientation.FROM_LARGE);
		for(int i=0;i<k;i++){
			binaryHeap.add(Double.MAX_VALUE);
		}
		while (nodeList.size() != 0) {
			KDNode node = nodeList.get(0);
			nodeList.remove(0);
			while (true) {
				double tempValue = calDistance(data, node.nodes.get(0));
				if (tempValue < binaryHeap.getRoot()) {
					binaryHeap.delete(0);
					binaryHeap.add(tempValue);
				}
				if (data.innerData[node.dim] >= node.splitValue) {
					
					if (node.right == null) {
						break;
					}
					
					if(node.left!=null){
						nodeList.add(node.left);
					}					
					node = node.right;
				} else {
					if (node.left == null) {
						break;
					}
					if(node.right!=null){
						nodeList.add(node.right);
					}
					node = node.left;
				}
			}
		}

		return binaryHeap.getArrayList();
	}

	/**
	 * ��ʼ��KD��
	 * 
	 * @return �Ƿ�ɹ�
	 */
	public boolean initKDTree() {
		setRootNode(divideTree(list, 0));
		return true;
	}

	/**
	 * ��ӡKDnode�ڵ���б�ֵ
	 * 
	 * @param nodeList
	 */
	public static void printKDNodeList(ArrayList<KDNode> nodeList) {
		for (KDNode node : nodeList) {
			System.out.println(node);
		}
	}

	/**
	 * ��ӡKDnode�ڵ�
	 * 
	 * @param node
	 *            ��ӡ�ڵ�
	 */
	public static void printKDNode(KDNode node) {
		System.out.println(node);
	}

	public void printTree(KDNode node) {
		if (node == null) {
			System.out.println("null");
			return;
		}
		printList(node.nodes);
		System.out.println("");
		printTree(node.left);
		printTree(node.right);
	}

	/**
	 * ����KD�������������ĵ�
	 * 
	 * @param data
	 * @return
	 */
	public KDNode findNodeFromRoot(Data data) {
		return findNode(data, rootNode);
	}

	/**
	 * ����KD�������������ĵ�
	 * 
	 * 
	 */
	public KDNode findNode(Data data, KDNode node) {
		while (true) {
			if (data.innerData[node.dim] >= node.splitValue) {
				if (node.right == null) {
					break;
				}
				node = node.right;
			} else {
				if (node.left == null) {
					break;
				}
				node = node.left;
			}
		}
		return node;
	}

	/**
	 * �ҵ����ĵ㲢�ҽ��м�¼���ҵĹ���
	 * 
	 * @param data
	 *            ��Ҫ���ҵĵ�
	 * @param node
	 *            ��Ҫ���ҵ���ʼ��
	 * @param list
	 *            ����·��
	 * @return ���սڵ�
	 */
	public KDNode findNodeAndRecord(Data data, KDNode node, ArrayList<KDNode> list) {
		while (true) {
			list.add(0, node);
			if (data.innerData[node.dim] >= node.splitValue) {
				if (node.right == null) {
					break;
				}
				list.add(0, node.left);
				node = node.right;
			} else {
				if (node.left == null) {
					break;
				}
				node = node.left;
			}
		}
		return node;
	}

	public KDNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(KDNode rootNode) {
		this.rootNode = rootNode;
	}

	/**
	 * ��ӡ���е�����
	 * 
	 * @param list
	 *            ��Ҫ��ӡ��list������
	 */
	private void printList(ArrayList<Data> list) {
		System.out.print(Arrays.toString(list.get(0).innerData));
	}

	/**
	 * ���ӽڵ���з��࣬���ҽ��зֳ���
	 * 
	 * @param data
	 *            ��Ҫ����ĵ�
	 * @param dim
	 *            ���ĸ�ά�Ƚ��з���
	 * @return �ڵ�
	 */
	private KDNode divideTree(ArrayList<Data> data, int dim) {
		// �ҵ���λ�����Ǽ�����
		DataComparator compare = new DataComparator(dim);
		Collections.sort(data, compare);
		int middle = data.size() / 2;
		double middleValue = data.get(middle).innerData[dim];
		ArrayList<Data> sameSplitElement = new ArrayList<>();
		ArrayList<Data> leftSplitElement = new ArrayList<>();
		ArrayList<Data> rightSplitElement = new ArrayList<>();
		for (Data dataElement : data) {
			if (dataElement.innerData[dim] == middleValue) {
				sameSplitElement.add(dataElement);
			} else if (dataElement.innerData[dim] < middleValue) {
				leftSplitElement.add(dataElement);
			} else {
				rightSplitElement.add(dataElement);
			}
		}
		KDNode node = new KDNode(sameSplitElement, leftSplitElement, rightSplitElement, dim, middleValue);
		int nextDim = dim + 1;
		if (nextDim >= dimension) {
			nextDim = 0;
		}
		if (leftSplitElement.size() != 0) {
			node.setLeft(divideTree(leftSplitElement, nextDim));
			node.leftUnClassifyNode.clear();
		}
		if (rightSplitElement.size() != 0) {
			node.setRight(divideTree(rightSplitElement, nextDim));
			node.rightUnClassifyNode.clear();
		}
		return node;
	}

	/**
	 * ���в���
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Data> arrayList = new ArrayList<Data>();
		Data data1 = new Data(new double[] { 2, 3 });
		Data data2 = new Data(new double[] { 3, 3 });
		Data data3 = new Data(new double[] { 9, 6 });
		Data data4 = new Data(new double[] { 4, 7 });
		Data data5 = new Data(new double[] { 8, 1 });
		Data data6 = new Data(new double[] { 7, 2 });
		arrayList.add(data1);
		arrayList.add(data2);
		arrayList.add(data3);
		arrayList.add(data4);
		arrayList.add(data5);
		arrayList.add(data6);
		KDTree tree = new KDTree(arrayList);
		tree.initKDTree();
		ArrayList<KDNode> nodeList = new ArrayList<>();
		tree.findNodeAndRecord(new Data(new double[] { 9, 2 }), tree.rootNode, nodeList);
		System.out.println("��Ҫ���ĵ���" + new Data(new double[] { 9, 2 }));
		System.out.println(tree.searchNearestNode(new Data(new double[] { 9, 2 })));
		System.out.println("��Ҫ���ĵ���" + new Data(new double[] { 9, 2 }));
		tree.searchNearestNodeByBBF(new Data(new double[] { 9, 2 }),1,1, 1);
	}

}
