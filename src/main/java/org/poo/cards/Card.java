package org.poo.cards;

import org.poo.fileio.CardInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Card.
 */
@NoArgsConstructor
public abstract class Card {
    @Getter
    private CardInput card;
    @Setter
    private int x;
    @Setter
    private int y;

    /**
     * Instantiates a new Card.
     *
     * @param card the card
     */
    public Card(final CardInput card) {
        this.card = new CardInput();

        this.x = -1;
        this.y = -1;
    }
}
