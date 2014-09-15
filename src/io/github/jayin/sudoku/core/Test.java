package io.github.jayin.sudoku.core;

import io.github.jayin.sudoku.core.Table.PendingNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Test {
	public static void main(String[] args) throws NumberFormatException,
			IOException {
		/** 9x9宫格 */
		int[][] cur_Matrix = new int[Table.ROW][Table.ROW];

		String cur_path = System.getProperty("user.dir");
		File f = new File(cur_path + File.separator + "data/data.txt");
		if (f.exists()) {

			BufferedReader br = new BufferedReader(new FileReader(f));
			String lineString = null;
			int line = 0;
			while ((lineString = br.readLine()) != null) {
				String[] s = lineString.split(" ");
				for (int j = 0; j < s.length; j++) {
					cur_Matrix[line][j] = Integer.parseInt(s[j]);
				}
				line++;
			}
			br.close();
			for (int i = 0; i < Table.ROW; i++) {
				for (int j = 0; j < Table.ROW; j++) {
					System.out.print(cur_Matrix[i][j] + " ");
				}
				System.out.println();
			}
			Sudoku s= new Sudoku(true);
			try {
				s.init(cur_Matrix).solve();
				System.out.println("-----OK-----");
				int[][] _Matrix = s.getMatrix();
				for (int i = 0; i < Table.ROW; i++) {
					for (int j = 0; j < Table.ROW; j++) {
						System.out.print(_Matrix[i][j] + " ");
					}
					System.out.println();
				}
				System.out.println("time : "+s.getCostTime()+"ms");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			//pedding
			Sudoku su= new Sudoku(false);
			int position = 2;
			PendingNode n;
			try {
				n = su.init(cur_Matrix).getPendingNode(position/Table.ROW, position%Table.ROW);
				if(n!=null){
					List<Integer> numbers = n.getPendingList();
					System.out.println(numbers.toString());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else {
			System.out.println("not exist");
		}

	}

}
