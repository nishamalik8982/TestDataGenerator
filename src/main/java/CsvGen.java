import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvGen
{
    public static void main ( String[] args )
    {
        CsvGen app = new CsvGen();

        // Write out the data in a JTable to standard CSV file: row, col
        int row = Integer.parseInt(args[0]);
        int col = Integer.parseInt(args[1]);
        JTable jtable = app.newJTable(row, col);
        app.writeCSV( jtable.getModel(), args[2] );

        System.out.println( "« done »" );
    }

    private void writeCSV ( final TableModel model, String file )
    {
        CSVFormat format = CSVFormat.RFC4180;
        Path path = Paths.get( file );
        try (
                BufferedWriter writer = Files.newBufferedWriter( path , StandardCharsets.UTF_8 ) ;
                CSVPrinter printer = new CSVPrinter( writer , format ) ;
        )
        {
            // Print column headers, if you want.
            for ( int columnIndex = 0 ; columnIndex < model.getColumnCount() ; columnIndex++ )
            {
                printer.print( model.getColumnName( columnIndex ) );
            }
            printer.println();

            // Print rows.
            for ( int rowIndex = 0 ; rowIndex < model.getRowCount() ; rowIndex++ )
            {
                for ( int columnIndex = 0 ; columnIndex < model.getColumnCount() ; columnIndex++ )
                {
                    printer.print( model.getValueAt( rowIndex , columnIndex ) );
                }
                printer.println();
            }
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    private JTable newJTable (int row, int col)
    {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] columnNames = {"BookingDateTime","BookingRef","SupplierName","Currency","NetAmount",
                                "Adults","Children","Infants","DepartureDate","NumberNights",
                                "RoomConfirmation","Invoice Type","BookingStatus"};
        Object[][] data = new Object[row][col];
        List<Object> x = new ArrayList<Object>();
        for (int i=0; i < row; i++) {
            for (int j=0; j < col; j++) {
                data[i][j] = getData(i, j);
            }
        }
        JTable table = new JTable(data, columnNames );
        return table;
    }

    private String getData(int row, int col) {
        switch (col+1) {
            case (1):
                return "2021-09-22 09:09:13";
            case (2):
                return "P10171030" + row;
            case (3):
                return "Webbeds";
            case (4):
                return "AUD";
            case (5):
            case (7):
            case (8):
                return "0";
            case (6):
                return "2";
            case (9):
                return "09/10/2021";
            case (10):
                return "1";
            case (11):
                return "SH10365039";
            case (12):
                return "supplier_invoice";
            case (13):
                return "cancelled";
        }
        return "";
    }
}