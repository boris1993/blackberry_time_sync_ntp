package com.boris1993.timesyncntp;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.RichTextField;

public final class SyncTimeNtp extends UiApplication {
    public static void main(String[] args) {
        new SyncTimeNtp().enterEventDispatcher();
    }

    public SyncTimeNtp() {
        pushScreen(new MainAppScreen());
    }
}
