package com.boris1993.timesyncntp;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.component.ButtonField;

public class WideButton extends ButtonField {
    public WideButton(String label, long style) {
        super(label, style);
    }

    public int getPreferredWidth() {
        return Display.getWidth();
    }
}
