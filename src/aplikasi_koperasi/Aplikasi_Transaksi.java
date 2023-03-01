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
//import net.sf.jasperreports.view.JasperViewer;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author enoh
 */
public class Aplikasi_Transaksi extends javax.swing.JFrame {
koneksi kon = new koneksi();
private Object[][] datasementara=null;
private String[] labelsementara={"Kode Anggota","Nama","Alamat","Jumlah Pinjam","Lama Pinjam"};
    /**
     * Creates new form Aplikasi_Transaksi
     */
    public Aplikasi_Transaksi() {
        initComponents();
        kon.setKoneksi();
        setTanggal();
        awal();
        //tjumbel.setEnabled(true);
    }
    
    public Date date=new Date();
    public SimpleDateFormat noformat=new SimpleDateFormat("YYMM");
    public String KodeAnggota;
    public String NamaAnggota;
    public String AlamatAnggota;
    public String KodeUser;
    
        public String getKodeAnggota(){
            return KodeAnggota;
        }
        public String getNamaAnggota(){
            return NamaAnggota;
        }
        public String getAlamatAnggota(){
            return AlamatAnggota;
        }
        public String getKodeUser(){
            return KodeUser;
        }
        
        private void bersih(){
           tnotrans.setText("");
           tkd_anggota.setText("");
           tnama_anggota.setText("");
           tjumlah_pinjam.setText("");
           tlama_pinjam.setText("");
           tadmin.setText("");
           langsuran.setText("");
        }
        
        private void nonaktif(){
            tnotrans.setEditable(false);
            tkd_anggota.setEditable(false);
            tnama_anggota.setEditable(false);
            tjumlah_pinjam.setEditable(false);
            tlama_pinjam.setEditable(false);
            tadmin.setEditable(false);
            //langsuran.setEditable(false);
            ttanggal.setEditable(false);
        }
        
        private void aktif(){ // Method Untuk Mengaktifkan Text Field kodebarang,Jumbel,bayar
         tkd_anggota.setEditable(true);
         tjumlah_pinjam.setEditable(true);
         tlama_pinjam.setEditable(true);
         tadmin.setEditable(true);
        }
        
        private void awal(){
            nonaktif();
        }
        
    void setTanggal(){
        java.util.Date skrg = new java.util.Date();
        java.text.SimpleDateFormat kal = new java.text.SimpleDateFormat("yyyy-MM-dd");
        ttanggal.setText(kal.format(skrg));
    }
    
