/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import shared.Card;
import shared.CardManager;

/**
 *
 * @author jakub
 */
public class GameManager {

    private final LinkedList<Card> CARDSET;
    private String TRUMPHCOLOR;

    public GameManager() {                              // Konstruktor registru karet - vytvoří registr všech karet ve hře 

        CARDSET = creatCardSet();

    }

    public void setTRUMPHCOLOR(String TRUMPHCOLOR) {
        this.TRUMPHCOLOR = TRUMPHCOLOR;
    }

    private LinkedList<Card> creatCardSet() {
        LinkedList<Card> cards = new LinkedList<>();
        for (String COLORS_OF_CARDS1 : CardManager.COLORS_OF_CARDS) {
            for (String VALUES_OF_CARDS1 : CardManager.VALUES_OF_CARDS) {
                cards.add(new Card(VALUES_OF_CARDS1, COLORS_OF_CARDS1));
            }
        }
        shuffleCard(cards);
        return cards;
    }

    private void shuffleCard(LinkedList<Card> Cards) { // Náhodně promíchá výchozí seznam karet.
        Random random = new Random();
        Card c;
        int j;
        for (int i = Cards.size(); i > 0; i--) {
            j = random.nextInt(i);
            c = Cards.get(j);
            Cards.set(j, Cards.get(i - 1));
            Cards.set(i - 1, c);

        }

    }

    public LinkedList<Card> getPlayersCardsSet(int playerindex) { // Vrátí karyty rozdané danému hráči.
        LinkedList<Card> cards = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            if (playerindex + i * 4 < CARDSET.size()) {
                cards.add(CARDSET.get(playerindex + i * 4));
            }

        }
        return cards;
    }

    // Vrátí index vítězné karty, která je zároveň indexem dat o vítězném hráči
    public int getWinerOfRound(List<Card> playedCards, int indexOfFirst) {

        if (containsTrump(playedCards)) {

            return indexOfHigestValue(cardsFilter(playedCards, TRUMPHCOLOR));

        }

        return indexOfHigestValue(cardsFilter(playedCards, playedCards.get(indexOfFirst).getColor()));

    }

    private boolean containsTrump(List<Card> playedCards) { // Vrátí zda byly v daném kole zahrány trumfové karty
        boolean b;
        for (int i = 0; i < playedCards.size(); i++) {
            b = playedCards.get(i).getColor().equals(TRUMPHCOLOR);
            if (b) {
                return b;
            }

        }
        return false;
    }

    private List<Card> cardsFilter(List<Card> cards, String colorToFilter) { // Vrátí kopii vstupního pole, ze které jsou odstraněny karty jiné barvy než colorToFilter.

        for (int i = 0; i < cards.size(); i++) {
            if (!cards.get(i).getColor().equals(colorToFilter)) {
                cards.set(i, new Card("O", "O"));
            }

        }
        return cards;
    }

    public int getRoundPoints(List<Card> cards) { // Vrátí počet bodů získaných vítězným hráčem za dané kolo.
        int points = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue().equals("1") || cards.get(i).getValue().equals("A")) {
                points = points + 10;

            }

        }
        return points;
    }

    private int indexOfHigestValue(List<Card> cards) { // vrátí index karty s nejvyší hodnotou v poli.

        Card c = Collections.max(cards);
        return cards.indexOf(c);
    }

}
