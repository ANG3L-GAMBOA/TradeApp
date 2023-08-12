package guia3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import clases.Personal;
import conexion.ConexionSQL;
import conexion.ConexionMySQL;

public class viewUsuarios extends JFrame implements ActionListener {
	private JLabel lbl_titulo;
    private JLabel lbl1, lbl2, lbl3, lbl4, lbl5;

    private JTextField txt_dni, txt_nombre, txt_ap_paterno, txt_ap_materno;
    
    private JComboBox cbo_genero;

    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;

    private JTable tb;
    private JScrollPane scp;
    
    private ConexionMySQL cn = new ConexionMySQL();
    // private ConexionMySQL cn = new ConexionMySQL();
    
    // Constructor
    public viewUsuarios() {
        super();
        ConfigurarVentana();
        IniciarControles();
        MostrarDatos();
    }
    
    private void ConfigurarVentana() {
        this.setTitle("SENATI - ETI");
        this.setSize(430, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void IniciarControles() {
        lbl_titulo = new JLabel();
        lbl_titulo.setText("USUARIO");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 20));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(10, 10, 300, 25);

        lbl1 = new JLabel();
        lbl1.setText("Número DNI");
        lbl1.setBounds(10, 40, 120, 25);

        txt_dni = new JTextField();
        txt_dni.setBounds(130, 40, 80, 25);

        lbl2 = new JLabel();
        lbl2.setText("Nombre");
        lbl2.setBounds(10, 70, 120, 25);

        txt_nombre = new JTextField();
        txt_nombre.setBounds(130, 70, 150, 25);

        lbl3 = new JLabel();
        lbl3.setText("Apellido Paterno");
        lbl3.setBounds(10, 100, 150, 25);

        txt_ap_paterno = new JTextField();
        txt_ap_paterno.setBounds(130, 100, 150, 25);
        
        lbl4 = new JLabel();
        lbl4.setText("Apellido Materno");
        lbl4.setBounds(10, 130, 150, 25);

        txt_ap_materno = new JTextField();
        txt_ap_materno.setBounds(130, 130, 150, 25);

        lbl5 = new JLabel();
        lbl5.setText("Género");
        lbl5.setBounds(10, 160, 120, 25);
        
        String[] arr_genero = {"Masculino", "Femenino"};

        cbo_genero = new JComboBox();
        cbo_genero.setBounds(130, 160, 120, 25);

        for (String genero : arr_genero) {
            cbo_genero.addItem(genero);
        }
        
        cbo_genero.setSelectedIndex(-1);

        btn_nuevo = new JButton();
        btn_nuevo.setText("NUEVO");
        btn_nuevo.setBounds(10, 200, 90, 25);
        btn_nuevo.addActionListener(this);

        btn_agregar = new JButton();
        btn_agregar.setText("AGREGAR");
        btn_agregar.setBounds(110, 200, 90, 25);
        btn_agregar.addActionListener(this);

        btn_editar = new JButton();
        btn_editar.setText("EDITAR");
        btn_editar.setBounds(210, 200, 90, 25);
        btn_editar.addActionListener(this);

        btn_borrar = new JButton();
        btn_borrar.setText("BORRAR");
        btn_borrar.setBounds(310, 200, 90, 25);
        btn_borrar.addActionListener(this);

        btn_cerrar = new JButton();
        btn_cerrar.setText("CERRAR");
        btn_cerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btn_cerrar.setBackground(Color.RED);
        btn_cerrar.setForeground(Color.WHITE);
        btn_cerrar.setBounds(110, 420, 180, 25);
        btn_cerrar.addActionListener(this);

        tb = new JTable();

        tb.setRowHeight(20);
        tb.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        scp = new JScrollPane(tb);
        scp.setBounds(10, 250, 390, 150);

        // Agregar los controles al JFrame
        this.add(lbl_titulo);
        this.add(lbl1);
        this.add(txt_dni);
        this.add(lbl2);
        this.add(txt_nombre);
        this.add(lbl3);
        this.add(txt_ap_paterno);
        this.add(lbl4);
        this.add(txt_ap_materno);
        this.add(lbl5);
        this.add(cbo_genero);
        this.add(btn_nuevo);
        this.add(btn_agregar);
        this.add(btn_editar);
        this.add(btn_borrar);
        this.add(btn_cerrar);
        this.add(scp);

        ControladorTxt ctxt = new ControladorTxt();

        txt_dni.addKeyListener(ctxt);
        txt_nombre.addKeyListener(ctxt);
        txt_ap_paterno.addKeyListener(ctxt);

        ControladorClick click = new ControladorClick();

        tb.addMouseListener(click);

        ActivarBtn(true, true, false, false);
    }
    
    private void MostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setRowCount(0);

        Connection cnx = null;
        Statement stm = null;
        ResultSet rs = null;

        try {
            cnx = cn.Conectar();
            stm = cnx.createStatement();
            rs = stm.executeQuery("select * from tb_personal order by ap_paterno asc");
            //rs = stm.executeQuery("sp_ListarPersonal");

            int nc = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= nc; i++) {
                modelo.addColumn(rs.getMetaData().getColumnName(i));
            }

            while (rs.next()) {
                Object[] filas = new Object[nc];

                for (int i = 0; i < nc; i++) {
                    filas[i] = rs.getObject(i + 1);
                }

                modelo.addRow(filas);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stm != null) {
                    stm.close();
                }

                if (cnx != null) {
                    cnx.close();
                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        tb.setModel(modelo);

    }

