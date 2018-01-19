/*
 * GuiMethods.java
 *
 * Copyright (C) 2002 E. Gerber
 * Copyright (C) 2003, 2004, 2005, 2006 F. Gerbig (fgerbig@users.sourceforge.net)
 * Copyright (C) 2005 T. Groetzner
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

import java.awt.Color;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import dirsync.Const;
import dirsync.DirSync;
import dirsync.directory.Directory;
import dirsync.exceptions.WarningException;
import dirsync.sync.Sync;
import dirsync.tools.Log;

/**
 * Contains the GUI methods.
 * 
 * @author F. Gerbig (fgerbig@users.sourceforge.net)
 */
public abstract class GuiMethods extends GuiObjects {

    protected boolean isEnabled = true;

    protected File currentConfig; // for open/save dialog

    protected int lastSelectedIndex;

    // whether the directory is being updated from the gui
    protected boolean isUpdateDirFromGui = true;

    protected String currentDirName = ""; // for displaying in the title bar

    /**
     * Initializes a new configuration.
     */
    public void initConfig() {
        setGuiFromGlobal();
        lastSelectedIndex = 0;
        dirList.setListData(DirSync.getSync().getDirs());
        dirList.setSelectedIndex(-1);
        setGuiFromDir((Directory) DirSync.getSync().getDirs().get(0));

        outputArea.setText("");
        currentConfig = null;
        updateTitle();

        // store default config path if specified
        if (DirSync.getProperties().getProperty("dirsync.config.path") != null) {
            currentConfig = new File(DirSync.getProperties().getProperty("dirsync.config.path"));
        }
    }

    @Override
    protected void newConfig() {
        isUpdateDirFromGui = false;

        DirSync.setSync(new Sync());
        try {
            DirSync.setLog(new Log(""));
        } catch (final Exception e) {}
        isUpdateDirFromGui = false;

        initConfig();
    }

    @Override
    protected void openConfig() {

        String filename = "";

        try {
            final javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setFileFilter(new ConfigFileFilter());
            if (currentConfig != null) {
                fileChooser.setSelectedFile(currentConfig);
                fileChooser.setCurrentDirectory(currentConfig);
            }

            if (fileChooser.showOpenDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
                filename = fileChooser.getSelectedFile().getPath();

                newConfig(); // delete current loaded config

                // load config
                DirSync.getSync().load(filename);

                // update GUI
                setGuiFromGlobal();
                setGuiFromDir((Directory) DirSync.getSync().getDirs().elementAt(0));
                dirList.setListData(DirSync.getSync().getDirs());
                lastSelectedIndex = 0;
                dirList.setSelectedIndex(lastSelectedIndex);
                outputArea.setText("");

                // remember directory and file
                currentConfig = fileChooser.getSelectedFile();
                updateTitle();
            }
        } catch (final Exception e) {
            currentConfig = null;
            DirSync.displayError("Error while loading configuration '" + filename + "'.");
        }
    }

