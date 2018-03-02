package moe.pinkd.twentyone.player.contestants;

import moe.pinkd.twentyone.core.OperationExecutor;
import moe.pinkd.twentyone.player.Player;

/**
 * Created by PinkD on 2018/3/2.
 */
public class NoDraw extends Player{
    @Override
    public boolean yourTurn(OperationExecutor operationExecutor) throws Exception {
        return false;
    }
}
