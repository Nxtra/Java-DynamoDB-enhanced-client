package io.theclouddeveloper.memorycards.di;

import dagger.Component;
import io.theclouddeveloper.memorycards.MemoryCardsHandler;

import javax.inject.Singleton;

@Singleton
@Component(modules = { DIModule.class })
public interface DIComponent {

    // allow to inject into our Main class
    // method name not important
    void inject(MemoryCardsHandler MemoryCardsHandler);
}
