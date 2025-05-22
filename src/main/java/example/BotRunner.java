package example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotRunner {
    public static void main(String[] args) {
        try {
            // Инициализируем TelegramBotsApi с использованием дефолтного сессионного менеджера
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Регистрируем нашего бота
            botsApi.registerBot(new MyTelegramBot());
            System.out.println("Бот успешно запущен");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}