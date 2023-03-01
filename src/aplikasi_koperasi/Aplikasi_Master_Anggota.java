/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi_koperasi;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

/**
 *
 * @author enoh
 */

public class Aplikasi_Master_Anggota extends javax.swing.JFrame {
koneksi kon = new koneksi();
private Object [][] data_anggota=null;
private String[] label={"Kode Anggota","Nama","Alamat","Tempat Lahir","Tanggal Lahir","jenis Kelamin","Tanggal Masuk"};
private String tgl_lahir;
private String tgl_masuk;
private String sql="";

    /**
     * Creates new form Aplikasi_Master_Anggota
     */
    public Aplikasi_Master_Anggota() {
        initComponents();
        kon.setKoneksi();
        BacaTabelAnggota();
    }
    
    private String idAnggota()
    {
        String no=null;
        try{
                String sql = "Select right(kd_anggota,3)+1 from anggota ";
                ResultSet rs = kon.st.executeQuery(sql);
                if      (rs.next()){
                        rs.last();
                        no = rs.getString(1);
                while   (no.length()<3){
                        no="00"+no;
                        no="KOP"+no;
                        tkodeanggota.setText(no);
                        }
                        }
                else{
                        no="KOP001";
                        tkodeanggota.setText(no);
                    }
    }catch (SQLException e){
    }return no;
    }
    
