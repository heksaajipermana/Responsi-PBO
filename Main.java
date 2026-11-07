import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Kendaraan> daftarKendaraan = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int pilihan;
        do {
            tampilkanMenu();
            pilihan = bacaInt("Pilih menu: ");
            switch (pilihan) {
                case 1: registrasiMotor(); break;
                case 2: tampilkanDataMotor(); break;
                case 3: mulaiServis(); break;
                case 4: selesaikanServis(); break;
                case 5: hitungBiayaServis(); break;
                case 0: System.out.println("Terima kasih. Program selesai."); break;
                default: System.out.println("Pilihan tidak valid."); break;
            }
        } while (pilihan != 0);
    }

    static void tampilkanMenu() {
        System.out.println("========================================");
        System.out.println("       SISTEM BENGKEL MOTOR");
        System.out.println("========================================");
        System.out.println("1. Registrasi Motor");
        System.out.println("2. Tampilkan Data Motor");
        System.out.println("3. Mulai Servis");
        System.out.println("4. Selesaikan Servis");
        System.out.println("5. Hitung Biaya Servis");
        System.out.println("0. Keluar");
        System.out.println("========================================");
    }

    /* ---------- F1: Registrasi Motor ---------- */
    static void registrasiMotor() {
        System.out.println("\n--- Registrasi Motor ---");
        String plat = bacaTeks("Masukkan plat nomor: ");
        if (plat == null || plat.trim().isEmpty()) {
            System.out.println("Gagal: plat nomor tidak boleh kosong.");
            return;
        }
        plat = normalisasiPlat(plat);

        String pemilik = bacaTeks("Masukkan nama pemilik: ");
        if (pemilik == null || pemilik.trim().isEmpty()) {
            System.out.println("Gagal: nama pemilik tidak boleh kosong.");
            return;
        }

        if (cariKendaraan(plat) != null) {
            System.out.println("Gagal: plat nomor " + plat + " sudah terdaftar.");
            return;
        }

        System.out.println("Pilih jenis motor: 1 = Matic, 2 = Sport");
        int jenis = bacaInt("Pilihan: ");
        Kendaraan motor = buatMotor(plat, pemilik, jenis);

        if (motor != null) {
            daftarKendaraan.add(motor);
            System.out.println("Motor berhasil didaftarkan dengan status MENUNGGU.");
        }
    }

    static Kendaraan buatMotor(String plat, String pemilik, int jenis) {
        int defaultCC = (jenis == 1) ? 110 : 150;
        System.out.print("Masukkan kapasitas CC (kosongkan untuk default " + defaultCC + "): ");
        String ccStr = input.nextLine().trim();
        if (ccStr.isEmpty()) {
            return (jenis == 1) ? new MotorMatic(plat, pemilik)
                                : new MotorSport(plat, pemilik);
        }
        try {
            int cc = Integer.parseInt(ccStr);
            if (cc <= 0) {
                System.out.println("Gagal: CC harus angka positif.");
                return null;
            }
            return (jenis == 1)
                    ? new MotorMatic(plat, pemilik, cc)
                    : new MotorSport(plat, pemilik, cc);
        } catch (NumberFormatException e) {
            System.out.println("Gagal: CC bukan angka yang valid.");
            return null;
        }
    }

    /* ---------- F2: Tampilkan Data Motor ---------- */
    static void tampilkanDataMotor() {
        System.out.println("\n--- Data Motor ---");
        if (daftarKendaraan.isEmpty()) {
            System.out.println("Belum ada motor terdaftar.");
            return;
        }
        int i = 1;
        for (Kendaraan k : daftarKendaraan) {
            System.out.println("[" + i + "]");
            k.tampilkanInfo();
            System.out.println("----------------");
            i++;
        }
    }

    /* ---------- F3: Mulai Servis ---------- */
    static void mulaiServis() {
        System.out.println("\n--- Mulai Servis ---");
        String plat = bacaTeks("Masukkan plat nomor: ");
        Kendaraan k = cariMotor(plat);
        if (k == null) return;
        System.out.println("Pilih jenis servis: 1 = Ganti Oli, 2 = Servis Ringan, 3 = Servis Lengkap");
        int pilih = bacaInt("Pilihan: ");
        JenisServis jenis;
        switch (pilih) {
            case 1: jenis = JenisServis.GANTI_OLI; break;
            case 2: jenis = JenisServis.SERVIS_RINGAN; break;
            case 3: jenis = JenisServis.SERVIS_LENGKAP; break;
            default:
                System.out.println("Gagal: pilihan jenis servis tidak valid.");
                return;
        }
        DapatDiservis s = (DapatDiservis) k;
        if (s.mulaiServis(jenis)) {
            System.out.println("Servis dimulai. Status: SEDANG_SERVIS.");
        } else {
            System.out.println("Gagal: tidak dapat memulai servis (status saat ini: " + k.getStatus() + ").");
        }
    }

    /* ---------- F4: Selesaikan Servis ---------- */
    static void selesaikanServis() {
        System.out.println("\n--- Selesaikan Servis ---");
        String plat = bacaTeks("Masukkan plat nomor: ");
        Kendaraan k = cariMotor(plat);
        if (k == null) return;
        DapatDiservis s = (DapatDiservis) k;
        if (s.selesaiServis()) {
            System.out.println("Servis selesai. Status: SELESAI.");
        } else {
            System.out.println("Gagal: tidak dapat menyelesaikan servis (status saat ini: " + k.getStatus() + ").");
        }
    }

    /* ---------- F5: Hitung Biaya Servis ---------- */
    static void hitungBiayaServis() {
        System.out.println("\n--- Hitung Biaya Servis ---");
        String plat = bacaTeks("Masukkan plat nomor: ");
        Kendaraan k = cariMotor(plat);
        if (k == null) return;
        if (k.getStatus() != StatusServis.SELESAI) {
            System.out.println("Gagal: biaya hanya boleh dihitung setelah status SELESAI (status saat ini: " + k.getStatus() + ").");
            return;
        }
        double biaya = k.hitungBiaya();
        System.out.println("Plat Nomor  : " + k.getPlatNomor());
        System.out.println("Jenis Motor : " + (k instanceof MotorMatic ? "Motor Matic" : "Motor Sport"));
        System.out.println("Jenis Servis: " + (k.getJenisServis() != null ? k.getJenisServis().getLabel() : "-"));
        System.out.println("Biaya Admin : Rp " + formatRupiah((int) Kendaraan.BIAYA_ADMIN));
        System.out.println("Total       : Rp " + formatRupiah((int) biaya));
    }

    /* ---------- Helper umum ---------- */
    static Kendaraan cariMotor(String plat) {
        if (daftarKendaraan.isEmpty()) {
            System.out.println("Belum ada motor terdaftar.");
            return null;
        }
        Kendaraan k = cariKendaraan(plat);
        if (k == null) {
            System.out.println("Motor dengan plat " + plat + " tidak ditemukan.");
        }
        return k;
    }

    static Kendaraan cariKendaraan(String plat) {
        String target = normalisasiPlat(plat);
        for (Kendaraan k : daftarKendaraan) {
            if (normalisasiPlat(k.getPlatNomor()).equals(target)) {
                return k;
            }
        }
        return null;
    }

    static int bacaInt(String prompt) {
        System.out.print(prompt);
        String teks = input.nextLine().trim();
        try {
            return Integer.parseInt(teks);
        } catch (NumberFormatException e) {
            System.out.println("Input bukan angka yang valid.");
            return -1;
        }
    }

    static String bacaTeks(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    static String normalisasiPlat(String plat) {
        return plat.trim().toUpperCase();
    }

    static String formatRupiah(long nilai) {
        String s = Long.toString(nilai);
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (i > 0 && (len - i) % 3 == 0) {
                sb.append(".");
            }
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }
}
