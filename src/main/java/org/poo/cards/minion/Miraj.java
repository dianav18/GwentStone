package org.poo.cards.minion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.game.Game;
import lombok.NoArgsConstructor;

/**
 * The type Miraj.
 */
@NoArgsConstructor
public final class Miraj extends Minion {
    /**
     * Instantiates a new Miraj.
     *
     * @param cardInput the card input
     */
    public Miraj(final CardInput cardInput) {
        super(cardInput, false, Row.FRONT);
    }


    protected Minion constructNew() {
        return new Miraj();
    }


    @Override
    protected void internalUseAbility(final int xAttacked, final int yAttacked,
                                      final int xAttacker, final int yAttacker,
                                      final ObjectMapper objectMapper,
                                      final ArrayNode output, final Game game,
                                      final ObjectNode resultNode,
                                      final Minion attackedCard
    ) {
        final int originalHealth = this.getHealth();
        this.setHealth(attackedCard.getHealth());
        attackedCard.setHealth(originalHealth);
    }
}
