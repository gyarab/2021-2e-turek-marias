/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.io.Serializable;

/**
 *
 * @author jakub
 */
public class Card implements Serializable, Comparable<Card> {

    private String value;
    private String color;

    public Card(String value, String color) {
        this.value = value;
        this.color = color;
    }

    @Override
    public String toString() {
        return color + "." + value;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(Card o) {

        return Integer.compare(CardManager.VALUES_OF_CARDS.indexOf(this.value), CardManager.VALUES_OF_CARDS.indexOf(o.value));

    }

    public int compareTo(String value) {

        if (CardManager.VALUES_OF_CARDS.indexOf(this.value) > CardManager.VALUES_OF_CARDS.indexOf(value)) {
            return 1;
        } else {
            return -1;

        }
    }

}
