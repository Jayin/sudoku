package io.github.jayin.sudoku.core;

import io.github.jayin.sudoku.core.Table.PendingNode;

import java.util.List;

/**
 * ���� <strong>Usage</strong>
 * TODO ���ݸ������Ѷ�,�Զ�����һ����������
 * <pre>
 *  //��
 * Sudoku sudoku = new Sudoku();
 * sudoku.init(cur_Matrix).solve();
 * </pre>
 * <pre>
 * 	 //������
 *   new Sudoku.check(cur_Matrix);
 * </pre>
 * <p>ʵ��˼·</p>
 * <p>1.�����ÿ�У�ÿ�У�ÿģ��Ĵ����б�</p>
 * <p>2.�Դ����б����򣬴��������ٵ�����ǰ��</p>
 * <p>3.������Ϊ1�ģ�ֱ������</p>
 * 
 * @author Jayin
 * 
 */
public class Sudoku {
	/** ������ */
	Table table;
	/** ����� */
	List<PendingNode> pendingNodes;
	/** ����� */
	boolean debug = false;
	/** ��ʼʱ�� */
	long time_start;

	public Sudoku() {
		this(false);
	}

	/**
	 * �Ƿ�������ģʽ
	 * 
	 * @param debug
	 */
	public Sudoku(boolean debug) {
		this.debug = debug;
		time_start = System.currentTimeMillis();
	}

	/**
	 * ��������Ƿ���ȷ
	 * 
	 * @param cur_table
	 * @return true if it's correct
	 */
	public boolean check(int[][] cur_table) {
		try {
			for (int i = 1; i <= Table.ROW; i++)
				for (int j = 1; j <= Table.ROW; j++)
					if (cur_table[i][j] == 0) {
						return false;
					}
			new Table(cur_table);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * ��������
	 * 
	 * @param cur_Matrix
	 * @return
	 */
	public Sudoku init(int[][] cur_Matrix) {
		try {
			table = new Table(cur_Matrix);
			pendingNodes = table.getPendingNodes();
			if (debug) {
				for (PendingNode node : table.getPendingNodes()) {
					System.out.println(node.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * ��ʼ���
	 */
	public void solve() {
		solve(0);
	}

	/**
	 * �ӵ�һ����㿪ʼ���
	 * 
	 * @param cur
	 */
	private void solve(int cur) {
		if (cur == pendingNodes.size()) {
			if (debug)
				System.out.println("��ʱ: "
						+ (System.currentTimeMillis() - time_start) / 1000.0
						+ " s");
			int[][] result = table.getCurTable();
			if (check(result)) {
				// print
				for (int i = 1; i <= Table.ROW; i++) {
					for (int j = 1; j < Table.ROW; j++) {
						System.out.print(result[i][j] + " ");
					}
					System.out.println();
				}
			} else {
				System.out.println("���������ȷ");
			}

		} else {
			if(pendingNodes.get(cur).getPendingList().size() == 1){
				PendingNode node = pendingNodes.get(cur);
				int x = node.getX();
				int y = node.getY();
				int v = node.getPendingList().get(0);
				int blockId = table.getBlockId(x, y);
				table.setCurTable(x, y, v);
				table.setRow(x, v, true);
				table.setColumn(y, v, true);
				table.setBlock(blockId, v, true);
				solve(cur + 1);
			}else{
				for (int i = 0; i < pendingNodes.get(cur).getPendingList().size(); i++) {
					PendingNode node = pendingNodes.get(cur);
					int x = node.getX();
					int y = node.getY();
					int v = node.getPendingList().get(i);
					int blockId = table.getBlockId(x, y);
					if (table.enableRow(x, v) && table.enableColumn(y, v)
							&& table.enableBlock(blockId, v)) {
						table.setCurTable(x, y, v);
						table.setRow(x, v, true);
						table.setColumn(y, v, true);
						table.setBlock(blockId, v, true);
						solve(cur + 1);
						table.setCurTable(x, y, 0);
						table.setRow(x, v, false);
						table.setColumn(y, v, false);
						table.setBlock(blockId, v, false);
					}
				}
			}
		}
	}

}
