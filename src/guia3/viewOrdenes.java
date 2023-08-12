package guia3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import clases.Orden;
import conexion.ConexionMySQL;

public class viewOrdenes extends JFrame implements ActionListener {
	private JLabel lbl_titulo;
    private JLabel lbl1, lbl2, lbl3, lbl4;

    private JTextField txt_codigo, txt_cantidad;
    private JComboBox txt_producto, txt_usuario;

    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;

    private JTable tb;
    private JScrollPane scp;
    
    private HashMap<String, String> productos = new HashMap<String, String>();
    private HashMap<String, String> usuarios = new HashMap<String, String>();
    
    private ConexionMySQL cn = new ConexionMySQL();
    // private ConexionMySQL cn = new ConexionMySQL();
    
    // Constructor
    public viewOrdenes() {
        super();
        CargarProductos();
        CargarUsuarios();
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
        lbl_titulo.setText("Órdenes");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 20));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(10, 10, 300, 25);

        lbl1 = new JLabel();
        lbl1.setText("Código");
        lbl1.setBounds(10, 40, 120, 25);

        txt_codigo = new JTextField();
        txt_codigo.setBounds(130, 40, 80, 25);

        lbl2 = new JLabel();
        lbl2.setText("Producto");
        lbl2.setBounds(10, 70, 120, 25);

        txt_producto = new JComboBox();
        txt_producto.setBounds(130, 70, 150, 25);
        
        for (String prod : productos.keySet()) {
            txt_producto.addItem(prod);
        }
        
        txt_producto.setSelectedIndex(-1);
        
        lbl3 = new JLabel();
        lbl3.setText("Usuario");
        lbl3.setBounds(10, 100, 150, 25);

        txt_usuario = new JComboBox();
        txt_usuario.setBounds(130, 100, 150, 25);
        
        for (String user : usuarios.keySet()) {
            txt_usuario.addItem(user);
        }
        
        txt_usuario.setSelectedIndex(-1);
        
        lbl4 = new JLabel();
        lbl4.setText("Cantidad");
        lbl4.setBounds(10, 130, 150, 25);

        txt_cantidad = new JTextField();
        txt_cantidad.setBounds(130, 130, 120, 25);

        btn_nuevo = new JButton();
        btn_nuevo.setText("NUEVO");
        btn_nuevo.setBounds(10, 160, 90, 25);
        btn_nuevo.addActionListener(this);

        btn_agregar = new JButton();
        btn_agregar.setText("AGREGAR");
        btn_agregar.setBounds(110, 160, 90, 25);
        btn_agregar.addActionListener(this);

        btn_editar = new JButton();
        btn_editar.setText("EDITAR");
        btn_editar.setBounds(210, 160, 90, 25);
        btn_editar.addActionListener(this);

        btn_borrar = new JButton();
        btn_borrar.setText("BORRAR");
        btn_borrar.setBounds(310, 160, 90, 25);
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
        scp.setBounds(10, 200, 390, 150);

        // Agregar los controles al JFrame
        this.add(lbl_titulo);
        this.add(lbl1);
        this.add(txt_codigo);
        this.add(lbl2);
        this.add(txt_producto);
        this.add(lbl3);
        this.add(txt_usuario);
        this.add(lbl4);
        this.add(txt_cantidad);
        this.add(btn_nuevo);
        this.add(btn_agregar);
        this.add(btn_editar);
        this.add(btn_borrar);
        this.add(btn_cerrar);
        this.add(scp);

        ControladorTxt ctxt = new ControladorTxt();

        txt_codigo.addKeyListener(ctxt);
        txt_codigo.addKeyListener(ctxt);

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
            rs = stm.executeQuery("call sp_listar_ordenes()");

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

            if (e.getSource() == txt_codigo && txt_codigo.getText().length() == 20) {
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
                JOptionPane.showMessageDialog(null, "Orden no Elegida");
            }
            else {
                txt_codigo.setEditable(false);

                String cod = (String) tb.getValueAt(registro, 0);
                String prod = (String) tb.getValueAt(registro, 1);
                String user = (String) tb.getValueAt(registro, 2);
                String cant = (String) tb.getValueAt(registro, 3);
                
                // prod = productos.get(prod);
                // user = usuarios.get(user);

                txt_codigo.setText(cod);
                txt_producto.setSelectedItem(prod);
                txt_usuario.setSelectedItem(user);
                txt_cantidad.setText(cant);
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

            if (txt_codigo.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Falta ingresar el código");
                txt_codigo.requestFocus();
                return;
            }

            if (txt_producto.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta ingresar producto");
                txt_producto.requestFocus();
                return;
            }
            
            if (txt_usuario.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Falta ingresar usuario");
                txt_usuario.requestFocus();
                return;
            }
            
            if (txt_cantidad.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Falta ingresar cantidad");
                txt_cantidad.requestFocus();
                return;
            }
            
            Orden orden = new Orden();

            orden.setCodigo(txt_codigo.getText());
            orden.setProducto(productos.get(txt_producto.getSelectedItem().toString()));
            orden.setUsuario(usuarios.get(txt_usuario.getSelectedItem().toString()));
            orden.setCantidad(txt_cantidad.getText());
            
            Connection cnx = null;
            java.sql.PreparedStatement pstm = null;

            try {
                cnx = cn.Conectar();

                String sql = "";

                if (e.getSource() == btn_agregar) {

                    sql = "insert into tb_orden values (?, ?, ?, ?)";

                    pstm = cnx.prepareStatement(sql);

                    pstm.setString(1, orden.getCodigo());
                    pstm.setString(2, orden.getProducto());
                    pstm.setString(3, orden.getUsuario());
                    pstm.setString(4, orden.getCantidad());
                    
                    pstm.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Registro Guardado");

                } else if (e.getSource() == btn_editar) {

                    sql = "update tb_orden set id_producto = ?, id_usuario = ?, cantidad = ? where id_orden = ?";

                    pstm = cnx.prepareStatement(sql);
                    
                    pstm.setString(1, orden.getProducto());
                    pstm.setString(2, orden.getUsuario());
                    pstm.setString(3, orden.getCantidad());
                    pstm.setString(4, orden.getCodigo());

                    pstm.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Registro Actualizado");

                } else if (e.getSource() == btn_borrar) {

                    int opc = JOptionPane.showConfirmDialog(null, "¿Seguro de borrar el registro?", "SENATI", JOptionPane.YES_NO_OPTION);

                    if (opc == JOptionPane.YES_OPTION) {

                        sql = "delete from tb_orden where id_orden = ?";

                        pstm = cnx.prepareStatement(sql);

                        pstm.setString(1, orden.getCodigo());

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
        txt_codigo.setEditable(true);

        tb.clearSelection();

        JTextField txt;

        for (int i = 0; i < this.getContentPane().getComponents().length; i++) {
            if (this.getContentPane().getComponent(i) instanceof JTextField) {
                txt = (JTextField) this.getContentPane().getComponent(i);
                txt.setText("");
            }
        }

        txt_codigo.requestFocus();
    }

    private void ActivarBtn(boolean b1, boolean b2, boolean b3, boolean b4) {
        btn_nuevo.setEnabled(b1);
        btn_agregar.setEnabled(b2);
        btn_editar.setEnabled(b3);
        btn_borrar.setEnabled(b4);
    }
    
    private void CargarProductos() {
    	 Connection cnx = null;
         Statement stm = null;
         ResultSet rs = null;

         try {
             cnx = cn.Conectar();
             stm = cnx.createStatement();
             rs = stm.executeQuery("select codigo_producto, producto from tb_producto");

             while (rs.next()) {
            	 productos.put((String) rs.getString("producto"), (String) rs.getString("codigo_producto"));
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
    }
    
    private void CargarUsuarios() {
   	 Connection cnx = null;
        Statement stm = null;
        ResultSet rs = null;

        try {
            cnx = cn.Conectar();
            stm = cnx.createStatement();
            rs = stm.executeQuery("select dni, CONCAT(nombre, ' ', ap_paterno) as nombre from tb_personal");

            while (rs.next()) {
           	 usuarios.put((String) rs.getString("nombre"), (String) rs.getString("dni"));
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
   }
    
	public static void main(String[] args) {
		viewOrdenes orden = new viewOrdenes();
		orden.setVisible(true);
	}

}