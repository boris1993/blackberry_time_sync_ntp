package com.boris1993.timesyncntp;

import net.rim.device.api.ui.UiApplication;

public final class SyncTimeNtp extends UiApplication {
    public static void main(String[] args) {
        new SyncTimeNtp().enterEventDispatcher();
    }

    public SyncTimeNtp() {
        pushScreen(new MainAppScreen());
    }
}
