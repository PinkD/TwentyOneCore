package moe.pinkd.twentyone.player.contestants;

import moe.pinkd.twentyone.core.OperationExecutor;
import moe.pinkd.twentyone.player.Player;

public class DemoPlayer extends Player {
    @Override
    public boolean yourTurn(OperationExecutor operationExecutor) {
        return getTotalPoint() < 15;
    }
}
