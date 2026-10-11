import java.util.Scanner;

/**
 * Program sistem kasir toko: Queue (antrean pelanggan, FIFO) dan
 * Stack (riwayat transaksi, LIFO).
 *
 * Mode interaktif : java Main
 * Mode demo/uji   : java Main demo
 */
public class Main {

    public static void main(String[] args) {
        Queue antrian = new Queue();
        Stack riwayat = new Stack();
        if (args.length > 0 && args[0].equalsIgnoreCase("demo")) {
            demo(antrian, riwayat);
            return;
        }
        Scanner sc = new Scanner(System.in);
        jalankanMenu(antrian, riwayat, sc);
        sc.close();
    }

    /** Loop menu utama. */
    static void jalankanMenu(Queue antrian, Stack riwayat, Scanner sc) {
        boolean jalan = true;
        while (jalan) {
            System.out.println();
            System.out.println("=== SISTEM KASIR TOKO ===");
            System.out.println("1. Tambah Antrian");
            System.out.println("2. Layani Pelanggan");
            System.out.println("3. Tampilkan Antrian");
            System.out.println("4. Lihat Riwayat Transaksi");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            if (!sc.hasNextLine()) {
                break;
            }
            String pilihan = sc.nextLine().trim();
            System.out.println();
            switch (pilihan) {
                case "1": tambahAntrian(antrian, sc); break;
                case "2": layani(antrian, riwayat); break;
                case "3": antrian.display(); break;
                case "4": riwayat.display(); break;
                case "5":
                    System.out.println("Terima kasih. Program selesai.");
                    jalan = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Masukkan angka 1-5.");
            }
        }
    }

    /** Menu 1: enqueue pelanggan baru dengan validasi. */
    static void tambahAntrian(Queue antrian, Scanner sc) {
        // validasi batas jumlah data (<= 5) dilakukan sebelum meminta input
        if (antrian.isFull()) {
            System.out.println("Antrian penuh (maksimal " + Queue.MAKS
                    + " pelanggan). Layani pelanggan terlebih dahulu.");
            return;
        }
        System.out.print("Masukkan Nomor Antrian: ");
        String nomor = bacaBaris(sc);
        if (nomor.isEmpty()) {
            System.out.println("Gagal: nomor antrian tidak boleh kosong!");
            return;
        }
        if (antrian.contains(nomor)) {
            System.out.println("Gagal: nomor antrian '" + nomor + "' sudah ada di antrian!");
            return;
        }
        System.out.print("Masukkan Nama Pelanggan: ");
        String nama = bacaBaris(sc);
        if (nama.isEmpty()) {
            System.out.println("Gagal: nama pelanggan tidak boleh kosong!");
            return;
        }
        System.out.print("Masukkan Total Belanja: ");
        String teks = bacaBaris(sc);
        long total;
        try {
            total = Long.parseLong(teks);
        } catch (NumberFormatException e) {
            System.out.println("Gagal: total belanja harus berupa angka bulat!");
            return;
        }
        if (total <= 0) {
            System.out.println("Gagal: total belanja harus lebih dari 0!");
            return;
        }
        antrian.enqueue(nomor, nama, total);
        System.out.println("Data pelanggan ditambahkan ke antrian!");
    }

    /** Menu 2: dequeue pelanggan terdepan lalu push transaksinya ke Stack. */
    static void layani(Queue antrian, Stack riwayat) {
        Node pelanggan = antrian.dequeue();
        if (pelanggan == null) {
            System.out.println("Antrian kosong, tidak ada pelanggan yang dapat dilayani.");
            return;
        }
        System.out.println("Melayani pelanggan " + pelanggan.nomorAntrian
                + " (" + pelanggan.namaPelanggan + ")");
        riwayat.push(pelanggan);
        System.out.println("Transaksi disimpan ke riwayat.");
    }

    static String bacaBaris(Scanner sc) {
        return sc.hasNextLine() ? sc.nextLine().trim() : "";
    }

    /** Demo otomatis untuk menguji semua operasi dan kondisi batas. */
    static void demo(Queue antrian, Stack riwayat) {
        System.out.println("=== DEMO PENGUJIAN SISTEM KASIR ===");
        System.out.println("\n[1] Layani & riwayat saat kosong:");
        layani(antrian, riwayat);
        riwayat.display();

        System.out.println("\n[2] Tambah 5 pelanggan (batas maksimal):");
        String[][] p = {
            {"A001", "Andi", "125000"}, {"A002", "Budi", "80000"},
            {"A003", "Citra", "250500"}, {"A004", "Dewi", "47000"},
            {"A005", "Eko", "1500000"}
        };
        for (String[] d : p) {
            tambahAntrian(antrian, new Scanner(d[0] + "\n" + d[1] + "\n" + d[2] + "\n"));
        }
        antrian.display();

        System.out.println("\n[3] Tambah pelanggan ke-6 (harus ditolak):");
        tambahAntrian(antrian, new Scanner("A006\nFajar\n10000\n"));

        System.out.println("\n[4] Layani 2 pelanggan (FIFO: A001 lalu A002):");
        layani(antrian, riwayat);
        layani(antrian, riwayat);
        antrian.display();

        System.out.println("\n[5] Input tidak valid:");
        System.out.println("- Total bukan angka:");
        tambahAntrian(antrian, new Scanner("A006\nFajar\nabc\n"));
        System.out.println("- Total negatif:");
        tambahAntrian(antrian, new Scanner("A006\nFajar\n-5\n"));
        System.out.println("- Nomor duplikat (A003):");
        tambahAntrian(antrian, new Scanner("A003\nFajar\n10000\n"));
        System.out.println("- Nama kosong:");
        tambahAntrian(antrian, new Scanner("A006\n\n10000\n"));

        System.out.println("\n[6] Setelah ada slot, tambah A006 (berhasil):");
        tambahAntrian(antrian, new Scanner("A006\nFajar\n10000\n"));
        antrian.display();

        System.out.println("\n[7] Layani semua sisa pelanggan:");
        while (!antrian.isEmpty()) {
            layani(antrian, riwayat);
        }
        System.out.println("\n[8] Layani saat antrian kosong lagi:");
        layani(antrian, riwayat);
        antrian.display();

        System.out.println("\n[9] Riwayat (LIFO: A006 paling atas, A001 paling bawah):");
        riwayat.display();
        System.out.println("\n=== DEMO SELESAI ===");
    }
}
