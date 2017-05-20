/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author AmineHosni
 */
public class Companies {

    Form f;

    public Companies() {
        f = new Form("Companies", BoxLayout.y());

        ArrayList<String> companies = new ArrayList<String>();
        companies.add("tunisair");
        companies.add("lufthansa");
        companies.add("airfrance");

        Container companiesContainer = new Container(BoxLayout.y());
        Container companyContainer;

        for (int i = 0; i < companies.size(); i++) {

            ImageViewer imgViewerX = new ImageViewer();
            try {
                Image imgX = Image.createImage("/" + companies.get(i) + ".png");
                imgViewerX.setImage(imgX);
            } catch (IOException ex) {
            }
            Label lblX = new Label(companies.get(i));
            companyContainer = new Container(BoxLayout.y());
            companyContainer.add(imgViewerX);
            companyContainer.add(lblX);
            
            Label idX = new Label(String.valueOf(i+1));
            

            Button btn = new Button();
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Dialog.show("id: ",idX.getText(),"ok",null);

                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost/examen/getVols.php?id="+idX.getText());
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            System.out.println(new String(con.getResponseData()));

                        }
                    });

                    NetworkManager.getInstance().addToQueue(con);

                }
            });

            companyContainer.setLeadComponent(btn);

            companiesContainer.add(companyContainer);

        }

        f.add(companiesContainer);
        f.show();

    }

    public Form getF() {
        return f;
    }

    public void show() {
        f.show();
    }

    private String[] split(String original, String separator) {
        Vector nodes = new Vector();
        // Parse nodes into vector
        int index = original.indexOf(separator);
        while (index >= 0) {
            nodes.addElement(original.substring(0, index));
            original = original.substring(index + separator.length());
            index = original.indexOf(separator);
        }
        // Get the last node
        nodes.addElement(original);
        // Create split string array
        String[] result = new String[nodes.size()];
        if (nodes.size() > 0) {
            for (int loop = 0; loop < nodes.size(); loop++) {
                result[loop] = (String) nodes.elementAt(loop);
                System.out.println(result[loop]);
            }
        }
        return result;
    }
}
