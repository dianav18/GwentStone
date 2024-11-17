package org.poo.cards.minion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.game.Game;
import lombok.NoArgsConstructor;

/**
 * The type Sentinel.
 */
@NoArgsConstructor
public final class Sentinel extends Minion {
    /**
     * Instantiates a new Sentinel.
     *
     * @param cardInput the card input
     */
    public Sentinel(final CardInput cardInput) {
        super(cardInput, false, Row.BACK);
    }

    protected Minion constructNew() {
        return new Sentinel();
    }

    @Override
    protected void internalUseAbility(final int xAttacked, final int yAttacked,
                                      final int xAttacker, final int yAttacker,
                                      final ObjectMapper objectMapper,
                                      final ArrayNode output, final Game game, final ObjectNode resultNode,
                                      final Minion attackedCard
    ) {
        // No ability
    }
}
