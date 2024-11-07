package game;

import cards.minion.Minion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Hand {

    private List<Minion> minions;

    public Hand() {
        this.minions= new ArrayList<>();
    }
}
