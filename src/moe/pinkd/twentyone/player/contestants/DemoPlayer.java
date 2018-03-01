package moe.pinkd.twentyone.player.contestants;

import moe.pinkd.twentyone.core.OperationExecutor;
import moe.pinkd.twentyone.player.Player;

public class DemoPlayer extends Player {
    @Override
    public void yourTurn(OperationExecutor operationExecutor) {

        int enemy = operationExecutor.getEnemyVisibleTotalPoint(this);
        int sum = getTotalPoint();
        if (sum < 16 && enemy > 5) {
            operationExecutor.drawCard(this);
        }
    }
}
