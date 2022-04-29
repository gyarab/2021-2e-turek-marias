/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.List;
import shared.Card;

/**
 *
 * @author jakub
 */
public class Player {

    private int points;
    private List<Card> cards;
    private String name;

    public Player(int points, String name) {
        this.points = points;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getPoints() {
        return points;
    }

    public void updatePoints(int points) {
        this.points += points;
    }

    public void updateCards(Card card) {
        cards.remove(card);
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return name + ":" + points;
    }

    public void setName(String name) {
        if (this.name == null) {
            this.name = name;
        }
    }

}
