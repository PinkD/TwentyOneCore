package moe.pinkd.twentyone.core;

import moe.pinkd.twentyone.player.Player;

public class OperationExecutor {
    private GameManager gameManager;

    OperationExecutor(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public Integer[] getEnemyNumberCards(Player player) {
        Player enemy = gameManager.getEnemy(player);
        Integer[] enemyNumberCards = enemy.getNumberCards();
        enemyNumberCards[0] -= enemy.getHiddenCard();
        return enemyNumberCards;
    }

    public int getEnemyVisibleTotalPoint(Player player) {
        Player enemy = gameManager.getEnemy(player);
        return enemy.getTotalPoint() - enemy.getHiddenCard();
    }

    public boolean getEnemyDrawCard(Player player) {
        return gameManager.getEnemyDrawCard(player);
    }

    public void drawCard(Player player) {
        gameManager.getNextCard(player);
    }

}
