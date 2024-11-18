package org.poo.cards.minion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.game.Game;
import lombok.NoArgsConstructor;

/**
 * The type Berserker.
 */
@NoArgsConstructor
public final class Berserker extends Minion {
    /**
     * Instantiates a new Berserker.
     *
     * @param cardInput the card input
     */
    public Berserker(final CardInput cardInput) {
        super(cardInput, false, Row.BACK);
    }

    /**
     * Factory method to create a new instance of Berserker.
     *
     * @return a new Berserker minion
     */
    protected Minion constructNew() {
        return new Berserker();
    }

    @Override
    protected void internalUseAbility(final int xAttacked, final int yAttacked,
                                      final int xAttacker, final int yAttacker,
                                      final ObjectMapper objectMapper,
                                       final ArrayNode output, final Game game,
                                      final ObjectNode resultNode,
                                      final Minion attackedCard
    ) {
    }

}
