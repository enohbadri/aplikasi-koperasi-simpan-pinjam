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
import java.util.Date;
import java.awt.event.KeyEvent;
import java.util.HashMap;
/**
 *
 * @author enoh
 */
public class Aplikasi_Master_Simpanan extends javax.swing.JFrame {
koneksi kon = new koneksi();
private Object [][] data_simpanan=null;
private String[] label={"Kode Simpanan","Kode anggota","Jenis Simpanan","Besar Simpanan","Sukarela","Tanggal Simpanan","Keterangan"};
private String tgl_simpan;
private String sql="";    

    /**
     * Creates new form Aplikasi_Master_Simpanan
     */
    public String KodeAnggota;
    public String NamaAnggota;
    
    public String getKodeAnggota(){
            return KodeAnggota;
        }
    public String getNamaAnggota(){
            return NamaAnggota;
        }
    
    public Aplikasi_Master_Simpanan() {
        initComponents();
        kon.setKoneksi();
        BacaTabelSimpanan();
    }
    
    private String idSimpanan()
    {
        String no=null;
        try{
                String sql = "Select right(kd_simpan,3)+1 from simpanan ";
                ResultSet rs = kon.st.executeQuery(sql);
                if      (rs.next()){
                        rs.last();
                        no = rs.getString(1);
                while   (no.length()<3){
                        no="00"+no;
                        no="SIM"+no;
                        tkodesimpan.setText(no);
                        }
                        }
                else{
                        no="SIM001";
                        tkodesimpan.setText(no);
                    }
    }catch (SQLException e){
    }return no;
    }
    
