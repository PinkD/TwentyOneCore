package moe.pinkd.twentyone.player.contestants;

import moe.pinkd.twentyone.core.OperationExecutor;
import moe.pinkd.twentyone.player.Player;

public class PinkD extends Player {
    @Override
    public void yourTurn(OperationExecutor operationExecutor) {
        int enemy = operationExecutor.getEnemyVisibleTotalPoint(this);
        boolean enemyDraw = operationExecutor.getEnemyDrawCard(this);
        int sum = getTotalPoint();
        if (enemy > 20) {//enemy lose
            return;
        }
        if (sum < 15) {
            operationExecutor.drawCard(this);
        }
    }
}
