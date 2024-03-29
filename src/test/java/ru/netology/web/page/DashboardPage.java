package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import lombok.var;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards= $$ (".list__item div");

    public DashboardPage (){
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo){
        val text = cards.findBy(text(cardInfo.getCardNumber().substring(15))).getText();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo){
        cards.findBy(attribute("data-test-id",cardInfo.getTestId())).$("button"). click();
        return new TransferPage();
    }
    private int extractBalance(String text){
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(),finish);
        return Integer.parseInt(value);
    }

}
