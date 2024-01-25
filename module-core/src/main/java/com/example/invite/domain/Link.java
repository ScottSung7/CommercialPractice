package com.example.invite.domain;

public class Link {


    static char[] linkChar =  {65,65,65,65,65};
    static int currentNum = 0;

    public String createLink() {
        String linkString = combineChar();
        try {
            changeChar();
        }catch(Exception exception){
            System.err.println("Link Alphabet Range Error");
            return null;
        }
        return linkString;

    }
    private String combineChar(){
        String linkString = "";
        for (int i = 0; i < linkChar.length; i++) {
            linkString += Character.toString(linkChar[i]);
        }
        linkChar[currentNum]++;

        return linkString;
    }
    private void changeChar() throws Exception{
        for (int i = 0; i < linkChar.length; i++) {
            if(linkChar[currentNum] < 90){
                break;
            }else if(linkChar[currentNum] == 90){
                currentNum++;
                break;
            }else if(linkChar[currentNum] > 90 || currentNum > linkChar.length -1) {
                throw new Exception();
            }
        }
    }
}
