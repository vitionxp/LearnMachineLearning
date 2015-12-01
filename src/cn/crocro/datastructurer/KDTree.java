package cn.crocro.datastructurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import com.sun.org.apache.xml.internal.security.keys.content.KeyName;




/**
 * ����KD���Ĺ���
 * @author ����
 *
 */
public class KDTree {
	
	/**
	 * KD���Ľڵ��ڲ���
	 * @author ����
	 *
	 */
	public static class KDNode{
		private KDNode left=null;
		private KDNode right=null;
		public int dim;
		public double splitValue;
		public ArrayList<Data> nodes;
		public ArrayList<Data> leftUnClassifyNode;
		public ArrayList<Data> rightUnClassifyNode;
		public KDNode(ArrayList<Data> node,ArrayList<Data> left,ArrayList<Data> right,int dim,double splitValue){
			this.nodes=node;
			this.leftUnClassifyNode=left;
			this.rightUnClassifyNode=right;
			this.dim=dim;
			this.splitValue=splitValue;
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
			String string=new String();
			for(Data data:nodes){
				string += Arrays.toString(data.innerData)+"  ";
			}
			string +="\n �ָ�ֵ"+splitValue;
			string +="\n �ָ���"+dim;
			return string;
		}
		
	}
	
	/**
	 * ���ڵ��ڲ��洢������
	 * @author ����
	 *
	 */
	public static class Data{
		public double[] innerData;
		public Data(double[] data){
			this.innerData=data;
		}
	}
	
	/**
	 * �ڵ�Ƚ�����ͨ�����벻ͬ��ά�Ƚ��бȽ�
	 * @author ����
	 *
	 */
	private class DataComparator implements Comparator<Data>{
		public int dim=0;
		/**
		 * ����ά�ȹ�����
		 * @param dim ��Ҫ�Ƚϵ�ά��
		 */
		public DataComparator(int dim) {
			this.dim=dim;
		}
		@Override
		public int compare(Data o1, Data o2) {
			if (o1.innerData[dim]>o2.innerData[dim]){
				return 1;
			}
			else if (o1.innerData[dim]<o2.innerData[dim]) {
				return -1;
			}
			else {
				return 0;
			}		
		}	
	}

	private int dimension;
	private KDNode rootNode;
	private ArrayList<Data> list=new ArrayList<>();
	
	public KDTree( ArrayList<Data> list){
		this.list=list;
		dimension=list.get(0).innerData.length;
	}
	
	public KDNode SearchNearestNode(Data data){
		//���ȼ���Ƿ�λ�ò��ҽ���ѹջ
		//Ȼ�󵯳���������Ѱ
		ArrayList<KDNode> nodeList=new ArrayList<>();
		findNodeAndRecord(data, rootNode, nodeList);
		double value=Double.MAX_VALUE;
		KDNode nearestNode=rootNode;
		while(nodeList.size()!=0){
			KDNode node=nodeList.get(0);
			nodeList.remove(0);
			if(value>CalDistance(data, node.nodes.get(0))){
				value=CalDistance(data, node.nodes.get(0));
				nearestNode=node;
			}
			//�Ƿ��ཻ
		}
		return nearestNode;
	}
	
