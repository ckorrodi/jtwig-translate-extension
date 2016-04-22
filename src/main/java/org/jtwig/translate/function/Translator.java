package org.jtwig.translate.function;

import com.google.common.base.Supplier;
import org.jtwig.environment.Environment;
import org.jtwig.i18n.decorate.CompositeMessageDecorator;
import org.jtwig.i18n.decorate.MessageDecorator;
import org.jtwig.translate.configuration.TranslateConfiguration;

import java.util.Collection;
import java.util.Locale;

public class Translator {
    public String translate (Environment environment, String message, Locale locale, Collection<MessageDecorator> messageDecorators) {
        String key = message.trim();
        MessageDecorator messageDecorator = new CompositeMessageDecorator(messageDecorators);
        return TranslateConfiguration.messageResolver(environment)
                .resolve(locale, key, messageDecorator)
                .or(defaultMessage(key, messageDecorator));
    }


    private Supplier<String> defaultMessage(final String key, final MessageDecorator messageDecorator) {
        return new Supplier<String>() {
            @Override
            public String get() {
                return messageDecorator.decorate(key);
            }
        };
    }
}
