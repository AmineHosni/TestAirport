/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;

/**
 *
 * @author AmineHosni
 */
public class Booking {

    Form f;

    Database db;
    boolean created = true;

    public Booking(String flight, String companyName,String[] flights) {

        try {
            created = Database.exists("x.db");
            db = Database.openOrCreate("x.db");

            if (created == true) {
                f = new Form("Reservation", BoxLayout.y());

                ImageViewer imgViewerX = new ImageViewer();
                try {
                    Image imgX = Image.createImage("/" + companyName + ".png");
                    imgViewerX.setImage(imgX);
                } catch (IOException ex) {
                }
                Label lblX = new Label(flight);

                TextField tfCode = new TextField();
                Button btnBook = new Button("book");

                btnBook.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            int enteredCode = Integer.parseInt(tfCode.getText());
                            int dbCode;

                            String query = "SELECT code FROM codes order BY id DESC LIMIT 1;";
                            Cursor c = db.executeQuery(query);
                            Row r = c.getRow();
                            dbCode = r.getInteger(0);
                            System.out.println("DB code : " + dbCode);

                            if (enteredCode == dbCode) {
                                Dialog.show("Alert", "Code correct et la réservation est effectuée avec succès", "ok", null);
                                new Welcome().show();
                            } else {
                                Dialog.show("Alert", "Code erroné. Veuillez re-saisir le code", "ok", null);
                            }

                        } catch (java.lang.NumberFormatException ex) {
                            Dialog.show("code doit être entier", "veuillez vérifier votre code", "ok", null);
                        } catch (IOException ex) {

                        }

                    }
                });
                
                f.getToolbar().addCommandToLeftBar("back", null,l -> new Flights(flights, companyName).show());

                f.add(imgViewerX);
                f.add(lblX);

                f.add(tfCode);
                f.add(btnBook);
                f.show();

            }

        } catch (IOException ex) {
        }
    }

    public Form getF() {
        return f;
    }

    public void show() {
        f.show();
    }

}
