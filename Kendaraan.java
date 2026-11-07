public abstract class Kendaraan {
    public static final double BIAYA_ADMIN = 10000;

    private final String platNomor;
    private String pemilik;
    private StatusServis status;
    private JenisServis jenisServis;

    public Kendaraan(String platNomor, String pemilik) {
        this.platNomor = platNomor;
        this.pemilik = pemilik;
        this.status = StatusServis.MENUNGGU;
        this.jenisServis = null;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public String getPemilik() {
        return pemilik;
    }

    public void setPemilik(String pemilik) {
        if (pemilik != null && !pemilik.trim().isEmpty()) {
            this.pemilik = pemilik;
        }
    }

    public StatusServis getStatus() {
        return status;
    }

    protected void setStatus(StatusServis status) {
        this.status = status;
    }

    public JenisServis getJenisServis() {
        return jenisServis;
    }

    public void setJenisServis(JenisServis jenisServis) {
        this.jenisServis = jenisServis;
    }

    public void tampilkanInfo() {
        System.out.println("Plat Nomor  : " + platNomor);
        System.out.println("Pemilik     : " + pemilik);
        System.out.println("Status      : " + status);
        System.out.println("Jenis Servis: " + (jenisServis != null ? jenisServis.getLabel() : "-"));
    }

    public abstract boolean ubahStatus(StatusServis statusBaru);

    public abstract double hitungBiaya();
}
