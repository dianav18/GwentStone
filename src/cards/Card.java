package cards;

import fileio.CardInput;
import lombok.Getter;
import lombok.Setter;

public class Card {
    @Getter
    private CardInput card;
    @Setter
    private int x;
    @Setter
    private int y;
    @Setter
    private boolean frozen;
    @Setter
    private boolean hasAttacked;

    public Card(CardInput card) {
        this.card = new CardInput();

        this.x = -1;
        this.y = -1;

        this.frozen = false;
        this.hasAttacked = false;
    }
    private void setCard(CardInput card) {
        this.card = card;
    }
}