    private void BacaTabelSimpanan(){
        try{
        String sql="Select *From simpanan order by kd_simpan";
        kon.rs=kon.st.executeQuery(sql);
        ResultSetMetaData m=kon.rs.getMetaData();
        int kolom=m.getColumnCount();
        int baris=0;
        while(kon.rs.next()){
            baris=kon.rs.getRow();
            }
            data_simpanan=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
            data_simpanan[x][0]=kon.rs.getString("kd_simpan");
            data_simpanan[x][1]=kon.rs.getString("kd_anggota");
            data_simpanan[x][2]=kon.rs.getString("jenis_simpan");
            data_simpanan[x][3]=kon.rs.getString("besar_simpan");
            data_simpanan[x][4]=kon.rs.getString("Sukarela");
            data_simpanan[x][5]=kon.rs.getString("tgl_simpan");
            data_simpanan[x][6]=kon.rs.getString("keterangan");
            x++;
            }
            tbl_simpanan.setModel(new DefaultTableModel(data_simpanan,label));
            }
            catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
            }
    }
    
    private void BacaTabelCariSimpanan(){
    try{
        String sql="select *from simpanan where kd_simpan like '%"
        +tcari.getText()+ "%' ";
        kon.rs=kon.st.executeQuery(sql);
        ResultSetMetaData m=kon.rs.getMetaData();
        int kolom=m.getColumnCount();
        int baris=0;
        while(kon.rs.next()){
            baris=kon.rs.getRow();
           }
           data_simpanan=new Object[baris][kolom];
           int x=0;
           kon.rs.beforeFirst();
           while(kon.rs.next()){
            data_simpanan[x][0]=kon.rs.getString("kd_simpan");
            data_simpanan[x][1]=kon.rs.getString("kd_anggota");
            data_simpanan[x][2]=kon.rs.getString("jenis_simpan");
            data_simpanan[x][3]=kon.rs.getString("besar_simpan");
            data_simpanan[x][4]=kon.rs.getString("Sukarela");
            data_simpanan[x][5]=kon.rs.getString("tgl_simpan");
            data_simpanan[x][6]=kon.rs.getString("keterangan");
           x++;
           }
           tbl_simpanan.setModel(new DefaultTableModel(data_simpanan,label));
           }
           catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
    }
    }   
    
    private void setTable(){
        int row=tbl_simpanan.getSelectedRow();
                tkodesimpan.setText((String)tbl_simpanan.getValueAt(row,0));
                tkdAnggota.setText((String)tbl_simpanan.getValueAt(row,1));
                tSuka.setText((String)tbl_simpanan.getValueAt(row,1));
                tketerangan.setText((String)tbl_simpanan.getValueAt(row,1));
    }
    
    private void Bersih()
    {
        tkodesimpan    .setText("");
        tkdAnggota     .setText("");
        tSuka          .setText("");
        tketerangan    .setText("");
        tcari          .setText("");
    }
    
    private void aktif()
    {
        tkodesimpan   .setEnabled(true);
        tkdAnggota    .setEnabled(true);
        tSuka         .setEnabled(true);
        tketerangan   .setEnabled(true);
    }
    
    private void nonaktif()
    {
        tkodesimpan  .setEnabled(false);
        tkdAnggota    .setEnabled(false);
        tSuka         .setEnabled(false);
        tketerangan .setEnabled(false);
        
        bt_edit       .setEnabled(false);
        bt_update     .setEnabled(false);
        bt_hapus      .setEnabled(false);
        bt_simpan     .setEnabled(false);
    }
    
    private void SimpanDataSimpanan(){
        String kd_simpan    = tkodesimpan.getText();
        String kd_anggota   = tkdAnggota.getText();
        String keterangan   = tketerangan.getText();
        String sukarela     = tSuka.getText();
        String jenis_simpanan= null;
        String besar_simpanan= null;
        
        if(jRadioButton1.isSelected())
        {
        jenis_simpanan = "Pokok";
        besar_simpanan = "45000";
        }
        else
        if(jRadioButton2.isSelected())
         {
        jenis_simpanan = "Wajib";
        besar_simpanan ="100000";
        }                           

        try{
            String sql="insert into simpanan values('"+tkodesimpan.getText()+"','"
                                                      +tkdAnggota.getText()+"','"
                                                      +jenis_simpanan+"','"
                                                      +besar_simpanan+"','"
                                                      +tSuka.getText()+"','"
                                                      +tgl_simpan +"','"
                                                      +tketerangan.getText()+"')";
            
         kon.st.executeUpdate(sql); // untuk mengeksekusi query sql
         JOptionPane.showMessageDialog(null, "Data Berhasil Di Simpan");
         Bersih();
         BacaTabelSimpanan();
        
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void EditData(){
    try{
        String sql="Update simpanan set kd_simpan='"
                                      +tkodesimpan.getText()
                                      +"',sukarela='"
                                      +tSuka.getText()
                                      +"',keterangan='"
                                      +tketerangan.getText()
                                      +"' where kd_simpan='"+tkodesimpan.getText()+"'";
        kon.st.executeUpdate(sql);
        JOptionPane.showMessageDialog(null,"Data berhasil diupdate");
        Bersih();
        BacaTabelSimpanan();
        }
        catch(SQLException e){
        JOptionPane.showMessageDialog(null,e);
        }
    }
    
    private void HapusData(){
        try {
            String sql="delete from simpanan where kd_simpan='"+tkodesimpan.getText()+"'";
            kon.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data berhasi Dihapus");
            Bersih();
            BacaTabelSimpanan();
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void tampilDataAnggota(){
        try {
            String sql="select * from anggota where kd_anggota='" +tkdAnggota.getText()+"'";
            kon.rs=kon.st.executeQuery(sql);
            if (kon.rs.next()) {
                //tnamaanggota.setText(kon.rs.getString("nama"));
                //talamat.setText(kon.rs.getString("alamat"));
                //tjumlah_pinjam.requestFocus();
                //tlama_pinjam.requestFocus();
                tSuka.requestFocus();
                
            } else {
                JOptionPane.showMessageDialog(null, "Kode Anggota"+tkdAnggota.getText()+"Tidak Ditemukan");
            }
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

        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jlabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tketerangan = new javax.swing.JTextField();
        tkodesimpan = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        tSuka = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tkdAnggota = new javax.swing.JTextField();
        btCari = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tcari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_simpanan = new javax.swing.JTable();
        bt_tambah = new javax.swing.JButton();
        bt_simpan = new javax.swing.JButton();
        bt_edit = new javax.swing.JButton();
        bt_update = new javax.swing.JButton();
        bt_hapus = new javax.swing.JButton();
        bt_batal = new javax.swing.JButton();
        bt_keluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Input Data Simpanan"));

        jlabel.setText("Kode Simpan");

        jLabel3.setText("Jenis Simpanan");

        jLabel4.setText("Tanggal Simpan");

        jLabel6.setText("Keterangan");

        buttonGroup3.add(jRadioButton1);
        jRadioButton1.setText("Pokok = Rp 45.000");

        buttonGroup3.add(jRadioButton2);
        jRadioButton2.setText("Wajib = Rp 100.000");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Sim Sukarela");

        jLabel2.setText("Kode Anggota");

        tkdAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tkdAnggotaActionPerformed(evt);
            }
        });

        btCari.setText("jButton1");
        btCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCariActionPerformed(evt);
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
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jlabel))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tkodesimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tketerangan)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(tkdAnggota)
                            .addGap(18, 18, 18)
                            .addComponent(btCari, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tSuka)))
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlabel)
                        .addGap(44, 44, 44))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tkodesimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tkdAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(btCari))))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addGap(8, 8, 8)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tSuka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tketerangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel7.setText("MASTER DATA SIMPANAN ANGGOTA");

        jLabel8.setText("Search Kode Simpanan");

        tcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tcariKeyTyped(evt);
            }
        });

        tbl_simpanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tbl_simpanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_simpananMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_simpanan);

        bt_tambah.setText("TAMBAH");
        bt_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_tambahActionPerformed(evt);
            }
        });

        bt_simpan.setText("SIMPAN");
        bt_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_simpanActionPerformed(evt);
            }
        });

        bt_edit.setText("EDIT");
        bt_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_editActionPerformed(evt);
            }
        });

        bt_update.setText("UPDATE");
        bt_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_updateActionPerformed(evt);
            }
        });

        bt_hapus.setText("HAPUS");
        bt_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_hapusActionPerformed(evt);
            }
        });

        bt_batal.setText("BATAL");
        bt_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_batalActionPerformed(evt);
            }
        });

        bt_keluar.setText("KELUAR");
        bt_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_keluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(320, 320, 320)
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tcari, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(bt_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bt_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bt_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bt_update, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bt_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bt_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bt_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1)))))
                .addContainerGap(122, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(314, 314, 314))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(bt_batal, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                            .addComponent(bt_hapus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_update, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_edit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_simpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_tambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_keluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(124, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        bt_edit.setEnabled(false);
        bt_update.setEnabled(false);
        bt_hapus.setEnabled(false);
        bt_batal.setEnabled(true);
        bt_keluar.setEnabled(true);
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
        aktif();
        tkodesimpan.setText(idSimpanan());
        tkdAnggota.setText(KodeAnggota);
        tSuka.setText("");
        tketerangan.setText("");
        bt_tambah.setEnabled(false);
        bt_batal.setEnabled(true);
        bt_simpan.setEnabled(true);
    }//GEN-LAST:event_bt_tambahActionPerformed

    private void tbl_simpananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_simpananMouseClicked
        // TODO add your handling code here:
        setTable();
        bt_hapus.setEnabled(true);
        bt_edit.setEnabled(true);
        bt_tambah.setEnabled(false);
    }//GEN-LAST:event_tbl_simpananMouseClicked

    private void tcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tcariKeyTyped
        // TODO add your handling code here:
        BacaTabelCariSimpanan();
    }//GEN-LAST:event_tcariKeyTyped

    private void bt_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_batalActionPerformed
        // TODO add your handling code here:
        nonaktif();
        
        Bersih();
        bt_tambah.setEnabled(true);
    }//GEN-LAST:event_bt_batalActionPerformed

    private void bt_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_simpanActionPerformed
        // TODO add your handling code here:
        if (tkodesimpan.getText().isEmpty() 
                                           || tSuka.getText().isEmpty()
                                           || tketerangan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data Tidak Lengkap,"
                                              + " Silahkan Lengkapi data Terlebihdahulu", "Konfirmasi",
            JOptionPane.INFORMATION_MESSAGE);
            bt_tambah.setEnabled(true);} 
        else {
            bt_tambah.setEnabled(true);
            bt_keluar.setEnabled(true);
            SimpanDataSimpanan();
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
        tkodesimpan.setEnabled(false);
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

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        // TODO add your handling code here:
        if(jDateChooser1.getDate()!=null){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            tgl_simpan=format.format(jDateChooser1.getDate());
            tSuka.requestFocus();
        }
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void btCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCariActionPerformed
        // TODO add your handling code here:
        boolean closable = true;
        Aplikasi_Data_Anggota2 dataAnggota = new Aplikasi_Data_Anggota2(null, closable);
        dataAnggota.simpanan= this;
        dataAnggota.setVisible(true);
        dataAnggota.setResizable(true);
        tkdAnggota.setText(KodeAnggota);
        bt_simpan.setEnabled(true);
    }//GEN-LAST:event_btCariActionPerformed

    private void tkdAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tkdAnggotaActionPerformed
        // TODO add your handling code here:
        tampilDataAnggota();
    }//GEN-LAST:event_tkdAnggotaActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Aplikasi_Master_Simpanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Master_Simpanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Master_Simpanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Master_Simpanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Aplikasi_Master_Simpanan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCari;
    private javax.swing.JButton bt_batal;
    private javax.swing.JButton bt_edit;
    private javax.swing.JButton bt_hapus;
    private javax.swing.JButton bt_keluar;
    private javax.swing.JButton bt_simpan;
    private javax.swing.JButton bt_tambah;
    private javax.swing.JButton bt_update;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlabel;
    private javax.swing.JTextField tSuka;
    private javax.swing.JTable tbl_simpanan;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tkdAnggota;
    private javax.swing.JTextField tketerangan;
    private javax.swing.JTextField tkodesimpan;
    // End of variables declaration//GEN-END:variables
}
