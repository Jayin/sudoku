package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
	/** �� */
	public final static int ROW = 9;
	/** �� */
	public final static int COLUMN = 9;
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
	private int[][] cur_table = new int[ROW + 1][COLUMN + 1];
	/** ��״̬ */
	private boolean[][] row = new boolean[ROW + 1][COLUMN + 1];
	/** ��״̬ */
	private boolean[][] column = new boolean[ROW + 1][COLUMN + 1];
	/** ��״̬ */
	private boolean[][] block = new boolean[ROW + 1][COLUMN + 1];
	/** δ��ո��б� */
	private ArrayList<PendingNode> pendingList = new ArrayList<PendingNode>();

	public Table(int[][] table) {
		cur_table = table;
		// ��ʼ��
		for (int i = 1; i <= ROW; i++)
			for (int j = 1; j <= COLUMN; j++) {
				if (cur_table[i][j] != 0) {
					int v = cur_table[i][j];
					setRow(i, v, true);
					setColumn(j, v, true);
					setBlock(getBlockId(i, j), v, true);
				}
			}
		// ����δ���б�
		for (int i = 1; i <= ROW; i++)
			for (int j = 1; j <= COLUMN; j++) {
				if (cur_table[i][j] == 0) {
					// ��һ����Ĵ�����
					Map<Integer, Boolean> filled = new HashMap<Integer, Boolean>();

					// ����������
					for (int k = 1; k <= ROW; k++)
						if (cur_table[k][j] != 0)
							filled.put(cur_table[k][j], true);
					// ����������
					for (int k = 1; k <= COLUMN; k++)
						if (cur_table[i][k] != 0)
							filled.put(cur_table[i][k], true);
					// ��ģ��������
					for (int k = 1; k <= ROW; k++)
						if (!block[getBlockId(i, j)][k])
							filled.put(k, true);
					List<Integer> list = new ArrayList<Integer>();
					for (int k = 1; i <= ROW; k++)
						if (!filled.get(k))
							list.add(k);
					pendingList.add(new PendingNode(i, j, list));
				}
			}
	}

	public void setRow(int rowId, int number, boolean ok) {
		row[rowId][number] = ok;
	}

	public void setColumn(int columnId, int number, boolean ok) {
		column[columnId][number] = ok;
	}

	public void setBlock(int blockId, int number, boolean ok) {
		block[blockId][number] = ok;
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
	
	public ArrayList<PendingNode> getPendingList(){
		return pendingList;
	}

	class PendingNode {
		int x;
		int y;
		List<Integer> pendingList;

		public PendingNode(int x, int y, List<Integer> pendingList) {
			this.x = x;
			this.y = y;
			this.pendingList = pendingList;
		}
	}

	public static void main(String[] args) {

	}

}
