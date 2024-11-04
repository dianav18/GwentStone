package game;

import cards.minion.Minion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Hand {

    private List<Minion> minions;

    public Hand() {
        this.minions= new ArrayList<>();
    }

}