    private void BacaTabelAnggota(){
        try{
        String sql="Select *From anggota order by kd_anggota";
        kon.rs=kon.st.executeQuery(sql);
        ResultSetMetaData m=kon.rs.getMetaData();
        int kolom=m.getColumnCount();
        int baris=0;
        while(kon.rs.next()){
            baris=kon.rs.getRow();
            }
            data_anggota=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
            data_anggota[x][0]=kon.rs.getString("kd_anggota");
            data_anggota[x][1]=kon.rs.getString("nama");
            data_anggota[x][2]=kon.rs.getString("alamat");
            data_anggota[x][3]=kon.rs.getString("tempat_lahir");
            data_anggota[x][4]=kon.rs.getString("tgl_lahir");
            data_anggota[x][5]=kon.rs.getString("jenis_kelamin");
            data_anggota[x][6]=kon.rs.getString("tgl_masuk");
            x++;
            }
            tblanggota.setModel(new DefaultTableModel(data_anggota,label));
            }
            catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
            }
    }
    
    private void BacaTabelCariAnggota(){
    try{
        String sql="select *from anggota where nama like '%"
        +tcari.getText()+ "%' ";
        kon.rs=kon.st.executeQuery(sql);
        ResultSetMetaData m=kon.rs.getMetaData();
        int kolom=m.getColumnCount();
        int baris=0;
        while(kon.rs.next()){
            baris=kon.rs.getRow();
           }
           data_anggota=new Object[baris][kolom];
           int x=0;
           kon.rs.beforeFirst();
           while(kon.rs.next()){
           data_anggota[x][0]=kon.rs.getString("kd_anggota");
           data_anggota[x][1]=kon.rs.getString("nama");
           data_anggota[x][2]=kon.rs.getString("alamat");
           data_anggota[x][3]=kon.rs.getString("tempat_lahir");
           data_anggota[x][4]=kon.rs.getString("tgl_lahir");
           data_anggota[x][5]=kon.rs.getString("jenis_kelamin");
           data_anggota[x][6]=kon.rs.getString("tgl_masuk");
           x++;
           }
           tblanggota.setModel(new DefaultTableModel(data_anggota,label));
           }
           catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
    }
    }   
    
    private void setTable(){
        int row=tblanggota.getSelectedRow();
        tkodeanggota .setText((String)tblanggota.getValueAt(row,0));
        tnama        .setText((String)tblanggota.getValueAt(row,1));
        talamat      .setText((String)tblanggota.getValueAt(row,2));
        ttempat_lahir.setText((String)tblanggota.getValueAt(row,3));
    }
    
    private void Bersih()
    {
        tkodeanggota  .setText("");
        tnama         .setText("");
        talamat       .setText("");
        ttempat_lahir .setText("");
        tcari         .setText("");
    }
    
    private void aktif()
    {
        tkodeanggota  .setEnabled(true);
        tnama         .setEnabled(true);
        talamat       .setEnabled(true);
        ttempat_lahir .setEnabled(true);
        
    }
    
    private void nonaktif()
    {
        tkodeanggota  .setEnabled(false);
        tnama         .setEnabled(false);
        talamat       .setEnabled(false);
        ttempat_lahir .setEnabled(false);
        
        bt_edit       .setEnabled(false);
        bt_update     .setEnabled(false);
        bt_hapus      .setEnabled(false);
        bt_simpan     .setEnabled(false);
    }
    
    private void SimpanDataAngota(){
        String kd_anggota   = tkodeanggota.getText();
        String nama         = tnama.getText();
        String alamat       = talamat.getText();
        String tempat_lahir = ttempat_lahir.getText();
        
        String jenis_kelamin = null;
        
        if (jRadioButton1.isSelected()) {
                jenis_kelamin="Laki-Laki";
            } else if(jRadioButton2.isSelected()) {
                jenis_kelamin="Perempuan";
            }
        
        try{
            String sql="insert into anggota values('"+tkodeanggota.getText()+"','"
                                                     +tnama.getText()+"','"
                                                     +talamat.getText()+"','"
                                                     +ttempat_lahir.getText()+"','"
                                                     +  tgl_lahir +"','"
                                                     +jenis_kelamin+"','"
                                                     +  tgl_masuk +"')";
            
         kon.st.executeUpdate(sql); // untuk mengeksekusi query sql
         JOptionPane.showMessageDialog(null, "Data Berhasil Di Simpan");
         Bersih();
         BacaTabelAnggota();
        
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
   private void EditData(){
    try{
        String sql="Update anggota set kd_anggota='"
                                      +tkodeanggota.getText()
                                      +"',nama='"
                                      +tnama.getText()
                                      +"',alamat='"
                                      +talamat.getText()
                                      +"',tempat_lahir='"
                                      +ttempat_lahir.getText()
                                      +"' where kd_anggota='"+tkodeanggota.getText()+"'";
        kon.st.executeUpdate(sql);
        JOptionPane.showMessageDialog(null,"Data berhasil diupdate");
        Bersih();
        BacaTabelAnggota();
        }
        catch(SQLException e){
        JOptionPane.showMessageDialog(null,e);
        }
    }
   
   private void HapusData(){
        try {
            String sql="delete from anggota where kd_anggota='"+tkodeanggota.getText()+"'";
            kon.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data berhasi Dihapus");
            Bersih();
            BacaTabelAnggota();
        } 
        catch (SQLException e) {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tcari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblanggota = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tkodeanggota = new javax.swing.JTextField();
        tnama = new javax.swing.JTextField();
        talamat = new javax.swing.JTextField();
        ttempat_lahir = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        bt_tambah = new javax.swing.JButton();
        bt_keluar = new javax.swing.JButton();
        bt_edit = new javax.swing.JButton();
        bt_hapus = new javax.swing.JButton();
        bt_simpan = new javax.swing.JButton();
        bt_update = new javax.swing.JButton();
        bt_batal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Abyssinica SIL", 1, 24)); // NOI18N
        jLabel1.setText("MASTER DATA ANGGOTA KOPERASI");

        jLabel2.setText("Cari Nama Anggota : ");

        tcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tcariKeyTyped(evt);
            }
        });

        tblanggota.setModel(new javax.swing.table.DefaultTableModel(
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
        tblanggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblanggotaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblanggota);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Anggota"));

        jLabel3.setText("Kode Anggota");

        jLabel4.setText("Nama");

        jLabel5.setText("Alamat");

        jLabel6.setText("Tempat Lahir");

        jLabel8.setText("Jenis kelamin");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Laki-Laki");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Perempuan");

        jLabel7.setText("Tanggal Lahir");

        jLabel9.setText("Tanggal Masuk");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton2)
                    .addComponent(ttempat_lahir, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(talamat, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tnama, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tkodeanggota, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton1))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tkodeanggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(talamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ttempat_lahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(9, 9, 9)
                .addComponent(jLabel7)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addGap(13, 13, 13)
                .addComponent(jLabel9)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        bt_tambah.setText("TAMBAH");
        bt_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_tambahActionPerformed(evt);
            }
        });

        bt_keluar.setText("KELUAR");
        bt_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_keluarActionPerformed(evt);
            }
        });

        bt_edit.setText("EDIT");
        bt_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_editActionPerformed(evt);
            }
        });

        bt_hapus.setText("HAPUS");
        bt_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_hapusActionPerformed(evt);
            }
        });

        bt_simpan.setText("SIMPAN");
        bt_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_simpanActionPerformed(evt);
            }
        });

        bt_update.setText("UPDATE");
        bt_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_updateActionPerformed(evt);
            }
        });

        bt_batal.setText("BATAL");
        bt_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_batalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(42, 42, 42)
                            .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(bt_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(bt_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(bt_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(bt_update, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(bt_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(bt_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(bt_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(282, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bt_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_update, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    Bersih();
    nonaktif();
    }//GEN-LAST:event_formWindowActivated

    private void bt_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_keluarActionPerformed
        // TODO add your handling code here:
        Aplikasi_Menu_Utama xx;
        xx = new Aplikasi_Menu_Utama();
        xx.setVisible(true);
        this.dispose();  
    }//GEN-LAST:event_bt_keluarActionPerformed

    private void bt_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_tambahActionPerformed
        // TODO add your handling code here:
        
        Bersih();
        aktif();
        tkodeanggota.setText(idAnggota());
        tnama.setText("");
        talamat.setText("");
        ttempat_lahir.setText("");
        tnama.requestFocus();
        bt_batal.setEnabled(true);
        bt_tambah.setEnabled(false);
        bt_simpan.setEnabled(true);
    }//GEN-LAST:event_bt_tambahActionPerformed

    private void tblanggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblanggotaMouseClicked
        // TODO add your handling code here:
        setTable();
        bt_hapus.setEnabled(true);
        bt_edit.setEnabled(true);
        bt_tambah.setEnabled(false);
   
    }//GEN-LAST:event_tblanggotaMouseClicked

    private void tcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tcariKeyTyped
        // TODO add your handling code here:
    BacaTabelCariAnggota();
    }//GEN-LAST:event_tcariKeyTyped

    private void bt_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_batalActionPerformed
        // TODO add your handling code here:
        nonaktif();
        Bersih();
        bt_tambah.setEnabled(true);
    }//GEN-LAST:event_bt_batalActionPerformed

    private void bt_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_simpanActionPerformed
        // TODO add your handling code here:
        if (tkodeanggota.getText().isEmpty()|| tnama.getText().isEmpty()
                                            || talamat.getText().isEmpty() 
                                            || ttempat_lahir.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data Tidak Lengkap", "Konfirmasi",
            JOptionPane.INFORMATION_MESSAGE);
            bt_tambah.setEnabled(true);} 
        else {
            bt_tambah.setEnabled(true);
            bt_keluar.setEnabled(true);
            SimpanDataAngota();
            }
    }//GEN-LAST:event_bt_simpanActionPerformed

    private void bt_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_hapusActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this, "yakin mau dihapus?",
        "konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        HapusData();
        bt_tambah.setEnabled(true);} 
    else {
            JOptionPane.showMessageDialog(this, "Data Batal Dihapus",
            "Konfirmasi", JOptionPane.INFORMATION_MESSAGE);
            bt_tambah.setEnabled(true);
            return;
         }
        formWindowActivated(null);
   
    }//GEN-LAST:event_bt_hapusActionPerformed

    private void bt_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_editActionPerformed
        // TODO add your handling code here:
        aktif();
        tkodeanggota.setEnabled(false);
        bt_edit.setEnabled(false);
        bt_update.setEnabled(true);
        bt_batal.setEnabled(true);
        bt_hapus.setEnabled(false);
        bt_tambah.setEnabled(false);
    }//GEN-LAST:event_bt_editActionPerformed

    private void bt_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_updateActionPerformed
        // TODO add your handling code here:
        bt_update.setEnabled(false);
        bt_tambah.setEnabled(true);
        EditData();
    }//GEN-LAST:event_bt_updateActionPerformed

    private void jD_tgl_LahirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jD_tgl_LahirPropertyChange
        // TODO add your handling code here:
        if(jD_tgl_Lahir.getDate()!=null){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            tgl_lahir=format.format(jD_tgl_Lahir.getDate());
        }
    }//GEN-LAST:event_jD_tgl_LahirPropertyChange

    private void jD_tgl_masukPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jD_tgl_masukPropertyChange
        // TODO add your handling code here:
        if(jD_tgl_masuk.getDate()!=null){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            tgl_masuk=format.format(jD_tgl_masuk.getDate());
        }
    }//GEN-LAST:event_jD_tgl_masukPropertyChange

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
            java.util.logging.Logger.getLogger(Aplikasi_Master_Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Master_Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Master_Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Master_Anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Aplikasi_Master_Anggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_batal;
    private javax.swing.JButton bt_edit;
    private javax.swing.JButton bt_hapus;
    private javax.swing.JButton bt_keluar;
    private javax.swing.JButton bt_simpan;
    private javax.swing.JButton bt_tambah;
    private javax.swing.JButton bt_update;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField talamat;
    private javax.swing.JTable tblanggota;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tkodeanggota;
    private javax.swing.JTextField tnama;
    private javax.swing.JTextField ttempat_lahir;
    // End of variables declaration//GEN-END:variables
}
