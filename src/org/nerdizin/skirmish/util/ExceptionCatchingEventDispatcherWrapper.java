package org.nerdizin.skirmish.util;

import org.nerdizin.skirmish.ui.Dialogs;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;

public class ExceptionCatchingEventDispatcherWrapper implements EventDispatcher {

    private EventDispatcher eventDispatcher;

    public ExceptionCatchingEventDispatcherWrapper(final EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public Event dispatchEvent(final Event event, final EventDispatchChain tail) {
        try {
            return eventDispatcher.dispatchEvent(event, tail);
        } catch (final Exception e) {
            Dialogs.showError(e.getMessage());
            return event;
        }
    }
}
