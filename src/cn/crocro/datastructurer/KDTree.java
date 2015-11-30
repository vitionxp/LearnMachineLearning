package cn.crocro.datastructurer;

import java.util.ArrayList;

/**
 * 进行KD树的构造
 * @author 鳄鱼
 *
 */
public class KDTree {
	
	/**
	 * KD树的节点
	 * @author Administrator
	 *
	 */
	public class KDNode{
		private KDNode left;
		private KDNode right;
		private Data[] nodes;
		public Data[] unClassifyNode;
		public KDNode getLeftNode(){
			return left;
		}
		public KDNode getRightNode(){
			return right;
		}
	}
	
	public class Data{
		public double[] data;
	}
	
	
	private int dimension;
	private KDNode rootNode;
	private ArrayList<Data> list=new ArrayList<>();
	
	public KDTree( ArrayList<Data> list){
		this.list=list;
		dimension=list.get(0).data.length;
	}
	
	/**
	 * 初始化KD树
	 * @return 是否成功
	 */
	public boolean initKDTree(){
		rootNode=DivideTree(list,0);
		return true;
	}
	
	
	private KDNode DivideTree(ArrayList<Data> list,int dim){
		
		return null;
	}
	/**
	 * 进行测试
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello world");
	}

}
