package io.github.jayin.sudoku.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test {
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
			new Sudoku(true).init(cur_Matrix).solve();
		} else {
			System.out.println("not exist");
		}

	}

}
