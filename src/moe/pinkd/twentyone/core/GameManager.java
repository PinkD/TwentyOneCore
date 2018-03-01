package moe.pinkd.twentyone.core;

import com.sun.istack.internal.Nullable;
import moe.pinkd.twentyone.card.NumberCardPool;
import moe.pinkd.twentyone.player.Player;

import java.util.*;

public class GameManager {
    private Player[] players;
    private ArrayDeque<Integer> deck = new ArrayDeque<>();//a deck of cards
    private boolean[] draws = new boolean[2];
    private boolean gameOver;
    public static final int MAX = 21;

    public GameManager(Player[] players) {
        if (players.length != 2) {
            throw new IllegalArgumentException("Player count should be 2");
        }
        List<Integer> tmpDeck = new ArrayList<>(Arrays.asList(NumberCardPool.NUMBER_CARDS));
        this.players = players;
        Collections.shuffle(tmpDeck);
        deck.addAll(tmpDeck);
        gameOver = false;
        draws[0] = draws[1] = true;
    }

    public void getNextCard(Player player) {
        Integer card = deck.poll();
        if (card != null) {
            player.addCard(card);
        } else {
            notifyMessage("No card anymore game over");
            gameOver = true;
        }
        int i = getPlayerIndex(player);
        draws[i] = true;
    }


    public void notifyMessage(String message) {
        System.out.println(message);//TODO: replace with game output
    }

    public Player getEnemy(Player player) {
        return players[0] == player ? players[1] : players[0];
    }
    public boolean getEnemyDrawCard(Player player) {
        return players[0] == player ? draws[1] : draws[0];
    }

    private int getPlayerIndex(Player player) {
        return players[0] == player ? 1 : 0;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void round(OperationExecutor operationExecutor) {
        if (draws[0] || draws[1]) {
            draws[0] = draws[1] = false;
        } else {
            gameOver = true;
            return;
        }
        for (Player player : players) {
            player.yourTurn(operationExecutor);
        }
    }

    public void init() {
        for (Player player : players) {
            player.addCard(deck.poll());
            player.addCard(deck.poll());
        }
    }

    @Nullable
    public Player getWinner() {
        int[] sums = new int[2];
        for (int i = 0; i < players.length; i++) {
            sums[i] = players[i].getTotalPoint();
            System.out.println(players[i].toString() + Arrays.toString(players[i].getNumberCards()));
        }
        if (sums[0] == sums[1] || (sums[0] > MAX && sums[1] > MAX)) {//draw
            return null;
        } else {
            if (sums[0] > MAX) {
                return players[1];
            }
            if (sums[1] > MAX) {
                return players[0];
            }
            if (sums[0] > sums[1]) {
                return players[0];
            } else {
                return players[1];
            }
        }
    }
}
