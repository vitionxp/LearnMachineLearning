package cn.crocro.datastructurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import cn.crocro.datastructurer.BinaryHeap;
//TODO 增加分类的标签
//TODO KDNODE做成模板，比较做成接口
//TODO 改成K近邻分类接口
import cn.crocro.datastructurer.BinaryHeap.Orientation;

/**
 * 进行KD树的构造
 * 
 * @author 鳄鱼
 *
 */
public class KDTree {

	/**
	 * KD树的节点内部类
	 * 
	 * @author 鳄鱼
	 *
	 */
	public static class KDNode {
		/**
		 * kd树的左节点
		 */
		private KDNode left = null;
		/**
		 * kd树的右节点
		 */
		private KDNode right = null;
		/**
		 * 表示KD树的分割维度
		 */
		public int dim;
		/**
		 * 表示KD树的分割维度的值
		 */
		public double splitValue;
		/**
		 * 在这个分割维度的数值数组，因为有很多点的分割的值都是一样的
		 */
		public ArrayList<Data> nodes;
		/**
		 * 左边尚未分割的值
		 */
		public ArrayList<Data> leftUnClassifyNode;
		/**
		 * 右边尚未分割的值
		 */
		public ArrayList<Data> rightUnClassifyNode;

		/**
		 * KDnode节点构造器
		 * 
		 * @param node
		 *            这个节点的数据
		 * @param left
		 *            左边没有分类的数据
		 * @param right
		 *            右边还没有分类的数据
		 * @param dim
		 *            分类的维度
		 * @param splitValue
		 *            分类的数值
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
			string += "\n 分割值" + splitValue;
			string += "\n 分割轴" + dim;
			return string;
		}

	}

	/**
	 * 树节点内部存储的数据
	 * 
	 * @author 鳄鱼
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
	 * 节点比较器，通过输入不同的维度进行比较
	 * 
	 * @author 鳄鱼
	 *
	 */
	private class DataComparator implements Comparator<Data> {
		public int dim = 0;

		/**
		 * 输入维度构造器
		 * 
		 * @param dim
		 *            需要比较的维度
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
	 * 表示数组有多少维
	 */
	private int dimension;
	/**
	 * 表示根节点
	 */
	private KDNode rootNode;
	/**
	 * 需要进行分类的点的集合
	 */
	private ArrayList<Data> list = new ArrayList<>();

	/**
	 * 是否相交的判断，如果相交，就返回另一边的节点进行求解
	 * 
	 * @param node
	 *            需要判断的分隔器
	 * @param data
	 *            需要判断的数据
	 * @param dim
	 *            判断的维度
	 * @param value
	 *            判断的数值
	 * @return 相交的另一边的分类点
	 */
	private KDNode isInterset(KDNode node, Data data, int dim, double value) {
		// 进行匹配，表示相交
		if (Math.abs(data.innerData[dim] - node.nodes.get(0).innerData[dim]) < value) {
			// 相交之后决定是进入左节点还是右节点
			if (data.innerData[dim] > node.nodes.get(0).innerData[dim] && node.left != null) {
				return node.left;
			} else if (data.innerData[dim] < node.nodes.get(0).innerData[dim] && node.right != null) {
				return node.right;
			}
		}
		return null;
	}

	/**
	 * 计算那两个数据的距离
	 * 
	 * @param data1
	 *            数据1
	 * @param data2
	 *            数据2
	 * @return 距离
	 */
	private double calDistance(Data data1, Data data2) {
		double dis = 0;
		for (int i = 0; i < data1.innerData.length; i++) {
			dis += Math.pow(data1.innerData[i] - data2.innerData[i], 2.0);
		}
		return Math.sqrt(dis);
	}

	/**
	 * 数构造器
	 * 
	 * @param list
	 *            需要构造KD树的数据
	 */
	public KDTree(ArrayList<Data> list) {
		this.list = list;
		dimension = list.get(0).innerData.length;
	}

	/**
	 * 需要在KD树里面搜索某个数据
	 * 
	 * @param data
	 *            需要搜索的数据
	 * @return 最靠近的节点
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
		System.out.println("最近距离是" + value);
		return nearestNode;
	}

	/**
	 * 通过BBF算法进行k近邻域搜索并且利用大顶堆进行排序
	 * 
	 * @param data
	 *            需要搜索的数据
	 * @param k
	 *            需要寻找的最近邻个数
	 * @param max_search_nodes
	 *            最大搜索步数
	 * @param max_search_mills
	 *            最大搜索时间
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
	 * 初始化KD树
	 * 
	 * @return 是否成功
	 */
	public boolean initKDTree() {
		setRootNode(divideTree(list, 0));
		return true;
	}

	/**
	 * 打印KDnode节点的列表值
	 * 
	 * @param nodeList
	 */
	public static void printKDNodeList(ArrayList<KDNode> nodeList) {
		for (KDNode node : nodeList) {
			System.out.println(node);
		}
	}

	/**
	 * 打印KDnode节点
	 * 
	 * @param node
	 *            打印节点
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
	 * 二叉KD树查找属于他的点
	 * 
	 * @param data
	 * @return
	 */
	public KDNode findNodeFromRoot(Data data) {
		return findNode(data, rootNode);
	}

	/**
	 * 二叉KD树查找属于他的点
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
	 * 找到他的点并且进行记录查找的过程
	 * 
	 * @param data
	 *            需要查找的点
	 * @param node
	 *            需要查找的起始点
	 * @param list
	 *            查找路程
	 * @return 最终节点
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
	 * 打印成列的数据
	 * 
	 * @param list
	 *            需要打印的list的数据
	 */
	private void printList(ArrayList<Data> list) {
		System.out.print(Arrays.toString(list.get(0).innerData));
	}

	/**
	 * 将子节点进行分类，并且进行分成树
	 * 
	 * @param data
	 *            需要分类的点
	 * @param dim
	 *            在哪个维度进行分类
	 * @return 节点
	 */
	private KDNode divideTree(ArrayList<Data> data, int dim) {
		// 找到中位数及那几个数
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
	 * 进行测试
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
		System.out.println("需要检测的点是" + new Data(new double[] { 9, 2 }));
		System.out.println(tree.searchNearestNode(new Data(new double[] { 9, 2 })));
		System.out.println("需要检测的点是" + new Data(new double[] { 9, 2 }));
		tree.searchNearestNodeByBBF(new Data(new double[] { 9, 2 }),1,1, 1);
	}

}