    private class ControladorTxt implements KeyListener {

        public void keyTyped(KeyEvent e) {
            int limite = 0;

            if (e.getSource() == txt_dni && txt_dni.getText().length() == 8) {
                e.consume();
            } else if (e.getSource() == txt_nombre && txt_nombre.getText().length() == 20) {
                e.consume();
            } else if (e.getSource() == txt_ap_paterno && txt_ap_paterno.getText().length() == 20) {
                e.consume();
            } else if (e.getSource() == txt_ap_materno && txt_ap_materno.getText().length() == 20) {
                e.consume();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class ControladorClick extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            ActivarBtn(true, false, true, true);

            int registro = tb.getSelectedRow();

            if (registro == -1) {
                JOptionPane.showMessageDialog(null, "Persona no Elegida");
            }
            else {
                txt_dni.setEditable(false);

                String dni = (String) tb.getValueAt(registro, 0);
                String nom = (String) tb.getValueAt(registro, 1);
                String app = (String) tb.getValueAt(registro, 2);
                String apm = (String) tb.getValueAt(registro, 3);
                String gen = (String) tb.getValueAt(registro, 4);

                gen = gen.equals("M") ? "Masculino" : "Femenino";

                txt_dni.setText(dni);
                txt_nombre.setText(nom);
                txt_ap_paterno.setText(app);
                txt_ap_materno.setText(apm);
                cbo_genero.setSelectedItem(gen);
            }
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_cerrar) {
            // System.exit(0);
			dispose();
			return;
        }

        if (e.getSource() == btn_nuevo) {
            LimpiarDatos();

            ActivarBtn(true, true, false, false);

        } else {

            if (txt_dni.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Falta ingresar número de DNI");
                txt_dni.requestFocus();
                return;
            }

            if (txt_nombre.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Falta ingresar nombre");
                txt_nombre.requestFocus();
                return;
            }

            if (txt_ap_paterno.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Falta ingresar apellido paterno");
                txt_ap_paterno.requestFocus();
                return;
            }

            if (txt_ap_materno.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Falta ingresar apellido materno");
                txt_ap_materno.requestFocus();
                return;
            }

            if (cbo_genero.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta elegir género");
                cbo_genero.requestFocus();
                return;
            }
            
            Personal personal = new Personal();

            personal.setDni(txt_dni.getText());
            personal.setNombre(txt_nombre.getText());
            personal.setAp_paterno(txt_ap_paterno.getText());
            personal.setAp_materno(txt_ap_materno.getText());
            personal.setGenero(cbo_genero.getSelectedItem().toString().charAt(0));

            Connection cnx = null;
            java.sql.PreparedStatement pstm = null;

            try {
                cnx = cn.Conectar();

                String sql = "";

                if (e.getSource() == btn_agregar) {

                    sql = "insert into tb_personal values (?, ?, ?, ?, ?)";

                    pstm = cnx.prepareStatement(sql);

                    pstm.setString(1, personal.getDni());
                    pstm.setString(2, personal.getNombre());
                    pstm.setString(3, personal.getAp_paterno());
                    pstm.setString(4, personal.getAp_materno());
                    pstm.setString(5, Character.toString(personal.getGenero()));

                    pstm.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Registro Guardado");

                } else if (e.getSource() == btn_editar) {

                    sql = "update tb_personal set nombre = ?, ap_paterno = ?, ap_materno = ?, genero = ? where dni = ?";

                    pstm = cnx.prepareStatement(sql);
                    
                    pstm.setString(1, personal.getNombre());
                    pstm.setString(2, personal.getAp_paterno());
                    pstm.setString(3, personal.getAp_materno());
                    pstm.setString(4, Character.toString(personal.getGenero()));
                    pstm.setString(5, personal.getDni());

                    pstm.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Registro Actualizado");

                } else if (e.getSource() == btn_borrar) {

                    int opc = JOptionPane.showConfirmDialog(null, "¿Seguro de borrar el registro?", "SENATI", JOptionPane.YES_NO_OPTION);

                    if (opc == JOptionPane.YES_OPTION) {

                        sql = "delete from tb_personal where dni = ?";

                        pstm = cnx.prepareStatement(sql);

                        pstm.setString(1, personal.getDni());

                        pstm.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    }
                }

                MostrarDatos();

                ActivarBtn(true, true, false, false);

            } catch (SQLException e1) {
                e1.printStackTrace();

            } finally {
                try {
                    if (pstm != null) {
                        pstm.close();
                    }

                    if (cnx != null) {
                        cnx.close();
                    }

                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            LimpiarDatos();
        }
	}
	
	private void LimpiarDatos() {
        txt_dni.setEditable(true);

        tb.clearSelection();

        JTextField txt;

        for (int i = 0; i < this.getContentPane().getComponents().length; i++) {
            if (this.getContentPane().getComponent(i) instanceof JTextField) {
                txt = (JTextField) this.getContentPane().getComponent(i);
                txt.setText("");
            }
        }

        txt_dni.requestFocus();
    }

    private void ActivarBtn(boolean b1, boolean b2, boolean b3, boolean b4) {
        btn_nuevo.setEnabled(b1);
        btn_agregar.setEnabled(b2);
        btn_editar.setEnabled(b3);
        btn_borrar.setEnabled(b4);
    }
    
	public static void main(String[] args) {
		viewUsuarios ventas = new viewUsuarios();
		ventas.setVisible(true);
	}

}
