package cards.minion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import game.Game;
import lombok.NoArgsConstructor;

/**
 * The type Disciple.
 */
@NoArgsConstructor
    public final class Disciple extends Minion {
    /**
     * Instantiates a new Disciple.
     *
     * @param cardInput the card input
     */
    public Disciple(final CardInput cardInput) {
            super(cardInput, false, Row.BACK);
            this.setAttackDamage(0);
        }

    /**
     * Increases the allyMinion's health by 2
     *
     * @param allyMinion the minion on which the ability will be applied
     */
    public void godsPlan(final Minion allyMinion) {
        allyMinion.setHealth(allyMinion.getHealth() + 2);
    }

    protected Minion constructNew() {
        return new Disciple();
    }

    @Override
    public void useAbility(final int xAttacked, final int yAttacked,
                           final int xAttacker, final int yAttacker,
                           final ObjectMapper objectMapper,
                           final ArrayNode output, final Game game, final ObjectNode resultNode){
        final Minion attackerCard = game.getBoard()[xAttacker][yAttacker];
        final Minion attackedCard = game.getBoard()[xAttacked][yAttacked];

        boolean isAttackedCardAlly = false;

        if (game.getPlayerTurn() == game.getPlayer1() && (xAttacked == 2 || xAttacked == 3)) {
            isAttackedCardAlly = true;
        } else if (game.getPlayerTurn() == game.getPlayer2() && (xAttacked == 0 || xAttacked == 1)) {
            isAttackedCardAlly = true;
        }

        if (!isAttackedCardAlly) {
            cardUsesAbilityOutput(xAttacked, yAttacked, xAttacker,
                    yAttacker, objectMapper, resultNode);
            resultNode.put("error", "Attacked card does"
                    + " not belong to the current player.");
            output.add(resultNode);
            return;
        }
        ((Disciple) attackerCard).godsPlan(attackedCard);
        attackerCard.setHasAttacked(true);
    }

    @Override
    protected void internalUseAbility(final int xAttacked, final int yAttacked,
                                      final int xAttacker, final int yAttacker,
                                      final ObjectMapper objectMapper,
                                      final ArrayNode output, final Game game, final ObjectNode resultNode,
                                      final Minion attackerCard){
        // Not needed
    }
}
