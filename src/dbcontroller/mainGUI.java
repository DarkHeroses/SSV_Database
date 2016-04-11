package dbcontroller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Hermann on 11.04.2016.
 */
public class mainGUI {
    private JPanel mainPanel;
    private JTable table1;
    private DefaultTableModel model;
    private JButton loadButton;

    private DBController dbController;

    public mainGUI() {
        dbController = DBController.getInstance();
        model = new DefaultTableModel();
        table1.setModel(model);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet rs = dbController.loadPerson();
                    /*
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columns = rsmd.getColumnCount();
                    while (rs.next()) {
                        Vector v = new Vector();
                        for(int i = 1; i <= columns; i++) {
                            v.add(rs.getObject(i));
                        }
                        model.addRow(v);
                    }*/
                    model = buildTableModel(rs);
                    table1.setModel(model);
                    model.fireTableDataChanged();
                } catch (Exception exc) {
                    // other stuff
                }
            }
        });
    }

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 2; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 2; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("mainGUI");
        frame.setContentPane(new mainGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
