/*
 *   Swing Explorer. Tool for developers exploring Java/Swing-based application internals. 
 *   Copyright (C) 2012, Maxim Zakharenkov
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *   Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *   
 */
package org.swingexplorer.awt_events.filter;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import org.swingexplorer.awt_events.ActEventFilterChanged;
import org.swingexplorer.awt_events.Filter;

/**
 *
 * @author  Maxim Zakharenkov
 */
public class PNLEventFilter extends javax.swing.JPanel {
    
    /** Creates new form PNLEventFilter */
    public PNLEventFilter() {
        initComponents();
        initActions();
    }
    
    void initActions() {
        actEventFilterChanged = new ActEventFilterChanged(this);
        ItemListener itemListenerProxy = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(!itemEventsDisabled) {
                    commit();
                    fireFilterChanged();
                }
            }
        };
        
        int count = this.getComponentCount();
        for(int i = 0; i < count; i ++) {
            Component comp = getComponent(i);
            if(comp instanceof JCheckBox) {
                ((JCheckBox)comp).addItemListener(itemListenerProxy);
            }
        }
    }
    
    public void addFilterChangeListener(FilterChangeListener l) {
        filterListeners.add(l);
    }
    
    public void removeFilterChangeListener(FilterChangeListener l) {
        filterListeners.remove(l);
    }
    
    protected void fireFilterChanged() {
        for(FilterChangeListener listener: filterListeners) {
            listener.filterChanged(filter.clone());
        }
    }
    
    public void setEditListener() {
        
    }
    
    public void setFilter(Filter _filter) {
        filter = _filter.clone();
        
        itemEventsDisabled = true;
        chbKeyPressed.setSelected(filter.keyPressed);
        chbKeyReleased.setSelected(filter.keyReleased);
        chbKeyTyped.setSelected(filter.keyTyped);
        chbMouseClicked.setSelected(filter.mouseClicked);
        chbMouseDragged.setSelected(filter.mouseDragged);
        chbMouseEntered.setSelected(filter.mouseEntered);
        chbMouseExited.setSelected(filter.mouseExited);
        chbMouseMoved.setSelected(filter.mouseMoved);
        chbMousePressed.setSelected(filter.mousePressed);
        chbMouseReleased.setSelected(filter.mousePressed);
        chbMouseWeel.setSelected(filter.mouseWeel);
        itemEventsDisabled = false; 
    }
    
    public Filter getFilter() {
        return filter;
    }
    
    public Filter commit() {
        Filter newFilter = new Filter();
        newFilter.keyPressed = chbKeyPressed.isSelected();
        newFilter.mouseDragged = chbMouseDragged.isSelected();
        newFilter.keyPressed = chbKeyPressed.isSelected();
        newFilter.keyReleased = chbKeyReleased.isSelected();
        newFilter.keyTyped = chbKeyTyped.isSelected();
        newFilter.mouseEntered = chbMouseEntered.isSelected();
        newFilter.mouseClicked = chbMouseClicked.isSelected();
        newFilter.mouseExited = chbMouseExited.isSelected();
        newFilter.mouseMoved = chbMouseMoved.isSelected();
        newFilter.mouseReleased = chbMouseReleased.isSelected();
        newFilter.mouseWeel = chbMouseWeel.isSelected();
        newFilter.mousePressed = chbMousePressed.isSelected();
        filter = newFilter;
        return newFilter;
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chbMouseClicked = new javax.swing.JCheckBox();
        chbMousePressed = new javax.swing.JCheckBox();
        chbMouseDragged = new javax.swing.JCheckBox();
        chbKeyPressed = new javax.swing.JCheckBox();
        chbKeyReleased = new javax.swing.JCheckBox();
        chbKeyTyped = new javax.swing.JCheckBox();
        chbMouseReleased = new javax.swing.JCheckBox();
        chbMouseMoved = new javax.swing.JCheckBox();
        chbMouseEntered = new javax.swing.JCheckBox();
        chbMouseExited = new javax.swing.JCheckBox();
        chbMouseWeel = new javax.swing.JCheckBox();
        btnSelectAll = new javax.swing.JButton();
        btnClearAll = new javax.swing.JButton();

        chbMouseClicked.setText("Mouse clicked");

        chbMousePressed.setText("Mouse pressed");

        chbMouseDragged.setText("Mouse dragged");

        chbKeyPressed.setText("Key pressed");

        chbKeyReleased.setText("Key released");

        chbKeyTyped.setText("Key typed");

        chbMouseReleased.setText("Mouse released");

        chbMouseMoved.setText("Mouse moved");

        chbMouseEntered.setText("Mouse entered");

        chbMouseExited.setText("Mouse exited");

        chbMouseWeel.setText("Mouse weel");

        btnSelectAll.setText("Select all");
        btnSelectAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectAllActionPerformed(evt);
            }
        });

        btnClearAll.setText("Deselect all");
        btnClearAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearAllActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(chbMouseReleased)
                            .add(chbMousePressed)
                            .add(chbKeyTyped)
                            .add(chbKeyReleased)
                            .add(chbKeyPressed))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(chbMouseDragged)
                            .add(chbMouseWeel)
                            .add(chbMouseExited)
                            .add(chbMouseEntered)
                            .add(chbMouseMoved)))
                    .add(layout.createSequentialGroup()
                        .add(btnSelectAll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, 0)
                        .add(btnClearAll))
                    .add(chbMouseClicked))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(new java.awt.Component[] {btnClearAll, btnSelectAll}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnSelectAll)
                    .add(btnClearAll))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(chbKeyPressed)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(chbKeyReleased)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(chbKeyTyped)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(chbMousePressed)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(chbMouseReleased))
                    .add(layout.createSequentialGroup()
                        .add(chbMouseMoved)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(chbMouseEntered)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(chbMouseExited)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(chbMouseDragged)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(chbMouseWeel)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(chbMouseClicked)
                .addContainerGap(11, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void setAllSelected(boolean selected) {
        itemEventsDisabled = true;
        int count = this.getComponentCount();
        for(int i = 0; i < count; i ++) {
            Component comp = getComponent(i);
            if(comp instanceof JCheckBox) {
                ((JCheckBox)comp).setSelected(selected);
            }
        }
        itemEventsDisabled = false;
        
        commit();
        fireFilterChanged();
    }
    
    private void btnSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAllActionPerformed
        setAllSelected(true);
    }//GEN-LAST:event_btnSelectAllActionPerformed

    private void btnClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearAllActionPerformed
        setAllSelected(false);
    }//GEN-LAST:event_btnClearAllActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton btnClearAll;
    javax.swing.JButton btnSelectAll;
    javax.swing.JCheckBox chbKeyPressed;
    javax.swing.JCheckBox chbKeyReleased;
    javax.swing.JCheckBox chbKeyTyped;
    javax.swing.JCheckBox chbMouseClicked;
    javax.swing.JCheckBox chbMouseDragged;
    javax.swing.JCheckBox chbMouseEntered;
    javax.swing.JCheckBox chbMouseExited;
    javax.swing.JCheckBox chbMouseMoved;
    javax.swing.JCheckBox chbMousePressed;
    javax.swing.JCheckBox chbMouseReleased;
    javax.swing.JCheckBox chbMouseWeel;
    // End of variables declaration//GEN-END:variables

    Filter filter;
    ArrayList<FilterChangeListener> filterListeners = new ArrayList<FilterChangeListener>();
    ActEventFilterChanged actEventFilterChanged;
    boolean itemEventsDisabled = false;
}
