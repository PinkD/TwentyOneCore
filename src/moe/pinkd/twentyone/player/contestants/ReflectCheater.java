package moe.pinkd.twentyone.player.contestants;

import moe.pinkd.twentyone.core.GameManager;
import moe.pinkd.twentyone.core.OperationExecutor;
import moe.pinkd.twentyone.player.Player;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class ReflectCheater extends Player {
    @Override
    @SuppressWarnings("unchecked")
    public boolean yourTurn(OperationExecutor operationExecutor) throws NoSuchFieldException, IllegalAccessException {
        Field gameManagerField = operationExecutor.getClass().getDeclaredField("gameManager");
        gameManagerField.setAccessible(true);
        GameManager gameManager = (GameManager) gameManagerField.get(operationExecutor);
        int total = getTotalPoint();
        if (total != 21) {
            Field deckField = gameManager.getClass().getDeclaredField("deck");
            deckField.setAccessible(true);
            ArrayDeque<Integer> deck = (ArrayDeque<Integer>) deckField.get(gameManager);
            for (int i = (21 - total); i > 0; i--) {
                if (deck.contains(i)) {
                    ArrayList<Integer> tmpDeck = new ArrayList<>();
                    tmpDeck.addAll(deck);
                    addCard(tmpDeck.remove(tmpDeck.indexOf(i)), gameManager);
                    deck.clear();
                    deck.addAll(tmpDeck);
                    return false;
                }
            }
        }
        return false;
    }
}
