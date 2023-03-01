/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi_koperasi;

import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author enoh
 */
public class Aplikasi_Master_User extends javax.swing.JFrame {
    koneksi kon = new koneksi();
    private Object [][] datauser=null;
    private String[] label={"Kode User","Nama User","Password","Level"};

    /**
     * Creates new form Aplikasi_Master_User
     */
    public Aplikasi_Master_User() {
        initComponents();
        kon.setKoneksi();
        BacaTabelUser();
    }
    
    private void BacaTabelUser()
    {
        try{
            String sql="Select * from user order by kd_user";
            kon.rs=kon.st.executeQuery(sql);
            ResultSetMetaData m=kon.rs.getMetaData();
            int kolom=m.getColumnCount();
            int baris=0;
            while(kon.rs.next()){
                baris=kon.rs.getRow();
            }
            datauser=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
                datauser[x][0]=kon.rs.getString("kd_user");
                datauser[x][1]=kon.rs.getString("nama_user");
                datauser[x][2]=kon.rs.getString("password");
                datauser[x][3]=kon.rs.getString("level");
                x++;
            }
            tabel_user.setModel(new DefaultTableModel(datauser,label));
        }
        
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void BacaTabelUserCari(){
        try {
            String sql="Select * From user where nama_user like'%" +tcari.getText()+"%'";
            kon.rs=kon.st.executeQuery(sql);
            ResultSetMetaData m=kon.rs.getMetaData();
            int kolom=m.getColumnCount();
            int baris=0;
            while(kon.rs.next()){
                baris=kon.rs.getRow();
            }
            datauser=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while (kon.rs.next()) {
                
                datauser[x][0]=kon.rs.getString("kd_user");
                datauser[x][1]=kon.rs.getString("nama_user");
                datauser[x][2]=kon.rs.getString("password");
                datauser[x][3]=kon.rs.getString("level");
                x++;  
            }
            tabel_user.setModel(new DefaultTableModel(datauser,label));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    } 
    
    private void SetTabel()
    {
        int row=tabel_user.getSelectedRow();
        tkd_user .setText((String) tabel_user.getValueAt(row,0));
        tnama    .setText((String) tabel_user.getValueAt(row,1));
        jPasswordField1.setText((String) tabel_user.getValueAt(row,2));
        tlevel   .setText((String) tabel_user.getValueAt(row,3));
    }
    
    private void Bersih()
    {
        tkd_user .setText("");
        tnama    .setText("");
        jPasswordField1.setText("");
        tlevel   .setText("");
    }
    
    private void aktif()
    {
        tkd_user .setEnabled(true);
        tnama    .setEnabled(true);
        jPasswordField1.setEnabled(true);
        tlevel   .setEnabled(true);
    }
    
    private void nonaktif()
    {
        tkd_user .setEnabled(false);
        tnama    .setEnabled(false);
        jPasswordField1.setEnabled(false);
        tlevel   .setEnabled(false);
    }
    
    private void SimpanData()
    {
        try{
            String sql="insert into user values('"+tkd_user.getText()+"',"
                    +"'"+tnama.getText()+"','"+jPasswordField1.getText()+"',"
                    +"'"+tlevel.getText()+"')";
         kon.st.executeUpdate(sql); // untuk mengeksekusi query sql
         JOptionPane.showMessageDialog(null, "Data Berhasil Di Simpan");
         Bersih();
         BacaTabelUser();
        
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void UpdateUser()
    {
        try{
            String sql="update user set kd_user='"+tkd_user.getText()+"',"
                    +"nama_user='"+tnama.getText()+"',"
                    +"password='"+jPasswordField1.getText()+"',"
                    +"level='"+tlevel.getText()+"'where kd_user='"+tkd_user.getText()+"'";
            
             kon.st.executeUpdate(sql);
             JOptionPane.showMessageDialog(null, "Data Berhasil Di Edit");
             Bersih();
             BacaTabelUser();
        }
        
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void HapusData()
    {
        try {
            String sql="delete from user where kd_user='"+tkd_user.getText()+"'";
            kon.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data berhasi dihapus");
            Bersih();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tkd_user = new javax.swing.JTextField();
        tnama = new javax.swing.JTextField();
        tlevel = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tcari = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btTambah = new javax.swing.JButton();
        btKeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_user = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Input Data User"));

        jLabel2.setText("Kode User");

        jLabel3.setText("Nama ");

        jLabel4.setText("Password");

        jLabel5.setText("Level");

        tkd_user.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tkd_userKeyPressed(evt);
            }
        });

        tnama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tnamaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tkd_user)
                    .addComponent(tnama)
                    .addComponent(tlevel)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tkd_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tlevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel1.setText("INPUT DATA USER");

        jLabel6.setText("Search");

        tcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tcariActionPerformed(evt);
            }
        });
        tcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tcariKeyTyped(evt);
            }
        });

        btTambah.setText("TAMBAH");
        btTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTambahActionPerformed(evt);
            }
        });

        btKeluar.setText("KELUAR");
        btKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKeluarActionPerformed(evt);
            }
        });

        tabel_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_userMouseClicked(evt);
            }
        });
        tabel_user.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabel_userKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_user);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(94, 94, 94)
                                        .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(29, 29, 29)
                                    .addComponent(btKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(9, 9, 9))))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(14, 14, 14)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btTambah)
                    .addComponent(btKeluar))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tcariActionPerformed
        // TODO add your handling code here:
        kon.setKoneksi();
        BacaTabelUserCari();
    }//GEN-LAST:event_tcariActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        nonaktif();
        BacaTabelUser();
    }//GEN-LAST:event_formWindowActivated

    private void btKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKeluarActionPerformed
        // TODO add your handling code here:
        if(btKeluar.getText().equals("KELUAR"))
        {
            dispose();
            //Form Menu Utama Tampilin
        }
        
        else if(btKeluar.getText().equals("BATAL"))
        {
            Bersih();
            nonaktif();
            btKeluar.setText("KELUAR");
            btTambah.setText("TAMBAH");
        }
    }//GEN-LAST:event_btKeluarActionPerformed

    private void btTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTambahActionPerformed
        // TODO add your handling code here:
        if (btTambah.getText().equals("TAMBAH"))
        {
            btTambah.setText("SIMPAN");
            btKeluar.setText("BATAL");
            tkd_user.requestFocus();
            Bersih();
            aktif();
            
        } 
        else if (btTambah.getText().equals("SIMPAN"))
        {
            SimpanData();
            BacaTabelUser();
            btTambah.setText("TAMBAH");
            btKeluar.setText("KELUAR");
            Bersih();
            nonaktif();
        }
        else if(btTambah.getText().equals("UPDATE"))
        {
         UpdateUser();
         BacaTabelUser();
         btTambah.setText("TAMBAH");
         btKeluar.setText("KELUAR");
         Bersih();
         nonaktif();
        }
    }//GEN-LAST:event_btTambahActionPerformed

    private void tkd_userKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tkd_userKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()== KeyEvent.VK_ENTER) {
            try {
                String sql = "Select * from user where kd_user' " + tkd_user.getText()+ "'";
                kon.rs = kon.st.executeQuery(sql);
                if (kon.rs.next()) {
                    tkd_user.setEditable(false);
                    tkd_user.setText(kon.rs.getString("kd_user"));
                    tnama.setText(kon.rs.getString("nama_user"));
                    jPasswordField1.setText(kon.rs.getString("password"));
                    tlevel.setText(kon.rs.getString("level"));
                    btTambah.setText("UPDATE");
                    
                    
                } else {
                    tnama.requestFocus();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
            
        }
    }//GEN-LAST:event_tkd_userKeyPressed

    private void tnamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tnamaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jPasswordField1.requestFocus();
        }
    }//GEN-LAST:event_tnamaKeyPressed

    private void tabel_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_userMouseClicked
        // TODO add your handling code here
        SetTabel();
    }//GEN-LAST:event_tabel_userMouseClicked

    private void tabel_userKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabel_userKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            HapusData();
        }
    }//GEN-LAST:event_tabel_userKeyPressed

    private void tcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tcariKeyTyped
        // TODO add your handling code here:
        kon.setKoneksi();
        BacaTabelUserCari();
    }//GEN-LAST:event_tcariKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Master_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Master_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Master_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Master_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Aplikasi_Master_User().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btKeluar;
    private javax.swing.JButton btTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tabel_user;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tkd_user;
    private javax.swing.JTextField tlevel;
    private javax.swing.JTextField tnama;
    // End of variables declaration//GEN-END:variables
}
