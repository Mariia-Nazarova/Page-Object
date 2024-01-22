package ru.netology.web.test;

import lombok.val;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

public class MoneyTransferTest {
    DashboardPage dashboardPage;
    CardInfo firstCardInfo;
    CardInfo secondCardInfo;
    int firstCardBalance;
    int secondCardBalance;

    @BeforeEach
    void setup () {
        val loginPage = open ("http://localhost:9999", LoginPage.class);
        val authInfo = getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = getFirstCardInfo();
        secondCardInfo = getSecondCardInfo();
        firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
    }

    @Test
    void shouldTransferFromFirstToSecond(){
        val amount = generateValidAmount(firstCardBalance);
        val expectedBalanceFirstCard = firstCardBalance - amount;
        val expectedBalanceSecondCard = secondCardBalance + amount;
        val transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount),firstCardInfo);
        val actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        val actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldGetErrorMessageIfAmountMoreBalance(){
        val amount = generateValidAmount(secondCardBalance);
        val transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        transferPage.makeTransfer(String.valueOf(amount),secondCardInfo);
        transferPage.findErrorMessage("Выполнена попытка перевода суммы, превышающей остаток на карте списания");
        val actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        val actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);
    }
}