    public String nomor()
    {
        String urutan = null;
        try {
            kon.rs=kon.st.executeQuery("Select right (notransaksi,3)+1 from pinjaman as nomor order by notransaksi desc");
            if (kon.rs.next()) 
            {
             urutan=kon.rs.getString(1);
             while(urutan.length()<3)
             urutan="0"+urutan;
             urutan="PN"+noformat.format(date)+urutan;
            } else 
            {
             urutan="PN"+noformat.format(date)+"001";
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return urutan;
    }
    
    private void TampilTabelSementara(){
        try {
            String sql="Select * From sementara order by kd_anggota";
            kon.rs=kon.st.executeQuery(sql);
            ResultSetMetaData m=kon.rs.getMetaData();
            int kolom=m.getColumnCount();
            int baris=0;
            while(kon.rs.next()){
                baris=kon.rs.getRow();
            }
            datasementara=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while (kon.rs.next()) {
                datasementara[x][0] = kon.rs.getString("kd_anggota");
                datasementara[x][1] = kon.rs.getString("nama");
                datasementara[x][2] = kon.rs.getString("alamat");
                datasementara[x][3] = kon.rs.getString("jumlah_pinjam");
                datasementara[x][4] = kon.rs.getString("lama");
                //datasementara[x][5] = kon.rs.getString("angsuran");
                x++;
            }
            tbpinjam.setModel(new DefaultTableModel(datasementara,labelsementara));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void tampilDataAngota(){
        try {
            String sql="select * from anggota where kd_anggota='" +tkd_anggota.getText()+"'";
            kon.rs=kon.st.executeQuery(sql);
            if (kon.rs.next()) {
                tnama_anggota.setText(kon.rs.getString("nama"));
                talamat.setText(kon.rs.getString("alamat"));
                tjumlah_pinjam.requestFocus();
                tlama_pinjam.requestFocus();
                tadmin.requestFocus();
                
            } else {
                JOptionPane.showMessageDialog(null, "Kode Anggota"+tkd_anggota.getText()+"Tidak Ditemukan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void SimpanSementara(){ //Problem Resolv
        try {
            String sql="insert into sementara values('"+tkd_anggota.getText()+"',"
                                                   +"'"+tnama_anggota.getText()+"',"
                                                   +"'"+talamat.getText()+"',"
                                                   +"'"+tjumlah_pinjam.getText()+"',"
                                                   
                                                   +"'"+tlama_pinjam.getText()+"')";
            kon.st.executeUpdate(sql);
            TampilTabelSementara();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void HapusIsiSementara() // Perhitungan angsuran
    {
        int row=tbpinjam.getSelectedRow();
        int lama;
            lama=Integer.parseInt((String)tbpinjam.getValueAt(row, 5));
            
        int adm;
            adm=Integer.parseInt((String)tbpinjam.getValueAt(row, 6));
            
        int angsuran;
            angsuran=Integer.parseInt(langsuran.getText());
       
            angsuran=lama+adm; 
            langsuran.setText(Integer.toString(angsuran));
        try {
            String sql="Delete from sementara where kd_brg='"+(String)tbpinjam.getValueAt(row, 0)+"'";
            kon.st.executeUpdate(sql);
            TampilTabelSementara();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void SimpanPinjaman(){
        try {
            String sql="insert into pinjaman values('"+tnotrans.getText()+"',"
                                                  +"'"+ttanggal.getText()+"','"
                                                  +"'"+tjumlah_pinjam.getText()+"','"
                                                  +"'"+tlama_pinjam.getText()+"')";
            kon.st.executeUpdate(sql);
            
        } catch (SQLException e) {
            System.out.println("koneksi gagal"+e.toString());
        }
    }
    
    private void simpanDetailPinjaman(){
        try {
            String detail = "insert into detailpinjaman values('" +tnotrans.getText()+"','"
                                                              +"'"+ttanggal.getText()+"','"
                                                              +"'"+tjumlah_pinjam.getText()+"','"
                                                              +"'"+tlama_pinjam.getText()+"','"
                                                              +"'"+langsuran.getText()+"','"
                                                              +"'"+tkd_anggota.getText()+"')";
            kon.st.executeUpdate(detail);
        } 
        catch (SQLException e) {
            System.out.println("koneksi gagal"+e.toString());
        }
    }
    
    private void HapusTabelSementara(){
        try {
            String sql="delete from sementara";
            kon.st.executeUpdate(sql);
            TampilTabelSementara();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void cetakStruk(){
    try {
        String file = "src/aplikasi_koperasi/Struk.jasper";
        HashMap param = new HashMap();
        param.put("notrans", tnotrans.getText());
        //JasperPrint print = JasperFillManager.fillReport(file, param, kon.setKoneksi());
        //JasperViewer.viewReport(print, false);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tkd_anggota = new javax.swing.JTextField();
        tjumlah_pinjam = new javax.swing.JTextField();
        tlama_pinjam = new javax.swing.JTextField();
        tadmin = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        ttanggal = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tnotrans = new javax.swing.JTextField();
        btcari = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbpinjam = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        btkeluar = new javax.swing.JButton();
        btbatal = new javax.swing.JButton();
        btsimpan = new javax.swing.JButton();
        bttambah = new javax.swing.JButton();
        lRP = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tnama_anggota = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        talamat = new javax.swing.JTextField();
        langsuran = new javax.swing.JLabel();
        tkodeuser = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel1.setText("PINJAMAN BARU");

        jLabel2.setText("DATA TRANSAKSI");

        jLabel6.setText("Kode Angota  :");

        jLabel8.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel8.setText("DETAIL PINJAMAN");

        jLabel9.setText("Jumlah Pinjaman :");

        jLabel10.setText("Lama Pinjam * Angsuran :");

        tkd_anggota.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                tkd_anggotaComponentAdded(evt);
            }
        });
        tkd_anggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tkd_anggotaActionPerformed(evt);
            }
        });

        tjumlah_pinjam.setToolTipText("Masukan Jumlah Uang Yang Akan Di Pinjam");

        tlama_pinjam.setToolTipText("Lama Pinjam Max 12 Bulan");

        tadmin.setToolTipText("Biaya Admin Rp 10.000");
        tadmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tadminActionPerformed(evt);
            }
        });

        jLabel14.setText("Tanggal");

        jLabel15.setText("No. Transaksi");

        btcari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_koperasi/img/Search-icon-kecil.png"))); // NOI18N
        btcari.setToolTipText("Cari Data Anggota");
        btcari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btcariMouseClicked(evt);
            }
        });
        btcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcariActionPerformed(evt);
            }
        });

