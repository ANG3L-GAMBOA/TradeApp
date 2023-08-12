package guia3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap; //creacion de diccionarios

import clases.Producto;
import conexion.ConexionMySQL;

public class viewProducto extends JFrame implements ActionListener {
    private JLabel lbl_titulo;
    private JLabel lbl1, lbl2, lbl3, lbl4, lbl5;

    private JTextField txt_codigo, txt_producto, txt_costo;

    private JComboBox cbo_marca;
    private JComboBox cbo_categoria;

    
    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;
    
    private JTable tb;
    private JScrollPane scp;

    private ConexionMySQL cn = new ConexionMySQL();
    private HashMap<String, String> marcas= new HashMap<String, String>();
    private HashMap<String, String> categorias= new HashMap<String, String>();

    // Constructor
    public viewProducto() {
        super();
        CargarMarcas();
        CargarCategorias();
        ConfigurarVentana();
        IniciarControles();
        MostrarDatos();
    }

    private void ConfigurarVentana() {
        this.setTitle("SENATI - ETI");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void IniciarControles() {
    	
        lbl_titulo = new JLabel();
        lbl_titulo.setText("Productos");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 20));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(10, 10, 300, 25);

        lbl1 = new JLabel();
        lbl1.setText("Código");
        lbl1.setBounds(10, 40, 120, 25);
        //CAMBIOS AQUI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! txt_codigo = new JTextField();
    	txt_codigo = new JTextField();
    	txt_codigo.setBounds(130, 40, 80, 25);  	
            
        lbl2 = new JLabel();
        lbl2.setText("Producto");
        lbl2.setBounds(10, 70, 120, 25);

        txt_producto = new JTextField();
        txt_producto.setBounds(130, 70, 150, 25);

        lbl3 = new JLabel();
        lbl3.setText("Costo");
        lbl3.setBounds(10, 100, 120, 25);

        txt_costo = new JTextField();
        txt_costo.setBounds(130, 100, 80, 25);

        lbl4 = new JLabel();
        lbl4.setText("Marca");
        lbl4.setBounds(10, 130, 120, 25);        
        
        cbo_marca = new JComboBox();
        cbo_marca.setBounds(130, 130, 80, 25);
        
        for (String mar : marcas.keySet()) {
            cbo_marca.addItem(mar);
        }
        
        cbo_marca.setSelectedIndex(-1);
        
        lbl5 = new JLabel();
        lbl5.setText("Categoría");
        lbl5.setBounds(10, 160, 120, 25);
             
        
        cbo_categoria = new JComboBox();
        cbo_categoria.setBounds(130, 160, 80, 25);

        for (String cat : categorias.keySet()) {
            cbo_categoria.addItem(cat);
        }
        
        cbo_categoria.setSelectedIndex(-1);
        
        btn_nuevo = new JButton();
        btn_nuevo.setText("NUEVO");
        btn_nuevo.setBounds(10, 190, 90, 25);
        btn_nuevo.addActionListener(this);

        btn_agregar = new JButton();
        btn_agregar.setText("AGREGAR");
        btn_agregar.setBounds(110, 190, 90, 25);
        btn_agregar.addActionListener(this);

        btn_editar = new JButton();
        btn_editar.setText("EDITAR");
        btn_editar.setBounds(210, 190, 90, 25);
        btn_editar.addActionListener(this);

        btn_borrar = new JButton();
        btn_borrar.setText("BORRAR");
        btn_borrar.setBounds(310, 190, 90, 25);
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
        scp.setBounds(10, 230, 480, 150);

        // Agregar los controles al JFrame
        this.add(lbl_titulo);
        this.add(lbl1);
        this.add(txt_codigo);
        this.add(lbl2);
        this.add(txt_producto);
        this.add(lbl3);
        this.add(txt_costo);
        this.add(lbl4);
        this.add(cbo_marca);
        this.add(lbl5);
        this.add(cbo_categoria);
        this.add(btn_nuevo);
        this.add(btn_agregar);
        this.add(btn_editar);
        this.add(btn_borrar);
        this.add(btn_cerrar);
        this.add(scp);

        ControladorTxt ctxt = new ControladorTxt();

        txt_codigo.addKeyListener(ctxt);
        txt_producto.addKeyListener(ctxt);
        txt_costo.addKeyListener(ctxt);     

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
            rs = stm.executeQuery("call sp_listar_producto()");

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

