import java.awt.Color;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.sql.ResultSet;

public class Interface extends JFrame implements ChangeListener{
    private JTextField txtNombre, txtNota1, txtNota2, txtNota3, txtPromedio;
    private JButton btnCalcular, btnGuardar, btnConsultar;
    private JCheckBox chkCalcular, chkGuardar, chkConsultar;
    private DefaultTableModel tableModel;
    private JTable table;
    
    public Interface() {
        
        setTitle("Calcular y Guardar Promedio");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        //Cambiar color
        getContentPane().setBackground(Color.blue);
        
        // Etiquetas y Campos de Texto
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(150, 30, 200, 25);
        add(txtNombre);

        JLabel lblNota1 = new JLabel("Nota 1:");
        lblNota1.setBounds(30, 70, 100, 25);
        add(lblNota1);

        txtNota1 = new JTextField();
        txtNota1.setBounds(150, 70, 200, 25);
        add(txtNota1);

        JLabel lblNota2 = new JLabel("Nota 2:");
        lblNota2.setBounds(30, 110, 100, 25);
        add(lblNota2);

        txtNota2 = new JTextField();
        txtNota2.setBounds(150, 110, 200, 25);
        add(txtNota2);

        JLabel lblNota3 = new JLabel("Nota 3:");
        lblNota3.setBounds(30, 150, 100, 25);
        add(lblNota3);

        txtNota3 = new JTextField();
        txtNota3.setBounds(150, 150, 200, 25);
        add(txtNota3);

        JLabel lblPromedio = new JLabel("Promedio:");
        lblPromedio.setBounds(30, 190, 100, 25);
        add(lblPromedio);

        txtPromedio = new JTextField();
        txtPromedio.setBounds(150, 190, 200, 25);
        txtPromedio.setEditable(false);
        add(txtPromedio);

        // Botón Calcular
        btnCalcular = new JButton("Calcular");
        btnCalcular.setBounds(30, 230, 100, 25);
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double nota1 = Double.parseDouble(txtNota1.getText().replace(",", "."));
                    double nota2 = Double.parseDouble(txtNota2.getText().replace(",", "."));
                    double nota3 = Double.parseDouble(txtNota3.getText().replace(",", "."));

                    double promedio = (nota1 + nota2 + nota3) / 3;
                    txtPromedio.setText(String.format("%.2f", promedio));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos para las notas.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnCalcular);
        
        //checkbox calcular
        chkCalcular = new JCheckBox();
        chkCalcular.setBounds(140, 230, 25, 25);
        chkCalcular.addChangeListener(this);
        add(chkCalcular);

        // Botón Guardar
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(180, 230, 100, 25);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    
                    String nombre = txtNombre.getText();
                    if (nombre.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String promedioText = txtPromedio.getText().replace(",", ".");
                    if (promedioText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El promedio no está calculado.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double nota1 = Double.parseDouble(txtNota1.getText().replace(",", "."));
                    double nota2 = Double.parseDouble(txtNota2.getText().replace(",", "."));
                    double nota3 = Double.parseDouble(txtNota3.getText().replace(",", "."));
                    double promedio = Double.parseDouble(promedioText);
                    
                    guardarEnBaseDeDatos(nombre, nota1, nota2, nota3, promedio);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Formato de promedio inválido. Por favor, verifique.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnGuardar);
        
        
        //checkbox guardar
        chkGuardar = new JCheckBox();
        chkGuardar.setBounds(290, 230, 25, 25);
        chkGuardar.addChangeListener(this);
        add(chkGuardar);
        
        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(335, 230, 150, 25);
        btnConsultar.addActionListener( e-> {
            try{
                cargarDatos();
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error al consultar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        add(btnConsultar);
        
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(30, 270, 600, 200);
        add(scrollpane);
        
        
        //checkbox consultar
        chkConsultar = new JCheckBox();
        chkConsultar.setBounds(495, 230, 25, 25);
        chkConsultar.addChangeListener(this);
        add(chkConsultar);
        
        
    }
    
    public void stateChanged(ChangeEvent e){
        if(chkCalcular.isSelected()){
            btnCalcular.doClick();
            chkCalcular.setSelected(false);
        } else if(chkGuardar.isSelected()){
            btnGuardar.doClick();
            chkGuardar.setSelected(false);
        } else if(chkConsultar.isSelected()){
            btnConsultar.doClick();
            chkConsultar.setSelected(false);
        }
    }

    private void guardarEnBaseDeDatos(String nombre, double nota1, double nota2, double nota3, double promedio) {
        String url = "jdbc:mysql://localhost:3306/alumnos";
        String user = "developer2";
        String password = "Developer+2025";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO estudiantes (nombre, nota1, nota2, nota3, promedio) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nombre);
            stmt.setDouble(2, nota1);
            stmt.setDouble(3, nota2);
            stmt.setDouble(4, nota3);
            stmt.setDouble(5, promedio);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Datos guardados exitosamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }
    
    private void cargarDatos() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/alumnos";
        String user = "developer2";
        String password = "Developer+2025";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)){
            String query = "SELECT id, nombre, nota1, nota2, nota3, promedio FROM estudiantes;";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            tableModel.setRowCount(0);
            tableModel.setColumnIdentifiers(new String[] {"ID", "NOMBRE", "Nota1", "Nota2", "Nota3", "Promedio"});
            
            while(rs.next()){
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("nombre"));
                row.add(rs.getDouble("nota1"));
                row.add(rs.getDouble("nota2"));
                row.add(rs.getDouble("nota3"));
                row.add(rs.getDouble("promedio"));
                tableModel.addRow(row);
                
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
