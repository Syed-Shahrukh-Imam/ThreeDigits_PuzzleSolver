public class Digit {

    private String first_digit;
    private String second_digit;
    private String third_digit;
    private String num;
    int last_changed;


   /*
    A digit is single number in the Node
    */

    Digit(String number) {

            this.num = number;
            this.first_digit = String.valueOf(number.charAt(0));
            this.second_digit = String.valueOf(number.charAt(1));
            this.third_digit = String.valueOf(number.charAt(2));
            this.last_changed = 3;

    }

    String getDigitString(){
        return this.num;
    }
    String getFirst_digit(){
        return this.first_digit;
    }
    String getSecond_digit(){
        return this.second_digit;
    }
    String getThird_digit(){
        return this.third_digit;
    }


    String increaseFirstDigit(){
        int value = Integer.parseInt(this.first_digit) + 1;
        return value +String.valueOf(this.second_digit)+ this.third_digit;
    }

    String decreaseFirstDigit(){
        int value = Integer.parseInt(this.first_digit) - 1;
        return value +String.valueOf(this.second_digit)+
                this.third_digit;
    }

    String increaseSecondDigit(){
        int value = Integer.parseInt(this.second_digit) + 1;
        return this.first_digit + value +
                this.third_digit;
    }

    String decreaseSecondDigit(){
        int value = Integer.parseInt(this.second_digit ) - 1;
        return this.first_digit+String.valueOf(value)+
                this.third_digit;
    }

    String increaseThirdDigit(){
        int value = Integer.parseInt(this.third_digit) + 1;
        return this.first_digit + this.second_digit +
                value;
    }
    String decreaseThirdDigit(){
        int value = Integer.parseInt(this.third_digit ) - 1;
        return this.first_digit + this.second_digit +
                value;
    }

    void setLastChanged(int s){
        this.last_changed = s;
    }
}
