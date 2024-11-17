package org.poo.cards.minion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.game.Game;
import lombok.NoArgsConstructor;

/**
 * The type The ripper.
 */
@NoArgsConstructor
public final class TheRipper extends Minion {
    /**
     * Instantiates a new The ripper.
     *
     * @param cardInput the card input
     */
    public TheRipper(final CardInput cardInput) {
        super(cardInput, false, Row.FRONT);
    }

    protected Minion constructNew() {
        return new TheRipper();
    }

    @Override
    protected void internalUseAbility(final int xAttacked, final int yAttacked,
                                      final int xAttacker, final int yAttacker,
                                      final ObjectMapper objectMapper,
                                      final ArrayNode output, final Game game,
                                      final ObjectNode resultNode,
                                      final Minion attackedCard
    ) {

        int newAttackDamage = attackedCard.getAttackDamage() - 2;

        if (newAttackDamage < 0) {
            newAttackDamage = 0;
        }

        attackedCard.setAttackDamage(newAttackDamage);
    }
}
