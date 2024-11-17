package org.poo.game;

import org.poo.cards.hero.Hero;

/**
 * The type Get hero.
 */
public class GetHero {
    private final Player player1;
    private final Player player2;

    private Hero hero1;
    private Hero hero2;

    /**
     * Instantiates a new Get hero.
     *
     * @param player1 the player 1
     * @param player2 the player 2
     * @param hero1   the hero 1
     * @param hero2   the hero 2
     */
    public GetHero(final Player player1, final Player player2, final Hero hero1, final Hero hero2) {
        this.player1 = player1;
        this.player2 = player2;
    }
}
