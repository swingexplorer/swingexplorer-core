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
package org.swingexplorer;




import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


/**
 *
 * @author  Maxim Zakharenkov
 */
public class PNLComponentTree extends javax.swing.JPanel {
    
    static final DefaultMutableTreeNode EMPTY_ALL_TREE_ROOT = new DefaultMutableTreeNode(new TreeNodeObject(null, "root"));
    static final DefaultMutableTreeNode EMPTY_DISPLAYED_TREE_ROOT = new DefaultMutableTreeNode(new TreeNodeObject(null, "No Component is displayed"));
    
    /** Creates new form ComponentTree */
    public PNLComponentTree() {
        initComponents();
        
        tbpTrees.setName("tbpTrees");
        
        getTreeModel(treAll).setRoot(EMPTY_ALL_TREE_ROOT);
        getTreeModel(treDisplayed).setRoot(EMPTY_DISPLAYED_TREE_ROOT);
        
        
        treAll.setExpandsSelectedPaths(true);
        treAll.setRootVisible(false);
        treAll.setShowsRootHandles(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbpTrees = new javax.swing.JTabbedPane();
        scpTreeAll = new javax.swing.JScrollPane();
        treAll = new javax.swing.JTree();
        scpTreeDisplayed = new javax.swing.JScrollPane();
        treDisplayed = new javax.swing.JTree();

        treAll.setToggleClickCount(0);
        scpTreeAll.setViewportView(treAll);

        tbpTrees.addTab("All Roots", null, scpTreeAll, "All root components");

        treDisplayed.setToggleClickCount(0);
        scpTreeDisplayed.setViewportView(treDisplayed);

        tbpTrees.addTab("Displayed", null, scpTreeDisplayed, "Components starting from displayed root");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tbpTrees, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, tbpTrees, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scpTreeAll;
    private javax.swing.JScrollPane scpTreeDisplayed;
    private javax.swing.JTabbedPane tbpTrees;
    private javax.swing.JTree treAll;
    private javax.swing.JTree treDisplayed;
    // End of variables declaration//GEN-END:variables
    
    MdlSwingExplorer model;
    ModelListener modelListener = new ModelListener();
    JPopupMenu popupMenu = new JPopupMenu();
    TreeSelectionListener actTreeSelectionChanged;
    
    public void addAction(Action act) {
        popupMenu.add(act);
    }
    
    public void setRoot(DefaultMutableTreeNode root) {
        // memorize expansions
        TreePath pathToRoot = new TreePath(treAll.getModel().getRoot());
        Enumeration<TreePath> expandPaths = treAll.getExpandedDescendants(pathToRoot);

        // because "setRoot" clears selection we need to 
        // disable selection notification so that MdsSwingExplorer
        // do not know about temporary losing of selection
        disableSelectionNotification();
        
        // set root model
        ((DefaultTreeModel)treAll.getModel()).setRoot(root);
    
        // restore expansions
        GuiUtils.expandTreePaths(treAll, expandPaths);

        updateDisplayedComponentTree();
        
        // restore selection from MdsSwingExplorer ane enable
        // selection notification back
        setSelectComponents(model.getSelectedComponents());
        enableSelectionNotification();
    }
    
    public DefaultMutableTreeNode getRoot() {
        return (DefaultMutableTreeNode)treAll.getModel().getRoot();
    }
   
    private void updateDisplayedComponentTree() {
        // update displayed component tree
        if(model.getDisplayedComponent() != null) {                     
            
            // momorize expanded state
            TreePath pathToRoot = new TreePath(treDisplayed.getModel().getRoot());
            Enumeration<TreePath> expandPaths = treDisplayed.getExpandedDescendants(pathToRoot);
            
            // set new root
            TreePath path = getComponentPath(treAll, model.getDisplayedComponent());                    
            DefaultMutableTreeNode displayedRoot =  (DefaultMutableTreeNode)path.getLastPathComponent();
            getTreeModel(treDisplayed).setRoot(displayedRoot);
            
            // restore  expanded state
            GuiUtils.expandTreePaths(treDisplayed, expandPaths);
            treDisplayed.setEnabled(true);
        } else {
            getTreeModel(treDisplayed).setRoot(EMPTY_DISPLAYED_TREE_ROOT);
            treDisplayed.setEnabled(false);
        }
    }
    
    
    private DefaultTreeModel getTreeModel(JTree tree) {
        return (DefaultTreeModel)tree.getModel();
    }
    
    public Component getSelectedComponent() {
        if(tbpTrees.getSelectedComponent() == scpTreeAll) {     
            return getComponent(treAll.getSelectionPath());
        } else {
            return getComponent(treDisplayed.getSelectionPath());
        }
    }
    
    public void setModel(MdlSwingExplorer model) {

        if (model == this.model) {
            return;
        }

        if (this.model != null) {
            this.model.removePropertyChangeListener(modelListener);
        }
        if (model != null) {
            model.addPropertyChangeListener(modelListener);
        }
        this.model = model;
        repaint();
    }
    
    public static Component getComponent(TreePath path) {
        if(path == null) {
            return null;
        }
        DefaultMutableTreeNode node =  (DefaultMutableTreeNode) path.getLastPathComponent();
        
        Object obj = node.getUserObject();
        if(!(obj instanceof TreeNodeObject)) {
            return null;
        }
        TreeNodeObject treeNodeObject = (TreeNodeObject)obj; 
        
        return treeNodeObject.getComponent();
    }

    TreePath getComponentPath(JTree tree, Component comp) {
        TreePath[] paths = getComponentPaths(tree, comp);       
        return paths.length == 0 ? null : paths[0]; 
    }
    
    // returns component paths for given tree
    TreePath[] getComponentPaths(JTree tree, Component...components) {

        log("getComponentPaths");
        
        List<?> compList = Arrays.asList(components);
        ArrayList<TreePath> paths = new ArrayList<TreePath>();

        //  iterating all tree elements 
        
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
        
        Enumeration<?> preEnum = root.preorderEnumeration();
        while(preEnum.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) preEnum.nextElement();
            TreeNodeObject obj = (TreeNodeObject) node.getUserObject();
            
            // check if the component in the array 
            if(compList.contains(obj.getComponent())) {
                 TreeNode[] pathArray = getTreeModel(tree).getPathToRoot(node);
                 
                 TreePath path = new TreePath(pathArray);
                 paths.add(path);
            }
        }
        
        return paths.toArray(new TreePath[paths.size()]);
    }
    
    public void setSelectComponents(Component[] components) {
        log("setSelectComponents");
        
        // select components in All tree
        TreePath[] paths = getComponentPaths(treAll, components);
        
          // remove selection listeners temporary to avoid notification
        disableSelectionNotification();
        
        treAll.setSelectionPaths(paths);
        
        if(paths.length > 0) {
            treAll.scrollPathToVisible(paths[0]);
        }
        
        // select component in Displayed tree
        paths = getComponentPaths(treDisplayed, components);
        
        treDisplayed.setSelectionPaths(paths);


        enableSelectionNotification();
        
        if(paths.length > 0) {
            treDisplayed.scrollPathToVisible(paths[0]);
        }
    }
    
    
    private void disableSelectionNotification() {
        treAll.getSelectionModel().removeTreeSelectionListener(actTreeSelectionChanged);       
        treDisplayed.getSelectionModel().removeTreeSelectionListener(actTreeSelectionChanged);
    }
    
    private void enableSelectionNotification() {
        treAll.getSelectionModel().addTreeSelectionListener(actTreeSelectionChanged);       
        treDisplayed.getSelectionModel().addTreeSelectionListener(actTreeSelectionChanged);
    }
    
    public void setTreeSelectionAction(TreeSelectionListener _actTreeSelectionChanged) {
        actTreeSelectionChanged =_actTreeSelectionChanged;
        enableSelectionNotification();
    }
    
    public void setDefaultTreeAction(final Action act) {
        MouseListener mouseListener = new MouseListener(act);
        treAll.addMouseListener(mouseListener);
        treDisplayed.addMouseListener(mouseListener);
        treAll.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "default");
        treAll.getActionMap().put("default", act);
        treDisplayed.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "default");
        treDisplayed.getActionMap().put("default", act);
    }
    
    private final class MouseListener extends MouseAdapter {
        private final Action act;

        private MouseListener(Action act) {
            super();
            this.act = act;
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() > 1) {
                // fire aciton on double click:
                JTree tree = (JTree)e.getSource();
                TreePath path = tree.getPathForLocation( e.getX(), e.getY() );
        
                if (path != null) {
                    e.consume();
                    act.actionPerformed(new ActionEvent(tree, 0, ""));                      
                }
             }
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger()) {
                showPopup(e);
            }
        }
        
        
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                showPopup(e);
            }
        }
        
        private void showPopup(MouseEvent e) {
            // selected node under mouse cursor and show popup menu if set
            Point mousePoint = e.getPoint();
            JTree tree = (JTree)e.getSource();
            int row = tree.getRowForLocation( mousePoint.x, mousePoint.y );
            TreePath path = tree.getPathForRow( row );
            if (path != null) {
                if (!tree.isPathSelected( path )) {
                    tree.setSelectionRow( row );
                }
                popupMenu.show(tree, e.getX(), e.getY());
            }
        }
    }

    static class TreeNodeObject {
        WeakReference<Component> objRef;
        String name;
        
        
        TreeNodeObject(Component component, String _name) {
            
            objRef = new WeakReference<Component>(component);
            if(component == null) {
                // this is only for root
                return;
            }
            name = _name;
        }
        
        public Component getComponent() {
            return (Component) objRef.get();
        }
        
        public String toString() {
            return name;
        }
        
        public boolean equals(Object o) {
            if(!(o instanceof TreeNodeObject)) {
                return false;
            }
            
            TreeNodeObject castedO = (TreeNodeObject)o;
            return castedO.objRef.get() == objRef.get();
        }
    }
    
    class ModelListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            String propName = evt.getPropertyName();
            if ("selectedComponents".equals(propName)) {
                setSelectComponents(model.getSelectedComponents());
            } else if("displayedComponent".equals(propName)) {
                updateDisplayedComponentTree();             
            }
        }
    }


    
    void log(String msg) {
//      System.out.println("[PNLComponentTree] " + msg);
    }
}

