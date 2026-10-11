/**
 * Stack riwayat transaksi berbasis linked list dengan prinsip LIFO.
 * push dan pop bekerja di sisi top, keduanya O(1).
 */
public class Stack {
    private Node top; // transaksi terbaru
    private int size;

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Simpan transaksi. Yang disimpan adalah SALINAN data (node baru), bukan
     * node milik Queue, supaya pointer next Queue/Stack tidak saling merusak.
     */
    public void push(Node transaksi) {
        Node salinan = new Node(transaksi.nomorAntrian, transaksi.namaPelanggan,
                transaksi.totalBelanja);
        salinan.next = top;
        top = salinan;
        size++;
    }

    /** Ambil transaksi paling atas. @return null jika kosong. */
    public Node pop() {
        if (top == null) {
            return null;
        }
        Node keluar = top;
        top = top.next;
        keluar.next = null;
        size--;
        return keluar;
    }

    /** Tampilkan riwayat dari terbaru (paling atas) ke terlama. */
    public void display() {
        System.out.println("Riwayat Transaksi (terbaru -> terlama):");
        if (top == null) {
            System.out.println("(Belum ada riwayat transaksi)");
        }
        int no = 1;
        for (Node n = top; n != null; n = n.next) {
            System.out.println(no++ + ". " + n.nomorAntrian + " | " + n.namaPelanggan
                    + " | " + n.rupiah());
        }
        System.out.println("Total transaksi: " + size);
    }
}
