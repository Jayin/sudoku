package io.github.jayin.sudoku.core;

public class U {
	public static void copyMatrix(int[][] to, int[][] from) {
		int row = from.length;
		for (int i = 0; i < row; i++)
			for (int j = 0; j < row; j++)
				to[i][j] = from[i][j];
	}
	
	public static void main(String[] args) {
		int a[][] = {
				{1,2,3},{4,5,6},{7,8,9}
		};
		int b[][] = new int[3][3];
		U.copyMatrix(b, a);
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				System.out.print(b[i][j]+" ");
			}
			System.out.println();
		}
			
			
	}

}
