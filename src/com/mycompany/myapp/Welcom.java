/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.db.Database;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;

/**
 *
 * @author AmineHosni
 */
public class Welcom {

    Form f;

    Database db;
    boolean created = false;

    public Welcom() {

        try {
            created = Database.exists("x.db");
            db = Database.openOrCreate("x.db");

            if (created == false) {
                db.execute("create table codes (id INTEGER PRIMARY KEY autoincrement, code INTEGER);");
                System.out.println("databse just created");
            }

            f = new Form("Welcome", BoxLayout.y());
            ImageViewer imgViewer = new ImageViewer();
            try {
                Image img = Image.createImage("/accueil.png");
                imgViewer.setImage(img);

            } catch (IOException ex) {
            }
            f.add(imgViewer);

            CheckBox chxPremium = new CheckBox("Compte premium");
            CheckBox chxEco = new CheckBox("Compte economique");

            f.add(chxPremium);
            f.add(chxEco);

            TextField txtCode = new TextField();
            txtCode.setHint("saisir code");
            Button btnSubmit = new Button("Submit");

            f.add(txtCode);
            f.add(btnSubmit);

            txtCode.setHidden(true);
            btnSubmit.setHidden(true);

            chxEco.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (chxEco.isSelected()) {

                        btnSubmit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                String strCode = txtCode.getText();
                                try {
                                    int code = Integer.parseInt(strCode);
//                                Dialog.show("", "votre code :" + code, "ok", null
                                    String query = "insert into codes (code) values (" + code+");";
                                    System.out.println(query);
                                    db.execute(query);

                                } catch (java.lang.NumberFormatException ex) {
                                    Dialog.show("code doit être entier", "veuillez vérifier votre code", "ok", null);

                                } catch (IOException ex) {
                                    Dialog.show("erreur insert", ex.toString(), "ok", null);
                                }

                            }
                        });
                        txtCode.setHidden(false);
                        btnSubmit.setHidden(false);

                        f.refreshTheme();
                    } else {
                        txtCode.setHidden(true);
                        btnSubmit.setHidden(true);
                        f.refreshTheme();

                    }
                }
            });

            f.show();

        } catch (IOException ex) {
            Dialog.show("erreur db", ex.toString(), "ok", null);
        }

    }

    public Form getF() {
        return f;
    }

    public void show() {
        f.show();
    }

}
