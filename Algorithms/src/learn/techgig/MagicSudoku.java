package learn.techgig;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagicSudoku {

	public static void main(String[] args) throws Exception {

		int[][] sudoku = { { 1, 2, 3, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 1, 2, 3 }, { 0, 0, 0, 1, 2, 3, 0, 0, 0 },
				{ 2, 3, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 2, 3, 1 }, { 0, 0, 0, 2, 3, 1, 0, 0, 0 },
				{ 3, 1, 2, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 3, 1, 2 }, { 0, 0, 0, 3, 1, 2, 0, 0, 0 } };
		SolveMagicSquare(sudoku);

	}

	public static int SolveMagicSquare(int[][] sudoku) {
		int sudokuSize = sudoku.length;
		int smallSquare = (int) Math.sqrt(sudokuSize);
		Deque<String> visitedNodes = new ArrayDeque<String>();
		Map<String, List<Integer>> usedValues = new HashMap<String, List<Integer>>();
		int code = 1;
		rowLoop: for (int i = 0; i < sudokuSize; i++) {
			for (int j = 0; j < sudokuSize; j++) {
				if (sudoku[i][j] == 0) {
					boolean safe = false;
					List<Integer> values = new ArrayList<Integer>();
					String key = i + ":" + j;
					if (usedValues.containsKey(key)) {
						values = usedValues.get(key);
					} else {
						usedValues.put(key, values);
					}
					for (int k = 1; k <= sudokuSize; k++) {
						if (values.contains(k)) {
							continue;
						}
						sudoku[i][j] = k;
						values.add(k);
						safe = isSafe(sudoku, i, j, sudokuSize, smallSquare);
						if (safe) {
							visitedNodes.add(i + ":" + j);
							break;
						}
					}
					if (!safe) {
						sudoku[i][j] = 0;
						String node = visitedNodes.pollLast();
						if (node != null) {
							int row = Integer.parseInt(node.split(":")[0]);
							int column = Integer.parseInt(node.split(":")[1]);
							sudoku[row][column] = 0;
							values.clear();
							i = row;
							j = column - 1;
							continue;
						} else {
							code = 0;
							break rowLoop;
						}
					}
				}
			}
		}
		return code;

	}

	public static boolean isSafe(int[][] sudoku, int currRow, int currColumn, int size, int smallSquare) {
		if (sudoku[0][0] != 1) {
			return false;
		}

		if (!rowColumnCheck(sudoku, size)) {
			return false;
		}

		if (!checkSquare(sudoku, smallSquare, size)) {
			return false;
		}

		int smallSquareRow = 0;
		int smallSquareCol = 0;
		for (int i = smallSquare - 1; i > 0; i--) {
			if (currRow >= i * smallSquare) {
				smallSquareRow = i * smallSquare;
				break;
			}
		}
		for (int i = smallSquare - 1; i > 0; i--) {
			if (currColumn >= i * smallSquare) {
				smallSquareCol = i * smallSquare;
				break;
			}
		}

		// left swap
		if (smallSquareCol - smallSquare >= 0) {
			for (int i = smallSquareRow; i < smallSquareRow + smallSquare; i++) {
				for (int j = smallSquareCol; j < smallSquareCol + smallSquare; j++) {
					swap(sudoku, i, j, i, j - smallSquare);
				}
			}
			boolean check = rowColumnCheck(sudoku, size);
			for (int i = smallSquareRow; i < smallSquareRow + smallSquare; i++) {
				for (int j = smallSquareCol; j < smallSquareCol + smallSquare; j++) {
					swap(sudoku, i, j, i, j - smallSquare);
				}
			}

			if (!check) {
				return false;
			}
		}

		// top swap
		if (smallSquareRow - smallSquare >= 0) {
			for (int i = smallSquareRow; i < smallSquareRow + smallSquare; i++) {
				for (int j = smallSquareCol; j < smallSquareCol + smallSquare; j++) {
					swap(sudoku, i, j, i - smallSquare, j);
				}
			}
			boolean check = rowColumnCheck(sudoku, size);
			for (int i = smallSquareRow; i < smallSquareRow + smallSquare; i++) {
				for (int j = smallSquareCol; j < smallSquareCol + smallSquare; j++) {
					swap(sudoku, i, j, i - smallSquare, j);
				}
			}

			if (!check) {
				return false;
			}
		}

		// right swap
		if (smallSquareCol + smallSquare < size) {
			for (int i = smallSquareRow; i < smallSquareRow + smallSquare; i++) {
				for (int j = smallSquareCol; j < smallSquareCol + smallSquare; j++) {
					swap(sudoku, i, j, i, j + smallSquare);
				}
			}
			boolean check = rowColumnCheck(sudoku, size);
			for (int i = smallSquareRow; i < smallSquareRow + smallSquare; i++) {
				for (int j = smallSquareCol; j < smallSquareCol + smallSquare; j++) {
					swap(sudoku, i, j, i, j + smallSquare);
				}
			}

			if (!check) {
				return false;
			}
		}

		// bottom swap
		if (smallSquareRow + smallSquare < size) {
			for (int i = smallSquareRow; i < smallSquareRow + smallSquare; i++) {
				for (int j = smallSquareCol; j < smallSquareCol + smallSquare; j++) {
					swap(sudoku, i, j, i + smallSquare, j);
				}
			}
			boolean check = rowColumnCheck(sudoku, size);
			for (int i = smallSquareRow; i < smallSquareRow + smallSquare; i++) {
				for (int j = smallSquareCol; j < smallSquareCol + smallSquare; j++) {
					swap(sudoku, i, j, i + smallSquare, j);
				}
			}

			if (!check) {
				return false;
			}
		}

		return true;

	}

	private static boolean rowColumnCheck(int[][] sudoku, int size) {

		List<Integer> visitedValues = new ArrayList<Integer>();
		// row check
		for (int i = 0; i < size; i++) {
			visitedValues.clear();
			for (int j = 0; j < size; j++) {
				if (sudoku[i][j] == 0) {
					continue;
				}
				if (visitedValues.contains(sudoku[i][j])) {
					return false;
				}
				visitedValues.add(sudoku[i][j]);
			}
		}

		// Column check
		for (int i = 0; i < size; i++) {
			visitedValues.clear();
			for (int j = 0; j < size; j++) {
				if (sudoku[j][i] == 0) {
					continue;
				}
				if (visitedValues.contains(sudoku[j][i])) {
					return false;
				}
				visitedValues.add(sudoku[j][i]);
			}
		}

		return true;
	}

	private static void swap(int[][] sudoku, int a, int b, int c, int d) {
		int temp = sudoku[a][b];
		sudoku[a][b] = sudoku[c][d];
		sudoku[c][d] = temp;
	}

	private static boolean checkSquare(int[][] sudoku, int smallSquare, int size) {
		List<Integer> visitedValues = new ArrayList<>();
		int squareIndex = 0;
		while (squareIndex < size) {
			visitedValues.clear();
			for (int i = squareIndex; i < squareIndex + smallSquare; i++) {
				for (int j = squareIndex; j < squareIndex + smallSquare; j++) {
					if (sudoku[i][j] == 0) {
						continue;
					}
					if (visitedValues.contains(sudoku[i][j])) {
						return false;
					}
					visitedValues.add(sudoku[i][j]);
				}

			}
			squareIndex += smallSquare;
		}

		return true;
	}

}
