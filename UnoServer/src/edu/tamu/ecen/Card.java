package edu.tamu.ecen;

public class Card {

    //format is  |COLOR,VALUE| for formatted Card, otherwise, pass arguments


    private CardColor color;   //color of the card, may be R=Red, B=Blue,  G=Green, Y=Yellow, or W=wildcard
    private CardValue value;   //value on the card, may be 0-9, dTWO=draw 2, REV=reverse,

    public Card (String color, String value) {
        this.color = CardColor.valueOf(color);
        this.value = CardValue.valueOf(value);
    }

    //TODO test this more robustly, removing pipes may mess up the indexing currently here
    public Card (String formattedCard) throws IllegalArgumentException {
        if (formattedCard.contains("|"))
            formattedCard = formattedCard.split("|")[1];


        String[] ss = formattedCard.split(",");



        if (ss.length!=2) {
            throw new IllegalArgumentException("Improperly formatted card - use the following Format: |Color,Value|");
        }

        String c = ss[0].substring(1);

        //if this is a number, need to append an "N"
        String v = ss[1];
        v = v.substring(0,v.length()-1);

        color = CardColor.valueOf(c);
        if (Character.isDigit(v.charAt(0))) {
            v = "N" + v;
        }
        value = CardValue.valueOf(v);

    }

    public CardColor getColor() {
        return color;
    }

    public CardValue getValue() {
        return value;
    }

    public String getColorStr() {
        return color.toString();
    }

    public String getValueStr() {
        if (value.toString().contains("N")) {
            String v = value.toString();
            return v.substring(1);
        }
        return value.toString();
    }

    public String formatCard() {
        String v = getValueStr();
        String c = getColorStr();

        return "|" + c + "," + v + "|";
    }

    public static boolean isValid(String format) {
        String c, v;
        String[] ss = format.split(",");

        c = ss[0].substring(1);

        //if this is a number, need to append an "N"
        v = ss[1];
        v = v.substring(0,v.length()-1);

        try {
            CardColor.valueOf(c);
            if (Character.isDigit(v.charAt(0))) {
                v = "N" + v;
            }
            CardValue.valueOf(v);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;

    }

    public static boolean isValid(String c, String v) {
        try {
            CardColor.valueOf(c);
            CardValue.valueOf(v);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object card) {
        if (card.getClass() != Card.class) {
            return false;
        }
        Card c = (Card) card;
        return (c.getColor() == this.color) && (c.getValue() == this.value);
    }

}
