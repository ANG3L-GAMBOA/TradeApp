package guia3;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

public class principal extends JFrame implements ActionListener {
	private JLabel lbl_titulo;
	private JButton btn_frm1, btn_frm2, btn_frm3, btn_frm4, btn_frm5, btn_cerrar;
	
	public principal() {
        super();
        Configurar();
        Controles();
    }
	
	private void Configurar() {
		this.setTitle("SENATI");
		this.setSize(400, 250);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void Controles() {
        lbl_titulo = new JLabel();
        lbl_titulo.setText("TradeApp");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 22));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(10, 10, 300, 25);
        
        btn_frm1 = new JButton();
        btn_frm1.setText("Usuarios");
        btn_frm1.setBounds(50, 60, 100, 30);
        btn_frm1.addActionListener(this);
        
        btn_frm2 = new JButton();
        btn_frm2.setText("Productos");
        btn_frm2.setBounds(50, 110, 100, 30);
        btn_frm2.addActionListener(this);
        
        btn_frm3 = new JButton();
        btn_frm3.setText("Categorias");
        btn_frm3.setBounds(250, 110, 100, 30);
        btn_frm3.addActionListener(this);
        
        btn_frm4 = new JButton();
        btn_frm4.setText("Marcas");
        btn_frm4.setBounds(250, 60, 100, 30);
        btn_frm4.addActionListener(this);
        
        btn_frm5 = new JButton();
        btn_frm5.setText("Ordenes");
        btn_frm5.setBounds(150, 160, 100, 30);
        btn_frm5.addActionListener(this);
        
        btn_cerrar = new JButton();
        btn_cerrar.setText("CERRAR");
        btn_cerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btn_cerrar.setBackground(Color.RED);
        btn_cerrar.setForeground(Color.WHITE);
        btn_cerrar.setBounds(300, 190, 90, 25);
        btn_cerrar.addActionListener(this);
        
        this.add(lbl_titulo);
        this.add(btn_frm1);
        this.add(btn_frm2);
        this.add(btn_frm3);
        this.add(btn_frm4);
        this.add(btn_frm5);
        this.add(btn_cerrar);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == btn_frm1) {
	    	
	        // Acciones para el botón btn_frm1
	    	viewUsuarios viewusuarios = new viewUsuarios();
	    	viewusuarios.setVisible(true);
	        
	    } else if (e.getSource() == btn_frm2) {
	        // Acciones para el botón btn_frm2
	    	
	    	viewProducto viewproducto = new viewProducto();
	    	viewproducto.setVisible(true);
	    } else if (e.getSource() == btn_frm3) {
	        // Acciones para el botón btn_frm3
	    	
	    	viewCategoria viewcategoria = new viewCategoria();
	    	viewcategoria.setVisible(true);
	    } else if (e.getSource() == btn_frm4) {
	        // Acciones para el botón btn_frm4
	    	viewMarcas viewmarcas = new viewMarcas();
	    	viewmarcas.setVisible(true);
	    } else if (e.getSource() == btn_frm5) {
	        // Acciones para el botón btn_frm5
	    	viewOrdenes viewordenes = new viewOrdenes();
	    	viewordenes.setVisible(true);
	    } else if (e.getSource() == btn_cerrar) {
	        System.exit(0);
	    }
	}
	
	public static void main(String[] args) {
		principal principal = new principal();
		principal.setVisible(true);
	}

}
