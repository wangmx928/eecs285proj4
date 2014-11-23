package eecs285.proj4;

import java.awt.Component;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class FileListCellRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList<?> list,
                                 Object value,
                                 int index,
                                 boolean isSelected,
                                 boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof File) {
            File listFile = (File)value;
            setText(listFile.getName().substring(0, listFile.getName().length() - 4));
        }
        return this;
    }
}