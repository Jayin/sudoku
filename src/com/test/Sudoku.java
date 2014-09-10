package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Sudoku implements ISudoku {
	Table table;

	public Sudoku() {

	}

	@Override public void check() {

	}

	@Override public void init(int[][] cur_Matrix) {
		table = new Table(cur_Matrix);
	}

	@Override public void solve() {

	}

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		/** 当前数独的矩阵 */
		int[][] cur_Matrix = new int[Table.ROW + 1][Table.COLUMN + 1];

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

			for (int i = 1; i <= Table.ROW; i++) {
				for (int j = 1; j <= Table.COLUMN; j++) {
					System.out.print(cur_Matrix[i][j] + " ");
				}
				System.out.println();
			}
		} else {
			System.out.println("not exist");
		}
		
		
		
	}

}