        jLabel16.setText("Angsuran :");

        tbpinjam.setModel(new javax.swing.table.DefaultTableModel(
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
        tbpinjam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbpinjamKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbpinjam);

        jLabel18.setText("Adm = RP 10.000 + 10 %");

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btkeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_koperasi/img/close-icon.png"))); // NOI18N
        btkeluar.setToolTipText("KELUAR");
        btkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btkeluarActionPerformed(evt);
            }
        });

        btbatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_koperasi/img/Arrows-Undo-icon.png"))); // NOI18N
        btbatal.setToolTipText("CANCEL");
        btbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbatalActionPerformed(evt);
            }
        });

        btsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_koperasi/img/save-icon.png"))); // NOI18N
        btsimpan.setToolTipText("SIMPAN");
        btsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsimpanActionPerformed(evt);
            }
        });

        bttambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_koperasi/img/add kecil.png"))); // NOI18N
        bttambah.setToolTipText("TAMBAH");
        bttambah.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bttambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(bttambah, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bttambah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                    .addComponent(btkeluar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btsimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btbatal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        lRP.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        lRP.setText("Rp :");

        jLabel3.setText("Nama Anggota");

        jLabel4.setText("Alamat");

        langsuran.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        langsuran.setText("Angsuran");

        jLabel5.setText("User");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(241, 241, 241)
                                .addComponent(jLabel14)
                                .addGap(40, 40, 40)
                                .addComponent(ttanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(59, 59, 59)
                                .addComponent(tkodeuser, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(tjumlah_pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tlama_pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(41, 41, 41)
                                .addComponent(jLabel16))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tadmin, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addComponent(lRP)
                                .addGap(16, 16, 16)
                                .addComponent(langsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tnotrans, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(12, 12, 12)
                                .addComponent(tkd_anggota, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btcari, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(111, 111, 111)
                                .addComponent(jLabel6)))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tnama_anggota, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(talamat, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(74, 74, 74))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ttanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tkodeuser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel14)
                            .addComponent(jLabel5))))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tnotrans)
                    .addComponent(tkd_anggota)
                    .addComponent(tnama_anggota)
                    .addComponent(talamat)
                    .addComponent(btcari, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel18)
                        .addComponent(jLabel16)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tjumlah_pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tlama_pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tadmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lRP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(langsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14)
                .addComponent(jLabel8)
                .addGap(6, 6, 6)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tkd_anggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tkd_anggotaActionPerformed
        // TODO add your handling code here:
        tampilDataAngota();
    }//GEN-LAST:event_tkd_anggotaActionPerformed

    private void btcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcariActionPerformed
        // TODO add your handling code here:
        boolean closable = true;
        Aplikasi_Data_Anggota dataAnggota = new Aplikasi_Data_Anggota(null, closable);
        dataAnggota.transaksi = this;
        dataAnggota.setVisible(true);
        dataAnggota.setResizable(true);
        tkd_anggota.setText(KodeAnggota);
        tnama_anggota.setText(NamaAnggota);
        talamat.setText(AlamatAnggota);
        tjumlah_pinjam.requestFocus();
        
        /*Memanggil form Aplikasi_Data_Barang, dan memanggil nilai yg ada pada variabel
KodeBarang,
NamaBarang,
HargaBarang
kedalam
textfield
tkodebarang,
tnamabarang, dan tharga.*/
    }//GEN-LAST:event_btcariActionPerformed

    private void tkd_anggotaComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tkd_anggotaComponentAdded
        // TODO add your handling code here:
        tjumlah_pinjam.requestFocus();
        tlama_pinjam.requestFocus();
        tadmin.requestFocus();
    }//GEN-LAST:event_tkd_anggotaComponentAdded

    private void btkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btkeluarActionPerformed
        // TODO add your handling code here:
        HapusTabelSementara();
        Aplikasi_Menu_Utama xx;
        xx = new Aplikasi_Menu_Utama();
        xx.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btkeluarActionPerformed

    private void bttambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttambahActionPerformed
        // TODO add your handling code here:
       aktif();
       tkd_anggota.setEnabled(true);
       tjumlah_pinjam.setEnabled(true);
       tlama_pinjam.setEnabled(true); 
       tadmin.setEnabled(true); 
       tnotrans.setText(nomor()); 
       //Memberikan nilai kepada textfield tnotrans, yang diambil dari method nomor()
    }//GEN-LAST:event_bttambahActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        tkodeuser.setText(KodeUser);
        TampilTabelSementara();
        /*Memberi nilai kepada tkodeuser diambil dari variabel KodeUser, dan menampilkan
        tabel sementara dari method TampilTabelSementara()*/
    }//GEN-LAST:event_formWindowActivated

    private void btbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbatalActionPerformed
        // TODO add your handling code here:
        awal();
        HapusTabelSementara();
        tnotrans.setText("");
        /*Saat diklik batal, maka isi tabel sementara akan dihapus melalui method
        HapusTabelSementara, dan nomor transaksi menjadi kosong kembali.*/
    }//GEN-LAST:event_btbatalActionPerformed

    private void tbpinjamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbpinjamKeyPressed
        // TODO add your handling code here:
         if (evt.getKeyCode()== KeyEvent.VK_BACK_SPACE)
        {
            HapusIsiSementara();
        }
