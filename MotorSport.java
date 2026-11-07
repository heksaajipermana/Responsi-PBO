public class MotorSport extends Kendaraan implements DapatDiservis, DapatDihitungBiaya {
    private int kapasitasCC;

    public MotorSport(String plat, String pemilik) {
        this(plat, pemilik, 150);
    }

    public MotorSport(String plat, String pemilik, int kapasitasCC) {
        super(plat, pemilik);
        if (kapasitasCC <= 0) {
            throw new IllegalArgumentException("Kapasitas CC harus lebih dari 0.");
        }
        this.kapasitasCC = kapasitasCC;
    }

    public int getKapasitasCC() {
        return kapasitasCC;
    }

    public void setKapasitasCC(int kapasitasCC) {
        if (kapasitasCC <= 0) {
            throw new IllegalArgumentException("Kapasitas CC harus lebih dari 0.");
        }
        this.kapasitasCC = kapasitasCC;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("Jenis      : Motor Sport");
        System.out.println("Kapasitas CC: " + kapasitasCC);
    }

    @Override
    public boolean ubahStatus(StatusServis baru) {
        if (getStatus() == StatusServis.MENUNGGU && baru == StatusServis.SEDANG_SERVIS) {
            setStatus(baru);
            return true;
        } else if (getStatus() == StatusServis.SEDANG_SERVIS && baru == StatusServis.SELESAI) {
            setStatus(baru);
            return true;
        } else {
            System.out.println("Transisi ditolak: status saat ini " + getStatus()
                    + " tidak dapat berubah ke " + baru + " (Motor Sport).");
            return false;
        }
    }

    @Override
    public double hitungBiaya() {
        if (getJenisServis() == null) {
            return 0;
        }
        return BIAYA_ADMIN + (getJenisServis().getHarga() * 1.5);
    }

    @Override
    public boolean mulaiServis(JenisServis jenis) {
        if (jenis == null) {
            return false;
        }
        if (ubahStatus(StatusServis.SEDANG_SERVIS)) {
            setJenisServis(jenis);
            return true;
        }
        return false;
    }

    @Override
    public boolean selesaiServis() {
        return ubahStatus(StatusServis.SELESAI);
    }
}
