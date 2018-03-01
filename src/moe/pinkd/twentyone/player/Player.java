package moe.pinkd.twentyone.player;

import moe.pinkd.twentyone.core.OperationExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Superclass of all players
 *
 * @author pinkd
 */

public abstract class Player {
    protected String name;
    private int hiddenCard;
    private List<Integer> numberCards = new ArrayList<>();//first element is sum
    private static final Integer[] NUMBER_CARD_TYPE = new Integer[0];

    public Player() {
        hiddenCard = 0;
        numberCards.add(0);
        this.name = getClass().getSimpleName().replace("Player", "");
    }

//    NOTE: You can create a constructor like the following to override default name
//    ***WARNING: DO NOT USE PINYIN!!!***
//    public Player(String name) {
//        super();
//        this.name = name;
//    }

    public void addCard(int card) {
        int newSum = numberCards.get(0) + card;
        numberCards.set(0, newSum);
        if (hiddenCard == 0) {
            hiddenCard = card;
        } else {
            numberCards.add(card);
        }
    }

    public void init() {
        numberCards.clear();
        numberCards.add(0);
        hiddenCard = 0;
    }

    public int getTotalPoint() {
        return numberCards.get(0);
    }

    public int getHiddenCard() {
        return hiddenCard;
    }

    /**
     * @return int array of card numbers, the first number is sum
     */
    public Integer[] getNumberCards() {
        return numberCards.toArray(NUMBER_CARD_TYPE);
    }

    @Override
    public String toString() {
        return "Player " + name;
    }

    public abstract void yourTurn(OperationExecutor operationExecutor);

}
