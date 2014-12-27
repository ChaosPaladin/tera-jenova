package com.angelis.tera.packet.presentation.controllers;

import java.io.IOException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javolution.util.FastList;

import com.angelis.tera.common.presentation.network.packet.AbstractClientPacket;
import com.angelis.tera.common.presentation.network.packet.AbstractServerPacket;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.ClientPacketHandler;
import com.angelis.tera.game.presentation.network.packet.ServerPacketHandler;
import com.angelis.tera.packet.process.network.packet.Packet;

public class PacketController {

    @FXML
    private TextField filterTextField;

    @FXML
    private ListView<Packet> listView;

    @FXML
    private TextArea packetContentTextArea;
    
    private final List<Packet> packets = new FastList<>();

    public PacketController() {
    }

    public void setFilterTextField(final TextField filterTextField) {
        this.filterTextField = filterTextField;
    }

    public void setListView(final ListView<Packet> listView) {
        this.listView = listView;
    }

    public ListView<Packet> getListView() {
        return listView;
    }

    public TextField getFilterTextField() {
        return filterTextField;
    }

    public void loadUi() {
        listView.setCellFactory(new Callback<ListView<Packet>, ListCell<Packet>>() {
            @Override
            public ListCell<Packet> call(final ListView<Packet> p) {
                final ListCell<Packet> cell = new ListCell<Packet>() {
                    @Override
                    protected void updateItem(final Packet packet, final boolean empty) {
                        super.updateItem(packet, empty);
                        if (packet != null) {

                            final HBox hbox = new HBox();
                            String packetName = null;
                            String imageFile = null;
                            switch (packet.getPacketDirection()) {
                                case CLIENT_TO_SERVER:
                                    final Class<? extends AbstractClientPacket<TeraGameConnection>> clientPacket = ClientPacketHandler.getClientPacket(packet.getOpcode());
                                    if (clientPacket != null) {
                                        packetName = clientPacket.getSimpleName();
                                        imageFile = "fromClient.png";
                                    }
                                    else {
                                        imageFile = "fromClientErr.png";
                                    }
                                break;

                                case SERVER_TO_CLIENT:
                                    final Class<? extends AbstractServerPacket<TeraGameConnection>> serverPacket = ServerPacketHandler.getServerPacket(packet.getOpcode());
                                    if (serverPacket != null) {
                                        packetName = serverPacket.getSimpleName();
                                        imageFile = "fromServer.png";
                                    }
                                    else {
                                        imageFile = "fromServerErr.png";
                                    }
                                break;
                            }

                            final ObservableList<Node> children = hbox.getChildren();
                            try {
                                children.add(new ImageView(new Image(getClass().getResource("/com/angelis/tera/packet/presentation/images/" + imageFile).openStream())));
                            }
                            catch (final IOException e) {
                                throw new RuntimeException(e);
                            }

                            children.add(new Label(String.format(" 0x%02X ", packet.getOpcode())));
                            if (packetName != null) {
                                children.add(new Label(packetName));
                            }

                            setGraphic(hbox);
                        }
                    }
                };

                return cell;
            }
        });
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Packet>() {
            @Override
            public void changed(final ObservableValue<? extends Packet> observable, final Packet oldValue, final Packet newValue) {
                if (newValue != null) {
                    packetContentTextArea.setText(newValue.toString());
                }
            }
        });
    }
    
    public synchronized void addPacket(final Packet packet) {
        this.packets.add(packet);
        this.refreshListView();
    }
    
    private synchronized void refreshListView() {
        final ObservableList<Packet> listViewItems = this.listView.getItems();
        listViewItems.clear();

        final String filter = this.filterTextField.getText();
        if (filter == null || "".equals(filter)) {
            listViewItems.addAll(this.packets);
            return;
        }

        final ObservableList<Packet> subentries = FXCollections.observableArrayList();
        for (final Packet packet : this.packets) {
            if (String.format("0x%02X", packet.getOpcode()).equals(filter)) {
                subentries.add(packet);
            }
        }
        listView.setItems(subentries);
    }

    @FXML
    private void onFilterTextFieldKeyUp(final KeyEvent event) {
        this.refreshListView();
    }
    
    @FXML
    private void onClearButtonClick(final ActionEvent event) {
        this.packets.clear();
        this.refreshListView();
    }
}
