package com.cerberus.stockmarket.exception;

public class StockNotFoundException extends RuntimeException{

    private static final String messageId = "Акция с %d id не найдена";

    private static final String messageTicker = "Акция с тикером %s не найдена";

    public StockNotFoundException(Integer id) {
        super(messageId.formatted(id));
    }

    public StockNotFoundException(String ticker) {
        super(messageTicker.formatted(ticker));
    }
}
