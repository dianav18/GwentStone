package org.poo.cards.minion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.game.Game;
import lombok.NoArgsConstructor;

/**
 * The type The cursed one.
 */
@NoArgsConstructor
public final class TheCursedOne extends Minion {
    /**
     * Instantiates a new The cursed one.
     *
     * @param cardInput the card input
     */
    public TheCursedOne(final CardInput cardInput) {
        super(cardInput, false, Row.BACK);
        this.setAttackDamage(0);
    }


    protected Minion constructNew() {
        return new TheCursedOne();
    }

    @Override
    protected void internalUseAbility(final int xAttacked, final int yAttacked,
                                      final int xAttacker, final int yAttacker,
                                      final ObjectMapper objectMapper,
                                      final ArrayNode output, final Game game, final ObjectNode resultNode,
                                      final Minion attackedCard
    ){
        if (attackedCard.getAttackDamage() == 0) {
            attackedCard.setHealth(0);
        } else {
            final int originalHealth = attackedCard.getHealth();
            attackedCard.setHealth(attackedCard.getAttackDamage());
            attackedCard.setAttackDamage(originalHealth);
        }
    }
}
