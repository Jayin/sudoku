package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.test.Table.PendingNode;

public class Sudoku {
	Table table;
	List<PendingNode> pendingNodes;
	boolean debug = false;
	long time_start;

	public Sudoku() {
		this(false);
	}

	public Sudoku(boolean debug) {
		this.debug = debug;
		time_start = System.currentTimeMillis();
	}

	public boolean check(int[][] cur_table) {
		try {
			new Table(cur_table);
			return true;
		} catch (Exception e) {
			return false;
		}
		 
	}

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

	// solve(0)
	public void solve(int cur) {
		if (cur == pendingNodes.size()) {
			if(debug)System.out.println("耗时: "+(System.currentTimeMillis()-time_start)/1000.0+" s");
			int[][] res = table.getCurTable();
			if(check(res)){
				//print
				for (int i = 1; i <= Table.ROW; i++) {
					for (int j = 1; j < Table.ROW; j++) {
						System.out.print(res[i][j] + " ");
					}
					System.out.println();
				}
			}else{
				System.out.println("数独解答不正确");
			}

			
		} else {
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

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		/** 当前数独的矩阵 */
		int[][] cur_Matrix = new int[Table.ROW + 1][Table.ROW + 1];

		String cur_path = System.getProperty("user.dir");
		File f = new File(cur_path + File.separator + "data/data.txt");
		if (f.exists()) {

			BufferedReader br = new BufferedReader(new FileReader(f));
			String lineString = null;
			int line = 1;
			while ((lineString = br.readLine()) != null) {
				String[] s = lineString.split(" ");
				for (int j = 0; j < s.length; j++) {
					cur_Matrix[line][j + 1] = Integer.parseInt(s[j]);
				}
				line++;
			}

			new Sudoku(true).init(cur_Matrix).solve(0);
		} else {
			System.out.println("not exist");
		}

	}

}
