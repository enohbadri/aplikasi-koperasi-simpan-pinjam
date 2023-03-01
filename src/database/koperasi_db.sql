-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 25, 2018 at 06:21 
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `koperasi_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `anggota`
--

CREATE TABLE `anggota` (
  `kd_anggota` varchar(10) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `tempat_lahir` varchar(25) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `jenis_kelamin` char(9) NOT NULL,
  `tgl_masuk` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `anggota`
--

INSERT INTO `anggota` (`kd_anggota`, `nama`, `alamat`, `tempat_lahir`, `tgl_lahir`, `jenis_kelamin`, `tgl_masuk`) VALUES
('KOP-0001', 'Enoh Badri', 'Telagasari', 'Karawang', '2018-05-03', 'Laki-Laki', '2018-05-04'),
('KOP-00010', 'Arya Rifai', 'Karawang', 'Bandung', '2018-06-07', 'Laki-Laki', '2018-06-13'),
('KOP-00012', 'Abdul Aziz Aminudin ', 'Linggarsari', 'Karawang', '2018-06-01', 'Laki-Laki', '2018-06-23'),
('KOP-0002', 'Saadudin Kamal', 'Palumbonsari', 'Cirebon', '2018-05-04', 'Laki-Laki', '2018-05-18'),
('KOP-0003', 'Hefi Agung Wibowo', 'Majalengka', 'Jakarta', '2018-05-10', 'Laki-Laki', '2018-05-18'),
('KOP-0004', 'Lilis Siti Nurhasanah', 'Karawang', 'Garut', '2018-05-08', 'Perempuan', '2018-05-12'),
('KOP-0005', 'Marisa Aprianti', 'Karawang', 'Jakarta', '2018-05-12', 'Perempuan', '2018-05-12'),
('KOP-0006', 'Fazri Aditiya Pratama', 'Karawang', 'Jakarta', '2018-05-11', 'Laki-Laki', '2018-05-19'),
('KOP-0007', 'Dinar Rustian', 'Lamaran', 'Garut', '2018-05-05', 'Laki-Laki', '2018-05-25'),
('KOP-0008', 'Arif Saiful Rohman', 'Karawang', 'Tasik', '2018-05-12', 'Laki-Laki', '2018-05-14'),
('KOP-0009', 'Dinar', 'Karawang', 'Garut', '2002-05-10', 'Laki-Laki', '2018-05-11'),
('KOP0010', 'N Imas Mulyani', 'Karawang', 'Linggarsari', '2018-06-15', 'Perempuan', '2018-06-05'),
('KOP0011', 'Nur Azizah', 'Karawang', 'Linggarsari', '2018-06-02', 'Perempuan', '2018-06-29');

-- --------------------------------------------------------

--
-- Table structure for table `angsuran`
--

CREATE TABLE `angsuran` (
  `no_angsuran` varchar(10) NOT NULL,
  `kd_pinjam` varchar(10) NOT NULL,
  `tgl_angsuran` date NOT NULL,
  `sisa_angsuran` double NOT NULL,
  `total_jasa` double NOT NULL,
  `piutang` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `detailpinjaman`
--

CREATE TABLE `detailpinjaman` (
  `notransaksi` varchar(10) NOT NULL,
  `tanggal` date NOT NULL,
  `jumlah_pinjam` float NOT NULL,
  `lama` int(11) NOT NULL,
  `angsuran` float NOT NULL,
  `kd_anggota` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detailpinjaman`
--

INSERT INTO `detailpinjaman` (`notransaksi`, `tanggal`, `jumlah_pinjam`, `lama`, `angsuran`, `kd_anggota`) VALUES
('PN-1802515', '2018-06-05', 5000000, 10, 120000, ''),
('PN-1802516', '2018-06-07', 5000000, 10, 120000, 'KOP-0001'),
('', '2018-06-21', 1000000, 5, 0, 'KOP-0001');

-- --------------------------------------------------------

--
-- Table structure for table `pinjaman`
--

CREATE TABLE `pinjaman` (
  `notransaksi` varchar(10) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `tanggal` date NOT NULL,
  `jumlah_pinjam` float NOT NULL,
  `lama` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sementara`
--

CREATE TABLE `sementara` (
  `kd_anggota` varchar(10) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `alamat` varchar(40) NOT NULL,
  `jumlah_pinjam` float NOT NULL,
  `lama` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `simpanan`
--

CREATE TABLE `simpanan` (
  `kd_simpan` varchar(10) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `kd_anggota` varchar(10) NOT NULL,
  `jenis_simpan` varchar(15) NOT NULL,
  `besar_simpan` float NOT NULL,
  `sukarela` float NOT NULL,
  `tgl_simpan` date NOT NULL,
  `keterangan` text CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `simpanan`
--

INSERT INTO `simpanan` (`kd_simpan`, `kd_anggota`, `jenis_simpan`, `besar_simpan`, `sukarela`, `tgl_simpan`, `keterangan`) VALUES
('SIM-00010', '', 'Pokok', 45000, 50000, '2018-05-05', 'Nabung Buat Lebaran'),
('SIM-00012', '', 'Pokok', 45000, 50000, '2018-06-08', 'ok'),
('SIM-0008', '', 'Pokok', 45000, 50000, '2018-05-04', 'ok'),
('SIM-0009', '', 'Wajib', 100000, 100000, '2018-05-19', 'ok'),
('SIM0010', 'KOP-0001', 'Pokok', 45000, 50000, '2018-06-07', 'OK'),
('SIM0011', 'KOP-0002', 'Wajib', 100000, 70000, '2018-06-02', 'LUNAS'),
('SIM0012', 'KOP-0003', 'Pokok', 45000, 60000, '2018-06-08', 'OK'),
('SIM0013', 'KOP-0001', 'Wajib', 100000, 70000, '2018-06-01', 'ok'),
('SIM0014', 'KOP-00012', 'Pokok', 45000, 45000, '2018-06-09', 'OK'),
('SIM0015', 'KOP-0006', 'Wajib', 100000, 50000, '2018-06-15', 'OK'),
('SIM0016', 'KOP-00012', 'Pokok', 45000, 60000, '2018-06-15', 'OK');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `kd_user` varchar(10) NOT NULL,
  `nama_user` varchar(25) NOT NULL,
  `password` varchar(8) NOT NULL,
  `level` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`kd_user`, `nama_user`, `password`, `level`) VALUES
('ENB', 'Enoh Badri', '000111', '1'),
('AFA', 'Alya Azeza', '000222', '2'),
('SAD', 'Saadudin Kamal', '000333', '1'),
('HAW', 'Hefi Agung Wibowo', '000111', '1'),
('MSA', 'Marisa Apriani', '000222', '1'),
('FAP', 'Fazri Aditya Pratama', '000444', '1'),
('LSN', 'Lilis Siti Nurhasanah', '000555', '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`kd_anggota`);

--
-- Indexes for table `pinjaman`
--
ALTER TABLE `pinjaman`
  ADD PRIMARY KEY (`notransaksi`);

--
-- Indexes for table `simpanan`
--
ALTER TABLE `simpanan`
  ADD PRIMARY KEY (`kd_simpan`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
