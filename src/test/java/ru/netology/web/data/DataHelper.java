package ru.netology.web.data;

import lombok.Value;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.VerificationPage;

import java.util.Random;

public class DataHelper {
    private DataHelper(){
    }

    public static VerificationCode getVerificationCode(){
        return new VerificationCode("12345");
    }

    public static AuthInfo getAuthInfo(){
        return new AuthInfo("vasya","qwerty123");
    }

    public static CardInfo getFirstCardInfo(){
        return new CardInfo ("5599 0000 0000 0001", "  ");
    }

    public static CardInfo getSecondCardInfo(){
        return new CardInfo ("5599 0000 0000 0002", "  ");
    }

    public static int generateValidAmount(int balance) {

        return new Random().nextInt(Math.abs(balance))+1;
    }

    public static int generateInvalidAmount(int balance) {

        return Math.abs(balance) + new Random().nextInt(10000);
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    @Value
    public static class CardInfo{
        String cardNumber;
        String testId;
    }

    @Value
    public static class AuthInfo{
        String login;
        String password;
    }
}
