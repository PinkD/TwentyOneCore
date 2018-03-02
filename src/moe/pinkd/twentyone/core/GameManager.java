package moe.pinkd.twentyone.core;

import com.sun.istack.internal.Nullable;
import moe.pinkd.twentyone.Config;
import moe.pinkd.twentyone.card.NumberCardPool;
import moe.pinkd.twentyone.player.Player;

import java.util.*;

public class GameManager {
    private Player[] players;
    private ArrayDeque<Integer> deck = new ArrayDeque<>();//a deck of cards
    private HashMap<Player, Boolean> draws = new HashMap<>(2);
    private boolean gameOver;
    public static final int MAX = 21;

    GameManager(Player[] players) {
        if (players.length != 2) {
            throw new IllegalArgumentException("Player count should be 2");
        }
        List<Integer> tmpDeck = new ArrayList<>(Arrays.asList(NumberCardPool.NUMBER_CARDS));
        this.players = players;
        Collections.shuffle(tmpDeck);
        deck.addAll(tmpDeck);
        gameOver = false;
        draws.put(players[0], true);
        draws.put(players[1], true);
    }

    private void getNextCard(Player player) {
        Integer card = deck.poll();
        if (card != null) {
            player.addCard(card, this);
        } else {
            notifyMessage("No card anymore game over");
            gameOver = true;
        }
        draws.put(player, true);
    }


    void notifyMessage(String message) {
        System.out.println(message);
    }

    Player getEnemy(Player player) {
        return players[0] == player ? players[1] : players[0];
    }

    boolean getEnemyDrawCard(Player player) {
        return draws.get(getEnemy(player));
    }

    private int getPlayerIndex(Player player) {
        return players[0] == player ? 1 : 0;
    }

    boolean isGameOver() {
        return gameOver;
    }

    void round(OperationExecutor operationExecutor) {
        if (draws.get(players[0]) || draws.get(players[1])) {
            draws.put(players[0], false);
            draws.put(players[1], false);
        } else {
            gameOver = true;
            return;
        }
        for (Player player : players) {
            try {
                boolean drawn = player.yourTurn(operationExecutor);
                draws.put(player, drawn);
                if (drawn) {
                    getNextCard(player);
                }
            } catch (Exception e) {
                gameOver = true;
                for (Integer card : deck) {//花Q
                    player.addCard(card, this);
                }
            }
        }
    }

    void init() {
        for (Player player : players) {
            player.addCard(deck.poll(), this);
            player.addCard(deck.poll(), this);
        }
    }

    @Nullable
    Player getWinner() {
        Player cheater = antiCheat();
        if (cheater != null) {
            return players[1] == cheater ? players[0] : players[1];
        }
        int[] sums = new int[2];
        for (int i = 0; i < players.length; i++) {
            sums[i] = MAX - players[i].getTotalPoint();
            if (Config.DEBUG) {
                System.out.println(players[i].toString() + ": " + Arrays.toString(players[i].getNumberCards()));
            }
        }
        if (sums[0] == sums[1]) {//draw
            return null;
        }
        if (sums[0] < 0 && sums[1] > 0) {//player 0 bust
            return players[1];
        }
        if (sums[1] < 0 && sums[0] > 0) {//player 1 bust
            return players[0];
        }
        //compare the abs to find out who is closer to 21
        return Math.abs(sums[0]) < Math.abs(sums[1]) ? players[0] : players[1];
    }

    @Nullable
    private Player antiCheat() {
        for (Player player : players) {
            Integer[] numberCards = player.getNumberCards();
            for (int i = 1; i < numberCards.length; i++) {
                if (deck.contains(numberCards[i])) {
                    return player;
                }
            }
        }
        return null;
    }
}
