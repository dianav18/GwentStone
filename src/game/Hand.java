package game;

import cards.minion.Minion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Hand.
 */
@Getter
@Setter
public class Hand {

    private List<Minion> minions;

    /**
     * Instantiates a new Hand.
     */
    public Hand() {
        this.minions = new ArrayList<>();
    }
}
