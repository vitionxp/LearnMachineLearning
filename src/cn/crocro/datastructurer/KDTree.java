package cn.crocro.datastructurer;

import java.util.ArrayList;

/**
 * ����KD���Ĺ���
 * @author ����
 *
 */
public class KDTree {
	
	/**
	 * KD���Ľڵ�
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
	 * ��ʼ��KD��
	 * @return �Ƿ�ɹ�
	 */
	public boolean initKDTree(){
		rootNode=DivideTree(list,0);
		return true;
	}
	
	
	private KDNode DivideTree(ArrayList<Data> list,int dim){
		
		return null;
	}
	/**
	 * ���в���
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello world");
	}

}
