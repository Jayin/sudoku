package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
	/** �� */
	public final static int ROW = 9;
	/** ģ��id */
	private int[][] module =
		{
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 1, 1, 2, 2, 2, 3, 3, 3 },
			{ 0, 1, 1, 1, 2, 2, 2, 3, 3, 3 },
			{ 0, 1, 1, 1, 2, 2, 2, 3, 3, 3 },
			{ 0, 4, 4, 4, 5, 5, 5, 6, 6, 6 },
			{ 0, 4, 4, 4, 5, 5, 5, 6, 6, 6 },
			{ 0, 4, 4, 4, 5, 5, 5, 6, 6, 6 },
			{ 0, 7, 7, 7, 8, 8, 8, 9, 9, 9 },
			{ 0, 7, 7, 7, 8, 8, 8, 9, 9, 9 },
			{ 0, 7, 7, 7, 8, 8, 8, 9, 9, 9 }, };
	/** ��ǰ�����ľ��� */
	private int[][] cur_table = new int[ROW + 1][ROW + 1];
	/** ��״̬ */
	private boolean[][] row = new boolean[ROW + 1][ROW + 1];
	/** ��״̬ */
	private boolean[][] column = new boolean[ROW + 1][ROW + 1];
	/** ��״̬ */
	private boolean[][] block = new boolean[ROW + 1][ROW + 1];
	/** δ��ո��б� */
	private List<PendingNode> pendingNodes;

	public Table(int[][] table) throws Exception {
		cur_table = table;
		// ��ʼ��
		for (int i = 1; i <= ROW; i++)
			for (int j = 1; j <= ROW; j++) {
				if (cur_table[i][j] != 0) {
					int v = cur_table[i][j];
					
					if(getRow(i, v) || getColumn(j, v) || getBlock(getBlockId(i, j), v)){
						throw new Exception("����ʧ��:���������Ϲ涨");
					}
					setRow(i, v, true);
					setColumn(j, v, true);
					setBlock(getBlockId(i, j), v, true);
				}
			}
		// ����δ���б�
		List<PendingNode> tmpPenddingList = new ArrayList<PendingNode>();
		for (int i = 1; i <= ROW; i++)
			for (int j = 1; j <= ROW; j++) {
				if (cur_table[i][j] == 0) {
					// ��һ����Ĵ�����
					Map<Integer, Boolean> filled = new HashMap<Integer, Boolean>();

					// ����������
					for (int k = 1; k <= ROW; k++)
						if (cur_table[k][j] != 0)
							filled.put(cur_table[k][j], true);
					// ����������
					for (int k = 1; k <= ROW; k++)
						if (cur_table[i][k] != 0)
							filled.put(cur_table[i][k], true);
					// ��ģ��������
					for (int k = 1; k <= ROW; k++)
						// if (!block[getBlockId(i, j)][k])
						// filled.put(cur_table[i][k], true);
						if (block[getBlockId(i, j)][k]) {
							filled.put(k, true);
						}
					List<Integer> list = new ArrayList<Integer>();
					for (int k = 1; k <= ROW; k++)
						if (filled.get(k) == null)
							list.add(k);
					tmpPenddingList.add(new PendingNode(i, j, list));
				}
			}
		PendingNode[] nodes = tmpPenddingList.toArray(new PendingNode[]
			{});
		Arrays.sort(nodes, new Comparator<PendingNode>() {

			@Override public int compare(PendingNode o1, PendingNode o2) {
				if (o1.getPendingList().size() > o2.getPendingList().size())
					return 1;
				else
					return -1;
			}
		});
		pendingNodes = (List<PendingNode>) Arrays.asList(nodes);

	}

	public void setCurTable(int x, int y, int value) {
		cur_table[x][y] = value;
	}

	public int[][] getCurTable() {
		return cur_table;
	}

	public void setRow(int rowId, int number, boolean ok) {
		row[rowId][number] = ok;
	}

	public boolean getRow(int rowId, int number) {
		return row[rowId][number];
	}

	public void setColumn(int columnId, int number, boolean ok) {
		column[columnId][number] = ok;
	}

	public boolean getColumn(int columnId, int number) {
		return column[columnId][number];
	}

	public void setBlock(int blockId, int number, boolean ok) {
		block[blockId][number] = ok;
	}

	public boolean getBlock(int blockId, int number) {
		return block[blockId][number];
	}

	public boolean enableRow(int rowId, int number) {
		return !row[rowId][number];
	}

	public boolean enableColumn(int columnId, int number) {
		return !column[columnId][number];
	}

	public boolean enableBlock(int blockId, int number) {
		return !block[blockId][number];
	}

	/**
	 * ����(x,y)
	 * 
	 * @param x
	 * @param y
	 */
	public int getBlockId(int x, int y) {
		return module[x][y];
	}

	public List<PendingNode> getPendingNodes() {
		return pendingNodes;
	}

	class PendingNode {
		private int x;
		private int y;
		private List<Integer> pendingList;

		public PendingNode(int x, int y, List<Integer> pendingList) {
			this.x = x;
			this.y = y;
			this.pendingList = pendingList;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public List<Integer> getPendingList() {
			return pendingList;
		}

		public void setPendingList(List<Integer> pendingList) {
			this.pendingList = pendingList;
		}

		@Override public String toString() {
			return "PendingNode [pendingList=" + pendingList.toString()
					+ ", x=" + x + ", y=" + y + "]";
		}

	}

	public static void main(String[] args) {

	}

}
