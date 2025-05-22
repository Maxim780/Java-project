package example;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.oracle.svm.core.annotate.Inject;

@ApplicationScoped
public class MyTelegramBot extends TelegramLongPollingBot {
	private static final Logger LOG = Logger.getLogger(MyTelegramBot.class);

	// Токен и имя бота можно вынести в конфигурацию,
	// а здесь заданы жестко для примера
	@Inject

	@ConfigProperty(name = "telegram.bot.token")

	private String BOT_TOKEN = "5059855505:AAE--t45kssyjZ1FkhPjiaD7hxXrtryBD4M";
	private final String BOT_USERNAME = "@Bytenans_bot";

	@PostConstruct
	void init() {
	    try {
	        // Регистрируем бота при старте приложения
	        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
	        botsApi.registerBot(this);
	        LOG.info("Telegram бот успешно зарегистрирован.");
	        System.out.println("Бот успешно запущен");
	    } catch (TelegramApiException e) {
	        LOG.error("Ошибка регистрации Telegram бота", e);
	        System.out.println("Ошибка регистрации Telegram бота");
	    }
	}

	@Override
	public void onUpdateReceived(Update update) {
	    // Обработка входящих сообщений
	    if (update.hasMessage() && update.getMessage().hasText()) {
	        String messageText = update.getMessage().getText();
	        String chatId = update.getMessage().getChatId().toString();

	        String responseText = "Вы сказали: " + messageText;

	        SendMessage message = new SendMessage();
	        message.setChatId(chatId);
	        message.setText(responseText);

	        try {
	            execute(message);
	        } catch (TelegramApiException e) {
	            LOG.error("Ошибка отправки сообщения", e);
	        }
	    }
	}

	@Override
	public String getBotUsername() {
	    return BOT_USERNAME;
	}

	@Override
	public String getBotToken() {
	    return BOT_TOKEN;
	}

	}