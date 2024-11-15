package cards;

import fileio.CardInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
public abstract class Card {
    @Getter
    private CardInput card;
    @Setter
    private int x;
    @Setter
    private int y;

    public Card(CardInput card) {
        this.card = new CardInput();

        this.x = -1;
        this.y = -1;
    }
    private void setCard(CardInput card) {
        this.card = card;
    }
}