    @Override
    protected void saveConfig() {
        String filename = "";

        try {
            final javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.addChoosableFileFilter(new ConfigFileFilter());
            if (currentConfig != null) {
                fileChooser.setCurrentDirectory(currentConfig);
                fileChooser.setSelectedFile(new File(""));
                if (currentConfig.isFile()) {
                    fileChooser.setSelectedFile(currentConfig);
                }
            }

            if (fileChooser.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
                filename = fileChooser.getSelectedFile().getPath();

                // check for FILE_EXTENSION
                if (!filename.toLowerCase().endsWith("." + Const.FILE_EXTENSION.toLowerCase())) {
                    // file does not end with default extension FILE_EXTENSION; add it
                    filename += "." + Const.FILE_EXTENSION;
                }

                getGlobalFromGui();

                // init global log
                try {
                    DirSync.setLog(new Log(globalLogField.getText())); // create global log
                } catch (final WarningException we) {}

                // if file exists display warning dialog
                if (!(new File(filename).exists()) || (JOptionPane.showConfirmDialog(null, "Configuration '" + filename + "' exists.\nOverwrite?", "Warning",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)) {

                    DirSync.getSync().save(filename);
                }

                // remember directory and file
                currentConfig = new File(filename);
                updateTitle();
                // use the possibly changed (extension added) filename
            }
        } catch (final Exception e) {
            currentConfig = null;
            DirSync.displayError("Error while saving configuration '" + filename + "'.");
        }

    }

    @Override
    protected void setGuiFromDir(final Directory dir) {
        nameField.setText(dir.getName());
        srcField.setText(dir.getSrc());
        dstField.setText(dir.getDst());
        logField.setText(dir.getLogfile());
        withSubfoldersCheckBox.setSelected(dir.isWithSubfolders());
        verifyCheckBox.setSelected(dir.isVerify());
        fileIncludeField.setText(dir.getFileInclude());
        fileExcludeField.setText(dir.getFileExclude());
        dirIncludeField.setText(dir.getDirInclude());
        dirExcludeField.setText(dir.getDirExclude());

        copyAllCheckBox.setSelected(dir.isCopyAll());
        copyNewCheckBox.setSelected(dir.isCopyNew());
        copyModifiedCheckBox.setSelected(dir.isCopyModified());
        copyLargerCheckBox.setSelected(dir.isCopyLarger());
        copyLargerModifiedCheckBox.setSelected(dir.isCopyLargerModified());

        deleteFilesCheckBox.setSelected(dir.isDelFiles());
        deleteDirsCheckBox.setSelected(dir.isDelDirs());

        checkDefaults();
    }

    @Override
    protected void setGuiFromGlobal() {
        globalLogField.setText(DirSync.getLog().getFilename());
        globalTimestampWriteBackCheckBox.setSelected(DirSync.getSync().isWriteTimestampBack());
        globalTimestampDiffField.setText(String.valueOf(DirSync.getSync().getMaxTimestampDiff()));
        globalSymbolicLinkCopyRadioButton.setSelected(!DirSync.getSync().isSkipLinks());
        globalSymbolicLinkSkipRadioButton.setSelected(DirSync.getSync().isSkipLinks());
    }

    @Override
    protected void getGlobalFromGui() {
        // global log will be initialised by start()

        DirSync.getSync().setWriteTimestampBack(globalTimestampWriteBackCheckBox.isSelected());
        DirSync.getSync().setMaxTimestampDiff(globalTimestampDiffField.getText());
        DirSync.getSync().setSkipLinks(globalSymbolicLinkSkipRadioButton.isSelected());
    }

    @Override
    protected void checkDefaults() {
        if (copyAllCheckBox.isSelected()) {
            copyNewCheckBox.setEnabled(false);
            copyNewLabel.setEnabled(false);
            copyModifiedCheckBox.setEnabled(false);
            copyModifiedLabel.setEnabled(false);
            copyLargerCheckBox.setEnabled(false);
            copyLargerLabel.setEnabled(false);
            copyLargerModifiedCheckBox.setEnabled(false);
            copyLargerModifiedLabel.setEnabled(false);
        } else {
            copyNewCheckBox.setEnabled(true);
            copyNewLabel.setEnabled(true);
            copyModifiedCheckBox.setEnabled(true);
            copyModifiedLabel.setEnabled(true);
            copyLargerCheckBox.setEnabled(true);
            copyLargerLabel.setEnabled(true);
            copyLargerModifiedCheckBox.setEnabled(true);
            copyLargerModifiedLabel.setEnabled(true);

            if (copyLargerCheckBox.isSelected() || copyModifiedCheckBox.isSelected()) {
                copyLargerModifiedCheckBox.setEnabled(false);
                copyLargerModifiedLabel.setEnabled(false);
            } else {
                copyLargerModifiedCheckBox.setEnabled(true);
                copyLargerModifiedLabel.setEnabled(true);
            }
        }

        if (fileIncludeField.getText().equals("")) {
            fileIncludeField.setText("*");
        }

        if (dirIncludeField.getText().equals("")) {
            dirIncludeField.setText("*");
        }
    }

    @Override
    protected Directory getDirFromGui(final boolean enabled) {
        final Directory dir = new Directory(enabled);

        dir.setName(nameField.getText());
        dir.setSrc(srcField.getText());
        dir.setDst(dstField.getText());
        dir.setLogfile(logField.getText());
        dir.setWithSubfolders(withSubfoldersCheckBox.isSelected());
        dir.setVerify(verifyCheckBox.isSelected());
        dir.setFileInclude(fileIncludeField.getText());
        dir.setFileExclude(fileExcludeField.getText());
        dir.setDirInclude(dirIncludeField.getText());
        dir.setDirExclude(dirExcludeField.getText());

        dir.setCopyAll(copyAllCheckBox.isSelected());
        dir.setCopyNew(copyNewCheckBox.isSelected());
        dir.setCopyModified(copyModifiedCheckBox.isSelected());
        dir.setCopyLarger(copyLargerCheckBox.isSelected());
        dir.setCopyLargerModified(copyLargerModifiedCheckBox.isSelected());

        dir.setDelFiles(deleteFilesCheckBox.isSelected());
        dir.setDelDirs(deleteDirsCheckBox.isSelected());

        checkDefaults();

        return dir;
    }

    @Override
    protected void globalResetConfig() {
        DirSync.getSync().setWriteTimestampBack(false);
        DirSync.getSync().setMaxTimestampDiff(0);
        DirSync.getSync().setSkipLinks(false);
        setGuiFromGlobal();
    }

    @Override
    protected void copyOptionsToAllDirs() {
        DirSync.getSync().copyOptionsToAllDirs(getDirFromGui(true));
    }

    @Override
    protected void copyOptionsToEnabledDirs() {
        DirSync.getSync().copyOptionsToEnabledDirs(getDirFromGui(true));
    }

    @Override
    protected void toolsSwapSrcDst() {
        dirEvent(); // read changes from GUI
        DirSync.getSync().swapSrcDst();
        setGuiFromDir(((Directory) DirSync.getSync().getDirs().get(lastSelectedIndex))); // display changes in GUI
    }

    @Override
    protected void browseConfigPath() {
        try {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (!optionsConfigPathField.getText().equals("")) {
                fileChooser.setCurrentDirectory(new File(optionsConfigPathField.getText()));
            }

            if (fileChooser.showOpenDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
                optionsConfigPathField.setText(fileChooser.getSelectedFile().getPath());
            }
        } catch (final Exception e) {
            throw new Error(e);
        }
    }

    @Override
    protected void optionsConfigPath() {
        optionsConfigPathField.setText(DirSync.getProperties().getProperty("dirsync.config.path", ""));
        if (JOptionPane.showOptionDialog(DirSync.getGui(), optionsConfigPathPanel, "Set default configuration path", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null) == JOptionPane.OK_OPTION) {
            DirSync.getProperties().setProperty("dirsync.config.path", optionsConfigPathField.getText());
            DirSync.saveProperties();
        }
    }

    @Override
    protected void optionsLookandfeel() {
        try {
            if (optionsLookandfeelCheckBoxMenuItem.isSelected()) {
                DirSync.getProperties().setProperty("dirsync.gui.systemlookandfeel", "false");
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } else {
                DirSync.getProperties().setProperty("dirsync.gui.systemlookandfeel", "true");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (final Exception e) {}
        SwingUtilities.updateComponentTreeUI(DirSync.getGui()); // repaint all
        // components
        DirSync.saveProperties();
    }

    @Override
    protected void optionsNIO() {
        try {
            if (optionsNIOCheckBoxMenuItem.isSelected()) {
                DirSync.getProperties().setProperty("dirsync.NIO", "true");
            } else {
                DirSync.getProperties().setProperty("dirsync.NIO", "false");
            }
        } catch (final Exception e) {}
        DirSync.saveProperties();
    }

    @Override
    protected void disableAllDirs() {
        enableAllDirs(false);
    }

    @Override
    protected void enableAllDirs() {
        enableAllDirs(true);
    }

    /**
     * Starts a preview.
     */
    @Override
    public void previewStart() {
        DirSync.getSync().setMode(Sync.PREVIEW);
        start();
    }

    /**
     * Starts a synchronization.
     */
    @Override
    public void synchronizationStart() {
        DirSync.getSync().setMode(Sync.SYNCHRONIZATION);
        start();
    }

    @Override
    protected void contents() {
        final javax.swing.JFrame help = new Help();
        help.setVisible(true);
    }

    @Override
    protected void dirSelected() {

        int selectedIndex = dirList.getSelectedIndex();

        if (selectedIndex == lastSelectedIndex) {
            return;
        }

        // get dir from gui only if gui update is not disabled
        if (isUpdateDirFromGui) {
            DirSync.getSync().getDirs().set(lastSelectedIndex, getDirFromGui(((Directory) DirSync.getSync().getDirs().get(lastSelectedIndex)).isEnabled()));
        }
        dirList.setListData(DirSync.getSync().getDirs());
        checkDefaults();

        if (selectedIndex == -1) { // nothing selected, use last selected
            selectedIndex = lastSelectedIndex;
        } else { // remember selected
            lastSelectedIndex = selectedIndex;
        }

        setGuiFromDir((Directory) DirSync.getSync().getDirs().elementAt(selectedIndex));
    }

    @Override
    protected void browseDst() {

        try {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (!dstField.getText().equals("")) {
                fileChooser.setCurrentDirectory(new File(dstField.getText()));
            }

            if (fileChooser.showOpenDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {

                dstField.setText(fileChooser.getSelectedFile().getPath());
            }

            dirEvent();

        } catch (final Exception e) {
            throw new Error(e);
        }

    }

    @Override
    protected void browseLog() {

        try {
            final JFileChooser fileChooser = new JFileChooser();

            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

            if (!logField.getText().equals("")) {
                final File logFile = new File(logField.getText());
                fileChooser.setCurrentDirectory(logFile);
                if (logFile.isFile()) {
                    fileChooser.setSelectedFile(logFile);
                }
            }

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                final File file = fileChooser.getSelectedFile();
                String filename = file.getPath();

                // if only a path was selected suggest a filename
                if (file.isDirectory()) {
                    // if the path doesn't end with an separator add one
                    if (!filename.endsWith(File.separator)) {
                        filename += File.separator;
                    }
                    filename += nameField.getText() + ".log";
                }

                logField.setText(filename);
            }

            dirEvent();

        } catch (final Exception e) {
            throw new Error(e);
        }

    }

    @Override
    protected void dirEvent() {
        final boolean enabled = ((Directory) DirSync.getSync().getDirs().get(lastSelectedIndex)).isEnabled();

        DirSync.getSync().getDirs().set(lastSelectedIndex, getDirFromGui(enabled));
        dirList.setListData(DirSync.getSync().getDirs());
        // dirList.setSelectedIndex(lastSelectedIndex); //TODO ERROR, Field not editable!!
        checkDefaults();
        getGlobalFromGui();
    }

    @Override
    protected void browseSrc() {

        try {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (!srcField.getText().equals("")) {
                fileChooser.setCurrentDirectory(new File(srcField.getText()));
            }

            if (fileChooser.showOpenDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {

                srcField.setText(fileChooser.getSelectedFile().getPath());
            }

            dirEvent();

        } catch (final Exception e) {
            throw new Error(e);
        }
    }

    @Override
    protected void listDown() {

        if (DirSync.getSync().getDirs().size() > 1 && lastSelectedIndex < DirSync.getSync().getDirs().size() - 1) {
            final Directory dir = (Directory) DirSync.getSync().getDirs().remove(lastSelectedIndex);
            lastSelectedIndex++;
            DirSync.getSync().getDirs().insertElementAt(dir, lastSelectedIndex);
        }
        dirList.setListData(DirSync.getSync().getDirs());
        dirList.setSelectedIndex(lastSelectedIndex);
    }

    @Override
    protected void listUp() {

        if (DirSync.getSync().getDirs().size() > 1 && lastSelectedIndex > 0) {
            final Directory dir = (Directory) DirSync.getSync().getDirs().remove(lastSelectedIndex);
            lastSelectedIndex--;
            DirSync.getSync().getDirs().insertElementAt(dir, lastSelectedIndex);
        }
        dirList.setListData(DirSync.getSync().getDirs());
        dirList.setSelectedIndex(lastSelectedIndex);
    }

    @Override
    protected void listRemove() {

        if (DirSync.getSync().getDirs().size() > 1) {
            DirSync.getSync().getDirs().remove(lastSelectedIndex);
        }

        if (lastSelectedIndex >= DirSync.getSync().getDirs().size()) {
            lastSelectedIndex = DirSync.getSync().getDirs().size() - 1;
        }

        // disable updating the dir from the gui
        isUpdateDirFromGui = false;
        dirList.setListData(DirSync.getSync().getDirs());
        isUpdateDirFromGui = true;

        dirList.setSelectedIndex(lastSelectedIndex);
        setGuiFromDir((Directory) DirSync.getSync().getDirs().elementAt(lastSelectedIndex));
    }

    @Override
    protected void listCopy() {
        final Directory dir = getDirFromGui(((Directory) DirSync.getSync().getDirs().get(lastSelectedIndex)).isEnabled());
        lastSelectedIndex++;

        DirSync.getSync().getDirs().insertElementAt(dir, lastSelectedIndex);

        dirList.setListData(DirSync.getSync().getDirs());
        dirList.setSelectedIndex(lastSelectedIndex);
    }

    @Override
    protected void listAdd() {

        final Directory dir = new Directory();
        dir.setName("New directory");
        DirSync.getSync().getDirs().add(dir);

        dirList.setListData(DirSync.getSync().getDirs());
        lastSelectedIndex = DirSync.getSync().getDirs().size() - 1;
        dirList.setSelectedIndex(lastSelectedIndex);

        setGuiFromDir(dir);
    }

    @Override
    protected void browseGlobalLog() {

        try {
            final JFileChooser fileChooser = new JFileChooser();

            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

            if (!globalLogField.getText().equals("")) {
                final File logFile = new File(globalLogField.getText());
                fileChooser.setCurrentDirectory(logFile);
                if (logFile.isFile()) {
                    fileChooser.setSelectedFile(logFile);
                }
            }

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                final File file = fileChooser.getSelectedFile();
                String filename = file.getPath();

                // if only a path was selected suggest a filename
                if (file.isDirectory()) {
                    // if the path doesn't end with an separator add one
                    if (!filename.endsWith(File.separator)) {
                        filename += File.separator;
                    }
                    filename += "DirSync.log";
                }

                globalLogField.setText(filename);
                getGlobalFromGui();
            }
        } catch (final Exception e) {
            throw new Error(e);
        }
    }

    @Override
    protected void about() {
        javax.swing.JOptionPane.showMessageDialog(null, Const.COPYRIGHT + "\n\n" + Const.MESSAGE, Const.VERSION, javax.swing.JOptionPane.PLAIN_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/icons/about.png")));
    }

    @Override
    protected void quit() {
        if (DirSync.getSync().getState() == Sync.STOP) {
            // if no sync is running
            System.exit(0); // quit program
        } else { // if a sync is running
            DirSync.setOption_quit(true); // set option to quit after sync
            stop(); // stop running sync
        }
    }

    @Override
    protected void exitForm() {
        quit();
    }

    protected void start() {

        if (DirSync.getSync().getState() == Sync.START || DirSync.getSync().getState() == Sync.PAUSE || DirSync.getSync().getState() == Sync.STOPPING) {
            return;
        }
        dirEvent();

        // init global log
        try {
            DirSync.setLog(new Log(globalLogField.getText())); // create global log
        } catch (final WarningException we) {
            globalLogField.setText(""); // disable global log
            try {
                DirSync.setLog(new Log("")); // disable global log
            } catch (final Exception e) {}
            DirSync.getLog().print("Error: " + we.getMessage() + " Global logging has been disabled.", Const.STYLE_ERROR);
            if (DirSync.getGui() != null) {
                Gui.displayErrorDialog(we.getMessage() + " Global logging has been disabled.");
            }
        } catch (final Exception e) {
            throw new Error(e);
        }

        DirSync.getSync().setState(Sync.START);

        final SwingWorker worker = new SwingWorker() {
            @Override
            public Object construct() {
                // switch to output pane
                tabbedPane.setSelectedIndex(0);

                // lock GUI
                setGuiEnabled(false);

                getGlobalFromGui();
                outputArea.setText("");
                DirSync.getSync().synchronize();

                // unlock GUI
                setGuiEnabled(true);

                return null;
            }
        };
        worker.start(); // required for SwingWorker 3
    }

    /**
     * Check if the GUI is enabled.
     * 
     * @return <code>true</code> if the GUI is enabled, <code>false</code>
     *         otherwise.
     */
    public boolean isGuiEnabled() {
        return isEnabled;
    }

    protected void setGuiEnabled(final boolean enabled) {
        isEnabled = enabled;

        globalLogLabel.setEnabled(enabled);
        globalLogField.setEnabled(enabled);
        globalLogButton.setEnabled(enabled);

        globalTimestampWriteBackCheckBox.setEnabled(enabled);
        globalTimestampDiffField.setEnabled(enabled);
        globalSymbolicLinkCopyRadioButton.setEnabled(enabled);
        globalSymbolicLinkSkipRadioButton.setEnabled(enabled);
        globalResetConfigButton.setEnabled(enabled);

        newButton.setEnabled(enabled);
        openButton.setEnabled(enabled);

        newMenuItem.setEnabled(enabled);
        openMenuItem.setEnabled(enabled);
        swapSrcDstMenuItem.setEnabled(enabled);

        enableAllDirsButton.setEnabled(enabled);
        disableAllDirsButton.setEnabled(enabled);

        listAddButton.setEnabled(enabled);
        listCopyButton.setEnabled(enabled);
        listRemoveButton.setEnabled(enabled);
        listUpButton.setEnabled(enabled);
        listDownButton.setEnabled(enabled);

        nameLabel.setEnabled(enabled);
        nameField.setEnabled(enabled);

        srcLabel.setEnabled(enabled);
        srcField.setEnabled(enabled);
        srcButton.setEnabled(enabled);

        dstLabel.setEnabled(enabled);
        dstField.setEnabled(enabled);
        dstButton.setEnabled(enabled);

        logLabel.setEnabled(enabled);
        logField.setEnabled(enabled);
        logButton.setEnabled(enabled);

        fileIncludeLabel.setEnabled(enabled);
        fileIncludeField.setEnabled(enabled);
        fileExcludeLabel.setEnabled(enabled);
        fileExcludeField.setEnabled(enabled);

        dirIncludeLabel.setEnabled(enabled);
        dirIncludeField.setEnabled(enabled);
        dirExcludeLabel.setEnabled(enabled);
        dirExcludeField.setEnabled(enabled);

        copyAllCheckBox.setEnabled(enabled);
        copyAllLabel.setEnabled(enabled);

        copyNewCheckBox.setEnabled(enabled);
        copyNewLabel.setEnabled(enabled);

        copyLargerCheckBox.setEnabled(enabled);
        copyLargerLabel.setEnabled(enabled);

        copyModifiedCheckBox.setEnabled(enabled);
        copyModifiedLabel.setEnabled(enabled);

        copyLargerModifiedCheckBox.setEnabled(enabled);
        copyLargerModifiedLabel.setEnabled(enabled);

        withSubfoldersCheckBox.setEnabled(enabled);
        withSubfoldersLabel.setEnabled(enabled);

        verifyCheckBox.setEnabled(enabled);
        verifyLabel.setEnabled(enabled);

        deleteFilesCheckBox.setEnabled(enabled);
        deleteFilesLabel.setEnabled(enabled);

        deleteDirsCheckBox.setEnabled(enabled);
        deleteDirsLabel.setEnabled(enabled);

        copyOptionsToAllDirsButton.setEnabled(enabled);
        copyOptionsToEnabledDirsButton.setEnabled(enabled);

        if (enabled) {
            checkDefaults();
        }
    }

    @Override
    protected void pause() {
        if (DirSync.getSync().getState() == Sync.STOP || DirSync.getSync().getState() == Sync.STOPPING) {
            pauseButton.setSelected(false);
            return;
        }

        if (DirSync.getSync().getState() == Sync.START) {
            DirSync.getSync().setState(Sync.PAUSE);
            pauseButton.setSelected(true);
            return;
        }

        if (DirSync.getSync().getState() == Sync.PAUSE) {
            DirSync.getSync().setState(Sync.START);
            pauseButton.setSelected(false);
            return;
        }
    }

    @Override
    protected void stop() {
        pauseButton.setSelected(false);

        if (DirSync.getSync().getState() == Sync.STOP) {
            return;
        }
        DirSync.getSync().setState(Sync.STOPPING);
    }

    protected void enableAllDirs(final boolean enabled) {
        DirSync.getSync().enableAllDirs(enabled);
        dirList.repaint();
    }

    protected void initOutputAreaStyles() {
        // Initialize some styles.
        Style s;

        final Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        s = outputArea.addStyle(Const.STYLE_SYNC, defaultStyle);
        StyleConstants.setForeground(s, new Color(0, 0, 128));
        StyleConstants.setBackground(s, new Color(255, 252, 211));

        s = outputArea.addStyle(Const.STYLE_DIR, defaultStyle);
        StyleConstants.setForeground(s, new Color(0, 0, 128));
        StyleConstants.setBackground(s, new Color(235, 234, 251));

        s = outputArea.addStyle(Const.STYLE_SUBDIR, defaultStyle);
        StyleConstants.setForeground(s, new Color(0, 128, 0));

        s = outputArea.addStyle(Const.STYLE_INFO, defaultStyle);
        StyleConstants.setForeground(s, new Color(0, 0, 128));

        s = outputArea.addStyle(Const.STYLE_CONFIG, defaultStyle);
        StyleConstants.setFontFamily(s, "Courier");

        s = outputArea.addStyle(Const.STYLE_ACTION, defaultStyle);
        StyleConstants.setForeground(s, new Color(127, 0, 85));

        s = outputArea.addStyle(Const.STYLE_WARNING, defaultStyle);
        StyleConstants.setForeground(s, new Color(255, 128, 0));

        s = outputArea.addStyle(Const.STYLE_ERROR, defaultStyle);
        // StyleConstants.setBold(s, true);
        StyleConstants.setForeground(s, Color.RED);
    }

    /**
     * In GUI mode the given message is printed in the output area.
     * 
     * @param message
     *            The message to print.
     * @param style
     *            The style to use for the message.
     */
    public void print(String message, final String style) {

        if (DirSync.isGuiMode()) {

            message = "  " + message;

            final Document doc = outputArea.getStyledDocument();
            if (doc != null) {
                try {
                    if (style == null) { // no style

                        // all not empty messages get the file icon
                        if (!message.equals("  ")) {
                            insertIcon(doc, Const.ICON_FILE);
                        }
                        // print message
                        doc.insertString(doc.getLength(), message + System.getProperty("line.separator"), null);

                    } else { // style

                        // set icon, info as default
                        if (style.equals(Const.STYLE_SYNC)) {
                            insertIcon(doc, Const.ICON_SYNC);
                        } else if (style.equals(Const.STYLE_DIR)) {
                            insertIcon(doc, Const.ICON_DIR);
                        } else if (style.equals(Const.STYLE_SUBDIR)) {
                            insertIcon(doc, Const.ICON_SUBDIR);
                        } else if (style.equals(Const.STYLE_CONFIG)) {
                            insertIcon(doc, Const.ICON_CONFIG);
                        } else if (style.equals(Const.STYLE_ACTION)) {
                            insertIcon(doc, Const.ICON_ACTION);
                        } else if (style.equals(Const.STYLE_WARNING)) {
                            insertIcon(doc, Const.ICON_WARNING);
                        } else if (style.equals(Const.STYLE_ERROR)) {
                            insertIcon(doc, Const.ICON_ERROR);
                        } else {
                            insertIcon(doc, Const.ICON_INFO);
                        }

                        // print message
                        doc.insertString(doc.getLength(), message + System.getProperty("line.separator"), DirSync.getGui().outputArea.getStyle(style));
                    }

                    // scroll output to last line (if checkbox "" is selected)
                    if (autoScrollCheckBox.isSelected()) {
                        outputArea.setCaretPosition(doc.getLength());
                    }
                } catch (final BadLocationException e) {
                    System.err.println("Could not append message '" + message + "' to output.");
                }
            }
        }
    }

    private void insertIcon(final Document doc, final ImageIcon icon) throws BadLocationException {
        StyleConstants.setIcon(outputArea.getInputAttributes(), icon);
        ((AbstractDocument) doc).replace(doc.getLength(), 0, " ", outputArea.getInputAttributes());
    }

    /**
     * Sets the current configuration.
     * 
     * @param currentConfig
     *            The currentConfig to set.
     */
    public void setCurrentConfig(final File currentConfig) {
        this.currentConfig = currentConfig;
    }

    /**
     * Sets the current directory name.
     * 
     * @param actDirName
     *            The actDirName to set.
     */
    public void setCurrentDirName(final String actDirName) {
        currentDirName = actDirName;
    }

    /**
     * Updates the frame title with configuration name and progress.
     */
    public void updateTitle() {
        if (DirSync.isGuiMode()) {
            String title = "";

            // progress of sync
            if (DirSync.getSync().getState() != Sync.STOP) {
                title += syncProgress.getString() + " ";
            }

            // current directory
            if (!currentDirName.equals("")) {
                title += currentDirName + " - ";
            }

            // filename of current config
            if (currentConfig != null) {
                title += currentConfig.getName() + " - ";
            }

            // title of program
            title += "DirSync";

            DirSync.getGui().setTitle(title);
        }
    }

    /**
     * Initialize the synchronization progress bar.
     * 
     * @param numberOfDirs
     *            The number of enabled directories in the synchronization.
     */
    public void initSyncProgress(final int numberOfDirs) {
        syncProgress.setMaximum(numberOfDirs * 100);
        syncProgress.setValue(0);
    }

    /**
     * Initialize the directory progress bar.
     */
    public void initDirProgress() {
        dirProgress.setMaximum(0);
        dirProgress.setValue(0);
    }

    /**
     * Initialize the current file/directory progress bar.
     */
    public void initCurrentProgress() {
        currentProgress.setString("");
    }

    /**
     * Set the indeterminate status of the synchronization progress bar.
     * 
     * @param indeterminate
     *            The indeterminate status of the synchronization progress bar.
     */
    public void setSyncProgressIndeterminate(final boolean indeterminate) {
        if (DirSync.isGuiMode()) {
            syncProgress.setIndeterminate(indeterminate);
        }
    }

    /**
     * Set the indeterminate status of the directory progress bar.
     * 
     * @param indeterminate
     *            The indeterminate status of the directory progress bar.
     */
    public void setDirProgressIndeterminate(final boolean indeterminate) {
        if (DirSync.isGuiMode()) {
            dirProgress.setIndeterminate(indeterminate);
        }
    }

    /**
     * Set the progress of the synchronization.
     * 
     * @param value
     *            The progress of the synchronization.
     */
    public void setSyncProgress(final int value) {
        if (DirSync.isGuiMode()) {
            syncProgress.setValue(value);
        }
    }

    /**
     * Set the progress of the synchronization.
     * 
     * @param value
     *            The progress of the synchronization.
     */
    public void setCurrentProgress(final int value) {
        if (DirSync.isGuiMode()) {
            currentProgress.setValue(value);
        }
    }

    /**
     * Set the maximum value for the directory progress bar.
     * 
     * @param n
     *            The maximum value for the directory progress bar.
     */
    public void setDirProgressMaximum(final int n) {
        if (DirSync.isGuiMode()) {
            dirProgress.setMaximum(n);
        }
    }

    /**
     * Set the text for the directory progress bar.
     * 
     * @param s
     *            The text for the directory progress bar.
     */
    public void setDirProgressText(final String s) {
        if (DirSync.isGuiMode()) {
            dirProgress.setString(s);
        }
    }

    /**
     * Set the text for the current file/directory progress bar.
     * 
     * @param s
     *            The text for the current file/directory progress bar.
     */
    public void setCurrentProgressText(final String s) {
        if (DirSync.isGuiMode()) {
            currentProgress.setString(s);
        }
    }

    /**
     * Set the maximum value for the current progress bar.
     * 
     * @param n
     *            The maximum value for the current progress bar.
     */
    public void setCurrentProgressMaximum(final int n) {
        if (DirSync.isGuiMode()) {
            currentProgress.setMaximum(n);
        }
    }

    /**
     * Set the progress of the synchronization and update the progress bars.
     * 
     * @param n
     *            The value of the progress.
     * @param filename
     *            The name of the current file/directory.
     */
    public void setProgress(final int n, final String filename) {

        if (dirProgress.getMaximum() > 0) {
            syncProgress.setValue((DirSync.getSync().getDirCounter() - 1) * 100 + (n * 100) / dirProgress.getMaximum());
        } else {
            syncProgress.setValue(DirSync.getSync().getDirCounter() * 100);
        }

        dirProgress.setValue(n);

        currentProgress.setString(filename);

        updateTitle();
    }
}
