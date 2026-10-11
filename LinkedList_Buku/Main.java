import java.util.Scanner;

/**
 * Program pengelolaan daftar buku perpustakaan (Single Linked List).
 *
 * Mode interaktif : java Main
 * Mode demo/uji   : java Main demo   (menjalankan skenario uji otomatis di main())
 */
public class Main {
    static final int MAKS_KODE = 5;     // kode buku maksimal 5 karakter
    static final int MIN_DATA = 5;      // acuan minimal jumlah data (lihat README)

    public static void main(String[] args) {
        LinkedList daftar = new LinkedList();
        if (args.length > 0 && args[0].equalsIgnoreCase("demo")) {
            demo(daftar);
            return;
        }
        Scanner sc = new Scanner(System.in);
        jalankanMenu(daftar, sc);
        sc.close();
    }

    /** Loop menu utama. */
    static void jalankanMenu(LinkedList daftar, Scanner sc) {
        boolean jalan = true;
        while (jalan) {
            System.out.println();
            System.out.println("===== SISTEM DATA BUKU =====");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Cari Buku");
            System.out.println("4. Lihat Semua Buku");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            if (!sc.hasNextLine()) {   // input habis (mis. piped input)
                break;
            }
            String pilihan = sc.nextLine().trim();
            System.out.println();
            switch (pilihan) {
                case "1": tambahBuku(daftar, sc); break;
                case "2": hapusBuku(daftar); break;
                case "3": cariBuku(daftar, sc); break;
                case "4": tampilBuku(daftar); break;
                case "5":
                    System.out.println("Terima kasih. Program selesai.");
                    jalan = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Masukkan angka 1-5.");
            }
        }
    }

    /** Menu 1: tambah buku di akhir daftar dengan validasi input. */
    static void tambahBuku(LinkedList daftar, Scanner sc) {
        System.out.print("Masukkan Kode Buku: ");
        String kode = bacaBaris(sc);
        if (kode.isEmpty()) {
            System.out.println("Gagal: kode buku tidak boleh kosong!");
            return;
        }
        if (kode.length() > MAKS_KODE) {
            System.out.println("Gagal: kode buku maksimal " + MAKS_KODE
                    + " karakter (input: " + kode.length() + " karakter)!");
            return;
        }
        if (daftar.cari(kode) != null) {
            System.out.println("Gagal: kode buku '" + kode + "' sudah digunakan!");
            return;
        }
        System.out.print("Masukkan Judul: ");
        String judul = bacaBaris(sc);
        if (judul.isEmpty()) {
            System.out.println("Gagal: judul tidak boleh kosong!");
            return;
        }
        System.out.print("Masukkan Penulis: ");
        String penulis = bacaBaris(sc);
        if (penulis.isEmpty()) {
            System.out.println("Gagal: penulis tidak boleh kosong!");
            return;
        }
        daftar.tambah(kode, judul, penulis);
        System.out.println("Data berhasil ditambahkan!");
        if (daftar.getSize() < MIN_DATA) {
            System.out.println("Catatan: baru " + daftar.getSize() + " buku, tambahkan hingga minimal "
                    + MIN_DATA + " buku.");
        }
    }

    /** Menu 2: hapus buku terakhir. */
    static void hapusBuku(LinkedList daftar) {
        Node dihapus = daftar.hapusTerakhir();
        if (dihapus == null) {
            System.out.println("Tidak ada data untuk dihapus.");
        } else {
            System.out.println("Buku terakhir berhasil dihapus: " + dihapus.kodeBuku
                    + " - " + dihapus.judul);
        }
    }

    /** Menu 3: cari buku berdasarkan kode. */
    static void cariBuku(LinkedList daftar, Scanner sc) {
        System.out.print("Masukkan Kode Buku: ");
        String kode = bacaBaris(sc);
        Node hasil = daftar.cari(kode);
        if (hasil == null) {
            System.out.println("Buku tidak ditemukan.");
        } else {
            System.out.println("Buku ditemukan!");
            System.out.println("Kode: " + hasil.kodeBuku + " | Judul: " + hasil.judul
                    + " | Penulis: " + hasil.penulis);
        }
    }

    /** Menu 4: tampilkan semua buku + total. */
    static void tampilBuku(LinkedList daftar) {
        daftar.tampilkanSemua();
        if (daftar.getSize() < MIN_DATA) {
            System.out.println("Catatan: jumlah buku masih di bawah minimal " + MIN_DATA + ".");
        }
    }

    static String bacaBaris(Scanner sc) {
        return sc.hasNextLine() ? sc.nextLine().trim() : "";
    }

    /** Demo otomatis: menguji seluruh operasi dan kondisi batas. */
    static void demo(LinkedList daftar) {
        System.out.println("=== DEMO PENGUJIAN LINKED LIST ===");
        System.out.println("\n[1] Hapus saat daftar kosong:");
        hapusBuku(daftar);

        System.out.println("\n[2] Tambah 5 buku valid (lewat tambahBuku dengan input simulasi):");
        String[][] data = {
            {"BK101", "Struktur Data", "Michael Goodrich"},
            {"BK102", "Algoritma dan Pemrograman", "Rinaldi Munir"},
            {"BK103", "Basis Data", "Abraham Silberschatz"},
            {"BK104", "Sistem Operasi", "Andrew Tanenbaum"},
            {"BK105", "Rekayasa Perangkat Lunak", "Ian Sommerville"}
        };
        for (String[] d : data) {
            tambahBuku(daftar, new Scanner(d[0] + "\n" + d[1] + "\n" + d[2] + "\n"));
        }

        System.out.println("\n[3] Validasi input tidak valid:");
        System.out.println("- Kode 6 karakter (BK1001):");
        tambahBuku(daftar, new Scanner("BK1001\nJudul\nPenulis\n"));
        System.out.println("- Kode kosong:");
        tambahBuku(daftar, new Scanner("\nJudul\nPenulis\n"));
        System.out.println("- Kode duplikat (BK101):");
        tambahBuku(daftar, new Scanner("BK101\nJudul\nPenulis\n"));
        System.out.println("- Judul kosong:");
        tambahBuku(daftar, new Scanner("BK200\n\nPenulis\n"));
        System.out.println("- Total buku tetap: " + daftar.getSize());

        System.out.println("\n[4] Tampilkan semua:");
        tampilBuku(daftar);

        System.out.println("\n[5] Cari BK103 (ditemukan):");
        Node n = daftar.cari("BK103");
        System.out.println(n != null ? "Ditemukan: " + n.judul : "Buku tidak ditemukan.");

        System.out.println("\n[6] Cari BK999 (tidak ditemukan):");
        n = daftar.cari("BK999");
        System.out.println(n != null ? "Ditemukan: " + n.judul : "Buku tidak ditemukan.");

        System.out.println("\n[7] Hapus terakhir sampai sisa satu buku, lalu kosong:");
        while (daftar.getSize() > 0) {
            hapusBuku(daftar);
            if (daftar.getSize() == 1) {
                System.out.println("  (tersisa 1 node -> hapus berikutnya menguji kasus node tunggal)");
            }
        }
        System.out.println("\n[8] Hapus saat kosong lagi:");
        hapusBuku(daftar);

        System.out.println("\n[9] Tampilkan setelah kosong:");
        tampilBuku(daftar);
        System.out.println("\n=== DEMO SELESAI ===");
    }
}
