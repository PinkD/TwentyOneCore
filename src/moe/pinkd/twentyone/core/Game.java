package moe.pinkd.twentyone.core;

import moe.pinkd.twentyone.Config;
import moe.pinkd.twentyone.player.Player;

public class Game {

    private GameManager gameManager;
    private OperationExecutor operationExecutor;

    public Game(Player player1, Player player2) {
        this(new Player[]{player1, player2});
    }

    public Game(Player[] players) {
        gameManager = new GameManager(players);
        operationExecutor = new OperationExecutor(gameManager);
    }

    public Player play() {
        gameManager.init();
        while (!gameManager.isGameOver()) {
            gameManager.round(operationExecutor);
        }
        Player winner = gameManager.getWinner();
        if (winner == null) {
            if (Config.DEBUG) {
                gameManager.notifyMessage("It's a draw");
            }
            return null;
        } else {
            if (Config.DEBUG) {
                gameManager.notifyMessage("Winner is " + winner);
                gameManager.notifyMessage("-------------------------");
            }
            return winner;
        }
    }

}
