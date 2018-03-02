package moe.pinkd.twentyone;

import moe.pinkd.twentyone.core.Game;
import moe.pinkd.twentyone.player.Player;
import moe.pinkd.twentyone.player.contestants.ReflectCheater;
import moe.pinkd.twentyone.player.contestants.DemoPlayer;
import moe.pinkd.twentyone.player.contestants.PinkD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    private static List<Player> players = new ArrayList<>();
    private static HashMap<Player, Integer> score = new HashMap<>();

    public static void main(String[] args) {
        players.add(new DemoPlayer());
        players.add(new PinkD());
        players.add(new ReflectCheater());
        for (Player player : players) {
            score.put(player, 0);
        }
        for (int i = 0; i < players.size() - 1; i++) {
            for (int j = 1; j < players.size(); j++) {
                for (int k = 0; k < 100; k++) {
                    Game game = new Game(players.get(i), players.get(j));
                    Player winner = game.play();
                    if (winner != null) {
                        score.put(winner, score.get(winner) + 1);
                    }
                    players.get(i).init();
                    players.get(j).init();
                }
            }
        }
        score.forEach((player, count) -> System.out.println(player + " wins " + count + " time(s)"));
    }
}
