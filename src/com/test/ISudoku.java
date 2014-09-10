package com.test;

public interface ISudoku {
	
	public Sudoku init(int[][] table);
	
	public Sudoku solve();
	
	public void check();

}
