package edu.tamu.ecen;



public enum CardValue {

    /*a preceding "N" means this is a number; I had to do this because enums must start with a letter.
        when actually using this, just try to strip off the N
    */
    N0,N1,N2,N3,N4,N5,N6,N7,N8,N9,R,S,D2,D4,W;

    /*
    ERROR(-1),
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    REVERSE(10),
    SKIP(11),
    DRAW_TWO(12),
    DRAW_FOUR(13),
    WILD(14);


    private final int value;

    private CardValue(int value) {

        this.value =value;
    }

    //in case a string is provided when creating the card value
    private CardValue(String value) {
        switch (value) {

            case "REVERSE":
            case "R":
                this.value = 10;
                break;

            case "SKIP":
            case "S":
                this.value = 11;
                break;

            case "DRAW_TWO":
            case "D2":
                this.value = 12;
                break;

            case "DRAW_FOUR":
            case "D4":
                this.value = 13;
                break;

            case "WILD":
            case "W":
                this.value = 14;
                break;

            default:
                this.value = -1;


        }
    }
*/
}
