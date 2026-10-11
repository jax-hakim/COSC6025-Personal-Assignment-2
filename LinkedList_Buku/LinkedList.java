/**
 * Implementasi Single Linked List buatan sendiri (tanpa java.util.LinkedList).
 * Menyimpan pointer head dan tail agar penambahan di akhir bernilai O(1).
 */
public class LinkedList {
    private Node head; // node pertama
    private Node tail; // node terakhir
    private int size;  // jumlah buku

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    /** Tambah buku di akhir daftar. Kompleksitas O(1). */
    public void tambah(String kodeBuku, String judul, String penulis) {
        Node baru = new Node(kodeBuku, judul, penulis);
        if (head == null) {      // daftar kosong: node baru jadi head sekaligus tail
            head = baru;
            tail = baru;
        } else {                 // sambungkan setelah tail lalu geser tail
            tail.next = baru;
            tail = baru;
        }
        size++;
    }

    /**
     * Hapus node terakhir (Pop). Kompleksitas O(n) karena harus mencari
     * node sebelum tail (single linked list tidak punya pointer prev).
     * @return node yang dihapus, atau null jika daftar kosong
     */
    public Node hapusTerakhir() {
        if (head == null) {
            return null;
        }
        Node dihapus = tail;
        if (head == tail) {      // kasus hanya satu node
            head = null;
            tail = null;
        } else {                 // cari node sebelum tail
            Node bantu = head;
            while (bantu.next != tail) {
                bantu = bantu.next;
            }
            bantu.next = null;
            tail = bantu;
        }
        size--;
        return dihapus;
    }

    /** Cari buku berdasarkan kode (case-insensitive). Kompleksitas O(n). */
    public Node cari(String kodeBuku) {
        Node bantu = head;
        while (bantu != null) {
            if (bantu.kodeBuku.equalsIgnoreCase(kodeBuku)) {
                return bantu;
            }
            bantu = bantu.next;
        }
        return null;
    }

    /** Tampilkan seluruh buku sesuai urutan input beserta total. */
    public void tampilkanSemua() {
        System.out.println("Daftar Buku:");
        if (head == null) {
            System.out.println("(Belum ada data buku)");
        }
        Node bantu = head;
        while (bantu != null) {
            System.out.println("Kode: " + bantu.kodeBuku + " | Judul: " + bantu.judul
                    + " | Penulis: " + bantu.penulis);
            bantu = bantu.next;
        }
        System.out.println("Total Buku: " + size);
    }
}
