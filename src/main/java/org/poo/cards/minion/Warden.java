package org.poo.cards.minion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.game.Game;
import lombok.NoArgsConstructor;

/**
 * The type Warden.
 */
@NoArgsConstructor
public final class Warden extends Minion {
    /**
     * Instantiates a new Warden.
     *
     * @param cardInput the card input
     */
    public Warden(final CardInput cardInput) {
        super(cardInput, true, Row.FRONT);
    }

    protected Minion constructNew() {
        return new Warden();
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