//Script untuk menghapus isi tabel sementara yang telah tersimpan.
    }//GEN-LAST:event_tbpinjamKeyPressed

    private void btsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsimpanActionPerformed
        // TODO add your handling code here:
        SimpanPinjaman();
        simpanDetailPinjaman();
        JOptionPane.showMessageDialog(this, "Berhasil Disimpan",
                "Informasi",JOptionPane.INFORMATION_MESSAGE);
        TampilTabelSementara();
        
        if(JOptionPane.showConfirmDialog(this, "Cetak Bukti Pinjaman?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                
        HapusTabelSementara();
        awal();
        bersih();
        tnotrans.setText("");
        TampilTabelSementara();
        }
        else
        {
            HapusTabelSementara();
            awal();
            bersih();
            tnotrans.setText("");
        }
/*Script untuk melakukan proses penyimpanan ke tabel transaksi dan detailtransaksi
melalui pemanggilan method SimpanTransaksi() dan simpanDetailTransaksi().*/
    }//GEN-LAST:event_btsimpanActionPerformed

    private void tadminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tadminActionPerformed
        // TODO add your handling code here:
        int jmlpinjam,lamapinjam,angsuran,admin,bunga;
      
              jmlpinjam =Integer.parseInt(tjumlah_pinjam.getText());
              lamapinjam=Integer.parseInt(tlama_pinjam.getText());
              admin     =Integer.parseInt(tadmin.getText());
              bunga=jmlpinjam*10/100;
              angsuran=jmlpinjam/lamapinjam+admin+bunga;
     
        langsuran.setText(Integer.toString(angsuran));
        
        SimpanSementara();
        TampilTabelSementara();
        
        int ttl=0;
        
        for (int a= 0; a < tbpinjam.getRowCount(); a++) 
        {
            int sub=Integer.parseInt((String) tbpinjam.getValueAt(a, 4));
            ttl+=sub;
        }
        
        //langsuran.setText(Integer.toString(ttl));
        
        if (JOptionPane.showConfirmDialog(this, "Mau Tambah Pinjaman?",
            "Konfirmasi",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            aktif();
        tnotrans.setText("");
        tkd_anggota.requestFocus();
        tkd_anggota.setText("");
        tnama_anggota.setText("");
        talamat.setText("");
        tjumlah_pinjam.setText("");
        tlama_pinjam.setText("");
        tadmin.setText("");
        } else {
            tjumlah_pinjam.requestFocus();
        }
    }//GEN-LAST:event_tadminActionPerformed

    private void btcariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btcariMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btcariMouseClicked

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
            java.util.logging.Logger.getLogger(Aplikasi_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Aplikasi_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Aplikasi_Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btbatal;
    private javax.swing.JButton btcari;
    private javax.swing.JButton btkeluar;
    private javax.swing.JButton btsimpan;
    private javax.swing.JButton bttambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lRP;
    private javax.swing.JLabel langsuran;
    private javax.swing.JTextField tadmin;
    private javax.swing.JTextField talamat;
    private javax.swing.JTable tbpinjam;
    private javax.swing.JTextField tjumlah_pinjam;
    private javax.swing.JTextField tkd_anggota;
    private javax.swing.JTextField tkodeuser;
    private javax.swing.JTextField tlama_pinjam;
    private javax.swing.JTextField tnama_anggota;
    private javax.swing.JTextField tnotrans;
    private javax.swing.JTextField ttanggal;
    // End of variables declaration//GEN-END:variables
}
