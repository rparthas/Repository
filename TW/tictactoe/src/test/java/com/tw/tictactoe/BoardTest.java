package com.tw.tictactoe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	Board board = null;

	@Before
	public void setUp() throws Exception {
		board = new Board();

	}

	@Test
	public void testBoardExists() {
		Assert.assertNotNull(board);

	}

	@Test
	public void testBoardCapacity() {
		Assert.assertEquals(board.getTotalCells(), 9);

	}

	@Test
	public void testBoardEmpty() {
		Assert.assertTrue(board.isEmpty());
	}

	@Test
	public void testMovingPlayerToCell() {
		board.movePlayer(0, 0, Type.HUMAN);
		Assert.assertEquals(board.getPlayer(0, 0), 1);
	}

	@Test
	public void testBoardNonEmpty() {
		board.movePlayer(0, 1, Type.HUMAN);
		Assert.assertFalse(board.isEmpty());
	}

	@Test
	public void testValidPositionInBoard() {
		Assert.assertFalse(board.movePlayer(1000, 1000, Type.HUMAN));
	}

	@Test
	public void testInValidMoveInBoard() {
		board.movePlayer(0, 0, Type.HUMAN);
		Assert.assertFalse(board.movePlayer(0, 0, Type.COMPUTER));
	}

	@Test
	public void testInValidComputerMoveInBoard() {
		Assert.assertFalse(board.movePlayer(0, 0, Type.COMPUTER));
	}

	@Test
	public void testInValidHumanMoveInBoard() {
		board.movePlayer(0, 0, Type.HUMAN);
		Assert.assertFalse(board.movePlayer(0, 1, Type.HUMAN));

	}

	@Test
	public void testIsGameInProgress() {
		Assert.assertFalse(board.isGameOver());
	}

	@Test
	public void testIsGameOverRowLevel() {
		board.movePlayer(0, 0, Type.HUMAN);
		board.movePlayer(1, 0, Type.COMPUTER);
		board.movePlayer(0, 1, Type.HUMAN);
		board.movePlayer(1, 1, Type.COMPUTER);
		board.movePlayer(0, 2, Type.HUMAN);
		Assert.assertTrue(board.isGameOver());
	}

	@Test
	public void testIsGameOverColumnLevel() {
		board.movePlayer(0, 0, Type.HUMAN);
		board.movePlayer(2, 1, Type.COMPUTER);
		board.movePlayer(1, 0, Type.HUMAN);
		board.movePlayer(1, 1, Type.COMPUTER);
		board.movePlayer(2, 0, Type.HUMAN);
		Assert.assertTrue(board.isGameOver());
	}

	@Test
	public void testIsGameOverDiagonal1() {
		board.movePlayer(0, 0, Type.HUMAN);
		board.movePlayer(2, 1, Type.COMPUTER);
		board.movePlayer(1, 1, Type.HUMAN);
		board.movePlayer(2, 0, Type.COMPUTER);
		board.movePlayer(2, 2, Type.HUMAN);
		Assert.assertTrue(board.isGameOver());
	}

	@Test
	public void testIsGameOverDiagonal2() {
		board.movePlayer(0, 2, Type.HUMAN);
		board.movePlayer(2, 1, Type.COMPUTER);
		board.movePlayer(1, 1, Type.HUMAN);
		board.movePlayer(2, 2, Type.COMPUTER);
		board.movePlayer(2, 0, Type.HUMAN);
		Assert.assertTrue(board.isGameOver());
	}

	@Test
	public void testIsGameOverDraw() {
		board.movePlayer(0, 0, Type.HUMAN);
		board.movePlayer(0, 1, Type.COMPUTER);
		board.movePlayer(0, 2, Type.HUMAN);
		board.movePlayer(2, 0, Type.COMPUTER);
		board.movePlayer(2, 1, Type.HUMAN);
		board.movePlayer(2, 2, Type.COMPUTER);
		board.movePlayer(1, 1, Type.HUMAN);
		board.movePlayer(1, 0, Type.COMPUTER);
		board.movePlayer(1, 2, Type.HUMAN);
		Assert.assertNull(board.getWinner());
	}

}
