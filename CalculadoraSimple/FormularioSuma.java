//clase que contiene la interfaz grafica

//Importacion de librerias
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioSuma extends JFrame {
    private JTextField txtNumero1;
    private JTextField txtNumero2;
    private JButton btnSumar;
    private JButton btnRestar;
    private JButton btnMultiplicar;
    private JButton btnDividir;
    private JLabel lblResultado;
    
    private Calculadora calculadora;
    
    //Constructor
    public FormularioSuma(){
        calculadora = new Calculadora();
        
        setTitle("Calculadora Simple");
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        
        Rectangle rc;
        
        int padding = 10;
    
        txtNumero1 = new JTextField();
        txtNumero1.setBounds(50, 30, 90, 25);
        add(txtNumero1);
        
        rc = txtNumero1.getBounds();
        
        txtNumero2 = new JTextField();
        txtNumero2.setBounds(rc.x+rc.width+padding, rc.y, rc.width, rc.height);
        add(txtNumero2);
        
        rc = txtNumero1.getBounds();
        
        btnSumar = new JButton("Sumar");
        btnSumar.setBounds(rc.x, rc.y+rc.height+padding, (rc.width*2)+padding, rc.height);
        add(btnSumar);
        
        rc = btnSumar.getBounds();
        
        btnRestar = new JButton("Restar");
        btnRestar.setBounds(rc.x, rc.y+rc.height+padding, rc.width, rc.height);
        add(btnRestar);
        
        rc = btnRestar.getBounds();
        
        btnMultiplicar = new JButton("Multiplicar");
        btnMultiplicar.setBounds(rc.x, rc.y+rc.height+padding, rc.width, rc.height);
        add(btnMultiplicar);
        
        rc = btnMultiplicar.getBounds();
        
        btnDividir = new JButton("Dividir");
        btnDividir.setBounds(rc.x, rc.y+rc.height+padding, rc.width, rc.height);
        add(btnDividir);
        
        rc = btnDividir.getBounds();
        
        lblResultado = new JLabel("Resultado:");
        lblResultado.setBounds(rc.x, rc.y+rc.height+padding, rc.width, rc.height);
        add(lblResultado);
        
        btnSumar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int numero1 = Integer.parseInt(txtNumero1.getText());
                int numero2 = Integer.parseInt(txtNumero2.getText());
                
                int resultado = calculadora.sumar(numero1, numero2);
                
                lblResultado.setText("Resultado: " + resultado);
            }
        });
        
        btnRestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int numero1 = Integer.parseInt(txtNumero1.getText());
                int numero2 = Integer.parseInt(txtNumero2.getText());
                
                int resultado = calculadora.restar(numero1, numero2);
                
                lblResultado.setText("Resultado: " + resultado);
            }
        });
                
        btnMultiplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int numero1 = Integer.parseInt(txtNumero1.getText());
                int numero2 = Integer.parseInt(txtNumero2.getText());
                
                int resultado = calculadora.multiplicar(numero1, numero2); 
                
                lblResultado.setText("Resultado: " + resultado);
            }
        });
        
        btnDividir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int numero1 = Integer.parseInt(txtNumero1.getText());
                int numero2 = Integer.parseInt(txtNumero2.getText());
                
                int resultado = calculadora.dividir(numero1, numero2);
                
                lblResultado.setText("Resultado: " + resultado);
            }
        });
        
    }
}
