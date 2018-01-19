/*
 * GuiObjects.java
 *
 * Copyright (C) 2003, 2004, 2005, 2006 F. Gerbig (fgerbig@users.sourceforge.net)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package dirsync.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import dirsync.DirSync;
import dirsync.directory.Directory;

/**
 * The DirSync GUI.
 * 
 * @author F. Gerbig (fgerbig@users.sourceforge.net)
 */
public abstract class GuiObjects extends javax.swing.JFrame {

    // cell renderer for the list of directory definitions
    class DirectoryCellRenderer extends JCheckBox implements ListCellRenderer {

        protected javax.swing.border.Border noFocusBorder;

        /**
         * A renderer to render the cells of the directory definitions table.
         */
        public DirectoryCellRenderer() {
            super();
            if (noFocusBorder == null) {
                noFocusBorder = new javax.swing.border.EmptyBorder(1, 1, 1, 1);
            }
            setOpaque(true);
            setBorder(noFocusBorder);
        }

        /**
         * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
         */
        @Override
        public java.awt.Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected,
                final boolean cellHasFocus) {
            setComponentOrientation(list.getComponentOrientation());
            if (isSelected || cellHasFocus) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            final Directory dir = (Directory) value;
            setText(dir.getName());
            setSelected(dir.isEnabled());
            if (DirSync.isGuiMode()) {
                setEnabled(DirSync.getGui().isEnabled);
            }
            setIcon(null);

            setEnabled(list.isEnabled());
            setFont(list.getFont());

            javax.swing.border.Border border = null;
            if (cellHasFocus) {
                if (isSelected) {
                    border = UIManager.getBorder("List.focusSelectedCellHighlightBorder");
                }
                if (border == null) {
                    border = UIManager.getBorder("List.focusCellHighlightBorder");
                }
            } else {
                border = noFocusBorder;
            }
            setBorder(border);

            return this;
        }
    }

    protected void init() {
        initComponents();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JCheckBox autoScrollCheckBox;
    protected javax.swing.JCheckBox copyAllCheckBox;
    protected javax.swing.JLabel copyAllLabel;
    protected javax.swing.JCheckBox copyLargerCheckBox;
    protected javax.swing.JLabel copyLargerLabel;
    protected javax.swing.JCheckBox copyLargerModifiedCheckBox;
    protected javax.swing.JLabel copyLargerModifiedLabel;
    protected javax.swing.JCheckBox copyModifiedCheckBox;
    protected javax.swing.JLabel copyModifiedLabel;
    protected javax.swing.JCheckBox copyNewCheckBox;
    protected javax.swing.JLabel copyNewLabel;
    protected javax.swing.JButton copyOptionsToAllDirsButton;
    protected javax.swing.JButton copyOptionsToEnabledDirsButton;
    javax.swing.JProgressBar currentProgress;
    protected javax.swing.JCheckBox deleteDirsCheckBox;
    protected javax.swing.JLabel deleteDirsLabel;
    protected javax.swing.JCheckBox deleteFilesCheckBox;
    protected javax.swing.JLabel deleteFilesLabel;
    protected javax.swing.JTextField dirExcludeField;
    protected javax.swing.JLabel dirExcludeLabel;
    protected javax.swing.JTextField dirIncludeField;
    protected javax.swing.JLabel dirIncludeLabel;
    protected javax.swing.JList dirList;
    protected javax.swing.JProgressBar dirProgress;
    protected javax.swing.JButton disableAllDirsButton;
    protected javax.swing.JButton dstButton;
    protected javax.swing.JTextField dstField;
    protected javax.swing.JLabel dstLabel;
    protected javax.swing.JButton enableAllDirsButton;
    protected javax.swing.JTextField fileExcludeField;
    protected javax.swing.JLabel fileExcludeLabel;
    protected javax.swing.JTextField fileIncludeField;
    protected javax.swing.JLabel fileIncludeLabel;
    protected javax.swing.JButton globalLogButton;
    protected javax.swing.JTextField globalLogField;
    protected javax.swing.JLabel globalLogLabel;
    protected javax.swing.JButton globalResetConfigButton;
    protected javax.swing.JRadioButton globalSymbolicLinkCopyRadioButton;
    protected javax.swing.JRadioButton globalSymbolicLinkSkipRadioButton;
    protected javax.swing.JTextField globalTimestampDiffField;
    protected javax.swing.JCheckBox globalTimestampWriteBackCheckBox;
    protected javax.swing.JButton listAddButton;
    protected javax.swing.JButton listCopyButton;
    protected javax.swing.JButton listDownButton;
    protected javax.swing.JButton listRemoveButton;
    protected javax.swing.JButton listUpButton;
    protected javax.swing.JButton logButton;
    protected javax.swing.JTextField logField;
    protected javax.swing.JLabel logLabel;
    protected javax.swing.JTextField nameField;
    protected javax.swing.JLabel nameLabel;
    protected javax.swing.JButton newButton;
    protected javax.swing.JMenuItem newMenuItem;
    protected javax.swing.JButton openButton;
    protected javax.swing.JMenuItem openMenuItem;
    protected javax.swing.JTextField optionsConfigPathField;
    protected javax.swing.JPanel optionsConfigPathPanel;
    protected javax.swing.JCheckBoxMenuItem optionsLookandfeelCheckBoxMenuItem;
    protected javax.swing.JCheckBoxMenuItem optionsNIOCheckBoxMenuItem;
    protected javax.swing.JTextPane outputArea;
    protected javax.swing.JToggleButton pauseButton;
    protected javax.swing.JButton srcButton;
    protected javax.swing.JTextField srcField;
    protected javax.swing.JLabel srcLabel;
    private javax.swing.JMenuItem startPreviewMenuItem;
    private javax.swing.JMenuItem startSynchronizationMenuItem;
    private javax.swing.JMenuItem stopMenuItem;
    protected javax.swing.JMenuItem swapSrcDstMenuItem;
    protected javax.swing.JProgressBar syncProgress;
    protected javax.swing.JTabbedPane tabbedPane;
    protected javax.swing.JCheckBox verifyCheckBox;
    protected javax.swing.JLabel verifyLabel;
    protected javax.swing.JCheckBox withSubfoldersCheckBox;
    protected javax.swing.JLabel withSubfoldersLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        javax.swing.JButton aboutButton;
        javax.swing.JMenuItem aboutMenuItem;
        javax.swing.JPanel buttonPanel;
        javax.swing.JPanel buttonPanel1;
        javax.swing.JPanel buttonPanel2;
        javax.swing.JButton contentsButton;
        javax.swing.JMenuItem contentsMenuItem;
        javax.swing.JLabel currentProgressLabel;
        javax.swing.JPanel currentProgressPanel;
        javax.swing.JPanel deleteOptionsPanel;
        javax.swing.JPanel dirConfigPanelPaths;
        javax.swing.JPanel dirPanel;
        javax.swing.JLabel dirProgressLabel;
        javax.swing.JPanel dirProgressPanel;
        javax.swing.JPanel directoryDefinitionsPanel;
        javax.swing.JPanel dstPanel;
        javax.swing.JMenu fileMenu;
        javax.swing.JPanel filesAndDirectoriesPanel;
        javax.swing.JPanel globalAdvancedConfigPanel;
        javax.swing.JPanel globalConfigPanel;
        javax.swing.ButtonGroup globalSymbolicLinkButtonGroup;
        javax.swing.JLabel globalSymbolicLinkExplanationLabel;
        javax.swing.JPanel globalSymbolicLinkPanel;
        javax.swing.JLabel globalTimestampDiffExplanationLabel;
        javax.swing.JPanel globalTimestampDiffFieldPanel;
        javax.swing.JLabel globalTimestampDiffLabel1;
        javax.swing.JLabel globalTimestampDiffLabel2;
        javax.swing.JPanel globalTimestampDiffPanel;
        javax.swing.JLabel globalTimestampExplanationLabel;
        javax.swing.JPanel globalTimestampPanel;
        javax.swing.JPanel globalTimestampWriteBackPanel;
        javax.swing.JPanel globallogPanel;
        java.awt.GridBagConstraints gridBagConstraints;
        javax.swing.JMenu helpMenu;
        javax.swing.JPanel howToSynchronizePanel;
        javax.swing.JPanel includeAndExcludeDirsPanel;
        javax.swing.JPanel includeAndExcludeFilesPanel;
        javax.swing.JPanel includeAndExcludePanel;
        javax.swing.JLabel jLabel1;
        javax.swing.JLabel jLabel10;
        javax.swing.JLabel jLabel11;
        javax.swing.JLabel jLabel2;
        javax.swing.JLabel jLabel3;
        javax.swing.JLabel jLabel4;
        javax.swing.JPanel jPanel1;
        javax.swing.JPanel jPanel10;
        javax.swing.JPanel jPanel11;
        javax.swing.JPanel jPanel12;
        javax.swing.JPanel jPanel13;
        javax.swing.JPanel jPanel14;
        javax.swing.JPanel jPanel15;
        javax.swing.JPanel jPanel2;
        javax.swing.JPanel jPanel3;
        javax.swing.JPanel jPanel4;
        javax.swing.JPanel jPanel5;
        javax.swing.JPanel jPanel6;
        javax.swing.JPanel jPanel7;
        javax.swing.JPanel jPanel8;
        javax.swing.JPanel jPanel9;
        javax.swing.JScrollPane jScrollPane1;
        javax.swing.JSeparator jSeparator1;
        javax.swing.JSeparator jSeparator2;
        javax.swing.JSeparator jSeparator3;
        javax.swing.JSeparator jSeparator4;
        javax.swing.JTabbedPane jTabbedPane2;
        javax.swing.JPanel logPanel;
        javax.swing.JPanel mainPanel;
        javax.swing.JMenuBar menuBar;
        javax.swing.JButton optionsConfigPathButton;
        javax.swing.JLabel optionsConfigPathLabel;
        javax.swing.JMenuItem optionsConfigPathMenuItem;
        javax.swing.JMenu optionsMenu;
        javax.swing.JPanel optionsPanel;
        javax.swing.JPanel outputOptionsPanel;
        javax.swing.JPanel outputPanel;
        javax.swing.JPanel progressPanel;
        javax.swing.JMenuItem quitMenuItem;
        javax.swing.JMenu runMenu;
        javax.swing.JButton saveButton;
        javax.swing.JMenuItem saveMenuItem;
        javax.swing.JScrollPane scrollPane;
        javax.swing.JPanel srcPanel;
        javax.swing.JButton startPreviewButton;
        javax.swing.JButton startSynchronizationButton;
        javax.swing.JButton stopButton;
        javax.swing.JLabel syncProgressLabel;
        javax.swing.JPanel syncProgressPanel;
        javax.swing.JToolBar toolBar;
        javax.swing.JMenu toolsMenu;
        javax.swing.JPanel whatToSynchronizePanel;

        optionsConfigPathPanel = new javax.swing.JPanel();
        optionsConfigPathLabel = new javax.swing.JLabel();
        optionsConfigPathField = new javax.swing.JTextField();
        optionsConfigPathButton = new javax.swing.JButton();
        globalSymbolicLinkButtonGroup = new javax.swing.ButtonGroup();
        toolBar = new javax.swing.JToolBar();
        newButton = new javax.swing.JButton();
        openButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        startPreviewButton = new javax.swing.JButton();
        startSynchronizationButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JToggleButton();
        stopButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        aboutButton = new javax.swing.JButton();
        contentsButton = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        globalConfigPanel = new javax.swing.JPanel();
        globallogPanel = new javax.swing.JPanel();
        globalLogLabel = new javax.swing.JLabel();
        globalLogField = new javax.swing.JTextField();
        globalLogButton = new javax.swing.JButton();
        outputPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextPane();
        outputOptionsPanel = new javax.swing.JPanel();
        autoScrollCheckBox = new javax.swing.JCheckBox();
        progressPanel = new javax.swing.JPanel();
        syncProgressPanel = new javax.swing.JPanel();
        syncProgressLabel = new javax.swing.JLabel();
        syncProgress = new javax.swing.JProgressBar();
        dirProgressPanel = new javax.swing.JPanel();
        dirProgressLabel = new javax.swing.JLabel();
        dirProgress = new javax.swing.JProgressBar();
        currentProgressPanel = new javax.swing.JPanel();
        currentProgressLabel = new javax.swing.JLabel();
        currentProgress = new javax.swing.JProgressBar();
        directoryDefinitionsPanel = new javax.swing.JPanel();
        dirPanel = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        dirList = new javax.swing.JList();
        buttonPanel = new javax.swing.JPanel();
        buttonPanel1 = new javax.swing.JPanel();
        enableAllDirsButton = new javax.swing.JButton();
        disableAllDirsButton = new javax.swing.JButton();
        buttonPanel2 = new javax.swing.JPanel();
        listAddButton = new javax.swing.JButton();
        listCopyButton = new javax.swing.JButton();
        listRemoveButton = new javax.swing.JButton();
        listUpButton = new javax.swing.JButton();
        listDownButton = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        whatToSynchronizePanel = new javax.swing.JPanel();
        dirConfigPanelPaths = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        srcLabel = new javax.swing.JLabel();
        srcPanel = new javax.swing.JPanel();
        srcField = new javax.swing.JTextField();
        srcButton = new javax.swing.JButton();
        dstLabel = new javax.swing.JLabel();
        dstPanel = new javax.swing.JPanel();
        dstField = new javax.swing.JTextField();
        dstButton = new javax.swing.JButton();
        logLabel = new javax.swing.JLabel();
        logPanel = new javax.swing.JPanel();
        logField = new javax.swing.JTextField();
        logButton = new javax.swing.JButton();
        includeAndExcludePanel = new javax.swing.JPanel();
        includeAndExcludeDirsPanel = new javax.swing.JPanel();
        dirIncludeLabel = new javax.swing.JLabel();
        dirIncludeField = new javax.swing.JTextField();
        dirExcludeLabel = new javax.swing.JLabel();
        dirExcludeField = new javax.swing.JTextField();
        includeAndExcludeFilesPanel = new javax.swing.JPanel();
        fileIncludeLabel = new javax.swing.JLabel();
        fileIncludeField = new javax.swing.JTextField();
        fileExcludeLabel = new javax.swing.JLabel();
        fileExcludeField = new javax.swing.JTextField();
        howToSynchronizePanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        filesAndDirectoriesPanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        copyAllCheckBox = new javax.swing.JCheckBox();
        copyAllLabel = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        copyNewCheckBox = new javax.swing.JCheckBox();
        copyNewLabel = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        copyLargerCheckBox = new javax.swing.JCheckBox();
        copyLargerLabel = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        copyModifiedCheckBox = new javax.swing.JCheckBox();
        copyModifiedLabel = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        copyLargerModifiedCheckBox = new javax.swing.JCheckBox();
        copyLargerModifiedLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        optionsPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        withSubfoldersCheckBox = new javax.swing.JCheckBox();
        withSubfoldersLabel = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        verifyCheckBox = new javax.swing.JCheckBox();
        verifyLabel = new javax.swing.JLabel();
        deleteOptionsPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        deleteFilesCheckBox = new javax.swing.JCheckBox();
        deleteFilesLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        deleteDirsCheckBox = new javax.swing.JCheckBox();
        deleteDirsLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        copyOptionsToAllDirsButton = new javax.swing.JButton();
        copyOptionsToEnabledDirsButton = new javax.swing.JButton();
        globalAdvancedConfigPanel = new javax.swing.JPanel();
        globalTimestampPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        globalTimestampExplanationLabel = new javax.swing.JLabel();
        globalTimestampWriteBackPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        globalTimestampWriteBackCheckBox = new javax.swing.JCheckBox();
        globalTimestampDiffPanel = new javax.swing.JPanel();
        globalTimestampDiffFieldPanel = new javax.swing.JPanel();
        globalTimestampDiffLabel1 = new javax.swing.JLabel();
        globalTimestampDiffField = new javax.swing.JTextField();
        globalTimestampDiffLabel2 = new javax.swing.JLabel();
        globalTimestampDiffExplanationLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        globalSymbolicLinkPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        globalSymbolicLinkExplanationLabel = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        globalSymbolicLinkCopyRadioButton = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        globalSymbolicLinkSkipRadioButton = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        globalResetConfigButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        quitMenuItem = new javax.swing.JMenuItem();
        runMenu = new javax.swing.JMenu();
        startPreviewMenuItem = new javax.swing.JMenuItem();
        startSynchronizationMenuItem = new javax.swing.JMenuItem();
        stopMenuItem = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        swapSrcDstMenuItem = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        optionsLookandfeelCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        optionsNIOCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        optionsConfigPathMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        aboutMenuItem = new javax.swing.JMenuItem();

        optionsConfigPathPanel.setLayout(new java.awt.BorderLayout());

        optionsConfigPathPanel.setToolTipText("<html>Specify the default configuration path.<br>Leave empty to use standard configuration path.</html>");
        optionsConfigPathLabel.setText("Configuartion path: ");
        optionsConfigPathPanel.add(optionsConfigPathLabel, java.awt.BorderLayout.NORTH);

        optionsConfigPathField.setMinimumSize(new java.awt.Dimension(320, 20));
        optionsConfigPathField.setPreferredSize(new java.awt.Dimension(320, 20));
        optionsConfigPathPanel.add(optionsConfigPathField, java.awt.BorderLayout.CENTER);

        optionsConfigPathButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/browse.png")));
        optionsConfigPathButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                browseConfigPathActionPerformed(evt);
            }
        });

        optionsConfigPathPanel.add(optionsConfigPathButton, java.awt.BorderLayout.EAST);

        setTitle("DirSync");
        setIconImage(new ImageIcon(getClass().getResource("/icons/DirSync.png")).getImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(final java.awt.event.WindowEvent evt) {
                exitFormWindowClosing(evt);
            }
        });

        newButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/fileNew.png")));
        newButton.setToolTipText("Creates a new configuration.");
        newButton.setMaximumSize(new java.awt.Dimension(30, 30));
        newButton.setMinimumSize(new java.awt.Dimension(30, 30));
        newButton.setPreferredSize(new java.awt.Dimension(30, 30));
        newButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                newConfigActionPerformed(evt);
            }
        });

        toolBar.add(newButton);

        openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/fileOpen.png")));
        openButton.setToolTipText("Opens a new configuration.");
        openButton.setMaximumSize(new java.awt.Dimension(30, 30));
        openButton.setMinimumSize(new java.awt.Dimension(30, 30));
        openButton.setOpaque(false);
        openButton.setPreferredSize(new java.awt.Dimension(30, 30));
        openButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                openConfigActionPerformed(evt);
            }
        });

        toolBar.add(openButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/fileSave.png")));
        saveButton.setToolTipText("Saves the configuration.");
        saveButton.setMaximumSize(new java.awt.Dimension(30, 30));
        saveButton.setMinimumSize(new java.awt.Dimension(30, 30));
        saveButton.setPreferredSize(new java.awt.Dimension(30, 30));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                saveConfigActionPerformed(evt);
            }
        });

        toolBar.add(saveButton);

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setMaximumSize(new java.awt.Dimension(16, 16));
        toolBar.add(jSeparator2);

        startPreviewButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/preview.png")));
        startPreviewButton.setToolTipText("Starts a preview of the synchronization.");
        startPreviewButton.setMaximumSize(new java.awt.Dimension(30, 30));
        startPreviewButton.setMinimumSize(new java.awt.Dimension(30, 30));
        startPreviewButton.setPreferredSize(new java.awt.Dimension(30, 30));
        startPreviewButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                previewStartActionPerformed(evt);
            }
        });

        toolBar.add(startPreviewButton);

        startSynchronizationButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/start.png")));
        startSynchronizationButton.setToolTipText("Starts the synchronization.");
        startSynchronizationButton.setMaximumSize(new java.awt.Dimension(30, 30));
        startSynchronizationButton.setMinimumSize(new java.awt.Dimension(30, 30));
        startSynchronizationButton.setPreferredSize(new java.awt.Dimension(30, 30));
        startSynchronizationButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                synchronizationStartActionPerformed(evt);
            }
        });

        toolBar.add(startSynchronizationButton);

        pauseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pause.png")));
        pauseButton.setToolTipText("Pauses the synchronization.");
        pauseButton.setMaximumSize(new java.awt.Dimension(30, 30));
        pauseButton.setMinimumSize(new java.awt.Dimension(30, 30));
        pauseButton.setPreferredSize(new java.awt.Dimension(30, 30));
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                synchronizationPauseActionPerformed(evt);
            }
        });

        toolBar.add(pauseButton);

        stopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/stop.png")));
        stopButton.setToolTipText("Stops the synchronization.");
        stopButton.setMaximumSize(new java.awt.Dimension(30, 30));
        stopButton.setMinimumSize(new java.awt.Dimension(30, 30));
        stopButton.setPreferredSize(new java.awt.Dimension(30, 30));
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                synchronizationStopActionPerformed(evt);
            }
        });

        toolBar.add(stopButton);

        jSeparator3.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setMaximumSize(new java.awt.Dimension(16, 16));
        toolBar.add(jSeparator3);

        aboutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/about.png")));
        aboutButton.setToolTipText("Displays information about \"DirectorySynchronize\".");
        aboutButton.setMaximumSize(new java.awt.Dimension(30, 30));
        aboutButton.setMinimumSize(new java.awt.Dimension(30, 30));
        aboutButton.setPreferredSize(new java.awt.Dimension(30, 30));
        aboutButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });

        toolBar.add(aboutButton);

        contentsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/contents.png")));
        contentsButton.setToolTipText("Displays help for \"DirectorySynchronize\".");
        contentsButton.setMaximumSize(new java.awt.Dimension(30, 30));
        contentsButton.setMinimumSize(new java.awt.Dimension(30, 30));
        contentsButton.setPreferredSize(new java.awt.Dimension(30, 30));
        contentsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                contentsActionPerformed(evt);
            }
        });

        toolBar.add(contentsButton);

        getContentPane().add(toolBar, java.awt.BorderLayout.NORTH);

        mainPanel.setLayout(new java.awt.BorderLayout(5, 2));

        mainPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        globalConfigPanel.setLayout(new java.awt.BorderLayout(5, 2));

        globalConfigPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        globalConfigPanel.setToolTipText("Global configuration");
        globallogPanel.setLayout(new javax.swing.BoxLayout(globallogPanel, javax.swing.BoxLayout.X_AXIS));

        globalLogLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/log.png")));
        globalLogLabel.setLabelFor(globalLogField);
        globalLogLabel.setText("Global logfile: ");
        globalLogLabel.setToolTipText("The global logfile");
        globallogPanel.add(globalLogLabel);

        globalLogField.setToolTipText("The global logfile.");
        globalLogField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                globalEventActionPerformed(evt);
            }
        });
        globalLogField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(final java.awt.event.FocusEvent evt) {
                globalEventFocusLost(evt);
            }
        });
        globalLogField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyReleased(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyTyped(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }
        });

        globallogPanel.add(globalLogField);

        globalLogButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/browse.png")));
        globalLogButton.setToolTipText("Browse for the global logfile or directory.");
        globalLogButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                browseGlobalLogActionPerformed(evt);
            }
        });

        globallogPanel.add(globalLogButton);

        globalConfigPanel.add(globallogPanel, java.awt.BorderLayout.NORTH);

        outputPanel.setLayout(new java.awt.BorderLayout());

        outputPanel.setBorder(new javax.swing.border.TitledBorder("Output"));
        jScrollPane1.setBorder(null);
        jScrollPane1.setToolTipText("The output of the synchronization.");
        outputArea.setToolTipText("The output of the synchronization.");
        jScrollPane1.setViewportView(outputArea);

        outputPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        outputOptionsPanel.setLayout(new java.awt.BorderLayout());

        autoScrollCheckBox.setSelected(true);
        autoScrollCheckBox.setText("Scroll automatically");
        autoScrollCheckBox.setToolTipText("Scrolls the output of the synchronization automatically.");
        outputOptionsPanel.add(autoScrollCheckBox, java.awt.BorderLayout.CENTER);

        outputPanel.add(outputOptionsPanel, java.awt.BorderLayout.SOUTH);

        globalConfigPanel.add(outputPanel, java.awt.BorderLayout.CENTER);

        progressPanel.setLayout(new javax.swing.BoxLayout(progressPanel, javax.swing.BoxLayout.Y_AXIS));

        progressPanel.setBorder(new javax.swing.border.TitledBorder("Progress"));
        progressPanel.setToolTipText("The progress of the synchronization.");
        syncProgressPanel.setLayout(new java.awt.BorderLayout(5, 5));

        syncProgressPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(2, 5, 2, 5)));
        syncProgressLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/progressSync.png")));
        syncProgressLabel.setText("Synchronization:");
        syncProgressLabel.setToolTipText("The overall progress of the synchronization (e.g. Directory 1 of 3).");
        syncProgressPanel.add(syncProgressLabel, java.awt.BorderLayout.WEST);

        syncProgress.setToolTipText("The overall progress of the synchronization.");
        syncProgress.setStringPainted(true);
        syncProgressPanel.add(syncProgress, java.awt.BorderLayout.CENTER);

        progressPanel.add(syncProgressPanel);

        dirProgressPanel.setLayout(new java.awt.BorderLayout(5, 5));

        dirProgressPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(2, 5, 2, 5)));
        dirProgressLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/progressDir.png")));
        dirProgressLabel.setText("Directory:");
        dirProgressLabel.setToolTipText("The progress of the synchronization of this directory (e.g. File 1 of 300).");
        dirProgressPanel.add(dirProgressLabel, java.awt.BorderLayout.WEST);

        dirProgress.setToolTipText("The progress of the synchronization of the current directory.");
        dirProgress.setStringPainted(true);
        dirProgressPanel.add(dirProgress, java.awt.BorderLayout.CENTER);

        progressPanel.add(dirProgressPanel);

        currentProgressPanel.setLayout(new java.awt.BorderLayout());

        currentProgressPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(2, 5, 2, 5)));
        currentProgressLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/progressFile.png")));
        currentProgressLabel.setText("Current: ");
        currentProgressLabel.setToolTipText("The name of the currently processed file or directory.");
        currentProgressPanel.add(currentProgressLabel, java.awt.BorderLayout.WEST);

        currentProgress.setToolTipText("The progress of the synchronization of the current file.");
        currentProgress.setString(" ");
        currentProgress.setStringPainted(true);
        currentProgressPanel.add(currentProgress, java.awt.BorderLayout.CENTER);

        progressPanel.add(currentProgressPanel);

        globalConfigPanel.add(progressPanel, java.awt.BorderLayout.SOUTH);

        tabbedPane.addTab("Output", globalConfigPanel);

        directoryDefinitionsPanel.setLayout(new java.awt.BorderLayout());

        directoryDefinitionsPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        dirPanel.setLayout(new java.awt.BorderLayout());

        dirPanel.setBorder(new javax.swing.border.TitledBorder("Directory definitions"));
        dirPanel.setToolTipText("Directory definitions");
        dirList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        dirList.setToolTipText("<html>The directory definitions to synchronize.<br>Click to select, double click or popup menu to enable or disable.</html>");
        dirList.setCellRenderer(new DirectoryCellRenderer());
        dirList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                dirSelectedValueChanged(evt);
            }
        });
        dirList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(final java.awt.event.MouseEvent evt) {
                DirectoryListMouseHandler(evt);
            }

            @Override
            public void mousePressed(final java.awt.event.MouseEvent evt) {
                DirectoryListMouseHandler(evt);
            }

            @Override
            public void mouseReleased(final java.awt.event.MouseEvent evt) {
                DirectoryListMouseHandler(evt);
            }
        });

        scrollPane.setViewportView(dirList);

        dirPanel.add(scrollPane, java.awt.BorderLayout.CENTER);

        buttonPanel.setLayout(new java.awt.BorderLayout());

        buttonPanel1.setLayout(new java.awt.GridLayout(2, 1));

        enableAllDirsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checkbox_checked.png")));
        enableAllDirsButton.setText("all");
        enableAllDirsButton.setToolTipText("Enables all directories.");
        enableAllDirsButton.setActionCommand("Enable all");
        enableAllDirsButton.setBorder(new javax.swing.border.EtchedBorder());
        enableAllDirsButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        enableAllDirsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                enableAllDirsActionPerformed(evt);
            }
        });

        buttonPanel1.add(enableAllDirsButton);

        disableAllDirsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checkbox_unchecked.png")));
        disableAllDirsButton.setText("none");
        disableAllDirsButton.setToolTipText("Disables all directories.");
        disableAllDirsButton.setActionCommand("Disable all");
        disableAllDirsButton.setBorder(new javax.swing.border.EtchedBorder());
        disableAllDirsButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        disableAllDirsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                disableAllDirsActionPerformed(evt);
            }
        });

        buttonPanel1.add(disableAllDirsButton);

        buttonPanel.add(buttonPanel1, java.awt.BorderLayout.WEST);

        listAddButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dirNew.png")));
        listAddButton.setText("Add new");
        listAddButton.setToolTipText("Add a new directory definition to the list.");
        listAddButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                listAddActionPerformed(evt);
            }
        });

        buttonPanel2.add(listAddButton);

        listCopyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dirCopy.png")));
        listCopyButton.setText("Copy");
        listCopyButton.setToolTipText("Copy an existing directory definition.");
        listCopyButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                listCopyActionPerformed(evt);
            }
        });

        buttonPanel2.add(listCopyButton);

        listRemoveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dirRemove.png")));
        listRemoveButton.setText("Remove");
        listRemoveButton.setToolTipText("Remove a directory definition from the list.");
        listRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                listRemoveActionPerformed(evt);
            }
        });

        buttonPanel2.add(listRemoveButton);

        listUpButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dirUp.png")));
        listUpButton.setText("Up");
        listUpButton.setToolTipText("Move the selected directory definition up.");
        listUpButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                listUpActionPerformed(evt);
            }
        });

        buttonPanel2.add(listUpButton);

        listDownButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dirDown.png")));
        listDownButton.setText("Down");
        listDownButton.setToolTipText("Move the selected directory definition down.");
        listDownButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                listDownActionPerformed(evt);
            }
        });

        buttonPanel2.add(listDownButton);

        buttonPanel.add(buttonPanel2, java.awt.BorderLayout.CENTER);

        dirPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);

        directoryDefinitionsPanel.add(dirPanel, java.awt.BorderLayout.CENTER);

        whatToSynchronizePanel.setLayout(new java.awt.BorderLayout());

        whatToSynchronizePanel.setToolTipText("What to synchronize.");
        dirConfigPanelPaths.setLayout(new java.awt.GridBagLayout());

        dirConfigPanelPaths.setBorder(new javax.swing.border.TitledBorder("Paths"));
        dirConfigPanelPaths.setToolTipText("On this tab you can select the paths for the selected directory definition.");
        nameLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/name.png")));
        nameLabel.setLabelFor(globalLogField);
        nameLabel.setText("Name: ");
        nameLabel.setToolTipText("The name of this synchronization.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        dirConfigPanelPaths.add(nameLabel, gridBagConstraints);

        nameField.setToolTipText("The name of this synchronization.");
        nameField.setPreferredSize(new java.awt.Dimension(6, 23));
        nameField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });
        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(final java.awt.event.FocusEvent evt) {
                dirEventFocusLost(evt);
            }
        });
        nameField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyReleased(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyTyped(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        dirConfigPanelPaths.add(nameField, gridBagConstraints);

        srcLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/srcDir.png")));
        srcLabel.setLabelFor(srcField);
        srcLabel.setText("Source directory: ");
        srcLabel.setToolTipText("The source directory.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        dirConfigPanelPaths.add(srcLabel, gridBagConstraints);

        srcPanel.setLayout(new javax.swing.BoxLayout(srcPanel, javax.swing.BoxLayout.X_AXIS));

        srcField.setToolTipText("The source directory.");
        srcField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });
        srcField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(final java.awt.event.FocusEvent evt) {
                dirEventFocusLost(evt);
            }
        });
        srcField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyReleased(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyTyped(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }
        });

        srcPanel.add(srcField);

        srcButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/browse.png")));
        srcButton.setToolTipText("Browse for the source directory.");
        srcButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                browseSrcActionPerformed(evt);
            }
        });

        srcPanel.add(srcButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        dirConfigPanelPaths.add(srcPanel, gridBagConstraints);

        dstLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dstDir.png")));
        dstLabel.setLabelFor(globalLogField);
        dstLabel.setText("Destination directory: ");
        dstLabel.setToolTipText(
                "<html>The destination directory. You can use the following wildcards:<br>\"&lt;date&gt;\" for the current date (or \"&lt;DD&gt;\" for day, \"&lt;MM&gt;\" for month, and \"&lt;YYYY&gt;\" for year) and<br>\"&lt;time&gt;\" for the current time (or \"&lt;hh&gt;\" for hour, \"&lt;mm&gt;\" for minute, and \"&lt;ss&gt;\" for second).</html> ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        dirConfigPanelPaths.add(dstLabel, gridBagConstraints);

        dstPanel.setLayout(new javax.swing.BoxLayout(dstPanel, javax.swing.BoxLayout.X_AXIS));

        dstField.setToolTipText(
                "<html>The destination directory. You can use the following wildcards:<br>\"&lt;date&gt;\" for the current date (or \"&lt;DD&gt;\" for day, \"&lt;MM&gt;\" for month, and \"&lt;YYYY&gt;\" for year) and<br>\"&lt;time&gt;\" for the current time (or \"&lt;hh&gt;\" for hour, \"&lt;mm&gt;\" for minute, and \"&lt;ss&gt;\" for second).</html> ");
        dstField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });
        dstField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(final java.awt.event.FocusEvent evt) {
                dirEventFocusLost(evt);
            }
        });
        dstField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyReleased(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyTyped(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }
        });

        dstPanel.add(dstField);

        dstButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/browse.png")));
        dstButton.setToolTipText("Browse for the destination directory.");
        dstButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                browseDstActionPerformed(evt);
            }
        });

        dstPanel.add(dstButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        dirConfigPanelPaths.add(dstPanel, gridBagConstraints);

        logLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/log.png")));
        logLabel.setLabelFor(globalLogField);
        logLabel.setText("Logfile: ");
        logLabel.setToolTipText(
                "<html>The logfile. You can use the following wildcards:<br>\"&lt;date&gt;\" for the current date (or \"&lt;DD&gt;\" for day, \"&lt;MM&gt;\" for month, and \"&lt;YYYY&gt;\" for year) and<br>\"&lt;time&gt;\" for the current time (or \"&lt;hh&gt;\" for hour, \"&lt;mm&gt;\" for minute, and \"&lt;ss&gt;\" for second).</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        dirConfigPanelPaths.add(logLabel, gridBagConstraints);

        logPanel.setLayout(new javax.swing.BoxLayout(logPanel, javax.swing.BoxLayout.X_AXIS));

        logField.setToolTipText(
                "<html>The logfile. You can use the following wildcards:<br>\"&lt;global&gt;\" for the directory of the global log and \"&lt;name&gt;\" for the name of the directory definition<br>\"&lt;date&gt;\" for the current date (or \"&lt;DD&gt;\" for day, \"&lt;MM&gt;\" for month, and \"&lt;YYYY&gt;\" for year) and<br>\"&lt;time&gt;\" for the current time (or \"&lt;hh&gt;\" for hour, \"&lt;mm&gt;\" for minute, and \"&lt;ss&gt;\" for second).</html>");
        logField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });
        logField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(final java.awt.event.FocusEvent evt) {
                dirEventFocusLost(evt);
            }
        });
        logField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyReleased(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyTyped(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }
        });

        logPanel.add(logField);

        logButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/browse.png")));
        logButton.setToolTipText("Browse for the logfile or directory.");
        logButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                browseLogActionPerformed(evt);
            }
        });

        logPanel.add(logButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        dirConfigPanelPaths.add(logPanel, gridBagConstraints);

        whatToSynchronizePanel.add(dirConfigPanelPaths, java.awt.BorderLayout.CENTER);

        includeAndExcludePanel.setLayout(new java.awt.GridLayout(1, 2));

        includeAndExcludeDirsPanel.setLayout(new java.awt.GridBagLayout());

        includeAndExcludeDirsPanel.setBorder(new javax.swing.border.TitledBorder("Especially in- and exclude directories"));
        includeAndExcludeDirsPanel.setToolTipText("In- or exclude this directory patterns in the synchronization; separate patterns by \",\" or \";\".");
        dirIncludeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/includeDir.png")));
        dirIncludeLabel.setText("Include directories: ");
        dirIncludeLabel.setToolTipText("Include this directory patterns in the synchronization (e.g. 'MyDocuments;*_important').");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 0, 0);
        includeAndExcludeDirsPanel.add(dirIncludeLabel, gridBagConstraints);

        dirIncludeField.setText("*");
        dirIncludeField.setToolTipText("Include this directory patterns in the synchronization (e.g. 'MyDocuments;*_important').");
        dirIncludeField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });
        dirIncludeField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(final java.awt.event.FocusEvent evt) {
                dirEventFocusLost(evt);
            }
        });
        dirIncludeField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyReleased(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyTyped(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 2);
        includeAndExcludeDirsPanel.add(dirIncludeField, gridBagConstraints);

        dirExcludeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/excludeDir.png")));
        dirExcludeLabel.setText("Exclude directories: ");
        dirExcludeLabel.setToolTipText("Exclude this directory patterns from the synchronization (e.g. '*_old;Backup').");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 2, 0);
        includeAndExcludeDirsPanel.add(dirExcludeLabel, gridBagConstraints);

        dirExcludeField.setToolTipText("Exclude this directory patterns from the synchronization (e.g. '*_old;Backup').");
        dirExcludeField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });
        dirExcludeField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(final java.awt.event.FocusEvent evt) {
                dirEventFocusLost(evt);
            }
        });
        dirExcludeField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyReleased(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyTyped(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 2);
        includeAndExcludeDirsPanel.add(dirExcludeField, gridBagConstraints);

        includeAndExcludePanel.add(includeAndExcludeDirsPanel);

        includeAndExcludeFilesPanel.setLayout(new java.awt.GridBagLayout());

        includeAndExcludeFilesPanel.setBorder(new javax.swing.border.TitledBorder("Especially in- and exclude files"));
        includeAndExcludeFilesPanel.setToolTipText("In- or exclude this file patterns in the synchronization; separate patterns by \",\" or \";\".");
        fileIncludeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/includeFile.png")));
        fileIncludeLabel.setLabelFor(fileIncludeField);
        fileIncludeLabel.setText("Include files: ");
        fileIncludeLabel.setToolTipText("Include this file patterns in the synchronization (e.g. '*.doc;*.txt').");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 0, 0);
        includeAndExcludeFilesPanel.add(fileIncludeLabel, gridBagConstraints);

        fileIncludeField.setText("*");
        fileIncludeField.setToolTipText("Include this file patterns in the synchronization (e.g. '*.doc;*.txt').");
        fileIncludeField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });
        fileIncludeField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(final java.awt.event.FocusEvent evt) {
                dirEventFocusLost(evt);
            }
        });
        fileIncludeField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyReleased(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyTyped(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 2);
        includeAndExcludeFilesPanel.add(fileIncludeField, gridBagConstraints);

        fileExcludeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/excludeFile.png")));
        fileExcludeLabel.setLabelFor(fileExcludeField);
        fileExcludeLabel.setText("Exclude files: ");
        fileExcludeLabel.setToolTipText("Exclude this file patterns from the synchronization (e.g. '*.old;*.bak').");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 2, 0);
        includeAndExcludeFilesPanel.add(fileExcludeLabel, gridBagConstraints);

        fileExcludeField.setToolTipText("Exclude this file patterns from the synchronization (e.g. '*.old;*.bak').");
        fileExcludeField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });
        fileExcludeField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(final java.awt.event.FocusEvent evt) {
                dirEventFocusLost(evt);
            }
        });
        fileExcludeField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyReleased(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }

            @Override
            public void keyTyped(final java.awt.event.KeyEvent evt) {
                dirEventKey(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 2);
        includeAndExcludeFilesPanel.add(fileExcludeField, gridBagConstraints);

        includeAndExcludePanel.add(includeAndExcludeFilesPanel);

        whatToSynchronizePanel.add(includeAndExcludePanel, java.awt.BorderLayout.SOUTH);

        jTabbedPane2.addTab("What to synchronize", whatToSynchronizePanel);

        howToSynchronizePanel.setLayout(new java.awt.BorderLayout());

        howToSynchronizePanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        howToSynchronizePanel.setToolTipText("How to synchronize.");
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        filesAndDirectoriesPanel.setLayout(new javax.swing.BoxLayout(filesAndDirectoriesPanel, javax.swing.BoxLayout.Y_AXIS));

        filesAndDirectoriesPanel.setBorder(new javax.swing.border.TitledBorder("Sync this files and dirs"));
        filesAndDirectoriesPanel.setToolTipText("Select depending on what criteria files and directories will be synchronized.");
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jPanel8.setToolTipText("Synchronizes all files and directories.");
        copyAllCheckBox.setToolTipText("Synchronize all files and dirs.");
        copyAllCheckBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });

        jPanel8.add(copyAllCheckBox);

        copyAllLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/copyAll.png")));
        copyAllLabel.setText("All");
        copyAllLabel.setToolTipText("Synchronize all files and dirs.");
        jPanel8.add(copyAllLabel);

        filesAndDirectoriesPanel.add(jPanel8);

        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jPanel9.setToolTipText("Synchronizes only files and directories not existing in the destination directory.");
        copyNewCheckBox.setToolTipText("Synchronize new files (i.e. not existing in the destination directory).");
        copyNewCheckBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });

        jPanel9.add(copyNewCheckBox);

        copyNewLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/copyNew.png")));
        copyNewLabel.setText("New");
        copyNewLabel.setToolTipText("Synchronize new files (i.e. not existing in the destination directory).");
        jPanel9.add(copyNewLabel);

        filesAndDirectoriesPanel.add(jPanel9);

        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jPanel10.setToolTipText("Synchronizes only files and directories that are larger than in the destination directory.");
        copyLargerCheckBox.setToolTipText("Synchronize larger files (i.e. with a smaller version in the destination directory).");
        copyLargerCheckBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });

        jPanel10.add(copyLargerCheckBox);

        copyLargerLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/copyLarger.png")));
        copyLargerLabel.setText("Larger");
        copyLargerLabel.setToolTipText("Synchronize larger files (i.e. with a smaller version in the destination directory).");
        jPanel10.add(copyLargerLabel);

        filesAndDirectoriesPanel.add(jPanel10);

        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jPanel11.setToolTipText("Synchronizes only files and directories that are newer than in the destination directory.");
        copyModifiedCheckBox.setToolTipText("Synchronize modified files (i.e. with a older version in the destination directory).");
        copyModifiedCheckBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });

        jPanel11.add(copyModifiedCheckBox);

        copyModifiedLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/copyModified.png")));
        copyModifiedLabel.setText("Modified");
        copyModifiedLabel.setToolTipText("Synchronize modified files (i.e. with a older version in the destination directory).");
        jPanel11.add(copyModifiedLabel);

        filesAndDirectoriesPanel.add(jPanel11);

        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jPanel12.setToolTipText("Synchronize only larger AND modified (i.e. with a smaller, older version existing in the destination directory) files.");
        copyLargerModifiedCheckBox
                .setToolTipText("Synchronize only larger AND modified files (i.e. with a smaller, older version in the destination directory).");
        copyLargerModifiedCheckBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });

        jPanel12.add(copyLargerModifiedCheckBox);

        copyLargerModifiedLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/copyLargerModified.png")));
        copyLargerModifiedLabel.setText("Only larger and modified");
        copyLargerModifiedLabel.setToolTipText("Synchronize only larger AND modified files (i.e. with a smaller, older version in the destination directory).");
        jPanel12.add(copyLargerModifiedLabel);

        filesAndDirectoriesPanel.add(jPanel12);

        jPanel4.add(filesAndDirectoriesPanel);
        filesAndDirectoriesPanel.getAccessibleContext().setAccessibleName("Sync this files and directories");

        jPanel2.setLayout(new java.awt.GridLayout(2, 1));

        optionsPanel.setLayout(new javax.swing.BoxLayout(optionsPanel, javax.swing.BoxLayout.Y_AXIS));

        optionsPanel.setBorder(new javax.swing.border.TitledBorder("Sync using this options"));
        optionsPanel.setToolTipText("Options for the synchronization.");
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jPanel6.setToolTipText("Synchronize including subfolders.");
        withSubfoldersCheckBox.setToolTipText("Synchronize including subfolders.");
        withSubfoldersCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        withSubfoldersCheckBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });

        jPanel6.add(withSubfoldersCheckBox);

        withSubfoldersLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/withSubdirs.png")));
        withSubfoldersLabel.setText("Include subfolders");
        withSubfoldersLabel.setToolTipText("Synchronize including subfolders.");
        jPanel6.add(withSubfoldersLabel);

        optionsPanel.add(jPanel6);

        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jPanel7.setToolTipText("Verify copied files.");
        verifyCheckBox.setToolTipText("Verify files after they have been synchronized.");
        verifyCheckBox.setActionCommand("Verify files");
        verifyCheckBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });

        jPanel7.add(verifyCheckBox);

        verifyLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/verify.png")));
        verifyLabel.setText("Verify synchronized files");
        verifyLabel.setToolTipText("Verify files after they have been synchronized.");
        jPanel7.add(verifyLabel);

        optionsPanel.add(jPanel7);

        jPanel2.add(optionsPanel);

        deleteOptionsPanel.setLayout(new javax.swing.BoxLayout(deleteOptionsPanel, javax.swing.BoxLayout.Y_AXIS));

        deleteOptionsPanel.setBorder(new javax.swing.border.TitledBorder("After sync delete unnecessary"));
        deleteOptionsPanel.setToolTipText("Options regarding what to delete after the synchronization.");
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jPanel1.setToolTipText("Delete files not existing in the source directory from the destination directory.");
        deleteFilesCheckBox.setToolTipText("Delete files not existing in the source directory from the destination directory.");
        deleteFilesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });

        jPanel1.add(deleteFilesCheckBox);

        deleteFilesLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/deleteFile.png")));
        deleteFilesLabel.setText("Files");
        deleteFilesLabel.setToolTipText("Delete files not existing in the source directory from the destination directory.");
        jPanel1.add(deleteFilesLabel);

        deleteOptionsPanel.add(jPanel1);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        jPanel5.setToolTipText("Delete directories not existing in the source directory from the destination directory.");
        deleteDirsCheckBox.setToolTipText("Delete directories not existing in the source directory from the destination directory.");
        deleteDirsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                dirEventActionPerformed(evt);
            }
        });

        jPanel5.add(deleteDirsCheckBox);

        deleteDirsLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/deleteDir.png")));
        deleteDirsLabel.setText("Directories");
        deleteDirsLabel.setToolTipText("Delete directories not existing in the source directory from the destination directory.");
        jPanel5.add(deleteDirsLabel);

        deleteOptionsPanel.add(jPanel5);

        jPanel2.add(deleteOptionsPanel);

        jPanel4.add(jPanel2);

        howToSynchronizePanel.add(jPanel4, java.awt.BorderLayout.CENTER);

        copyOptionsToAllDirsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/copyOptionsToAllDirs.png")));
        copyOptionsToAllDirsButton.setText("Copy to all directory definitions");
        copyOptionsToAllDirsButton.setToolTipText("Copy the selected \"How to synchronize\" options to all directory definitions.");
        copyOptionsToAllDirsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                copyOptionsToAllDirsButtonOptionsToAllDirsActionPerformed(evt);
            }
        });

        jPanel3.add(copyOptionsToAllDirsButton);

        copyOptionsToEnabledDirsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/copyOptionsToEnabledDirs.png")));
        copyOptionsToEnabledDirsButton.setText("Copy to enabled directory definitions");
        copyOptionsToEnabledDirsButton.setToolTipText("Copy the selected \"How to synchronize\" options to the enabled directory definitions.");
        copyOptionsToEnabledDirsButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                copyOptionsToEnabledDirsActionPerformed(evt);
            }
        });

        jPanel3.add(copyOptionsToEnabledDirsButton);

        howToSynchronizePanel.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jTabbedPane2.addTab("How to synchronize", howToSynchronizePanel);

        directoryDefinitionsPanel.add(jTabbedPane2, java.awt.BorderLayout.SOUTH);

        tabbedPane.addTab("Directories", directoryDefinitionsPanel);

        globalAdvancedConfigPanel.setLayout(new java.awt.GridBagLayout());

        globalAdvancedConfigPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(10, 5, 5, 5)));
        globalAdvancedConfigPanel.setToolTipText("Advanced configuration options. This are global options affecting all directory definitions.");
        globalAdvancedConfigPanel.setMinimumSize(new java.awt.Dimension(341, 520));
        globalAdvancedConfigPanel.setPreferredSize(new java.awt.Dimension(500, 520));
        globalTimestampPanel.setLayout(new java.awt.GridBagLayout());

        globalTimestampPanel.setBorder(new javax.swing.border.TitledBorder("Handling of timestamp related problems"));
        globalTimestampPanel.setToolTipText(
                "<html>\ne.g. FAT always forces the timestamp down to even seconds.<br>If you copy a file with a source timestamp of \"12:05:01\" to FAT it will be stored with a destination timestamp of \"12:05:00\".<br>The next time you synchronize this file with the \"Copy modified\" option it will be copied again!\n</html>");
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/timestamp.png")));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        globalTimestampPanel.add(jLabel2, gridBagConstraints);

        globalTimestampExplanationLabel.setText(
                "<html> Some filesystems do not store timestamps accurately and it is possible that problems with the option \"Sync modified\" arise. There are two possible workarounds:<br>  <br> </html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        globalTimestampPanel.add(globalTimestampExplanationLabel, gridBagConstraints);

        globalTimestampWriteBackPanel.setLayout(new java.awt.GridBagLayout());

        globalTimestampWriteBackPanel.setBorder(new javax.swing.border.TitledBorder("1. Write timestamps back to source files"));
        globalTimestampWriteBackPanel.setToolTipText(
                "<html>\nThe timestamp of \"12:05:00\" would be written back to the source file.<br>The next time you synchronize this file with the \"Modified\" option it will not be copied again because both files have the same timestamp.\n</html>");
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/writeTimestampBack.png")));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        globalTimestampWriteBackPanel.add(jLabel1, gridBagConstraints);

        jPanel15.setLayout(new java.awt.GridLayout(1, 0));

        globalTimestampWriteBackCheckBox.setText(
                "<html>After a file has been copied the destination timestamp is written back to the source file; this ensures that the timestamps are identical.<br><br>PROBLEM: This is not possible if the source is read-only.</html>");
        globalTimestampWriteBackCheckBox.setToolTipText(
                "<html> The timestamp of \"12:05:00\" would be written back to the source file.<br>The next time you synchronize this file with the \"Modified\" option it will not be copied again because both files have the same timestamp. </html>");
        globalTimestampWriteBackCheckBox.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel15.add(globalTimestampWriteBackCheckBox);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        globalTimestampWriteBackPanel.add(jPanel15, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        globalTimestampPanel.add(globalTimestampWriteBackPanel, gridBagConstraints);

        globalTimestampDiffPanel.setLayout(new java.awt.GridBagLayout());

        globalTimestampDiffPanel.setBorder(new javax.swing.border.TitledBorder("2. Specify max seconds between timestamps to be equal"));
        globalTimestampDiffPanel.setToolTipText(
                "<html>\nIf you specify more than one second in this option the timestamp of \"12:05:00\" would be treated as equal to the timestamp \"12:05:01\".<br>If  you synchronize this file with the \"Modified\" option it will not be copied because both files have an equal timestamp.\n</html>");
        globalTimestampDiffFieldPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        globalTimestampDiffFieldPanel.setMaximumSize(new java.awt.Dimension(32767, 30));
        globalTimestampDiffLabel1.setText("Treat timestamps which differ less than ");
        globalTimestampDiffFieldPanel.add(globalTimestampDiffLabel1);

        globalTimestampDiffField.setText("0");
        globalTimestampDiffField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(final JComponent input) {
                final JTextField field = (JTextField) input;
                int value = 0;

                try {
                    value = Integer.parseInt(field.getText());
                } catch (final NumberFormatException nfe) {
                    field.setText("0");
                    displayWarning();
                    return true;
                }
                if ((value < 0) || (value > 60)) {
                    field.setText("0");
                    displayWarning();
                }
                return true;
            }

            private void displayWarning() {
                JOptionPane.showMessageDialog(DirSync.getGui(), "The value bust be between 0 and 60.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        globalTimestampDiffField.setMaximumSize(new java.awt.Dimension(20, 20));
        globalTimestampDiffField.setMinimumSize(new java.awt.Dimension(20, 20));
        globalTimestampDiffField.setPreferredSize(new java.awt.Dimension(20, 20));
        globalTimestampDiffField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                globalEventActionPerformed(evt);
            }
        });
        globalTimestampDiffField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(final java.awt.event.FocusEvent evt) {
                globalEventFocusLost(evt);
            }
        });

        globalTimestampDiffFieldPanel.add(globalTimestampDiffField);

        globalTimestampDiffLabel2.setText("seconds as equal.");
        globalTimestampDiffFieldPanel.add(globalTimestampDiffLabel2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        globalTimestampDiffPanel.add(globalTimestampDiffFieldPanel, gridBagConstraints);

        globalTimestampDiffExplanationLabel.setText(
                "<html>This ensures that timestamps that differ due to filesystem inaccuracy are handled as the same.<br><br>PROBLEM: If this value is chosen too big some files that are actually different might be falsely considered to be identical.</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        globalTimestampDiffPanel.add(globalTimestampDiffExplanationLabel, gridBagConstraints);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/timestampDiff.png")));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        globalTimestampDiffPanel.add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        globalTimestampPanel.add(globalTimestampDiffPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        globalAdvancedConfigPanel.add(globalTimestampPanel, gridBagConstraints);

        globalSymbolicLinkPanel.setLayout(new java.awt.GridBagLayout());

        globalSymbolicLinkPanel.setBorder(new javax.swing.border.TitledBorder("Handling of symbolic links"));
        globalSymbolicLinkPanel.setToolTipText("How to handle symbolic links (not relevant for Windows).");
        globalSymbolicLinkPanel.setPreferredSize(new java.awt.Dimension(320, 114));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/link.png")));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        globalSymbolicLinkPanel.add(jLabel4, gridBagConstraints);

        globalSymbolicLinkExplanationLabel.setText(
                "<html>Some filesystems can have symbolic links (a symbolic link is a special type of file that serves as a reference to another file).<br><br>Symbolic links can be copied as files or they can be skipped:<br></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        globalSymbolicLinkPanel.add(globalSymbolicLinkExplanationLabel, gridBagConstraints);

        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        globalSymbolicLinkButtonGroup.add(globalSymbolicLinkCopyRadioButton);
        globalSymbolicLinkCopyRadioButton.setSelected(true);
        jPanel13.add(globalSymbolicLinkCopyRadioButton);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/copyLinks.png")));
        jLabel10.setText("Copy symbolic links as files");
        jLabel10.setToolTipText("Symbolic links will be copied as normal files.");
        jPanel13.add(jLabel10);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        globalSymbolicLinkPanel.add(jPanel13, gridBagConstraints);

        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        globalSymbolicLinkButtonGroup.add(globalSymbolicLinkSkipRadioButton);
        jPanel14.add(globalSymbolicLinkSkipRadioButton);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/skipLinks.png")));
        jLabel11.setText("Skip symbolic links");
        jLabel11.setToolTipText("Nothing will be copied for symbolic links.");
        jPanel14.add(jLabel11);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        globalSymbolicLinkPanel.add(jPanel14, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        globalAdvancedConfigPanel.add(globalSymbolicLinkPanel, gridBagConstraints);

        globalResetConfigButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/default.png")));
        globalResetConfigButton.setText("Reset to default");
        globalResetConfigButton.setToolTipText("Reset the advanced options to reasonable default values.");
        globalResetConfigButton.setAlignmentX(0.5F);
        globalResetConfigButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                globalResetConfigActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        globalAdvancedConfigPanel.add(globalResetConfigButton, gridBagConstraints);

        tabbedPane.addTab("Advanced", globalAdvancedConfigPanel);

        mainPanel.add(tabbedPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        fileMenu.setText("File");
        newMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/fileNew.png")));
        newMenuItem.setText("New");
        newMenuItem.setToolTipText("Creates a new configuration.");
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                newConfigActionPerformed(evt);
            }
        });

        fileMenu.add(newMenuItem);

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/fileOpen.png")));
        openMenuItem.setText("Open");
        openMenuItem.setToolTipText("Opens a existing configuration.");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                openConfigActionPerformed(evt);
            }
        });

        fileMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/fileSave.png")));
        saveMenuItem.setText("Save");
        saveMenuItem.setToolTipText("Saves the current configuration.");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                saveConfigActionPerformed(evt);
            }
        });

        fileMenu.add(saveMenuItem);
        saveMenuItem.getAccessibleContext().setAccessibleDescription("Saves the configuration.");

        fileMenu.add(jSeparator4);

        quitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        quitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/quit.png")));
        quitMenuItem.setText("Quit");
        quitMenuItem.setToolTipText("Quits the program.");
        quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                quitActionPerformed(evt);
            }
        });

        fileMenu.add(quitMenuItem);

        menuBar.add(fileMenu);

        runMenu.setText("Run");
        startPreviewMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/preview.png")));
        startPreviewMenuItem.setText("Preview");
        startPreviewMenuItem.setToolTipText("Starts a preview of the synchronization.");
        startPreviewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                previewStartActionPerformed(evt);
            }
        });

        runMenu.add(startPreviewMenuItem);

        startSynchronizationMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/start.png")));
        startSynchronizationMenuItem.setText("Start");
        startSynchronizationMenuItem.setToolTipText("Starts the synchronization.");
        startSynchronizationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                synchronizationStartActionPerformed(evt);
            }
        });

        runMenu.add(startSynchronizationMenuItem);

        stopMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/stop.png")));
        stopMenuItem.setText("Stop");
        stopMenuItem.setToolTipText("Stops the synchronization.");
        stopMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                synchronizationStopActionPerformed(evt);
            }
        });

        runMenu.add(stopMenuItem);

        menuBar.add(runMenu);

        toolsMenu.setText("Tools");
        swapSrcDstMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/swapSrcDst.png")));
        swapSrcDstMenuItem.setText("Swap source and destination directories");
        swapSrcDstMenuItem.setToolTipText("Swaps the source and destination directories in all directory definitions.");
        swapSrcDstMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                toolsSwapSrcDstActionPerformed(evt);
            }
        });

        toolsMenu.add(swapSrcDstMenuItem);

        menuBar.add(toolsMenu);

        optionsMenu.setText("Options");
        optionsLookandfeelCheckBoxMenuItem.setText("Use Java \"Metal\" look and feel");
        optionsLookandfeelCheckBoxMenuItem
                .setToolTipText("Selects the Java cross platform look and feel. Otherwise the native look and feel of this platform will be used.");
        optionsLookandfeelCheckBoxMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lookandfeel.png")));
        optionsLookandfeelCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                optionsLookandfeelActionPerformed(evt);
            }
        });

        optionsMenu.add(optionsLookandfeelCheckBoxMenuItem);

        optionsNIOCheckBoxMenuItem.setText("Use fast copy (Java NIO)");
        optionsNIOCheckBoxMenuItem.setToolTipText(
                "Selects the NIO \"New Input Output\" architecture for copies. This should be much faster, but can leave the GUI unresponsive on computers with slow CPUs.");
        optionsNIOCheckBoxMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/NIO.png")));
        optionsNIOCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                optionsNIOActionPerformed(evt);
            }
        });

        optionsMenu.add(optionsNIOCheckBoxMenuItem);

        optionsConfigPathMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/config.png")));
        optionsConfigPathMenuItem.setToolTipText("Sets the default path for configurations used by the open and save dialogs.");
        optionsConfigPathMenuItem.setLabel("Set default configuration path");
        optionsConfigPathMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                optionsConfigPathActionPerformed(evt);
            }
        });

        optionsMenu.add(optionsConfigPathMenuItem);

        menuBar.add(optionsMenu);

        helpMenu.setText("Help");
        contentsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        contentsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/contents.png")));
        contentsMenuItem.setText("Contents");
        contentsMenuItem.setToolTipText("Displays help for \"DirectorySynchronize\".");
        contentsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                contentsActionPerformed(evt);
            }
        });

        helpMenu.add(contentsMenuItem);

        helpMenu.add(jSeparator1);

        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        aboutMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/about.png")));
        aboutMenuItem.setText("About");
        aboutMenuItem.setToolTipText("Displays information about \"DirectorySynchronize\".");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });

        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents

    protected void dirEventKey(final java.awt.event.KeyEvent evt) {// GEN-FIRST:event_dirEventKey
        dirEvent();
    }// GEN-LAST:event_dirEventKey

    protected void aboutActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_aboutActionPerformed
        about();
    }// GEN-LAST:event_aboutActionPerformed

    protected void browseConfigPathActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_browseConfigPathActionPerformed
        browseConfigPath();
    }// GEN-LAST:event_browseConfigPathActionPerformed

    protected void browseDstActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_browseDstActionPerformed
        browseDst();
    }// GEN-LAST:event_browseDstActionPerformed

    protected void browseGlobalLogActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_browseGlobalLogActionPerformed
        browseGlobalLog();
    }// GEN-LAST:event_browseGlobalLogActionPerformed

    protected void browseLogActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_browseLogActionPerformed
        browseLog();
    }// GEN-LAST:event_browseLogActionPerformed

    protected void browseSrcActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_browseSrcActionPerformed
        browseSrc();
    }// GEN-LAST:event_browseSrcActionPerformed

    protected void contentsActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_contentsActionPerformed
        contents();
    }// GEN-LAST:event_contentsActionPerformed

    protected void copyOptionsToEnabledDirsActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_copyOptionsToEnabledDirsActionPerformed
        copyOptionsToEnabledDirs();
    }// GEN-LAST:event_copyOptionsToEnabledDirsActionPerformed

    protected void copyOptionsToAllDirsButtonOptionsToAllDirsActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_copyOptionsToAllDirsButtonOptionsToAllDirsActionPerformed
        copyOptionsToAllDirs();
    }// GEN-LAST:event_copyOptionsToAllDirsButtonOptionsToAllDirsActionPerformed

    protected void DirectoryListMouseHandler(final java.awt.event.MouseEvent evt) {// GEN-FIRST:event_DirectoryListMouseHandler
        // Do nothing if GUI is disabled
        if (!DirSync.getGui().isGuiEnabled()) {
            return;
        }

        final int index = dirList.locationToIndex(evt.getPoint());
        final Directory dir = (Directory) DirSync.getSync().getDirs().get(index);

        // double click left mouse button
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            dir.setEnabled(!dir.isEnabled()); // toggle enable
            dirList.repaint();
        }

        // popup menu
        if (evt.isPopupTrigger()) {

            final JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem menuItem;

            if (dir.isEnabled()) {
                menuItem = new JMenuItem("Disable");
            } else {
                menuItem = new JMenuItem("Enable");
            }

            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    dir.setEnabled(!dir.isEnabled()); // toggle enable
                    dirList.repaint();
                }
            });

            popupMenu.add(menuItem);
            popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }// GEN-LAST:event_DirectoryListMouseHandler

    protected void dirEventActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_dirEventActionPerformed
        dirEvent();
    }// GEN-LAST:event_dirEventActionPerformed

    protected void dirEventFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_dirEventFocusLost
        dirEvent();
    }// GEN-LAST:event_dirEventFocusLost

    protected void dirSelectedValueChanged(final javax.swing.event.ListSelectionEvent evt) {// GEN-FIRST:event_dirSelectedValueChanged
        dirSelected();
    }// GEN-LAST:event_dirSelectedValueChanged

    protected void disableAllDirsActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_disableAllDirsActionPerformed
        disableAllDirs();
    }// GEN-LAST:event_disableAllDirsActionPerformed

    protected void enableAllDirsActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_enableAllDirsActionPerformed
        enableAllDirs();
    }// GEN-LAST:event_enableAllDirsActionPerformed

    protected void exitFormWindowClosing(final java.awt.event.WindowEvent evt) {// GEN-FIRST:event_exitFormWindowClosing
        exitForm();
    }// GEN-LAST:event_exitFormWindowClosing

    protected void globalEventActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_globalEventActionPerformed
        getGlobalFromGui();
    }// GEN-LAST:event_globalEventActionPerformed

    protected void globalEventFocusLost(final java.awt.event.FocusEvent evt) {// GEN-FIRST:event_globalEventFocusLost
        getGlobalFromGui();
    }// GEN-LAST:event_globalEventFocusLost

    protected void globalResetConfigActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_globalResetConfigActionPerformed
        globalResetConfig();
    }// GEN-LAST:event_globalResetConfigActionPerformed

    protected void listAddActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_listAddActionPerformed
        listAdd();
    }// GEN-LAST:event_listAddActionPerformed

    protected void listCopyActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_listCopyActionPerformed
        listCopy();
    }// GEN-LAST:event_listCopyActionPerformed

    protected void listDownActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_listDownActionPerformed
        listDown();
    }// GEN-LAST:event_listDownActionPerformed

    protected void listRemoveActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_listRemoveActionPerformed
        listRemove();
    }// GEN-LAST:event_listRemoveActionPerformed

    protected void listUpActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_listUpActionPerformed
        listUp();
    }// GEN-LAST:event_listUpActionPerformed

    protected void newConfigActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newConfigActionPerformed
        newConfig();
    }// GEN-LAST:event_newConfigActionPerformed

    protected void openConfigActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_openConfigActionPerformed
        openConfig();
    }// GEN-LAST:event_openConfigActionPerformed

    protected void optionsConfigPathActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_optionsConfigPathActionPerformed
        optionsConfigPath();
    }// GEN-LAST:event_optionsConfigPathActionPerformed

    protected void optionsLookandfeelActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_optionsLookandfeelActionPerformed
        optionsLookandfeel();
    }// GEN-LAST:event_optionsLookandfeelActionPerformed

    private void optionsNIOActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_optionsNIOActionPerformed
        optionsNIO();
    }// GEN-LAST:event_optionsNIOActionPerformed

    protected void previewStartActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_previewStartActionPerformed
        previewStart();
    }// GEN-LAST:event_previewStartActionPerformed

    protected void quitActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_quitActionPerformed
        quit();
    }// GEN-LAST:event_quitActionPerformed

    protected void saveConfigActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_saveConfigActionPerformed
        saveConfig();
    }// GEN-LAST:event_saveConfigActionPerformed

    protected void synchronizationPauseActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_synchronizationPauseActionPerformed
        pause();
    }// GEN-LAST:event_synchronizationPauseActionPerformed

    protected void synchronizationStartActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_synchronizationStartActionPerformed
        synchronizationStart();
    }// GEN-LAST:event_synchronizationStartActionPerformed

    protected void synchronizationStopActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_synchronizationStopActionPerformed
        stop();
    }// GEN-LAST:event_synchronizationStopActionPerformed

    protected void toolsSwapSrcDstActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_toolsSwapSrcDstActionPerformed
        toolsSwapSrcDst();
    }// GEN-LAST:event_toolsSwapSrcDstActionPerformed

    abstract protected void about();

    abstract protected void browseConfigPath();

    abstract protected void browseDst();

    abstract protected void browseGlobalLog();

    abstract protected void browseLog();

    abstract protected void browseSrc();

    abstract protected void checkDefaults();

    abstract protected void contents();

    abstract protected void copyOptionsToEnabledDirs();

    abstract protected void copyOptionsToAllDirs();

    abstract protected void dirEvent();

    abstract protected void dirSelected();

    abstract protected void disableAllDirs();

    abstract protected void enableAllDirs();

    abstract protected void exitForm();

    abstract protected Directory getDirFromGui(boolean enabled);

    abstract protected void getGlobalFromGui();

    abstract protected void globalResetConfig();

    abstract protected void listAdd();

    abstract protected void listCopy();

    abstract protected void listDown();

    abstract protected void listRemove();

    abstract protected void listUp();

    abstract protected void newConfig();

    abstract protected void openConfig();

    abstract protected void optionsConfigPath();

    abstract protected void optionsLookandfeel();

    abstract protected void optionsNIO();

    abstract protected void pause();

    abstract protected void previewStart();

    abstract protected void quit();

    abstract protected void saveConfig();

    abstract protected void setGuiFromDir(Directory dir);

    abstract protected void setGuiFromGlobal();

    abstract protected void stop();

    abstract protected void synchronizationStart();

    abstract protected void toolsSwapSrcDst();
}