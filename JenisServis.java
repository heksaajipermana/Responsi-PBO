public enum JenisServis {
    GANTI_OLI(50000, "Ganti Oli"),
    SERVIS_RINGAN(100000, "Servis Ringan"),
    SERVIS_LENGKAP(200000, "Servis Lengkap");

    private final double harga;
    private final String label;

    JenisServis(double harga, String label) {
        this.harga = harga;
        this.label = label;
    }

    public double getHarga() {
        return harga;
    }

    public String getLabel() {
        return label;
    }
}