	private double CalDistance(Data data1,Data data2){
		double dis=0;
		for(int i=0;i<data1.innerData.length;i++){
			dis+=Math.pow(data1.innerData[i]-data2.innerData[i], 2.0);
		}
		return Math.sqrt(dis);
	}
	/**
	 * ��ʼ��KD��
	 * @return �Ƿ�ɹ�
	 */
	public boolean initKDTree(){
		setRootNode(divideTree(list,0));
		return true;
	}
	
	
	public static void  printKDNodeList(ArrayList<KDNode> nodeList){
		for (KDNode node:nodeList){
			System.out.println(node);
		}
	}
	public void printTree(KDNode node){
		if (node==null) {
			System.out.println("null");
			return;
		}
		printList(node.nodes);
		System.out.println("");
		printTree(node.left);
		printTree(node.right);
	}
	public KDNode findNodeFromRoot(Data data){
		return findNode(data, rootNode);
	}
	public KDNode findNode(Data data,KDNode node){
		while(true){
			if (data.innerData[node.dim]>=node.splitValue) {
				if(node.right==null){
					break;
				}
				node=node.right;
			}
			else{
				if(node.left==null){
					break;
				}
				node=node.left;
			}
		}
		return node;
	}
	public KDNode findNodeAndRecord(Data data,KDNode node,ArrayList<KDNode> list){
		while(true){
			list.add(node);
			if (data.innerData[node.dim]>=node.splitValue) {
				if(node.right==null){
					break;
				}
				node=node.right;
			}
			else{
				if(node.left==null){
					break;
				}
				node=node.left;
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
	 * @param list ��Ҫ��ӡ��list������
	 */
	private void printList(ArrayList<Data> list){
			System.out.print(Arrays.toString(list.get(0).innerData));
	}
	/**
	 * ���ӽڵ���з��࣬���ҽ��зֳ���
	 * @param data ��Ҫ����ĵ�
	 * @param dim ���ĸ�ά�Ƚ��з���
	 * @return �ڵ�
	 */
	private KDNode divideTree(ArrayList<Data> data,int dim){
		//�ҵ���λ�����Ǽ�����
		DataComparator compare=new DataComparator(dim);
		Collections.sort(data,compare);
		int middle=data.size()/2;
		double middleValue=data.get(middle).innerData[dim];
		ArrayList<Data> sameSplitElement=new ArrayList<>();
		ArrayList<Data> leftSplitElement=new ArrayList<>();
		ArrayList<Data> rightSplitElement=new ArrayList<>();
		for (Data dataElement:data){
			if (dataElement.innerData[dim]==middleValue){
				sameSplitElement.add(dataElement);
			}
			else if (dataElement.innerData[dim]<middleValue) {
				leftSplitElement.add(dataElement);
			}
			else{
				rightSplitElement.add(dataElement);
			}
		}
		KDNode node=new KDNode(sameSplitElement, leftSplitElement, rightSplitElement,dim,middleValue);
		int nextDim=dim+1;
		if(nextDim>=dimension){
			nextDim=0;
		}
		if(leftSplitElement.size()!=0){
			node.setLeft(divideTree(leftSplitElement,nextDim));
		}
		if(rightSplitElement.size()!=0){
			node.setRight(divideTree(rightSplitElement,nextDim));
		}
		return node;
	}
	
	/**
	 * ���в���
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Data> arrayList=new ArrayList<Data>();
		Data data1=new Data(new double[]{2,3});
		Data data2=new Data(new double[]{5,4});
		Data data3=new Data(new double[]{9,6});
		Data data4=new Data(new double[]{4,7});
		Data data5=new Data(new double[]{8,1});
		Data data6=new Data(new double[]{7,2});
		arrayList.add(data1);
		arrayList.add(data2);
		arrayList.add(data3);
		arrayList.add(data4);
		arrayList.add(data5);
		arrayList.add(data6);
		KDTree tree=new KDTree(arrayList);
		tree.initKDTree();
		ArrayList<KDNode> nodeList=new ArrayList<>();
		tree.findNodeAndRecord(new Data(new double[]{3,5}), tree.rootNode,nodeList);
		KDTree.printKDNodeList(nodeList);
		ArrayList<Integer> listInteger=new ArrayList<>();
		listInteger.add(3);
		listInteger.add(2);
		listInteger.add(1);
		listInteger.add(3);
		listInteger.add(4);
		listInteger.add(5);
		//System.out.println(tree.findNodeFromRoot(new Data(new double[]{8,7})));
		//tree.printTree(tree.rootNode);
	}

}
