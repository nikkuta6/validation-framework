package by.pranovich.validationframework.factory;

import by.pranovich.validationframework.handler.EmailHandler;
import by.pranovich.validationframework.handler.MaxHandler;
import by.pranovich.validationframework.handler.MinHandler;
import by.pranovich.validationframework.handler.NegativeHandler;
import by.pranovich.validationframework.handler.NotBlankHandler;
import by.pranovich.validationframework.handler.NotEmptyHandler;
import by.pranovich.validationframework.handler.NotNullHandler;
import by.pranovich.validationframework.handler.PatternHandler;
import by.pranovich.validationframework.handler.PositiveHandler;
import by.pranovich.validationframework.handler.RangeHandler;
import by.pranovich.validationframework.handler.SizeHandler;
import by.pranovich.validationframework.handler.ValidationHandler;

public class ValidationHandlerChainFactory {

    private ValidationHandlerChainFactory() {
    }

    public static ValidationHandler createDefaultChain() {
        ValidationHandler notNullHandler = new NotNullHandler();
        ValidationHandler notBlankHandler = new NotBlankHandler();
        ValidationHandler notEmptyHandler = new NotEmptyHandler();
        ValidationHandler patternHandler = new PatternHandler();
        ValidationHandler emailHandler = new EmailHandler();
        ValidationHandler sizeHandler = new SizeHandler();
        ValidationHandler rangeHandler = new RangeHandler();
        ValidationHandler maxHandler = new MaxHandler();
        ValidationHandler minHandler = new MinHandler();
        ValidationHandler positiveHandler = new PositiveHandler();
        ValidationHandler negativeHandler = new NegativeHandler();

        notNullHandler
                .linkWith(notBlankHandler)
                .linkWith(notEmptyHandler)
                .linkWith(patternHandler)
                .linkWith(emailHandler)
                .linkWith(sizeHandler)
                .linkWith(rangeHandler)
                .linkWith(maxHandler)
                .linkWith(minHandler)
                .linkWith(positiveHandler)
                .linkWith(negativeHandler);

        return notNullHandler;
    }

}
