package com.boris1993.timesyncntp;

import com.boris1993.timesyncntp.utils.NtpPacketUtils;
import net.rim.device.api.i18n.ResourceBundle;
import net.rim.device.api.io.transport.ConnectionDescriptor;
import net.rim.device.api.io.transport.ConnectionFactory;
import net.rim.device.api.system.Device;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.container.MainScreen;

import javax.microedition.io.Datagram;
import javax.microedition.io.UDPDatagramConnection;
import java.io.IOException;
import java.util.Date;

public final class MainAppScreen
        extends MainScreen
        implements LocalizationResource, FieldChangeListener {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_ID, BUNDLE_NAME);

    private final ObjectChoiceField ntpServerChoiceField;

    public MainAppScreen() {
        final LabelField title = new LabelField(RESOURCE_BUNDLE.getString(APPLICATION_TITLE));
        setTitle(title);

        final String labelChoiceNtpServer = RESOURCE_BUNDLE.getString(LABEL_CHOOSE_NTP_SERVER);
        final String[] ntpServers = RESOURCE_BUNDLE.getStringArray(NTP_SERVERS);
        ntpServerChoiceField = new ObjectChoiceField(labelChoiceNtpServer, ntpServers);
        add(ntpServerChoiceField);

        final WideButton buttonSyncTime = new WideButton(
                RESOURCE_BUNDLE.getString(BUTTON_SYNC_TIME),
                Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
        buttonSyncTime.setChangeListener(this);
        add(buttonSyncTime);
    }

    protected boolean onSavePrompt() {
        return true;
    }

    public void fieldChanged(Field field, int context) {
        final String url = "datagram://"
                .concat(ntpServerChoiceField.getChoice(ntpServerChoiceField.getSelectedIndex()).toString())
                .concat(":123");

        final ConnectionFactory connectionFactory = new ConnectionFactory();
        final ConnectionDescriptor connectionDescriptor = connectionFactory.getConnection(url);

        // Both request and response data are 448 bits or 56 bytes long
        byte[] request = NtpPacketUtils.buildPacket();
        byte[] response = new byte[56];

        UDPDatagramConnection connection = (UDPDatagramConnection) connectionDescriptor.getConnection();
        try {
            Datagram outDatagram = connection.newDatagram(request, request.length);
            connection.send(outDatagram);

            Datagram inDatagram = connection.newDatagram(response, response.length);
            connection.receive(inDatagram);
            response = inDatagram.getData();

            // Taking the "transmit timestamp" from the response data
            // as the current timestamp
            final byte[] transmitTimestampBytes = new byte[8];
            System.arraycopy(response, 40, transmitTimestampBytes, 0, 8);

            long currentTimestamp = NtpPacketUtils.getNtpTimestampMilliseconds(transmitTimestampBytes);

            // THIS METHOD CALL IS ONLY ACCESSIBLE BY SIGNED APPLICATIONS
            // https://www.blackberry.com/developers/docs/7.0.0api/net/rim/device/api/system/Device.html#setDateTime(long)
            Device.setDateTime(currentTimestamp);

            final String readableDate = new Date(currentTimestamp).toString();
            Dialog.inform(
                    RESOURCE_BUNDLE.getString(INFORMATION_TIME_UPDATED_TO)
                            .concat("\n")
                            .concat(readableDate));
        } catch (IOException e) {
            Dialog.alert(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                Dialog.alert(e.getMessage());
            }
        }
    }
}