            if (e.getSource() == txt_codigo && txt_codigo.getText().length() == 8) {
                e.consume();
            } else if (e.getSource() == txt_producto && txt_producto.getText().length() == 20) {
                e.consume();
            } else if (e.getSource() == txt_costo && txt_costo.getText().length() == 20) {
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
                    txt_codigo.setEditable(false);

                    String cod = (String) tb.getValueAt(registro, 0);
                    String prod = (String) tb.getValueAt(registro, 1);
                    String cos = (String) Float.toString((float) tb.getValueAt(registro, 2));
                    String mar = (String) tb.getValueAt(registro, 3);
                    String cat = (String) tb.getValueAt(registro, 4);

                    // mar = marcas.get(mar); 
                    
                     // cat = categorias.get(cat);
                    
                    txt_codigo.setText(cod);
                    txt_producto.setText(prod);
                    txt_costo.setText(cos);
                    cbo_marca.setSelectedItem(mar);
                    cbo_categoria.setSelectedItem(cat);
                }
            }
    	

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_cerrar) {
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

            if (txt_producto.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Falta ingresar el producto");
                txt_producto.requestFocus();
                return;
            }

            if (txt_costo.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Falta ingresar el costo");
                txt_costo.requestFocus();
                return;
            }

            if (cbo_marca.getSelectedIndex()== -1) {
                JOptionPane.showMessageDialog(null, "Falta ingresar el código de marca");
                cbo_marca.requestFocus();
                return;
            }

            if (cbo_categoria.getSelectedIndex()==-1) {
                JOptionPane.showMessageDialog(null, "Falta ingresar el código de categoría");
                cbo_categoria.requestFocus();
                return;
            }

            Producto producto = new Producto();

            producto.setCodigo(txt_codigo.getText());
            producto.setProducto(txt_producto.getText());
            //PARA ARREGLAR EL ERROR
            producto.setCosto(txt_costo.getText());
            producto.setCodigoMarca(marcas.get(cbo_marca.getSelectedItem().toString()));
            producto.setCodigoCategoria(categorias.get(cbo_categoria.getSelectedItem().toString()));


            Connection cnx = null;
            java.sql.PreparedStatement pstm = null;

            try {
                cnx = cn.Conectar();

                String sql = "";

                if (e.getSource() == btn_agregar) {

                        sql = "INSERT INTO tb_producto VALUES (?, ?, ?, ?, ?)";

                        pstm = cnx.prepareStatement(sql);

                        pstm.setString(1, producto.getCodigo());
                        pstm.setString(2, producto.getProducto());
                        pstm.setString(3, producto.getCosto());
                        pstm.setString(4, producto.getCodigoMarca());
                        pstm.setString(5, producto.getCodigoCategoria());

                        pstm.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Registro Guardado");                   

                } else if (e.getSource() == btn_editar) {

                    sql = "UPDATE tb_producto SET producto = ?, costo = ?, producto_codigo_marca = ?, producto_codigo_categoria = ? WHERE codigo_producto = ?";

                    pstm = cnx.prepareStatement(sql);

                    pstm.setString(1, producto.getProducto());
                    pstm.setString(2, producto.getCosto());
                    pstm.setString(3, producto.getCodigoMarca());
                    pstm.setString(4, producto.getCodigoCategoria());
                    pstm.setString(5, producto.getCodigo());

                    pstm.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Registro Actualizado");

                } else if (e.getSource() == btn_borrar) {

                    int opc = JOptionPane.showConfirmDialog(null, "¿Seguro de borrar el registro?", "SENATI", JOptionPane.YES_NO_OPTION);

                    if (opc == JOptionPane.YES_OPTION) {

                        sql = "DELETE FROM tb_producto WHERE codigo_producto = ?";

                        pstm = cnx.prepareStatement(sql);

                        pstm.setString(1, producto.getCodigo());

                        pstm.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Registro Borrado");

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
        
        cbo_marca.setSelectedIndex(-1);
        cbo_categoria.setSelectedIndex(-1);
        txt_codigo.requestFocus();
    }


    private void ActivarBtn(boolean b1, boolean b2, boolean b3, boolean b4) {
        btn_nuevo.setEnabled(b1);
        btn_agregar.setEnabled(b2);
        btn_editar.setEnabled(b3);
        btn_borrar.setEnabled(b4);
    }

    public static void main(String[] args) {
        viewProducto producto = new viewProducto();
        producto.setVisible(true);
    }
    private void CargarMarcas() {
        Connection cnx = null;
        Statement stm = null;
        ResultSet rs = null;

        try {
            cnx = cn.Conectar();
            stm = cnx.createStatement();
            rs = stm.executeQuery("select codigo_marca, marca from tb_marca");

            while (rs.next()) {
                marcas.put((String) rs.getString("marca"), (String) rs.getString("codigo_marca"));
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
    private void CargarCategorias() {
        Connection cnx = null;
        Statement stm = null;
        ResultSet rs = null;

        try {
            cnx = cn.Conectar();
            stm = cnx.createStatement();
            rs = stm.executeQuery("select codigo_categoria, categoria from tb_categoria");

            while (rs.next()) {
                categorias.put((String) rs.getString("categoria"), (String) rs.getString("codigo_categoria"));
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
}


