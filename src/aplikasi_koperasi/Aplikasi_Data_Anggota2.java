/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi_koperasi;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author enoh
 */
public class Aplikasi_Data_Anggota2 extends javax.swing.JDialog {
koneksi kon= new koneksi();
public String KodeAnggota;
public Aplikasi_Master_Simpanan simpanan= null;
private Object[][] dataanggota=null;
private String[] label = {"Kode Anggota","Nama","Alamat","Tempat Lahir",
                          "Tgl Lahir","Jenis Kelamin","Tgl Masuk "};

    /**
     * Creates new form Aplikasi_Data_Anggota2
     */
    public Aplikasi_Data_Anggota2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        kon.setKoneksi();
        BacaTabelAnggota();
    }
    
    private void BacaTabelAnggota(){
            try {
                String sql ="Select * from anggota order by kd_anggota";
                kon.rs=kon.st.executeQuery(sql);
                ResultSetMetaData m=kon.rs.getMetaData();
                int kolom=m.getColumnCount();
                int baris=0;
                while(kon.rs.next()){
                    baris=kon.rs.getRow();
                }
                dataanggota=new Object[baris][kolom];
                int x=0;
                kon.rs.beforeFirst();
                while(kon.rs.next()){
                    dataanggota[x][0]=kon.rs.getString("kd_anggota");
                    dataanggota[x][1]=kon.rs.getString("nama");
                    dataanggota[x][2]=kon.rs.getString("alamat");
                    dataanggota[x][3]=kon.rs.getString("tempat_lahir");
                    dataanggota[x][4]=kon.rs.getString("tgl_lahir");
                    dataanggota[x][5]=kon.rs.getString("jenis_kelamin");
                    dataanggota[x][6]=kon.rs.getString("tgl_masuk");
                    x++;
                }
                tbl_anggota.setModel(new DefaultTableModel(dataanggota,label));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
            /*Digunakan untuk menampilkan data Anggota keseluruhan*/
}
    
    private void BacaTabelAnggota2(){
        try {
            String sql ="Select * from anggota where nama like '%" +tcari.getText()+"%'";
                kon.rs=kon.st.executeQuery(sql);
                ResultSetMetaData m=kon.rs.getMetaData();
                int kolom=m.getColumnCount();
                int baris=0;
                while(kon.rs.next()){
                    baris=kon.rs.getRow();
                }
                dataanggota=new Object[baris][kolom];
                int x=0;
                kon.rs.beforeFirst();
                while(kon.rs.next()){
                    dataanggota[x][0]=kon.rs.getString("kd_anggota");
                    dataanggota[x][1]=kon.rs.getString("nama");
                    dataanggota[x][2]=kon.rs.getString("alamat");
                    dataanggota[x][3]=kon.rs.getString("tempat_lahir");
                    dataanggota[x][4]=kon.rs.getString("tgl_lahir");
                    dataanggota[x][5]=kon.rs.getString("jenis_kelamin");
                    dataanggota[x][6]=kon.rs.getString("tgl_masuk");
                    x++;
                }
                tbl_anggota.setModel(new DefaultTableModel(dataanggota,label));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        /*Digunakan untuk menampilkan data anggota berdasarkan pencarian melalui tcari*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tcari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_anggota = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Abyssinica SIL", 0, 24)); // NOI18N
        jLabel1.setText("Data Anggota");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")), "Tabel Data Anggota"));

        jLabel2.setText("Cari Anggota");

        tcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tcariKeyTyped(evt);
            }
        });

        tbl_anggota.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_anggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_anggotaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_anggota);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(jLabel1)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tcariKeyTyped
        // TODO add your handling code here:
        BacaTabelAnggota2();
    }//GEN-LAST:event_tcariKeyTyped

    private void tbl_anggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_anggotaMouseClicked
        // TODO add your handling code here:
        int tabelAnggota     = tbl_anggota.getSelectedRow();
        simpanan.KodeAnggota = tbl_anggota.getValueAt(tabelAnggota,0).toString();
        simpanan.NamaAnggota = tbl_anggota.getValueAt(tabelAnggota,1).toString();
        this.dispose();
    }//GEN-LAST:event_tbl_anggotaMouseClicked

    /**
     * @param args the command line argumentss
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
            java.util.logging.Logger.getLogger(Aplikasi_Data_Anggota2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Data_Anggota2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Data_Anggota2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Data_Anggota2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Aplikasi_Data_Anggota2 dialog = new Aplikasi_Data_Anggota2(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_anggota;
    private javax.swing.JTextField tcari;
    // End of variables declaration//GEN-END:variables
}
