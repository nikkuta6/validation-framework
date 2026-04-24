package by.pranovich.validationframework.factory;

import by.pranovich.validationframework.handler.EmailHandler;
import by.pranovich.validationframework.handler.MaxHandler;
import by.pranovich.validationframework.handler.MinHandler;
import by.pranovich.validationframework.handler.NegativeHandler;
import by.pranovich.validationframework.handler.NotNullHandler;
import by.pranovich.validationframework.handler.PatternHandler;
import by.pranovich.validationframework.handler.PositiveHandler;
import by.pranovich.validationframework.handler.SizeHandler;
import by.pranovich.validationframework.handler.ValidationHandler;

public class ValidationHandlerChainFactory {

  private ValidationHandlerChainFactory() {
  }

  public static ValidationHandler createDefaultChain() {
    ValidationHandler notNullHandler = new NotNullHandler();
    ValidationHandler patternHandler = new PatternHandler();
    ValidationHandler emailHandler = new EmailHandler();
    ValidationHandler sizeHandler = new SizeHandler();
    ValidationHandler maxHandler = new MaxHandler();
    ValidationHandler minHandler = new MinHandler();
    ValidationHandler positivHandler = new PositiveHandler();
    ValidationHandler negativHandler = new NegativeHandler();

    notNullHandler
        .linkWith(patternHandler)
        .linkWith(emailHandler)
        .linkWith(sizeHandler)
        .linkWith(maxHandler)
        .linkWith(minHandler)
        .linkWith(positivHandler)
        .linkWith(negativHandler);

    return notNullHandler;
  }

}
